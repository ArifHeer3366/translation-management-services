package com.example.translation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


public class TranslationRequest {

    @NotBlank
    private String namespace;

    @NotBlank
    private String key;

    @NotBlank
    private String locale;

    @NotBlank
    private String content;

    private List<String> tags;

    @NotEmpty
    private List<TranslationValueRequest> values;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<TranslationValueRequest> getValues() {
        return values;
    }

    public void setValues(List<TranslationValueRequest> values) {
        this.values = values;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
