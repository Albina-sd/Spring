package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.BookRepositoryWithMap;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Имплементация репозитория
 * механизма доступа к данным без базы данных
 * для сущности книга
 */

@Repository
public class BookRepositoryImpl implements BookRepositoryWithMap {
    private Map<Book, Long> map = new HashMap<>();

    public List<Book> getAllBooks() {
       return new ArrayList(map.values());
    }

    @Override
    public Book findById(long id){
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author){
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter((o) -> o.getTitle() == title)
                .filter((o) -> o.getAuthor() == author)
                .findAny()
                .orElse(null);
    }

    @Override
    public Book save(Book b){
        Book book = new Book();
        book.setAuthor(b.getAuthor());
        book.setPageCount(b.getPageCount());
        book.setTitle(b.getTitle());
        book.setUserId(b.getUserId());

        if (!map.isEmpty()){
            long lastId = Collections.max(map.values());
            b.setId(lastId + 1);
            book.setId(lastId + 1);
        } else {
            b.setId(1L);
            book.setId(1L);
        }

        //map.put(b, b.getId());
        map.put(book, book.getId());

        return book;
    }

    @Override
    public void delete(Long id){
        Book book = findById(id);
        map.remove(book, id);
    }

    @Override
    public Book update(Book book){
        Book oldBook = findById(book.getId());
        map.remove(oldBook);
        map.put(book, book.getId());

        return book;
    }

    @Override
    public List<Book> findBooksByUserId(Long userId){
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
