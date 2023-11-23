
package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    @Query("SELECT p from Supplier p where status = 1")
    Page<Supplier> pageSupplier(Pageable pageable);

    @Query("SELECT p from Supplier p where CONCAT(p.name) like %?1%")
    List<Supplier> searchSupplier(String keyword);
}

