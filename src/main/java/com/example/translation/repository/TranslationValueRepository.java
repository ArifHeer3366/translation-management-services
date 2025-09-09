package com.example.translation.repository;

import com.example.translation.dto.ExportDto;
import com.example.translation.entity.TranslationValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TranslationValueRepository extends
        JpaRepository<TranslationValue, Long>,
        JpaSpecificationExecutor<TranslationValue> {

    @EntityGraph(attributePaths = {"key"})
    Optional<TranslationValue> findByKeyIdAndLocale(Long keyId, String locale);

    @Query("select v from TranslationValue v join v.key k where (" +
            ":namespace is null or k.namespace=:namespace) and (" +
            ":key is null or k.key like concat('%',:key,'%')) and (" +
            ":locale is null or v.locale=:locale) and (" +
            ":q is null or v.content like concat('%',:q,'%'))")
    Page<TranslationValue> search(@Param("namespace") String namespace,
                                  @Param("key") String key,
                                  @Param("locale") String locale,
                                  @Param("q") String q,
                                  Pageable pageable);

    @Query("""
        select new com.example.translation.dto.ExportDto(
            k.namespace, k.key, v.locale, v.content
        )
        from TranslationValue v
        join v.key k
        where v.locale = :locale
          and (:namespace is null or k.namespace = :namespace)
    """)
    List<ExportDto> streamByLocale(@Param("locale") String locale,
                                   @Param("namespace") String namespace);
}