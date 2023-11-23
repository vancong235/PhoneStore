package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/employee/{pageNo}")
    public String getAllEmployee(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<Employee> listEmployee = employeeService.pageEmployee(pageNo);
        model.addAttribute("listEmployee", listEmployee);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listEmployee.getTotalPages());
        model.addAttribute("employeeNew", new Employee());
        return "employee";
    }

    @PostMapping("/add-employee")
    public String add(@ModelAttribute("EmployeeNew") Employee employee, Model model, RedirectAttributes attributes) {
        try {
            employeeService.save(employee);
            model.addAttribute("employeeNew", employee);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/employee/0";
    }

    @RequestMapping(value = "/findByIdEmployee/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Employee findById(@PathVariable("id") Integer id){
        return employeeService.findById(id);
    }

    @GetMapping("/update-employee")
    public String update(Employee employee, RedirectAttributes attributes) {
        try {
            employeeService.update(employee);
            attributes.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/employee/0";
    }

    @RequestMapping(value = "/status-employee", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            employeeService.setStatus(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/employee/0";
    }

    @GetMapping("/search-employee/{pageNo}")
    public String searchCustomer(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Employee> listEmployee = employeeService.searchEmployees(pageNo, keyword);
        model.addAttribute("size", listEmployee.getSize());
        model.addAttribute("listEmployee", listEmployee);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listEmployee.getTotalPages());
        return "employee";
    }
}
