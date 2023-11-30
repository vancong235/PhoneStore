package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.repository.RoleRepository;
import com.assignments.ecomerce.repository.UserRepository;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Users save(UserDto userDto) {
        Users user = new Users(userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()),"USER",userDto.getFullname());

        return userRepository.save(user);
    }

    @Override
    public Users save(Users user) {
        Users u = new Users(user.getEmail(),passwordEncoder.encode(user.getPassword()),user.getRole(),user.getFullname());
        return userRepository.save(u);
    }


    public List<Users> getAllUsers() {
        return (List<Users>) userRepository.findAll();
    }


    public Users findByFullname(String fullname){
        return userRepository.findByFullname(fullname.trim());
    }

    public Users findByEmail(String email){
        return userRepository.findByEmail(email.trim());
    }

    public Users unlockUser(Integer id) {

        Users userUpdate = null;
        try {
            userUpdate = userRepository.findById(id).get();
            userUpdate.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRepository.save(userUpdate);
    }

    public Users lockUser(Integer id) {

        Users userUpdate = null;
        try {
            userUpdate = userRepository.findById(id).get();
            userUpdate.setStatus(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRepository.save(userUpdate);
    }
}
