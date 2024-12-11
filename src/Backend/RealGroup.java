package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RealGroup implements Group {

    private String name;
    private String description;
    private ArrayList<Content> contents;
    private ArrayList<User> pendingRequests;
    private String groupId;
    private String photoPath;
    private HashMap<User, String> userRoles;


    private RealGroup(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.contents = builder.contents;
        this.groupId = builder.groupId;
        this.photoPath = builder.photoPath;
        this.pendingRequests =builder.pendingRequests;
        this.userRoles = builder.userRoles;
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



    @Override
    public void addContent(Content content) {
       contents.add(content);
       GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void removeContent(Content content) {
        contents.remove(content);
        GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void addPendingRequest(User user) {
         pendingRequests.add(user);
        GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void removePendingRequest(User user) {
        pendingRequests.remove(user);
        GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void addUser(User user, String role) {
        userRoles.put(user, role);
        GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void removeUser(User user) {
        if (userRoles.containsKey(user)) {
            userRoles.remove(user);
            GroupManagement.saveGroupToFile(this);
        }
    }



    @Override
    public void deleteGroup() {

    }

    @Override
    public void promoteUser(User user) {
        if (userRoles.containsKey(user)) {
            userRoles.replace(user, "admin");
            GroupManagement.saveGroupToFile(this);
        }
    }

    @Override
    public void demoteUser(User user) {
        if (userRoles.containsKey(user)) {
            userRoles.replace(user, "user");
            GroupManagement.saveGroupToFile(this);
        }
    }

    @Override
    public void approveRequest(User user) {
         userRoles.put(user, "user");
        GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void rejectRequest(User user) {
      pendingRequests.remove(user);
        GroupManagement.saveGroupToFile(this);
    }

    @Override
    public void leaveGroup(User user) {
       userRoles.remove(user);
        GroupManagement.saveGroupToFile(this);
    }

    public static class Builder {
        private String name;
        private String description;
        private ArrayList<Content> contents;
        private ArrayList<User> pendingRequests;
        private String groupId;
        private String photoPath;
        private HashMap<User, String> userRoles;
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setContents(ArrayList<Content> contents) {
            this.contents = contents;
            return this;
        }

        public Builder addContent(Content content) {
            this.contents.add(content);  // Use this method if adding one content at a time
            return this;
        }

        public Builder setPendingRequests(ArrayList<User> pendingRequests) {
            this.pendingRequests = pendingRequests;
            return this;
        }

        public Builder addPendingRequest(User user) {
            this.pendingRequests.add(user);  // Use this method if adding one user at a time
            return this;
        }

        public Builder setGroupId() {
            this.groupId = UUID.randomUUID().toString();;
            return this;
        }

        public Builder setPhotoPath(String photoPath) {
            this.photoPath = photoPath;
            return this;
        }

        public Builder setUserRoles(HashMap<User, String> userRoles) {
            this.userRoles = userRoles;
            return this;
        }

        public Builder addUserRole(User user, String role) {
            this.userRoles.put(user, role);  // Use this method to add a user with a role
            return this;
        }

        // Build the GroupManagement object
        public RealGroup build() {
            return new RealGroup(this);
        }
    }
}
