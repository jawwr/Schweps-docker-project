package com.example.web.controller;

import com.example.web.models.Link;
import com.example.web.models.LinkDbModel;
import com.example.web.models.LinkDto;
import com.example.web.service.LinkService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links/")
public class LinkController {
    private final LinkService service;
    private final AmqpTemplate template;

    @Autowired
    public LinkController(LinkService service, AmqpTemplate template) {
        this.service = service;
        this.template = template;
    }

    @PostMapping
    public ResponseEntity<?> postLink(@RequestBody Link link) {
        try {
            return ResponseEntity.ok(service.createLink(link));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getLink(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(service.getLinkById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> putLink(@RequestBody LinkDto dto) {
        try {
            return ResponseEntity.ok(service.updateLink(dto));
        } catch (Exception e) {
             return ResponseEntity.notFound().build();
        }
    }
}
