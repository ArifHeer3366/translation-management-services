package com.example.translation.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="tags")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_seq")
    @SequenceGenerator(name = "tags_seq", sequenceName = "tags_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable=false, unique=true, length=64)
    private String name;
}