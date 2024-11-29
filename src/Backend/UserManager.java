package Backend;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

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
                System.out.println("Email already exists!");
                return false;
            }
        }

        // Create a new user
        User newUser = new User(name, email, username, hashedPassword, dateOfBirth);

        // Save the user to the file
        String userId = UUID.randomUUID().toString();
        JSONObject newUserObject = new JSONObject();
        newUserObject.put("userId", userId);
        newUserObject.put("email", email);
        newUserObject.put("username", username);
        newUserObject.put("hashedPassword", hashedPassword);
        newUserObject.put("dateOfBirth", dateOfBirth);
        newUserObject.put("status", "offline");

        users.put(newUserObject);
        DatabaseManager.writeJSONFile(USERS_FILE, users);

        return true;
    }
}
