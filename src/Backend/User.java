
package Backend;

public class User {
    private String Name;
    private String userId;
    private String email;
    private String username;
    private String hashedPassword;
    private String dateOfBirth;
    private String status;
    private FriendManagement friendManagement;
    private UserProfile userProfile;

    public User(String Name, String userId, String email, String username, String hashedPassword, String dateOfBirth) {
        this.Name = Name;
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.dateOfBirth = dateOfBirth;
        this.status = "offline";
        this.friendManagement = new FriendManagement(this);
        this.userProfile = new UserProfile();
    }

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

    public void setFriendManagement(FriendManagement friendManagement) {
        this.friendManagement = friendManagement;
    }

    public String getName() {
        return Name;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }


}

