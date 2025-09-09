package com.example.translation.controller;

import com.example.translation.entity.Tag;
import com.example.translation.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestParam String name) {
        Tag saved = tagService.createTag(name);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> list() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}

