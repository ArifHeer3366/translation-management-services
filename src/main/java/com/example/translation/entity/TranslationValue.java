package com.example.translation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "translation_values",
        uniqueConstraints=@UniqueConstraint(
                columnNames={"key_id","locale"}))
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TranslationValue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_values_seq")
    @SequenceGenerator(name = "translation_values_seq", sequenceName = "translation_values_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable=false,length=8)
    private String locale; // e.g., en, fr, es
    @Column(nullable=false,columnDefinition="text")
    private String content;
    private int version = 1;
    @Column(name="updated_at")
    private Instant updatedAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="key_id", nullable=false)
    private TranslationKey key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "translation_id")
    private Translation translation;

}
