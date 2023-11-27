package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT COUNT(*) FROM customer", nativeQuery = true)
    int countCustomers();

    @Query("SELECT p from Customer p where CONCAT(p.name,p.phoneNumber,p.address,p.email) like %?1%")
    List<Customer> searchCustomers(String keyword);
    @Query(value = "select * from Customer where statusCustomer = 1", nativeQuery = true)
    List<Customer> findByStatusActivated();
    @Query(value = "select * from Customer where statusCustomer = 1", nativeQuery = true)
    Page<Customer> pageCustomer(Pageable pageable);

    @Query(value = "select c from Customer c where c.email like %?1%")
    Customer findByEmail(String email);

    @Query(value = "select c from Customer c where c.phoneNumber like %?1%")
    Customer findByPhone(String phoneNumber);
}
