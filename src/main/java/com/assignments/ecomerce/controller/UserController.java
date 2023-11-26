package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.Permissions;
import com.assignments.ecomerce.model.RolePermissions;
import com.assignments.ecomerce.model.Roles;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.service.PermissionsService;
import com.assignments.ecomerce.service.RolePermissionsService;
import com.assignments.ecomerce.service.RoleService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionsService permissionsService;






    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<Users> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @PostMapping("/add-user")
    public String add(@ModelAttribute("userNew") UserDto user, Model model, RedirectAttributes attributes) {
        try {
            userService.save(user);
            model.addAttribute("userNew", user);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/users";
    }



}
