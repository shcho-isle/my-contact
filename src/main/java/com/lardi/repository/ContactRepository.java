package com.lardi.repository;

import com.lardi.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    @Override
    Contact save(Contact contact);

    @Query("select c from Contact c where c.userLogin=:userLogin")
    Iterable<Contact> findByUserLogin(@Param("userLogin") String userLogin);

    @Override
    boolean exists(Integer id);

    @Override
    void delete(Integer id);
}