/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication;


import java.util.Date;
import javaapplication.User;
import javaapplication.Medicine;
import javaapplication.BackEnd;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;


/**
 *
 * @author N
 */
public class JavaApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        // Creating a sample user
    User user = new User(1, "John Doe", "john.doe@example.com", "password123",
            "john_doe", "user_image.jpg", "Pharmacist", "Active",
            new Date(), new Date(), 123456789, "Pharmacist", "Central");

    
    
    
    // Displaying user information
        user.displayUserInfoDialog();

        // Checking if the user is an administrator
        if (user.isAdmin()) {
            System.out.println("User is an administrator.");
        } else {
            System.out.println("User is not an administrator.");
        }
    
    
    
    // Creating a sample medicine
    Medicine medicine = new Medicine(101, "Paracetamol", "Tablet", "Bottle",
            Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()),
            new Date(),
            100, 80);

    // Creating an instance of the BackEnd class
    BackEnd backend = new BackEnd();

    // Performing some operations using the BackEnd class
    try {
            // Attempt to log in
            boolean loginResult = backend.login("john_doe", "password123");  // Use correct username and password
            if (loginResult) {
                if (user.getUsername().equals("john_doe") && user.getPassword().equals("password123")) {
                    boolean qualification = backend.getQualification(user.getUsername());
                    System.out.println("User Qualification: " + qualification);

                    backend.addUser(user);
                    backend.addMedicine(medicine);

                    // Display user list in a dialog
                    backend.displayUserListDialog();

                    // You can continue performing more operations as needed
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed: Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Login failed: Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources if needed
        }
        
        
        
    }
    
}
