package com.telecom.repository.datajpa;

import com.telecom.model.Contact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudContactRepository extends CrudRepository<Contact, Integer> {

    @Override
    Contact save(Contact item);

    @Query("SELECT c FROM Contact c WHERE c.userId=:userId ORDER BY c.lastName ASC")
    List<Contact> getAll(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Contact c WHERE c.id=:id AND c.userId=:userId")
    int delete(@Param("id") int id, @Param("userId") Integer userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT c from Contact c WHERE c.userId=:userId AND (c.firstName LIKE :filterRequest OR c.lastName LIKE :filterRequest OR c.mobilePhone LIKE :filterRequest) ORDER BY c.lastName ASC")
    List<Contact> getFiltered(@Param("filterRequest") String filterRequest, @Param("userId") Integer userId);
}