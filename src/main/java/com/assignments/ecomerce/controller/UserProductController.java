package com.assignments.ecomerce.controller;


import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class UserProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("user/product/{pageNo}")
    public String pageProduct(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        List<Category> categories = categoryService.getAllCategory();
        Page<Product> listProducts = productService.pageProducts(pageNo);
        model.addAttribute("categories", categories);
        model.addAttribute("size", listProducts.getSize());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("productNew", new Product());
        return "index";
    }

}
