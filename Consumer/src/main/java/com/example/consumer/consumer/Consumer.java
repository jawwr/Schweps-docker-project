package com.example.consumer.consumer;

import com.example.consumer.models.Link;
import com.example.consumer.models.LinkDto;
import com.example.consumer.models.LinkStatusCache;
import com.example.consumer.repository.CacheRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@EnableRabbit
public class Consumer {
    private CacheRepo repo;

//    @Autowired
//    public Consumer(CacheRepo repo) {
//        this.repo = repo;
//    }

    @RabbitListener(queues = "links")
    public void listener(String mess) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Link link = mapper.readValue(mess, Link.class);
//            var status = getLinkStatus(link.getUrl());
            var status = getStatusFromInternet(link.getUrl());
            updateLink(link, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLink(Link link, String status){
        var dto = new LinkDto(link.getId(), status);
        try {
            ObjectMapper mapper = new ObjectMapper();
            var stringBody = mapper.writeValueAsString(dto);
            URL url = new URL("http://127.0.0.1:8081/api/links/update");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(stringBody);
            writer.flush();
            writer.close();

            connection.connect();

            System.out.println(connection.getResponseCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLinkStatus(String url) {
        var key = "url-" + url;
        var statusCache = getStatusFromCache(key);

        if (statusCache == null) {
            var status = getStatusFromInternet(url);
            statusCache = new LinkStatusCache(key, status);
            updateStatusInCache(key, statusCache);
        }

        return statusCache.getStatus();
    }

    private LinkStatusCache getStatusFromCache(String key) {
        var status = repo.findById(key);

        return status.orElse(null);
    }

    private void updateStatusInCache(String key, LinkStatusCache statusCache) {
        repo.deleteById(key);
        repo.save(statusCache);
    }

    private String getStatusFromInternet(String url) {
        try {
            URL urlCheck = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlCheck.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            return Integer.toString(connection.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();

            return "404";
        }
    }
}
