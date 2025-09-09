package com.example.translation.repository;


import com.example.translation.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByNamespaceAndKey(String namespace, String key);

    @Query("SELECT t FROM Translation t JOIN t.tags tag WHERE tag.name IN :tags")
    List<Translation> findByTags(@Param("tags") Set<String> tags);
}

