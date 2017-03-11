package com.lardi.repository.datajpa;

import com.lardi.model.Contact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrudContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findByUserLogin(String userLogin);

    @Modifying
    @Query("DELETE FROM Contact c WHERE c.id=:id")
    int delete(@Param("id") int id);
}