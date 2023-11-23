package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Permissions;
import com.assignments.ecomerce.repository.PermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsService {
    @Autowired
    private PermissionsRepository permissionsRepository;

    public List<Permissions> getAllPermissions() {
        return (List<Permissions>) permissionsRepository.findAll();
    }
}
