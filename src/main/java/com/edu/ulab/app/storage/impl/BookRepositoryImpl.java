package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private List<Book> list = new ArrayList<>();

    public List<Book> getAllBooks() {
        return list;
    }

    @Override
    public Book findById(long id){
        return list.stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Book findBookByTitleAndAuthor(String title, String author){
        return list.stream()
                .filter(Objects::nonNull)
                .filter((o) -> o.getTitle() == title)
                .filter((o) -> o.getAuthor() == author)
                .findAny()
                .orElse(null);
    }

    @Override
    public Book save(Book b){
        Book book = new Book();

        if (!list.isEmpty()) {
            long lastId = list.get(list.size() - 1).getId();
            book.setId(lastId + 1);
        } else {
            book.setId(1L);
        }

        book.setAuthor(b.getAuthor());
        book.setPageCount(b.getPageCount());
        book.setTitle(b.getTitle());
        book.setUserId(b.getUserId());

        list.add(book);

        return book;
    }

    @Override
    public void delete(Long id){
        list.removeIf(x -> x.getId() == (id));
    }

    @Override
    public Book update(Book book){
        int id = -1;

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getId() == (book.getId())) {
                id = i;
                break;
            }
        }

        if (id >= 0) {
            Book book1 = new Book(book.getId(), book.getUserId(), book.getTitle(), book.getAuthor(), book.getPageCount());
            list.set(id, book);

            return book1;
        } else {
            return null;
        }
    }

    @Override
    public List<Book> findBooksByUserId(Long userId){
        return list.stream()
                .filter((o) -> o.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
