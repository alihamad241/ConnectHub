package Backend;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

public class ProfileManager {
    private static final String USERS_FILE = "databases/users.json";
    JSONArray users;

    public  List<UserProfile> getUsers() throws IOException {
        return JSONArray users = DatabaseManager.readJSONFile(USERS_FILE);
    }

    public  void saveUsers(List<UserProfile> users) throws IOException {
        DatabaseManager.writeJSONFile(USERS_FILE, users);
    }

    public  UserProfile findUserById(String userId) throws IOException {
        List<UserProfile> users = getUsers();
        for (UserProfile user : users) {
            if (user.getUserID().equals(userId)) {
                return user;
            }
}
