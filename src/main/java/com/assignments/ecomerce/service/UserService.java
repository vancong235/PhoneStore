package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.UserDTO;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.repository.RoleRepository;
import com.assignments.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    public List<Users> getAllUsers() {
        return (List<Users>) userRepository.findAll();
    }

    public Users save(UserDTO userDTO) {
        Users users = new Users();
        users.setFullName(userDTO.getFullName());
        users.setUserName(userDTO.getPassWord());
        users.setPassWord(userDTO.getPassWord());
        users.setRoles(Arrays.asList(
                roleRepository.findByName("ADMIN"),
                roleRepository.findByName("SELLER"),
                roleRepository.findByName("CUSTOMER")
        ));
        return userRepository.save(users);
    }

    public Users findByUsername(String userName){
        return userRepository.findByUserName(userName);
    }

}
