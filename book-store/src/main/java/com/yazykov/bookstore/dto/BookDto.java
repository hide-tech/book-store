package com.yazykov.bookstore.dto;

public record BookDto(
        Long id,
        String title,
        String author,
        String description
) {
}
