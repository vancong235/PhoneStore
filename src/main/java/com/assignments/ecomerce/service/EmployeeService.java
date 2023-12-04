package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getALlEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> employeeList = transfer(employees);
        return employeeList;
    }

    public Page<Employee> pageEmployee(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return employeeRepository.pageEmployee(pageable);
    }

    public Employee save(Employee employee) {
        employee.setStatus(1);
        return employeeRepository.save(employee);
    }

    public  Employee findByEmail(String email){
        return employeeRepository.findByEmail(email);
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    public Employee update(Employee employee) {
        Employee employeeUpdate = null;
        try {
            employeeUpdate = employeeRepository.findById(employee.getId()).get();
            employeeUpdate.setName(employee.getName());
            employeeUpdate.setPhoneNumber(employee.getPhoneNumber());
            employeeUpdate.setAddress(employee.getAddress());
            employeeUpdate.setEmail(employee.getEmail());
            employeeUpdate.setSalary(employee.getSalary());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeRepository.save(employeeUpdate);
    }

    public void deleteById(Integer id) {
        Employee employee = employeeRepository.getById(id);
        employeeRepository.save(employee);
    }

    public void setStatus(Integer id) {
        Employee employee = employeeRepository.getById(id);
        employee.setStatus(0);
        employeeRepository.save(employee);
    }

    public Page<Employee> searchEmployees(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Employee> employee = transfer(employeeRepository.searchEmployees(keyword.trim()));
        Page<Employee> employeePages = toPage(employee, pageable);
        return employeePages;
    }

    private Page toPage(List<Employee> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Employee> transfer(List<Employee> employees) {
        List<Employee> productList = new ArrayList<>();
        for (Employee employee : employees) {
            Employee newEmployee = new Employee();
            newEmployee.setId(employee.getId());
            newEmployee.setName(employee.getName());
            newEmployee.setPhoneNumber(employee.getPhoneNumber());
            newEmployee.setAddress(employee.getAddress());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setSalary(employee.getSalary());
            productList.add(newEmployee);
        }
        return productList;
    }

    public Employee updateStatus(Integer id) {
        Employee employeeUpdate = null;
        try {
            employeeUpdate = employeeRepository.findById(id).get();
            employeeUpdate.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeRepository.save(employeeUpdate);
    }
}
