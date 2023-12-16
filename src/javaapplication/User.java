/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author N
 */





public class User {
    
    
    
     // Attributes
    private int id;
    private String name;
    private String email;
    private String password;
    private String username;
    private String image;
    
    private String status;
    private Date joinDate;
    private Date lastLogin;
    private int phone;
    private String title;
    private String central;
      private UserRole role;

    
    
    

    // Constructor to initialize the User object
   public User(int id, String name, String email, String password, String username, String image, String status, String title, Date joinDate, Date lastLogin, int phone, String central, String central1) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.image = image;
         this.role = role;
        this.status = status;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
        this.phone = phone;
        this.title = title;
        this.central = central;
    }

   
   public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    // Getter methods for all attributes
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

   

    public String getStatus() {
        return status;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public int getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

    public String getCentral() {
        return central;
    }

    // Setter methods for attributes that may be modified
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImage(String image) {
        this.image = image;
    }

   

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCentral(String central) {
        this.central = central;
    }

    // Additional methods can be added based on specific requirements

    // For example, a method to check if the user is an administrator
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(role);
    }

    // Another example: a method to check if the user's password is expired
    public boolean isPasswordExpired() {
        // Add logic to check if the password is expired
        // This is just a placeholder, replace it with your actual logic
        return false;
    }
    
    // Method to display user information in a dialog
   public void displayUserInfoDialog() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("ID: ").append(id).append("\n");
        userInfo.append("Name: ").append(name).append("\n");
        userInfo.append("Email: ").append(email).append("\n");
        userInfo.append("Role: ").append(role).append("\n"); // Display the role
        // Add more attributes as needed

        JOptionPane.showMessageDialog(null, userInfo.toString(), "User Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
    


