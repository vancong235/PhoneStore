package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Integer> {
    Roles findByName(String name);
}
