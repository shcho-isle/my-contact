package com.lardi.repository.datajpa;

import com.lardi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DataJpaRoleRepositoryImpl implements RoleRepository{

    @Autowired
    CrudRoleRepository crudRepository;
}
