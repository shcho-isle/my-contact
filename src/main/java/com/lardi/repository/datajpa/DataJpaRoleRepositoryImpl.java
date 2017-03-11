package com.lardi.repository.datajpa;

import com.lardi.model.Role;
import com.lardi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dataJpaRoleRepository")
public class DataJpaRoleRepositoryImpl implements RoleRepository{

    @Autowired
    private CrudRoleRepository crudRepository;

    @Override
    public void save(Role role) {
        crudRepository.save(role);
    }
}
