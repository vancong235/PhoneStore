package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    Users save (UserDto userDto);

    Users save (Users users);

    Users findByFullname(String fullname);

    Users findByEmail(String email);

    List<Users> getAllUsers();

     Users unlockUser(Integer id);

    Users lockUser(Integer id);


    Page<Users> searchUsers(int pageNo, String keyword);

    List<Users> transfer(List<Users> users);

    Page<Users> pageUser(int pageNo, int pageSize);
}
