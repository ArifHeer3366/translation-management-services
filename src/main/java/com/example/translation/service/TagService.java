package com.example.translation.service;

import com.example.translation.entity.Tag;

import java.util.List;

public interface TagService {

    Tag createTag(String name);
    List<Tag> getAllTags();
}
