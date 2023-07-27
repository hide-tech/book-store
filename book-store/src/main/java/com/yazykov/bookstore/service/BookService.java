package com.yazykov.bookstore.service;

import com.yazykov.bookstore.dto.AuthorInfo;
import com.yazykov.bookstore.dto.BookDto;
import com.yazykov.bookstore.dto.GroupedBook;
import com.yazykov.bookstore.model.Book;
import com.yazykov.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooksTitleDescend() {
        return bookRepository.findAll().stream().sorted(Comparator.comparing(Book::title).reversed())
                .map(book -> {
                    return new BookDto(book.id(), book.title(), book.author(), book.description());
                }).collect(Collectors.toList());
    }

    public BookDto createNewBook(BookDto bookDto) {
        Book newBook = new Book(bookDto.id(), bookDto.title(), bookDto.author(), bookDto.description());
        Book savedBook = bookRepository.save(newBook);
        return new BookDto(savedBook.id(), savedBook.title(), savedBook.author(), savedBook.description());
    }

    public GroupedBook getAllBooksGroupedByAuthor() {
        Map<String, List<Book>> result = bookRepository.findAll().stream()
                .collect(Collectors.groupingBy(Book::author));
        return new GroupedBook(result);
    }

    public List<AuthorInfo> getAllAuthorsWithBookTitlesMaxMatchesChar(Character character) {
        return bookRepository.findTitlesByMatchedString(String.valueOf(character));
    }
}
