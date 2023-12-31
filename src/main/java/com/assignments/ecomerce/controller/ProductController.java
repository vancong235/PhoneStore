package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UserService userService;

    @GetMapping("/product-details/{id}")
    public String DetailProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        List<Product> productDtoList = productService.findAllByCategory(product.getCategory().getName());
        List<Category> categories = categoryService.getAllCategory();


        model.addAttribute("products", productDtoList);
        model.addAttribute("productDetail", product);
        model.addAttribute("categories", categories);
        return "product-detail";
    }

    @GetMapping("/ViewByCategory/{pageNo}")
    public String ViewByCategory(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("categoryId") Integer categoryId, Model model) {
        List<Category> categories = categoryService.getAllCategory();
        Page<Product> listProducts = productService.pageProductByCategory(pageNo, categoryId);
        Category category = categoryService.findById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("size", listProducts.getSize());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        return "subcategory";
    }

    @GetMapping("/product")
    public String getAllProducts(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        List<Product> listProducts = productService.getAllProducts();
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        return "product";
    }

    @RequestMapping(value = "/findByIdProduct/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Product findById(@PathVariable("id") Integer id) {
        return productService.findById(id);
    }


    @GetMapping("/product/{pageNo}")
    public String pageProduct(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        List<Category> categories = categoryService.getAllCategory();
        Page<Product> listProducts = productService.pageProducts(pageNo, 9);

        Users users = userService.findByEmail(principal.getName());
        model.addAttribute("user", users);


        model.addAttribute("categories", categories);
        model.addAttribute("size", listProducts.getSize());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("productNew", new Product());
        return "product";
    }

    @GetMapping("/search-products/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam("keyword") String keyword,
                                Model model, Principal principal,HttpSession session) {

        Page<Product> listProducts = productService.searchProducts(pageNo, keyword, 9);

        List<Category> categories = categoryService.getAllCategory();

        Users users = userService.findByEmail(principal.getName());
        model.addAttribute("user", users);

        session.setAttribute("keyword", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categories);

        model.addAttribute("size", listProducts.getSize());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("productNew", new Product());
        return "product";
    }

    @GetMapping("/search-productByKeyword/{pageNo}")
    public String searchProductByCategory(@PathVariable("pageNo") int pageNo,
                                          @RequestParam("keyword") String keyword,
                                          Model model, Principal principal, HttpSession session) {

        Page<Product> listProducts = productService.searchProducts(pageNo, keyword);
        List<Category> categories = categoryService.getAllCategory();

        Users users = userService.findByEmail(principal.getName());
        model.addAttribute("user", users);

        session.setAttribute("keyword", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categories);
        model.addAttribute("size", listProducts.getSize());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        return "searchCategory";
    }

    @GetMapping("/detail-product/{id}")
    public String DetailProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
        Product newProduct = productService.getById(id);
        List<Category> categories = categoryService.getAllCategory();
        List<String> listSizeProduct = productService.getAllSizeProduct(newProduct.getName().trim());
        Integer getSoldShoeCount = productService.getSoldShoeCount(id);
        Users users = userService.findByEmail(principal.getName());
        model.addAttribute("user", users);
        model.addAttribute("categories", categories);
        model.addAttribute("listProducts", newProduct);
        model.addAttribute("listSizeProduct", listSizeProduct);
        model.addAttribute("getSoldShoeCount", getSoldShoeCount);
        return "detail";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute("productNew") Product product,
                             @RequestParam("photo_file") MultipartFile photo,
                             Model model, Principal principal,
                             RedirectAttributes attributes) {
        try {
            productService.save(photo, product);
            model.addAttribute("productNew", product);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/product/0";
    }


    @GetMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id);
        String productImage = product.getImage();
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("newProduct", product);
        model.addAttribute("productImage", productImage);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdateProduct(@PathVariable("id") Integer id,
                                       @ModelAttribute("newProduct") Product product,
                                       @RequestParam("photo_file") MultipartFile photo_file,
                                       RedirectAttributes attributes) {
        try {
            System.out.println(id);
            productService.update(photo_file, product);
            attributes.addFlashAttribute("success", "Update successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update");
        }
        return "redirect:/product/0";
    }

    @RequestMapping(value = "/status-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/product/0";
    }

    //    function for user
    @GetMapping("/")
    public String login(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            Model model,
            Principal principal,
            HttpSession session
    ) {
        int defaultPageNo = 0;
        String defaultKeyWord = "";

        if (pageNo == null) {
            pageNo = defaultPageNo;
        }
        if (keyword == null) {
            keyword = defaultKeyWord;
        } else {
            session.setAttribute("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        if (principal != null) {
            model.addAttribute("name", principal.getName());
        } else {
            model.addAttribute("name", "");
        }
        List<Category> categories = categoryService.getAllCategory();
        Page<Product> listProducts = productService.searchProducts(pageNo, keyword, 9);
        model.addAttribute("categories", categories);
        model.addAttribute("size", listProducts.getSize());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listProducts.getTotalPages());

        return "index";
    }
    @GetMapping("/userProductDetail/{id}")
    public String userProductDetail(@PathVariable("id") Integer id, Principal principal, Model model) {
        Product newProduct = productService.getById(id);

        List<Comments> comments = commentsService.findByProductId(newProduct.getId());
        for (Comments comment : comments) {
            System.out.println("Comment ID: " + comment.getId());
            System.out.println("Comment Content: " + comment.getContent());
            System.out.println("Comment Start: " + comment.getStar());
            System.out.println("Comment Start: " + comment.getCustomer().getEmail());
            System.out.println("--------------------");
        }

        if (principal != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            Users user = userService.findByEmail(principal.getName());
            model.addAttribute("userId", user.getId());
            model.addAttribute("user", userDetails);
            model.addAttribute("email", principal.getName());
            model.addAttribute("name", user.getFullname());
            Customer customer = customerService.findByEmail(principal.getName());
            model.addAttribute("comments", comments);
        } else {

        }
        List<Product> listProduct = productService.getProductsWithDifferentSizes(newProduct.getName());

        model.addAttribute("product", newProduct);
        model.addAttribute("productSizeList", listProduct);
        return "userProductDetail";
    }
}