package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
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
    private final UserRepository userRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           UserRepository userRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setPerson(userRepository.findById(bookDto.getUserId()).orElse(null));
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookToBookDto(savedBook);
    }

    // ToDo пересмотреть метод
    @Override
    public BookDto updateBook(BookDto bookDto) {
        // реализовать недстающие методы
        Person person = userRepository.findById(bookDto.getUserId()).orElse(null);
        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setPerson(person);

        log.info("Mapped book: {}", book);

        Book updatedBook = bookRepository.findByAuthorAndPerson(book.getAuthor(), book.getPerson()).orElse(null);
        if (updatedBook == null) {
            updatedBook = bookRepository.findByTitleAndPerson(book.getTitle(), book.getPerson()).orElse(null);
        }


        if (updatedBook != null){
            log.info("Updating book: {}", updatedBook);
            book.setId(updatedBook.getId());
            log.info("Updated to: {}", book);
        }

        BookDto savedBook = createBook(bookMapper.bookToBookDto(book));


        return savedBook;
    }

    @Override
    public BookDto getBookById(Long id) {
        // реализовать недстающие методы
        Book book = bookRepository.findById(id).orElse(null);
        log.info("Get book: {}", book);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public List<Book> getBookByUser(Person user) {
        log.info("Get book for user: {}", user);
        return bookRepository.findAllByPerson(user);
    }

    @Override
    public void deleteBookById(Long id) {
        // реализовать недстающие методы
        log.info("Delete book with id: {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBookByUser(Person user) {
        List<Book> books = getBookByUser(user);
        log.info("Delete books: {}", books);
        for (Book book: books){
            bookRepository.deleteById(book.getId());
        }
    }
}
