package com.example.web.controller;

import com.example.web.models.LinkDbModel;
import com.example.web.models.LinkDto;
import com.example.web.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links/")
public class LinkController {
    private final LinkService service;

    @Autowired
    public LinkController(LinkService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> postLink(@RequestBody LinkDbModel link) {
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

    @PutMapping
    public ResponseEntity<?> putLink(@RequestBody LinkDto dto) {
        try {
            return ResponseEntity.ok(service.updateLink(dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
