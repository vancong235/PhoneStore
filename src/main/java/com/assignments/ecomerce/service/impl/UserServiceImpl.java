package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.repository.RoleRepository;
import com.assignments.ecomerce.repository.UserRepository;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Page<Users> searchUsers(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum, 9);
        List<Users> users = transfer(userRepository.searchByKeyword(keyword.trim()));
        Page<Users> userPages = toPage(users, pageable);
        return userPages;
    }

    public List<Users> transfer(List<Users> users) {
        List<Users> userList = new ArrayList<>();
        for (Users user : users) {
            Users u = new Users();
            u.setId(user.getId());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            u.setStatus(user.getStatus());
            u.setRole(user.getRole());
            u.setFullname(user.getFullname());

            userList.add(u);
        }
        return userList;
    }




    private Page toPage(List<Users> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    @Override
    public Page<Users> pageUser(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.pageUsers(pageable);
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
