package com.example.translation.service.serviceImpl;

import com.example.translation.entity.Tag;
import com.example.translation.repository.TagRepository;
import com.example.translation.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepo;

    public TagServiceImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public Tag createTag(String name) {
        return tagRepo.findByName(name).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setName(name);
            return tagRepo.save(tag);
        });
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }
}

