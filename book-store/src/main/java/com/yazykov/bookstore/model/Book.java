package com.yazykov.bookstore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
public record Book(
        @Id
        Long id,
        String title,
        String author,
        String description
) {
}
