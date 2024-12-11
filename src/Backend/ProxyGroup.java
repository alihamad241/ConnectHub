package Backend;

import javax.swing.*;

public class ProxyGroup implements Group{

    public RealGroup realGroup;
    public User user;
    public ProxyGroup(RealGroup realGroup) {
        this.realGroup = realGroup;
        this.user = user;
    }
    // Helper methods to check roles in the HashMap based on a given user
    private boolean isAdminOrCreator(User user) {
        // Check if the specified user is either an Admin or Creator
        return realGroup.getUserRoles().get(user).equals("Admin") || realGroup.getUserRoles().get(user).equals("PrimaryAdmin");
    }

    private boolean isAdmin(User user) {
        // Check if the specified user is an Admin
        return realGroup.getUserRoles().get(user).equals("Admin");
    }

    private boolean isCreator(User user) {
        // Check if the specified user is the Creator
        return realGroup.getUserRoles().get(user).equals("PrimaryAdmin");
    }

    @Override
    //anyone can add content
    public void addContent(Content content) {
         realGroup.addContent(content);
    }

    @Override
    //only admin or creator can remove content
    public void removeContent(Content content) {
        if (isAdminOrCreator(user)) {
            realGroup.removeContent(content);
        }
        else JOptionPane.showMessageDialog(null, "You do not have permission to remove content");
    }

    @Override
        //anyone can add pending request
    public void addPendingRequest(User user1) {
      realGroup.addPendingRequest(user1);
    }

    @Override
            //only admin or creator can remove pending request
    public void removePendingRequest(User user1) {
        if(isAdminOrCreator(user)){
            realGroup.removePendingRequest(user1);
        }
        else JOptionPane.showMessageDialog(null, "You do not have permission to remove pending request");
    }

    @Override
    //only admin or creator can add user
    public void addUser(User user1, String role) {
        if(isAdminOrCreator(user)){
            realGroup.addUser(user1, role);
        }
        else JOptionPane.showMessageDialog(null, "You do not have permission to add user");
    }

    @Override
    // creator can remove anyone
    //admin can remove only user
    public void removeUser(User user1) {
        if (isCreator(user)) {
            realGroup.removeUser(user1);
        } else if (isAdmin(user) && realGroup.getUserRoles().get(user1).equals("User")) {
            realGroup.removeUser(user1);
        } else JOptionPane.showMessageDialog(null, "You do not have permission to remove user");
    }


//only creator can delete group
    @Override
    public void deleteGroup() {
if(isCreator(user)){
    realGroup.deleteGroup();
}
    }

    @Override
    //only creator can promote user
    public void promoteUser(User user) {
        if (isCreator(user)) {
            realGroup.promoteUser(user);
        }
    }



    @Override
    //only creator can demote user
    public void demoteUser(User user) {
if(isCreator(user)) {
    realGroup.demoteUser(user);
}}



    @Override
    //only admin or creator can approve request
    public void approveRequest(User user) {
        if (isAdminOrCreator(user)) {
            realGroup.approveRequest(user);

        }
    }

    @Override
    //only admin or creator can reject request
    public void rejectRequest(User user) {
        if (isAdminOrCreator(user)) {
            realGroup.rejectRequest(user);
        }
    }



    @Override
    //anyone can leave group
    public void leaveGroup(User user) {
    realGroup.leaveGroup(user);
    }
}
