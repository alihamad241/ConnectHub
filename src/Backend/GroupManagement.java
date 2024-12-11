package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

public class GroupManagement {
    public static ArrayList<Content> allgroups = new ArrayList<>();
    private static final String GROUPS_FILE_PATH ="databases/groups.json" ;
    private static final DatabaseManager databaseManager = Backend.DatabaseManager.getInstance();
    public static JSONArray groupsArray = databaseManager.readJSONFile(GROUPS_FILE_PATH);

    public static JSONObject toJSONObject(RealGroup group) {
        JSONArray usersArray = new JSONArray();
        JSONArray adminsArray = new JSONArray();
        User creator =null;

        for (User user : group.getUserRoles().keySet()) {
            if (group.getUserRoles().get(user).equals("user")) {
                usersArray.put(user);
            } else if (group.getUserRoles().get(user).equals("admin")) {
                adminsArray.put(user);
            } else if (group.getUserRoles().get(user).equals("creator")) {
                creator = user;
            }
        }

        JSONObject groupJson = new JSONObject();
        groupJson.put("name", group.getName());
        groupJson.put("description",group.getDescription());
        groupJson.put("contents",group.getContents());
        groupJson.put("pendingRequests",group.getPendingRequests());
        groupJson.put("groupId", group.getGroupId());
        groupJson.put("photoPath", group.getPhotoPath());
        groupJson.put("users", usersArray);
        groupJson.put("admins", adminsArray);
        groupJson.put("creator", creator);

        return groupJson;
    }

    public static void saveGroupToFile(RealGroup group) {
        // Get the instance of DatabaseManager
        DatabaseManager dbManager = DatabaseManager.getInstance();

        // Read the current JSON data in the file
        JSONArray groupsArray = dbManager.readJSONFile(GROUPS_FILE_PATH);

        // If the file is empty or doesn't exist, initialize an empty JSONArray
        if (groupsArray == null) {
            groupsArray = new JSONArray();
        }

        // Convert the Group object to a JSON object
        JSONObject groupJson = toJSONObject(group);

        // Add the group JSON to the array
        groupsArray.put(groupJson);

        // Write the updated array back to the file
        boolean success = dbManager.writeJSONFile(GROUPS_FILE_PATH, groupsArray);
        if (success) {
            JOptionPane.showMessageDialog(null, "Group saved successfully to " + GROUPS_FILE_PATH);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to save the group to " + GROUPS_FILE_PATH);
        }
    }

}
