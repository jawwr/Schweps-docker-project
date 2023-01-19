package com.example.consumer.repository;

import com.example.consumer.models.LinkStatusCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository {
    private static final String HASH_KEY = "Links";
    private RedisTemplate template;

    @Autowired
    public CacheRepository(RedisTemplate template) {
        this.template = template;
    }

    public void save(LinkStatusCache statusCache){
        template.opsForHash().put(HASH_KEY, statusCache.getKey(), statusCache);
    }

    public LinkStatusCache getLinkById(String key){
        return (LinkStatusCache) template.opsForHash().get(HASH_KEY, key);
    }

    public void updateLink(String key, LinkStatusCache statusCache){
        template.opsForHash().delete(HASH_KEY, key);
        save(statusCache);
    }
}
