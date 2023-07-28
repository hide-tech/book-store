package com.yazykov.bookstore.controller;

import com.yazykov.bookstore.dto.AuthorInfo;
import com.yazykov.bookstore.dto.BookDto;
import com.yazykov.bookstore.dto.GroupedBook;
import com.yazykov.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("store/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooksTitleDescend() {
        return ResponseEntity.ok(bookService.getAllBooksTitleDescend());
    }

    @PostMapping
    public ResponseEntity<BookDto> createNewBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.status(201).body(bookService.createNewBook(bookDto));
    }

    @GetMapping("/group/authors")
    public ResponseEntity<GroupedBook> getGroupedBooksByAuthor() {
        return ResponseEntity.ok(bookService.getAllBooksGroupedByAuthor());
    }

    @GetMapping("/titles/match")
    public ResponseEntity<List<AuthorInfo>> getAuthorInfoMatchedWithChar(@RequestParam("char") Character character) {
        return ResponseEntity.ok(bookService.getAllAuthorsWithBookTitlesMaxMatchesChar(character));
    }
}
