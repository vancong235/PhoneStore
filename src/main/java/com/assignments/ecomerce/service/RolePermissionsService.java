package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.RolePermissions;
import com.assignments.ecomerce.repository.RolePermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionsService {
    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    public List<RolePermissions> getAllRolePermissions() {
        return (List<RolePermissions>) rolePermissionsRepository.findAll();
    }

}
