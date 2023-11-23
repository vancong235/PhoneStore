
package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/{pageNo}")
    public String getAllSupplier(@PathVariable("pageNo") int pageNo,Model model, Principal principal) {
        //List<Supplier> listSupp = supplierService.getAllSuppliers();
        Page<Supplier> listSupp = supplierService.pageSupplier(pageNo);
        model.addAttribute("listSupplier", listSupp);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listSupp.getTotalPages());
        model.addAttribute("supplierNew", new Supplier());
        return "supplier";
    }

    @PostMapping("/add-supplier")
    public String add(@ModelAttribute("supplierNew") Supplier supplier, Model model, RedirectAttributes attributes) {
        try {
            supplierService.save(supplier);
            model.addAttribute("supplierNew", supplier);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/supplier";
    }

    @RequestMapping(value = "/findByIdSupplier/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Supplier findById(@PathVariable("id") Integer id){
        return supplierService.findById(id);
    }

    @GetMapping("/update-supplier")
    public String update(Supplier supplier, RedirectAttributes attributes) {
        try {
            supplierService.update(supplier);
            attributes.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/supplier/0";
    }

    @RequestMapping(value = "/enable-supplier", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            supplierService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/supplier/0";
    }

    @RequestMapping(value = "/delete-supplier", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            supplierService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/supplier/0";
    }

    @GetMapping("/search-supplier/{pageNo}")
    public String searchSupplier(@PathVariable("pageNo") int pageNo,
                              @RequestParam("keyword") String keyword,
                              Model model, Principal principal) {
        Page<Supplier> listSupplier = supplierService.searchSuppliers(pageNo, keyword);
        model.addAttribute("size", listSupplier.getSize());
        model.addAttribute("listSupplier", listSupplier);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listSupplier.getTotalPages());
        model.addAttribute("supplierNew", new Supplier());
        return "supplier";
    }
}

