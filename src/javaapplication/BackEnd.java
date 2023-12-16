/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication;


import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.List;
import javax.security.auth.login.LoginException;


/**
 *
 * @author N
 */
public class BackEnd {
 
    
    
    // Placeholder for user data (replace this with a database connection)
    private List<User> userList;
private List<Medicine> medicineList;
    // Constructor to initialize the BackEnd class (e.g., setup database connection)
    public BackEnd() {
        // Initialize any necessary resources or connections
         // For example: userList = initializeUserListFromDatabase();
    userList = new ArrayList<>(); // Initialize the userList to an empty list

    // Add sample users for testing
    userList.add(new User(1, "John Doe", "john.doe@example.com", "password123", "john_doe", "user_image.jpg", "Pharmacist", "Active", new Date(), new Date(), 123456789, "Pharmacist", "Central"));
    userList.add(new User(2, "Alice Smith", "alice.smith@example.com", "password456", "alice_smith", "user_image.jpg", "Pharmacist", "Active", new Date(), new Date(), 987654321, "Pharmacist", "East"));
    userList.add(new User(3, "Bob Johnson", "bob.johnson@example.com", "password789", "bob_johnson", "user_image.jpg", "Pharmacist", "Active", new Date(), new Date(), 555555555, "Pharmacist", "West"));
   userList.add(new User(1, "John Doe", "john.doe@example.com", "password123","john_doe", "user_image.jpg", "Pharmacist", "Active",new Date(), new Date(), 123456789, "Pharmacist", "Central"));
    // Add more users as needed

    // For example: medicineList = initializeMedicineListFromDatabase();
    medicineList = new ArrayList<>(); // Initialize the medicineList to an empty listt
         // Initialize any necessary resources or connections
    // For example: medicineList = initializeMedicineListFromDatabase();
    medicineList = new ArrayList<>(); // Initialize the medicineList to an empty list
    }

    /**
     * Method for user login.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the login is successful, false otherwise.
     */
  /**
     * Method for user login.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the login is successful, false otherwise.
     */
    public boolean login(String username, String password) throws LoginException {
    // Check if userList is null or empty before iterating
    if (userList != null && !userList.isEmpty()) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Login successful
            }
        }
        throw new LoginException("Invalid username or password");
    } else {
        throw new LoginException("User list is empty or null");
    }
}

    /**
     * Display user list in a dialog.
     */
    public void displayUserListDialog() {
        StringBuilder userListInfo = new StringBuilder("User List:\n");

        // Check if userList is null or empty before iterating
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
                userListInfo.append("ID: ").append(user.getId()).append(", Name: ").append(user.getName()).append("\n");
                // Add more user attributes as needed
            }
        } else {
            userListInfo.append("No users found");
        }

        JOptionPane.showMessageDialog(null, userListInfo.toString(), "User List", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method for getting user qualification.
     *
     * @param username The username of the user.
     * @return True if the qualification is retrieved successfully, false otherwise.
     */
    public boolean getQualification(String username) {
        // Placeholder code, replace with actual implementation
        // You might fetch this information from a database or external system
        return true;
    }

    /**
     * Method for setting user qualifiers.
     *
     * @param username   The username of the user.
     * @param qualifiers The list of qualifiers to set.
     * @return True if the qualifiers are set successfully, false otherwise.
     */
    public boolean setQualifiers(String username, List<String> qualifiers) {
        // Placeholder code, replace with actual implementation
        // You might update the user's qualifications in a database
        return true;
    }

    // Additional methods can be added based on specific requirements

    // Placeholder for initializing user list from a database (replace this with actual code)
    private List<User> initializeUserListFromDatabase() {
        // Example code for illustration (replace with actual database access)
        // ...
        return new ArrayList<>(); // Return an empty list or the fetched list from the database
    }

    // Getter for userList
    public List<User> getUserList() {
        return userList;
    }

    // Setter for userList
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user) {
    // Implementation for adding a user
    // Placeholder code, replace with actual implementation
    userList.add(user);
}

   public void addMedicine(Medicine medicine) {
    // Implementation for adding a medicine
    // Placeholder code, replace with actual implementation
    medicineList.add(medicine);
}
    
}
