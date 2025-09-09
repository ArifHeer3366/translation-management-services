package com.example.translation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translation_keys", uniqueConstraints = @UniqueConstraint(columnNames = {"namespace","t_key"}))
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationKey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_keys_seq")
    @SequenceGenerator(name = "translation_keys_seq", sequenceName = "translation_keys_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable=false,length=64) private String namespace = "default";
    @Column(name="t_key", nullable=false, length=255)
    private String key;
    @Column(name="created_at")
    private Instant createdAt = Instant.now();
    @Column(name="updated_at")
    private Instant updatedAt = Instant.now();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "translation_key_tags",
            joinColumns = @JoinColumn(name = "key_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "key", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TranslationValue> values = new HashSet<>();

}
