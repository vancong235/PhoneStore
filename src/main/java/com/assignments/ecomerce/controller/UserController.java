package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.PermissionsService;
import com.assignments.ecomerce.service.RolePermissionsService;
import com.assignments.ecomerce.service.RoleService;
import com.assignments.ecomerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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

    @GetMapping("/user/{pageNo}")
    public String pageUser(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {

        Page<Users> listUsers = userService.pageUser(pageNo, 9);

        System.out.println(listUsers.getTotalPages());

        model.addAttribute("size", listUsers.getSize());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listUsers.getTotalPages());
        model.addAttribute("userNew", new Users());
        return "users";
    }


    @GetMapping("/search-user/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam("keyword") String keyword,
                                Model model, Principal principal,HttpSession session) {

        Page<Users> listUsers = userService.searchUsers(pageNo, keyword);

        session.setAttribute("keyword", keyword);
        model.addAttribute("keyword", keyword);

        model.addAttribute("size", listUsers.getSize());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listUsers.getTotalPages());
        model.addAttribute("userNew", new Users());
        return "users";
    }

    @PostMapping("/add-user")
    public String add(@ModelAttribute("userNew") UserDto user, Model model, RedirectAttributes attributes) {
        try {
            Users u = userService.findByEmail(user.getEmail());
            if (u != null) {
                attributes.addFlashAttribute("error", "Email has been used!");
                return "redirect:/user/0";

            }

            userService.save(user);
            model.addAttribute("userNew", user);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of user, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error Server");
        }
        return "redirect:/user/0";
    }

    @PostMapping("/add-user-admin")
    public String addUser(@ModelAttribute("userNew") Users user, Model model, RedirectAttributes attributes) {
        try {
            Users u = userService.findByEmail(user.getEmail());
            if (u != null) {
                attributes.addFlashAttribute("error", "Email has been used!");
                return "redirect:/user/0";
            }

            userService.save(user);
            model.addAttribute("userNew", user);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of user, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error Server");
        }
        return "redirect:/user/0";
    }

    @RequestMapping(value = "/unlock-account", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String enabledAccount(Integer id, RedirectAttributes attributes) {
        try {
            userService.unlockUser(id);
            attributes.addFlashAttribute("success", "UnLock successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to deleted");
        }
        return "redirect:/user/0";
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
        return "redirect:/user/0";
    }


}
