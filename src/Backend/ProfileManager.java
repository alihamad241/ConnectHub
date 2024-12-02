package Backend;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;



public class ProfileManager {
    private static final String USERS_FILE = "databases/users.json";
    JSONArray users;

    public  JSONArray getUsers() throws IOException {
        return DatabaseManager.readJSONFile(USERS_FILE);
    }

    public  void saveUsers(JSONArray users) throws IOException {
        DatabaseManager.writeJSONFile(USERS_FILE, users);
    }

    public  User findUserById(String userId) throws IOException {
        JSONArray users = getUsers();
        for (Object user : users) {
            if (((User)user).getUserId().equals(userId)) {
                return (User) user;
            }
        }
        return null;
    }
}
