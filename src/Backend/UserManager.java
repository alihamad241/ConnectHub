package Backend;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class UserManager {
    private static final String USERS_FILE = "databases/users.json";

    public boolean signup(String name, String email, String username, String password, String dateOfBirth) {
        // Hash the password
        String hashedPassword = PasswordHashing.hashPassword(password);

        // Check if email already exists
        JSONArray users = DatabaseManager.readJSONFile(USERS_FILE);
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("email").equals(email)) {
                JOptionPane.showMessageDialog(null,"Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Create a new user
        User newUser = new User(name, email, username, hashedPassword, dateOfBirth);

        // Save the user to the file
        String userId = UUID.randomUUID().toString();
        JSONObject newUserObject = new JSONObject();
        newUserObject.put("Name", name);
        newUserObject.put("userId", userId);
        newUserObject.put("email", email);
        newUserObject.put("username", username);
        newUserObject.put("hashedPassword", hashedPassword);
        newUserObject.put("dateOfBirth", dateOfBirth);
        newUserObject.put("status", "offline");

        users.put(newUserObject);


        return DatabaseManager.writeJSONFile(USERS_FILE, users);
    }

    public User login(String username, String password) {
        // Check if the user exists
        JSONArray users = DatabaseManager.readJSONFile(USERS_FILE);
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("username").equals(username)) {
                // Check if the password is correct
                if (PasswordHashing.checkPassword(password, user.getString("hashedPassword"))) {
                    // Return the user
                    JOptionPane.showMessageDialog(null,"Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    User newUser = new User(user.getString("userId"), user.getString("email"), user.getString("username"), user.getString("hashedPassword"), user.getString("dateOfBirth"));
                    newUser.setStatus("online");
                    return newUser;
                } else {
                    JOptionPane.showMessageDialog(null,"Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        }

        JOptionPane.showMessageDialog(null,"User not found!", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
}
