package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;

import java.util.List;

public interface BookRepositoryWithMap {

    List<Book> getAllBooks();

    public Book findById(long id);

    public Book save(Book b);

    public void delete(Long id);

    public Book update(Book book);

    public List<Book> findBooksByUserId(Long userId);

    public Book findBookByTitleAndAuthor(String title, String author);

}
