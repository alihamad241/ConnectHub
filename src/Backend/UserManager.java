package Backend;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class UserManager {
    private static final String USERS_FILE = "databases/users.json";
    public static JSONArray users = DatabaseManager.readJSONFile(USERS_FILE);


    public boolean signup(String name, String email, String username, String password, String dateOfBirth) {
        // Hash the password
        String hashedPassword = PasswordHashing.hashPassword(password);

        // Check if email already exists
        assert users != null : "Users file is empty";
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("email").equals(email)) {
                JOptionPane.showMessageDialog(null, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        String userId = UUID.randomUUID().toString();
        // Create a new user
        User newUser = new User(name, userId, email, username, hashedPassword, dateOfBirth);

        // Save the user to the file

        JSONObject newUserObject = new JSONObject();
        newUserObject.put("Name", name);
        newUserObject.put("userId", userId);
        newUserObject.put("email", email);
        newUserObject.put("username", username);
        newUserObject.put("hashedPassword", hashedPassword);
        newUserObject.put("dateOfBirth", dateOfBirth);
        newUserObject.put("status", "offline");
        newUserObject.put("profilePhotoPath", newUser.getUserProfile().getProfilePhotoPath());
        newUserObject.put("coverPhotoPath", newUser.getUserProfile().getCoverPhotoPath());
        newUserObject.put("bio", newUser.getUserProfile().getBio());

        users.put(newUserObject);

        return DatabaseManager.writeJSONFile(USERS_FILE, users);
    }

    public User login(String username, String password) {
        // Check if the user exists
        assert users != null : "Users file is empty";
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("username").equals(username)) {
                // Check if the password is correct
                if (PasswordHashing.checkPassword(password, user.getString("hashedPassword"))) {
                    // Return the user
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    User newUser = new User(user.getString("Name"), user.getString("userId"), user.getString("email"), user.getString("username"), user.getString("hashedPassword"), user.getString("dateOfBirth"));
                    newUser.setStatus("online");
                    return newUser;
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        }


        JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }


    public void logout(User user) {
        user.setStatus("offline");
    }

    public void saveUserToDatabase(User user) {
        // Save the user to the file
        for (Object obj : users) {
            JSONObject userObject = (JSONObject) obj;
            if (userObject.getString("userId").equals(user.getUserId())) {
                userObject.put("Name", user.getName());
                userObject.put("email", user.getEmail());
                userObject.put("username", user.getUsername());
                userObject.put("hashedPassword", user.getHashedPassword());
                userObject.put("dateOfBirth", user.getDateOfBirth());
                userObject.put("status", user.getStatus());
                userObject.put("profilePhotoPath", user.getUserProfile().getProfilePhotoPath());
                userObject.put("coverPhotoPath", user.getUserProfile().getCoverPhotoPath());
                userObject.put("bio", user.getUserProfile().getBio());
            }
        }
        DatabaseManager.writeJSONFile(USERS_FILE, users);
    }


}
