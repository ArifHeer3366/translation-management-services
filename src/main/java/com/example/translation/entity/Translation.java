package com.example.translation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translations_seq")
    @SequenceGenerator(name = "translations_seq", sequenceName = "translations_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String namespace;

    @Column(nullable = false)
    private String key;

    @OneToMany(mappedBy = "translation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TranslationValue> values = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "translation_tags",
            joinColumns = @JoinColumn(name = "translation_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}

