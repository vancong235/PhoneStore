package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.*;
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
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("userNew", new Users());
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
            attributes.addFlashAttribute("failed", "Duplicate name of user, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/users";
    }

    @PostMapping("/add-user-admin")
    public String addUser(@ModelAttribute("userNew") Users user, Model model, RedirectAttributes attributes) {
        try {
            userService.save(user);
            model.addAttribute("userNew", user);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of user, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/unlock-account", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String enabledAccount(Integer id, RedirectAttributes attributes) {
        try {
            userService.unlockUser(id);
            attributes.addFlashAttribute("success", "UnLock successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/block-account", method = {RequestMethod.PUT, RequestMethod.GET})
    public String blockAccount(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
           userService.lockUser(id);
            redirectAttributes.addFlashAttribute("success", "Block successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/users";
    }


}
