package com.yazykov.bookstore.repository;

import com.yazykov.bookstore.dto.AuthorInfo;
import com.yazykov.bookstore.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        String query = "select * from books";
        return jdbcTemplate.queryForList(query, Book.class);
    }

    public List<AuthorInfo> findTitlesByMatchedString(String character) {
        String query = "select b.author as author, length(lower(b.title)) - length(replace(lower(b.title),lower(?), '')) " +
                "as matches from books b group by b.author limit 10";
        return jdbcTemplate.queryForList(query, AuthorInfo.class);
    }

    public Book save(Book book) {
        Long index = jdbcTemplate.query("SELECT nextval('books_seq')",
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException("Unable to retrieve value from sequence books_seq.");
                    }
                });
        String query = "insert into books(id, title, author, description) values (?, ?, ?, ?)";
        Object[] args = {index, book.title(), book.author(), book.description()};
        Long resultIndex = (long) jdbcTemplate.update(query, args);
        return new Book(resultIndex, book.title(), book.author(), book.description());
    }
}
