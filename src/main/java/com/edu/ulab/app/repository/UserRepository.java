package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * UserRepository
 *
 * Интерфейс, описывающий поведение объекта, предоставляющего доступ к данным (паттерн DAO)
 */

public interface UserRepository extends CrudRepository<Person, Long> {

    /*
    User has books - book - started - comited status - other logic
    User has books - book - in progress
    User has books - book - finished
     */

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select p from Person p where p.id = :id")
//    Optional<Person> findByIdForUpdate(long id);

    Optional<Person> findById(Long id);
    Optional<Person> findByFullName(String fullName);
}
