
package Backend;

import org.json.JSONObject;

import java.util.UUID;

public class User {
    private String name;
    private final String userId;
    private String email;
    private String username;
    private String hashedPassword;
    private String dateOfBirth;
    private String status;
    private final FriendManagement friendManagement;
    private final UserProfile userProfile;

    public User(String Name, String userId, String email, String username, String hashedPassword, String dateOfBirth) {
        this.name = Name;
        this.userId= userId;
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.dateOfBirth = dateOfBirth;
        this.status = "offline";
        this.friendManagement = new FriendManagement(this);
        this.userProfile = new UserProfile();
    }

//    public User(JSONObject jsonUser) {
//        this.name = jsonUser.getString("Name");
//        this.userId = jsonUser.getString("userId");
//        this.email = jsonUser.getString("email");
//        this.username = jsonUser.getString("username");
//        this.hashedPassword = jsonUser.getString("hashedPassword");
//        this.dateOfBirth = jsonUser.getString("dateOfBirth");
//        this.status = jsonUser.getString("status");
//        this.friendManagement = new FriendManagement(this);
//        this.userProfile = new UserProfile();
//    }


    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public FriendManagement getFriendManagement() {
        return friendManagement;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void addFriend(User user){
        friendManagement.addFriend(user);
    }

    public void removeFriend(User user){
        friendManagement.removeFriend(user);
    }

    public void acceptFriendRequest(User user){
        friendManagement.acceptFriendRequest(user);
    }

    public void declineFriendRequest(User user){
        friendManagement.declineFriendRequest(user);
    }

    public void blockUser(User user){
        friendManagement.blockUser(user);
    }

    public void unblockUser(User user){
        friendManagement.unblockUser(user);
    }

    public void sendFriendRequest(User user){
        friendManagement.sendFriendRequest(user);
    }

    public void cancelFriendRequest(User user){
        friendManagement.cancelFriendRequest(user);
    }






}

