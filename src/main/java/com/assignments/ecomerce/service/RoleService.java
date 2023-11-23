package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Roles;
import com.assignments.ecomerce.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Roles> getAllRoles() {
        return (List<Roles>) roleRepository.findAll();
    }
}
