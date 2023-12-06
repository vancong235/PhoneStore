package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.ConfirmRespone;
import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.service.EmployeeService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;


    @GetMapping("/employee/{pageNo}")
    public String getAllEmployee(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<Employee> listEmployee = employeeService.pageEmployee(pageNo);
        Users user = userService.findByEmail(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("listEmployee", listEmployee);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listEmployee.getTotalPages());
        model.addAttribute("employeeNew", new Employee());
        return "employee";
    }

    @PostMapping("/add-employee")
    public String add(@ModelAttribute("EmployeeNew") Employee employee, Model model, RedirectAttributes attributes) {
        try {
            Employee e = employeeService.findByEmail(employee.getEmail());
            if(e != null){
                attributes.addFlashAttribute("error", "Failed to add new Employee, Email has been used !");
                return "redirect:/employee/0";
            }
            employeeService.save(employee);

            //create new employee account
            Users users = new Users(employee.getEmail(),"123456","MANAGER",employee.getName());
            userService.save(users);

            model.addAttribute("employeeNew", employee);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Failed, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error Server");
        }
        return "redirect:/employee/0";
    }

    @RequestMapping(value = "/findByIdEmployee/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Employee findById(@PathVariable("id") Integer id){
        System.out.println(id);
        return employeeService.findById(id);
    }


    @RequestMapping(value = "/findByEmailEmployee/{email}/{orderId}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public ConfirmRespone findByEmail(@PathVariable("email") String email, @PathVariable("orderId") Integer orderId, Model model){
        System.out.println(email);
        System.out.println(orderId);
        model.addAttribute("orderId",orderId);

        ConfirmRespone confirmRespone = new ConfirmRespone();
        confirmRespone.setEmployee(employeeService.findByEmail(email.toString()));
        confirmRespone.setOrderId(orderId);
//        return employeeService.findByEmail(email.toString());
        return confirmRespone;
    }

    @GetMapping("/update-employee")
    public String update(Employee employee, RedirectAttributes attributes) {
        try {
            employeeService.update(employee);
            attributes.addFlashAttribute("success", "Updated successfully !");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update, something was wrong !");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/employee/0";
    }

    @RequestMapping(value = "/status-employee", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            employeeService.setStatus(id);
            redirectAttributes.addFlashAttribute("success", "Delete employee successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Delete employee failed!");
        }
        return "redirect:/employee/0";
    }

    @GetMapping("/search-employee/{pageNo}")
    public String searchCustomer(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Employee> listEmployee = employeeService.searchEmployees(pageNo, keyword);
        Users user = userService.findByEmail(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("size", listEmployee.getSize());
        model.addAttribute("listEmployee", listEmployee);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listEmployee.getTotalPages());
        model.addAttribute("employeeNew", new Employee());
        return "employee";
    }
}
