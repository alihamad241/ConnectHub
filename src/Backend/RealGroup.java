package Backend;

import Backend.Notifications.Notification;
import Backend.Notifications.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class RealGroup implements Group, Observer {

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
        if (userRoles.containsKey(user)) {
            userRoles.remove(user);

        }
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

    @Override
    public void update(Notification notification) {

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

        public Builder setGroupId(String groupId) {
            this.groupId = groupId;
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

    public String toString(){
        return "Group Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Contents: " + contents + "\n" +
                "Pending Requests: " + pendingRequests + "\n" +
                "Group ID: " + groupId + "\n" +
                "Photo Path: " + photoPath + "\n" +
                "User Roles: " + userRoles + "\n";
    }
}
