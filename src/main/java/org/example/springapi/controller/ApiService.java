package org.example.springapi.controller;

import org.example.springapi.model.Vendor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendors")
public class ApiService {

    private final List<Vendor> vendorList = new ArrayList<>(Arrays.asList(
            new Vendor("Laptop", 1, "A", "A"),
            new Vendor("Smartphone", 2, "B", "B"),
            new Vendor("Headphones", 3, "C", "C"),
            new Vendor("Keyboard", 4, "D", "D"),
            new Vendor("Monitor", 5, "E", "E")
    ));

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        if (vendorList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendorList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable int id) {
        Optional<Vendor> vendor = vendorList.stream()
                .filter(v -> v.getId() == id)
                .findFirst();

        return vendor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) {
        // Check if vendor with this ID already exists
        boolean idExists = vendorList.stream()
                .anyMatch(v -> v.getId() == vendor.getId());

        // Check if vendor with same name already exists (additional conflict check)
        boolean nameExists = vendorList.stream()
                .anyMatch(v -> v.getName().equalsIgnoreCase(vendor.getName()));

        if (idExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Vendor with ID " + vendor.getId() + " already exists");
        }

        if (nameExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Vendor with name '" + vendor.getName() + "' already exists");
        }

        vendorList.add(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable int id, @RequestBody Vendor vendor) {
        // Validate path ID matches request body ID
        if (vendor.getId() != id) {
            return ResponseEntity.badRequest()
                    .body("Error: Path ID and Vendor ID don't match");
        }

        // Check if vendor exists
        Optional<Vendor> existingVendor = vendorList.stream()
                .filter(v -> v.getId() == id)
                .findFirst();

        if (existingVendor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Check for name conflict with other vendors
        boolean nameConflict = vendorList.stream()
                .filter(v -> v.getId() != id) // exclude current vendor
                .anyMatch(v -> v.getName().equalsIgnoreCase(vendor.getName()));

        if (nameConflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Another vendor with name '" + vendor.getName() + "' already exists");
        }

        // Update the vendor
        vendorList.replaceAll(v -> v.getId() == id ? vendor : v);
        return ResponseEntity.ok(vendor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable int id) {
        boolean removed = vendorList.removeIf(v -> v.getId() == id);

        if (removed) {
            return ResponseEntity.ok("Vendor with ID " + id + " was deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }
}