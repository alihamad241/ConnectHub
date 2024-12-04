package Backend;

import org.json.JSONArray;

import java.io.IOException;



public class ProfileManager {
    private static final String USERS_FILE = "databases/users.json";
    JSONArray users;

    public  JSONArray getUsers() throws IOException {
        return DatabaseManager.readJSONFile(USERS_FILE);
    }

    public  void saveUsers(JSONArray users) throws IOException {
        DatabaseManager.writeJSONFile(USERS_FILE, users);
    }

}
