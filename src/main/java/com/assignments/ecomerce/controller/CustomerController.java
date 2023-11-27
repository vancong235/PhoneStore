package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.CustomerDTO;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/{pageNo}")
    public String getAllCustomer(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<Customer> listCustomer = customerService.pageCustomer(pageNo);
        model.addAttribute("listCustomer", listCustomer);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCustomer.getTotalPages());
        model.addAttribute("customerNew", new Customer());
        return "customer";
    }

    @PostMapping("/add-customer")
    public String add(@ModelAttribute("customerNew") CustomerDTO customer, Model model, RedirectAttributes attributes) {
        try {
            /*boolean isEmailExists = customerService.checkIfEmailExists(customer.getEmail());
            boolean isPhoneExists = customerService.checkIfPhoneExists(customer.getPhoneNumber());

            if (isEmailExists || isPhoneExists) {
                model.addAttribute("error", "Email or phone number already exists");
                model.addAttribute("customerNew", customer);
                return "redirect:/customer/0";
            }*/
            customerService.save(customer);
            model.addAttribute("customerNew", customer);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/customer/0";
    }

    @RequestMapping(value = "/findByIdCustomer/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Customer findById(@PathVariable("id") Integer id) {
        return customerService.findById(id);
    }


    @GetMapping("/update-customer")
    public String update(CustomerDTO customer, RedirectAttributes attributes) {
        try {
            customerService.update(customer);
            attributes.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/customer/0";
    }

    @RequestMapping(value = "/unlock-customer", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id, RedirectAttributes attributes) {
        try {
            customerService.deleteById(id);
            attributes.addFlashAttribute("success", "UnLock successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/customer/0";
    }

    @RequestMapping(value = "/block-customer", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            customerService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Block successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/customer/0";
    }

    @GetMapping("/search-customer/{pageNo}")
    public String searchCustomer(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Customer> listCustomer = customerService.searchCustomer(pageNo, keyword.trim());

        model.addAttribute("size", listCustomer.getSize());
        model.addAttribute("listCustomer", listCustomer);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCustomer.getTotalPages());
        model.addAttribute("customerNew", new Customer());
        return "customer";
    }
}
