package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private Inventory inventory;

    public ProductController() {
        inventory = new Inventory();
    }

    // 1. GET ALL PRODUCTS
    @GetMapping
    public List<Product> getProducts() {
        return inventory.getProducts();
    }

    // 2. ADD NEW PRODUCT (POST)
    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product newProduct) {
        return inventory.addProduct(newProduct);
    }
   // 3. UPDATE PRODUCT (PUT - Stock & Price)
   @PutMapping("/add-stock/{id}")
public boolean addStock(@PathVariable int id, @RequestBody int additionalQty) {
    return inventory.addStock(id, additionalQty);
}
    @PutMapping("/update-stock/{id}")
    public Product updateStock(@PathVariable int id, @RequestBody int newQuantity) {
        return inventory.updateStock(id, newQuantity);
    }
    // 4. DELETE PRODUCT
    @DeleteMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable int id) {
        boolean isDeleted = inventory.deleteProduct(id);
        if (isDeleted) {
            return "Product successfully deleted.";
        } else {
            return "Error: Product not found.";
        }
    }
}