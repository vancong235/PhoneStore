package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.CustomerDTO;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> customerList = transfer(customers);
        return customerList;
    }

    public List<Customer> getAllCustomers1() {
        List<Customer> customers = customerRepository.findByStatusActivated();
        List<Customer> customerList = transfer(customers);
        return customerList;
    }

    public Page<Customer> pageCustomer(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return customerRepository.pageCustomer(pageable);
    }

    public int countCustomers(){
        return customerRepository.countCustomers();
    }

    public Customer save(CustomerDTO customer) {
        Customer CustomerSave = new Customer();
        CustomerSave.setName(customer.getName());
        CustomerSave.setPhoneNumber(customer.getPhoneNumber());
        CustomerSave.setAddress(customer.getAddress());
        CustomerSave.setEmail(customer.getEmail());
        CustomerSave.setBirthday(customer.getBirthday());
        CustomerSave.setGender(customer.getGender());
        CustomerSave.setStatus(1);
        return customerRepository.save(CustomerSave);
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id).get();
    }

    public Customer update(CustomerDTO customer) {
        Customer CustomerUpdate = null;
        try {
            CustomerUpdate = customerRepository.findById(customer.getId()).get();
            CustomerUpdate.setName(customer.getName());
            CustomerUpdate.setPhoneNumber(customer.getPhoneNumber());
            CustomerUpdate.setAddress(customer.getAddress());
            CustomerUpdate.setEmail(customer.getEmail());
            CustomerUpdate.setBirthday(customer.getBirthday());
            CustomerUpdate.setGender(customer.getGender());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerRepository.save(CustomerUpdate);
    }

    public void deleteById(Integer id) {
        Customer customer = customerRepository.getById(id);
        customer.setStatus(1);
        customerRepository.save(customer);
    }

    public void enableById(Integer id) {
        Customer customer = customerRepository.getById(id);
        customer.setStatus(0);
        customerRepository.save(customer);
    }

    public Page<Customer> searchCustomer(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Customer> customer = transfer(customerRepository.searchCustomers(keyword.trim()));
        Page<Customer> customerPages = toPage(customer, pageable);
        return customerPages;
    }

    private Page toPage(List<Customer> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Customer> transfer(List<Customer> customers) {
        List<Customer> productList = new ArrayList<>();
        for (Customer customer : customers) {
            Customer newCustomer = new Customer();
            newCustomer.setId(customer.getId());
            newCustomer.setName(customer.getName());
            newCustomer.setPhoneNumber(customer.getPhoneNumber());
            newCustomer.setAddress(customer.getAddress());
            newCustomer.setEmail(customer.getEmail());
            newCustomer.setBirthday(customer.getBirthday());
            newCustomer.setGender(customer.getGender());
            newCustomer.setStatusCustomer(customer.getStatusCustomer());
            productList.add(newCustomer);
        }
        return productList;
    }

    public Customer updateStatus(Integer id) {
        Customer customerUpdate = null;
        try {
            customerUpdate = customerRepository.findById(id).get();
            customerUpdate.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerRepository.save(customerUpdate);
    }

    public boolean checkIfEmailExists(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return customer != null;
    }

    public boolean checkIfPhoneExists(String phone) {
        Customer customer = customerRepository.findByPhone(phone);
        return customer != null;
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
