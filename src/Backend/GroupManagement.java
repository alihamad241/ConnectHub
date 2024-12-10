package Backend;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupManagement implements Group {

    private String name;
    private String description;
    private ArrayList<Content> contents;
    private ArrayList<User> pendingRequests;
    private String groupId;
    private String photoPath;
    private HashMap<User, String> userRoles;
    private static final String GROUPS_FILE_PATH ="databases/groups.json" ;
    private static final DatabaseManager databaseManager = Backend.DatabaseManager.getInstance();
    public static JSONArray groupsArray = databaseManager.readJSONFile(GROUPS_FILE_PATH);

    private GroupManagement(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.contents = builder.contents;
        this.groupId = builder.groupId;
        this.photoPath = builder.photoPath;
        this.pendingRequests = new ArrayList<>();
        this.userRoles = new HashMap<>();
    }

    public HashMap<User, String> getUserRoles() {
        return userRoles;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public ArrayList<User> getPendingRequests() {
        return pendingRequests;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String toString() {
        StringBuilder users = new StringBuilder();
        StringBuilder admins = new StringBuilder();
        String creator = "";
        for (User user : userRoles.keySet()) {
            if (userRoles.get(user).equals("user")) {
                users.append(user.getUsername()).append(", ");
            } else if (userRoles.get(user).equals("admin")) {
                admins.append(user.getUsername()).append(", ");
            } else if (userRoles.get(user).equals("creator")) {
                creator = user.getUsername();
            }
        }

        return
                "name=" + name  +
                ", description='" + description +
                ", contents=" + contents +
                ", pendingRequests=" + pendingRequests +
                ", groupId='" + groupId  +
                ", photoPath='" + photoPath+
                        " ,Users =["+users+"]"+
                ", Admins= ["+admins+"]"+
                ", creator="+creator;

    }

    @Override
    public void addContent(Content content) {
       contents.add(content);

    }

    @Override
    public void removeContent(Content content) {
        contents.remove(content);
    }

    @Override
    public void addPendingRequest(User user) {
         pendingRequests.add(user);
    }

    @Override
    public void removePendingRequest(User user) {
        pendingRequests.remove(user);
    }

    @Override
    public void addUser(User user, String role) {
        userRoles.put(user, role);
    }

    @Override
    public void removeUser(User user) {
        if (userRoles.containsKey(user))
          userRoles.remove(user);
    }



    @Override
    public void deleteGroup() {

    }

    @Override
    public void promoteUser(User user) {
        if (userRoles.containsKey(user)) {
            userRoles.replace(user, "admin");
        }
    }

    @Override
    public void demoteUser(User user) {
        if (userRoles.containsKey(user)) {
            userRoles.replace(user, "user");
        }
    }

    @Override
    public void approveRequest(User user) {
         userRoles.put(user, "user");
    }

    @Override
    public void rejectRequest(User user) {
      pendingRequests.remove(user);
    }

    @Override
    public void leaveGroup(User user) {
       userRoles.remove(user);
    }

    public static class Builder {
        private String name;
        private String description;
        private ArrayList<Content> contents;
        private String groupId;
        private String photoPath;


        public Builder(String name, String description, String groupId) {
            this.name = name;
            this.description = description;
            this.groupId = groupId;
            this.contents = new ArrayList<>();

        }

        public Builder photoPath(String photoPath) {
            this.photoPath = photoPath;
            return this;
        }

        public GroupManagement build() {
            return new GroupManagement(this);
        }

    }

}
