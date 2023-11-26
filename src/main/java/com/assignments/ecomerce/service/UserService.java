package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    Users save (UserDto userDto);

    Users save (Users users);

    Users findByFullname(String fullname);

    Users findByEmail(String email);

    List<Users> getAllUsers();
}
