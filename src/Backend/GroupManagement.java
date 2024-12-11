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
        String creator ="";

        for (User user : group.getUserRoles().keySet()) {
            if (group.getUserRoles().get(user).equals("user")) {
                usersArray.put(user.getUserId());
            } else if (group.getUserRoles().get(user).equals("admin")) {
                adminsArray.put(user.getUserId());
            } else if (group.getUserRoles().get(user).equals("creator")) {
                creator = user.getUserId();
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
        // Convert the Group object to a JSON object
        JSONObject groupJson = toJSONObject(group);

        // Flag to check if the group is found
        boolean groupFound = false;

        // Iterate over the groups array
        for (int i = 0; i < groupsArray.length(); i++) {
            JSONObject currentGroup = groupsArray.getJSONObject(i);

            // Check if the groupId matches
            if (currentGroup.getString("groupId").equals(group.getGroupId())) {
                // Replace the existing group
                groupsArray.put(i, groupJson);
                groupFound = true;
                break; // Exit the loop as we found the group
            }
        }

        // If the group was not found, optionally add it to the array
        if (!groupFound) {
            groupsArray.put(groupJson);
            JOptionPane.showMessageDialog(null, "Group saved successfully!");
        }

        // Write the updated array back to the file
        databaseManager.writeJSONFile(GROUPS_FILE_PATH, groupsArray);

    }

}
