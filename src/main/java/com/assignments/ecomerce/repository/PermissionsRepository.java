package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions,Integer> {
}
