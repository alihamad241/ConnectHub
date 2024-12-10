package Backend;

import java.util.ArrayList;

public class Group {

    private String name;
    private String description;
    private User creator;
    private ArrayList<User> admins;
    private ArrayList<User> members;
    private ArrayList<User> pendingRequests;
    private ArrayList<Content> contents;
    private String groupId;
    private String photoPath;

    private Group(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.creator = builder.creator;
        this.admins = builder.admins;
        this.members = builder.members;
        this.pendingRequests = builder.pendingRequests;
        this.contents = builder.contents;
        this.groupId = builder.groupId;
        this.photoPath = builder.photoPath;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getCreator() {
        return creator;
    }

    public ArrayList<User> getAdmins() {
        return admins;
    }

    public ArrayList<User> getMembers() {
        return members;
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
        return "GroupId" + groupId + "Name" + name + "PhotoPath" + photoPath + "Description" + description + "Creator" + creator+"Admins"+admins+"Members"+members+"PendingRequests"+pendingRequests+"Contents"+contents;

    }

    public void addContent(Content content) {
        contents.add(content);
    }

    public void removeContent(Content content) {
        contents.remove(content);
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    public void addAdmin(User user) {
        admins.add(user);
    }
     public void removeAdmin(User user) {
         admins.remove(user);
     }
     public void addPendingRequest(User user) {
            pendingRequests.add(user);
        }
        public void removePendingRequest(User user) {
            pendingRequests.remove(user);
        }



    public static class Builder {

        private String name;
        private String description;
        private User creator;
        private ArrayList<User> admins;
        private ArrayList<User> members;
        private ArrayList<User> pendingRequests;
        private ArrayList<Content> contents;
        private String groupId;
        private String photoPath;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCreator(User creator) {
            this.creator = creator;
            return this;
        }

        public Builder setAdmins(ArrayList<User> admins) {
            this.admins = admins;
            return this;
        }

        public Builder setMembers(ArrayList<User> members) {
            this.members = members;
            return this;
        }

        public Builder setPendingRequests(ArrayList<User> pendingRequests) {
            this.pendingRequests = pendingRequests;
            return this;
        }

        public Builder setContents(ArrayList<Content> contents) {
            this.contents = contents;
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

        public Group build() {
            return new Group(this);
        }
    }
}