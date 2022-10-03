package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;

import java.util.List;

public interface BookRepositoryWithMap {

    List<Book> getAllBooks();

    public Book findById(long id);

    public Book save(Book b);

    public void delete(Long id);

    public Book update(Book book);

    public List<Book> findBooksByUserId(Person user);

    public Book findBookByTitleAndAuthor(String title, String author);

}
