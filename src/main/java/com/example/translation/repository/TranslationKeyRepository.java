package com.example.translation.repository;

import com.example.translation.entity.TranslationKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface TranslationKeyRepository extends JpaRepository<TranslationKey, Long> {
    Optional<TranslationKey> findByNamespaceAndKey(String namespace, String key);


    @Query("select k from TranslationKey k join k.tags t where t.name in :tags")
    Page<TranslationKey> findByTags(@Param("tags") Set<String> tags, Pageable pageable);

}
