package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT c from Category c where c.name like %?1%")
    List<Category> searchCategory(String keyword);

    @Query(value = "select * from Category where status = 1", nativeQuery = true)
    List<Category> findByStatusActivated();

    @Query("SELECT p from Category p where status = 1")
    Page<Category> pageCategory(Pageable pageable);

    @Query(value = "select c from Category c where c.name like %?1%")
    Category findByName(String name);
}
