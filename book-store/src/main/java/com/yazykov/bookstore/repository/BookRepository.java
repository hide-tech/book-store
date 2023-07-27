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
        String query = "select b.id, b.title, b.author, b.description from books b";
        return jdbcTemplate.query(query, (rs, rn) -> {
            return new Book(rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("description"));
        });

    }

    public List<AuthorInfo> findTitlesByMatchedString(String character) {
        String query = "select b.author as author, " +
                "sum(length(lower(b.title)) - length(replace(lower(b.title), ?, ''))) as matches " +
                "from books b group by b.author " +
                "having sum(length(lower(b.title)) - length(replace(lower(b.title), ?, ''))) > 0 " +
                "order by matches desc limit 10;";
        Object[] args = {character, character};
        return jdbcTemplate.query(query, args,(rs, rn) -> {
            return new AuthorInfo(rs.getString("author"), rs.getInt("matches"));
        });
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
        Integer rowsNumber = jdbcTemplate.update(query, args);
        return new Book(index, book.title(), book.author(), book.description());
    }
}
