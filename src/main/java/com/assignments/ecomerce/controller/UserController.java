package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.CustomerDTO;
import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.*;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
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

        Users user = userService.findByEmail(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("size", listUsers.getSize());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listUsers.getTotalPages());
        model.addAttribute("userNew", new Users());
        return "users";
    }

    @GetMapping("/userInfor")
    public String userPage(Model model, Principal principal, @RequestParam(value = "remember", required = false) boolean remember) {
        if (principal != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

            Users user = userService.findByEmail(principal.getName());
            model.addAttribute("userId", user.getId());
            model.addAttribute("user", userDetails);
            model.addAttribute("email", principal.getName());
            model.addAttribute("name", user.getFullname());

            Customer customer = customerService.findByEmail(principal.getName());

            if (customer != null) {
                if (customer.getAddress() != null) {
                    model.addAttribute("address", customer.getAddress());
                } else {
                    model.addAttribute("address", "");
                }
            } else {
                model.addAttribute("address", "");
            }

            List<Category> categories = categoryService.getAllCategory();
            Page<Product> listProducts = productService.searchProducts(0, "", 9);

            model.addAttribute("categories", categories);
            model.addAttribute("size", listProducts.getSize());
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", listProducts.getTotalPages());

            model.addAttribute("phone", customer.getPhoneNumber());
            model.addAttribute("fullname", customer.getName());
            model.addAttribute("address", customer.getAddress());
            LocalDate birthday = customer.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Định dạng giá trị ngày thành chuỗi YYYY-MM-DD
            String formattedBirthday = birthday.format(DateTimeFormatter.ISO_DATE);
            model.addAttribute("birthday", formattedBirthday);
            model.addAttribute("sex", customer.getGender());
            return "userInfor";
        } else {
            // User is not logged in, redirect to login page
            return "redirect:/login";
        }
    }

    @PostMapping("/userInfor")
    public String processPayment(
            @RequestParam("fullname") String fullname,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
            @RequestParam("sex") Boolean sex,
            Principal principal, Model model) {
        Users c = userService.findByEmail(principal.getName());
        CustomerDTO customer = new CustomerDTO();
        customer.setId(c.getId());
        customer.setEmail(principal.getName());
        customer.setName(fullname);
        customer.setStatus(1);
        customer.setGender(sex);
        customer.setPhoneNumber(phone);
        customer.setAddress(address);
        Date convertedBirthday = java.sql.Date.valueOf(birthday);
        // Set the converted birthday to the customer object
        customer.setBirthday(convertedBirthday);
        customerService.update(customer);
        return "redirect:/userInfor"; // Trả về tên view (thường là trang hiển thị thông tin người dùng)
    }
    @GetMapping("/search-user/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam("keyword") String keyword,
                                Model model, Principal principal,HttpSession session) {

        Page<Users> listUsers = userService.searchUsers(pageNo, keyword);
        Users user = userService.findByEmail(principal.getName());

        model.addAttribute("user", user);
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
