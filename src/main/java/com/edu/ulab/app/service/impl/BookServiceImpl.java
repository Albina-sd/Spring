package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookServiceImpl
 *
 * Реализация сервиса BookService с помощью CRUD repository
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.createBookMapping(bookDto);
        log.info("Mapped book: {} service", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {} service", savedBook);
        return bookMapper.bookResponse(savedBook);
    }

    // ToDo пересмотреть метод
    @Override
    public BookDto updateBook(BookDto bookDto) {
        // реализовать недстающие методы
        Book book = bookMapper.createBookMapping(bookDto);

        log.info("Mapped book: {}", book);

        Book updatedBook = bookRepository.findByAuthorAndUserId(book.getAuthor(), book.getUserId()).orElse(null);
        if (updatedBook == null) {
            updatedBook = bookRepository.findByTitleAndUserId(book.getTitle(), book.getUserId()).orElse(null);
        }


        if (updatedBook != null){
            log.info("Updating book: {}", updatedBook);
            book.setId(updatedBook.getId());
            log.info("Updated to: {}", book);
        }

        createBook(bookMapper.bookResponse(book));

        return bookMapper.bookResponse(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        // реализовать недстающие методы
        Book book = bookRepository.findById(id).orElse(null);
        log.info("Get book: {} service", book);
        return bookMapper.bookResponse(book);
    }

    @Override
    public List<Book> getBookByUserId(Long userId) {
        log.info("Get book for user id: {} service", userId);
        return bookRepository.findByUserId(userId);
    }

    @Override
    public void deleteBookById(Long id) {
        // реализовать недстающие методы
        log.info("Delete book with id: {} service", id);
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBookByUserId(Long userId) {
        List<Book> books = getBookByUserId(userId);
        log.info("Delete books: {} service", books);
        for (Book book: books){
            book.setUserId(null);
            bookRepository.deleteById(book.getId());
        }
    }
}
