package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

/**
 * BookRepository
 *
 * Интерфейс, описывающий поведение объекта, предоставляющего доступ к данным (паттерн DAO)
 */

public interface BookRepository extends CrudRepository<Book, Long> {
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select b from Book b where b.id = :id")
//    Optional<Book> findByIdForUpdate(long id);
    List<Book> findAllByPerson(Person user);
    Optional<Book> findByAuthorAndPerson(String author, Person user);
    Optional<Book> findByTitleAndPerson(String title, Person user);
}
