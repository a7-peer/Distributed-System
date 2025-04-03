package org.example.springapi.controller;

import org.example.springapi.exception.VendorNotFoundException;
import org.example.springapi.model.Vendor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
public class ApiService {

    private Vendor vendor;

    @GetMapping("/{id}")
    public Vendor getVendorDetails(@PathVariable int id) {
        if (vendor == null || vendor.getId() != id) {
            throw new VendorNotFoundException("Vendor with ID " + id + " not found.");
        }
        return vendor;
    }

    @PostMapping
    public String createVendorDetails(@RequestBody Vendor vendor) {
        this.vendor = vendor;
        return "Vendor Created Successfully";
    }

    @PutMapping
    public String updateVendorDetails(@RequestBody Vendor vendor) {
        if (this.vendor == null) {
            throw new VendorNotFoundException("No existing vendor to update.");
        }
        this.vendor = vendor;
        return "Vendor Updated Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteVendorDetails(@PathVariable int id) {
        if (vendor == null || vendor.getId() != id) {
            throw new VendorNotFoundException("Vendor with ID " + id + " not found.");
        }
        this.vendor = null;
        return "Vendor Deleted Successfully";
    }

    // Exception Handler
    @ExceptionHandler(VendorNotFoundException.class)
    public String handleVendorNotFound(VendorNotFoundException ex) {
        return ex.getMessage();
    }
}
