package com.yazykov.bookstore.dto;

import com.yazykov.bookstore.model.Book;

import java.util.List;
import java.util.Map;

public record GroupedBook(
        Map<String, List<Book>> booksAuthorGrouped
) {
}
