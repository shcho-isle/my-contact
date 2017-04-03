package com.telecom.repository.datajpa;

import com.telecom.model.Role;
import com.telecom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Profile("mysql")
@Repository
public class DataJpaRoleRepositoryImpl implements RoleRepository {

    private final CrudRoleRepository crudRepository;

    @Autowired
    public DataJpaRoleRepositoryImpl(CrudRoleRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public void save(Role role) {
        crudRepository.save(role);
    }

    @Override
    public Set<Role> getAll(Integer userId) {
        return crudRepository.getAll(userId);
    }
}
