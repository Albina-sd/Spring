package com.edu.ulab.app.mapper;

import com.edu.ulab.app.entity.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MapperForBook
 *
 * Кастомная имплементация RowMapper для сущности книги
 *
 * Используется для логики сопоставления строк ResultSet
 */

public class MapperForBook implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getLong("id"));
        //book.getPerson(rs.getLong("user_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPageCount(rs.getLong("page_count"));

        return book;
    }

}
