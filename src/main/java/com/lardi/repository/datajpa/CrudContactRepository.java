package com.lardi.repository.datajpa;

import com.lardi.model.Contact;
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

    @Query("SELECT c FROM Contact c WHERE c.userLogin=:userLogin ORDER BY c.lastName ASC")
    List<Contact> getAll(@Param("userLogin") String userLogin);

    @Modifying
    @Transactional
    @Query("DELETE FROM Contact c WHERE c.id=:id AND c.userLogin=:userLogin")
    int delete(@Param("id") int id, @Param("userLogin") String userLogin);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT c from Contact c WHERE c.userLogin=:userLogin AND (c.firstName LIKE :filterRequest OR c.lastName LIKE :filterRequest OR c.mobilePhone LIKE :filterRequest) ORDER BY c.lastName ASC")
    List<Contact> getFiltered(@Param("filterRequest") String filterRequest, @Param("userLogin") String userLogin);
}