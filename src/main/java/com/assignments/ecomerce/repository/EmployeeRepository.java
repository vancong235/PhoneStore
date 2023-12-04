package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT COUNT(*) FROM Employee", nativeQuery = true)
    int countEmployees();

    @Query("SELECT p from Employee p where status = 1")
    Page<Employee> pageEmployee(Pageable pageable);

    @Query("SELECT p from Employee p where p.status = 1 and CONCAT(p.name,p.phoneNumber,p.address,p.email) like %?1%")
    List<Employee> searchEmployees(String keyword);

    @Query(value = "SELECT p from Employee p where p.status = 1 and p.email =:email")
    Employee findByEmail(String email);
}
