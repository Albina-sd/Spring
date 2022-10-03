package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.AlreadyExistsException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.BookRepositoryWithMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImplWithMap implements BookService {
    private final BookRepositoryWithMap bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImplWithMap(BookRepositoryWithMap bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        if (bookRepository.findBookByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()) != null){
            throw new AlreadyExistsException("Book is already exist");
        }

        Book book = bookMapper.createBookMapping(bookDto);
        bookDto = bookMapper.bookResponse(bookRepository.save(book));

        log.info("Service create book: {}", book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        long id = bookDto.getId();
        Book book = bookRepository.findById(id);
        log.info("Service update book: {}", book);
        book = bookRepository.update(book);
        //log.info("Updated book: {}", book);
        BookDto bookDto1 = bookMapper.bookResponse(book);

        return bookDto1;
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id);

        BookDto bookDto = bookMapper.bookResponse(book);
        log.info("Getting book: {} by id", book);

        return bookDto;
    }

    @Override
    public List<Book> getBookByUserId(Person user) {
        log.info("Get book by userId: {}", user.getId());
        return bookRepository.findBooksByUserId(user);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Delete book by id: {}", id);
        bookRepository.delete(id);
    }

    @Override
    public void deleteBookByUserId(Person user){

        List<Book> books = bookRepository.findBooksByUserId(user);

        log.info("Delete all books for userId: {}", user.getId());

        for(Book book: books){
            deleteBookById(book.getId());
        }
    }


}
