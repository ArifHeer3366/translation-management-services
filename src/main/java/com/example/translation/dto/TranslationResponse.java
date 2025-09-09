package com.example.translation.dto;

import java.util.Map;
import java.util.Set;

public class TranslationResponse {

    private Long id;
    private String namespace;
    private String key;
    private Set<String> tags;
    private Map<String, String> values;

    public TranslationResponse(Long id, String namespace, String key,
                               Set<String> tags, Map<String, String> values) {
        this.id = id;
        this.namespace = namespace;
        this.key = key;
        this.tags = tags;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
