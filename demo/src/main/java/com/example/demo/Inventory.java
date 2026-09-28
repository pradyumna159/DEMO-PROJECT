package com.example.demo;
import java.io.*;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> products;
    private final String FILE_NAME = "demo/products.txt";

    public Inventory() {
        products = new ArrayList<>();
        loadFromFile();
    }

    public Product addProduct(Product product) {
        products.add(product);
        saveToFile();
        System.out.println("🎉 Product added successfully!");
        return product;
    }

    public void viewInventory() {
        if (products.isEmpty()) {
            System.out.println("⚠️ Inventory is currently empty.");
            return;
        }
        System.out.println("\n=== Current Inventory ===");
        double totalInventoryValue = 0;

        for (Product p : products) {
            double productStockValue = p.getPrice() * p.getQuantity();
            totalInventoryValue += productStockValue;
            System.out.println(p + String.format(" | Total Value: $%.2f", productStockValue));
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.format("📈 Total Cumulative Inventory Valuation: $%.2f\n", totalInventoryValue);
    }

     public Product updateStock(int id, int newQuantity) {
        Product product = findProduct(id);
        if (product != null) {
            product.setQuantity(newQuantity);
            saveToFile();
            System.out.println("🔄 Stock updated successfully!");
            return product;
        } else {
            System.out.println("❌ Product ID not found.");
            return null;
        }
    }

    
    public boolean deleteProduct(int id) {
        Product product = findProduct(id);
        if (product != null) {
            products.remove(product);
            saveToFile();
            System.out.println("🗑️ Product removed successfully!");
            return true;
        } else {
            System.out.println("❌ Product ID not found.");
            return false;
        }
    }
    
    public ArrayList<Product> getProducts() {
        return products;
    }

    private Product findProduct(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private void saveToFile() {
       try {
        File file = new File(FILE_NAME);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Product p : products) {
                writer.write(p.toCsvString());
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.out.println("❌ Error saving data: " + e.getMessage());
    }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                int quantity = Integer.parseInt(tokens[2]);
                double price = Double.parseDouble(tokens[3]);
                products.add(new Product(id, name, quantity, price));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("❌ Error loading records: " + e.getMessage());
        }
    }
}