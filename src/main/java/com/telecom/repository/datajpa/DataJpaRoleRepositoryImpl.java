package com.telecom.repository.datajpa;

import com.telecom.model.Role;
import com.telecom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("mysql")
@Repository
public class DataJpaRoleRepositoryImpl implements RoleRepository {

    @Autowired
    private CrudRoleRepository crudRepository;

    @Override
    public void save(Role role) {
        crudRepository.save(role);
    }
}
