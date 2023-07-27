package com.yazykov.bookstore.mapper;

import com.yazykov.bookstore.model.Book;
import org.flywaydb.core.internal.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs) throws SQLException {
        return new Book(rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("description"));
    }
}
