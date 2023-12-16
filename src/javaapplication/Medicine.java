/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author N
 */
public class Medicine {
 
    
    
    
   private int id;
    private String name;
    private String type;
    private String packing;
    private Date expiry;
    private Date purchaseDate;
    private int stock;
    private int stockAfterSelling;

    // Constructor for creating Medicine instances with specified attributes
    public Medicine(int id, String name, String type, String packing, Date expiry,
                    Date purchaseDate, int stock, int stockAfterSelling) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.packing = packing;
        this.expiry = expiry;
        this.purchaseDate = purchaseDate;
        this.stock = stock;
        this.stockAfterSelling = stockAfterSelling;
    }

    // Additional constructor for creating Medicine instances with additional attributes
    public Medicine(String name, String brand, String category, String type, String packing,
                    String expiryDate, double purchasePrice, int stock, String pic, double sellPrice, String description) {
        // Call the existing constructor with default values for id, purchaseDate, and stockAfterSelling
        this(0, name, type, packing, null, null, stock, 0);

        // Additional logic for setting other attributes based on the provided parameters
        // Note: You need to handle Date conversion from expiryDate and possibly other details
        // Replace the placeholder implementation with actual logic
        setExpiry(/* Convert expiryDate to Date */);
        setPurchaseDate(/* Convert purchaseDate to Date */);
        // Set other attributes as needed
    }

    
    // Method to display medicine information in a dialog
    public void displayMedicineInfoDialog() {
        StringBuilder medicineInfo = new StringBuilder();
        medicineInfo.append("ID: ").append(id).append("\n");
        medicineInfo.append("Name: ").append(name).append("\n");
        medicineInfo.append("Type: ").append(type).append("\n");
        medicineInfo.append("Packing: ").append(packing).append("\n");
        medicineInfo.append("Expiry Date: ").append(expiry).append("\n");
        medicineInfo.append("Purchase Date: ").append(purchaseDate).append("\n");
        medicineInfo.append("Stock: ").append(stock).append("\n");
        medicineInfo.append("Stock After Selling: ").append(stockAfterSelling).append("\n");

        // Check if the medicine is expired
        if (isExpired()) {
            medicineInfo.append("Status: Expired\n");
        } else {
            medicineInfo.append("Status: Not Expired\n");
        }

        JOptionPane.showMessageDialog(null, medicineInfo.toString(), "Medicine Information", JOptionPane.INFORMATION_MESSAGE);
    }

    
    
    // Getter and setter methods for all attributes

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPacking() {
        return packing;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockAfterSelling() {
        return stockAfterSelling;
    }

    public void setStockAfterSelling(int stockAfterSelling) {
        this.stockAfterSelling = stockAfterSelling;
    }

   /**
     * Check if the medicine is expired based on the current date.
     *
     * @return True if the medicine is expired, false otherwise.
     */
    public boolean isExpired() {
        if (expiry != null) {
            // Get the current date
            Date currentDate = new Date();

            // Compare the expiry date with the current date
            return expiry.before(currentDate);
        }
        // If expiry date is not set, consider the medicine as not expired
        return false;
    }

    // Other additional methods can be added based on specific requirements

    /**
     * Example: Calculate the profit for selling the medicine.
     *
     * @param sellingPrice The selling price of the medicine.
     * @return The profit amount.
     */
   
    // You can continue adding more methods as needed

    private void setExpiry() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setPurchaseDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String getCategory() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String getBrand() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}
