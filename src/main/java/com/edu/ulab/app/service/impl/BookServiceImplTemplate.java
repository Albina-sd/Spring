package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.MapperForBook;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * BookServiceImplTemplate
 *
 * Реализация сервиса BookService с помощью JDBC Template
 */

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {
    private final JdbcTemplate jdbcTemplate;
    private final BookMapper bookMapper;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate, BookMapper bookMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUserId());
                        return ps;
                    }
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return bookDto;
    }

    /**
     * updateBook
     *
     * Обновление книг
     *
     * При обновлении перезаписываются уже имеющиеся книги
     * и сохраняются новые.
     *
     * @param bookDto
     * @return
     */
    @Override
    public BookDto updateBook(BookDto bookDto) {
        // реализовать недстающие методы

        final String UPDATE_SQL = "UPDATE BOOK " +
                "SET AUTHOR = ?, PAGE_COUNT = ?" +
                "WHERE ID = ?";

        Book book = getByTitleAndUserID(bookDto).orElse(null);

        if (book != null){
            jdbcTemplate.update(UPDATE_SQL,
                    bookDto.getAuthor(),
                    bookDto.getPageCount(),
                    book.getId());

            bookDto.setId(book.getId());

            log.info("Updating book: {}", book);
            log.info("To book: {}", bookDto);
        } else {
            createBook(bookDto);
        }

        return bookDto;
    }

    /**
     * getByTitleAndUserID
     *
     * Вспомогательный метод для обновления книг
     * Находит книгу по Id пользователя и title
     * @param bookDto
     * @return bookDto - найденная книга или пустое значение
     */
    private Optional<Book> getByTitleAndUserID(BookDto bookDto){
        final String SELECT_SQL =
                "SELECT * " +
                "FROM BOOK " +
                "WHERE USER_ID = ? AND TITLE = ?";

        try{
            Book book= jdbcTemplate.queryForObject(SELECT_SQL,
                    new Object[]{bookDto.getUserId(), bookDto.getTitle()},
                    new MapperForBook());

            return Optional.of(book);
        }catch (EmptyResultDataAccessException e){
            log.debug("No record found in database for title: " + bookDto.getTitle()
                    + " and userId: " + bookDto.getUserId(), e);
            return Optional.empty();
        }
    }

    @Override
    public BookDto getBookById(Long id) {
        // реализовать недстающие методы

        final String SELECT_SQL = "SELECT * FROM BOOK WHERE ID = ?";

        Book book = jdbcTemplate.queryForObject(SELECT_SQL,
                new Object[]{id},
                new MapperForBook());

        log.info("Get book: {}", book);
        return bookMapper.bookResponse(book);
    }

    @Override
    public List<Book> getBookByUserId(Person user) {
        final String SELECT_SQL = "SELECT * FROM BOOK WHERE USER_ID = ?";

        List<Book> books = jdbcTemplate.query(SELECT_SQL,
                new Object[]{user.getId()},
                new MapperForBook());

        log.info("Get books by User Id: {}", books);

        return books;
    }

    @Override
    public void deleteBookById(Long id) {
        // реализовать недстающие методы
        final String DELETE_SQL = "DELETE FROM BOOK WHERE ID = ?";
        jdbcTemplate.update(DELETE_SQL, id);
        log.info("Deleting book with id: {}", id);
    }

    @Override
    public void deleteBookByUserId(Person user) {
        List<Book> deletedBooks = getBookByUserId(user);

        log.info("Deleting books for userId: {}", user.getId());
        for (Book book: deletedBooks){
            deleteBookById(book.getId());
        }
    }
}
