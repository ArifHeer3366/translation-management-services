package com.example.translation.dto;

import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Sort;

public class TranslationSearchCriteria {

    @Size(max = 255)
    private String namespace;

    @Size(max = 255)
    private String key;

    @Size(max = 8)
    private String locale;

    @Size(max = 255)
    private String content;

    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "updatedAt";
    private Sort.Direction sortDirection = Sort.Direction.DESC;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }
}
