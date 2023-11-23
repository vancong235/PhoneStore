
package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Supplier;
import com.assignments.ecomerce.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private  SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return (List<Supplier>) supplierRepository.findAll();
    }

    public Supplier save(Supplier supplier) {
        supplier.setStatus(1);
        return supplierRepository.save(supplier);
    }

    public Page<Supplier> pageSupplier(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return supplierRepository.pageSupplier(pageable);
    }

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).get();
    }

    public Supplier update(Supplier supplier) {
        Supplier supplierUpdate = null;
        try {
            supplierUpdate = supplierRepository.findById(supplier.getId()).get();
            supplierUpdate.setName(supplier.getName());
            supplierUpdate.setPhoneNumber(supplier.getPhoneNumber());
            supplierUpdate.setAddress(supplier.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierRepository.save(supplierUpdate);
    }

    public void deleteById(Integer id) {
        Supplier supplier = supplierRepository.getById(id);
        supplierRepository.save(supplier);
    }

    public void enableById(Integer id) {
        Supplier supplier = supplierRepository.getById(id);
        supplierRepository.save(supplier);
    }

    public Page<Supplier> searchSuppliers(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Supplier> supplier = transfer(supplierRepository.searchSupplier(keyword));
        Page<Supplier> supplierPages = toPage(supplier, pageable);
        return supplierPages;
    }

    private Page toPage(List<Supplier> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Supplier> transfer(List<Supplier> suppliers) {
        List<Supplier> supplierList = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            Supplier newSupplier = new Supplier();
            newSupplier.setId(supplier.getId());
            newSupplier.setName(supplier.getName());
            newSupplier.setPhoneNumber(supplier.getPhoneNumber());
            newSupplier.setAddress(supplier.getAddress());
            supplierList.add(newSupplier);
        }
        return supplierList;
    }

    public Supplier updateStatus(Integer id) {
        Supplier supplierUpdate = null;
        try {
            supplierUpdate = supplierRepository.findById(id).get();
            supplierUpdate.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierRepository.save(supplierUpdate);
    }
}

