package com.example.translation.util;

import com.example.translation.entity.TranslationValue;
import com.example.translation.entity.TranslationKey;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TranslationValueSpecification {

    public static Specification<TranslationValue> filter(
            String namespace, String key, String locale, String q
    ) {
        return (root, query, criteriaBuilder) -> {

            // Join with TranslationKey
            Join<TranslationValue, TranslationKey> keyJoin = root.join("key", JoinType.INNER);

            Predicate p = criteriaBuilder.conjunction();

            if (namespace != null && !namespace.isEmpty()) {
                p = criteriaBuilder.and(p,
                        criteriaBuilder.equal(keyJoin.get("namespace"), namespace));
            }

            if (key != null && !key.isEmpty()) {
                p = criteriaBuilder.and(p,
                        criteriaBuilder.like(keyJoin.get("key"), "%" + key + "%"));
            }

            if (locale != null && !locale.isEmpty()) {
                p = criteriaBuilder.and(p,
                        criteriaBuilder.equal(root.get("locale"), locale));
            }

            if (q != null && !q.isEmpty()) {
                p = criteriaBuilder.and(p,
                        criteriaBuilder.like(root.get("content"), "%" + q + "%"));
            }

            return p;
        };
    }
}

