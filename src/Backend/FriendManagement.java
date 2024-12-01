package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FriendManagement {
    private final ArrayList<User> friends;
    private final ArrayList<User> receivedRequests;
    private final ArrayList<User> sentRequests;
    private final ArrayList<User> suggestedFriends;
    private final ArrayList<User> blockedUsers;
    private final User user;

    private static final String FRIENDS_FILE_PATH = "databases/friends.json";
    private static final String USERS_FILE_PATH = "databases/users.json";



    public FriendManagement(User user){
        this.user = user;
        friends = new ArrayList<>();
        receivedRequests = new ArrayList<>();
        sentRequests = new ArrayList<>();
        suggestedFriends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
        loadFriends();
        fillSuggestedFriends(user);
    }


    public User findUser(String userId){
        JSONArray users = DatabaseManager.readJSONFile(USERS_FILE_PATH);
        assert users != null: "Users file is empty";
        for(Object obj : users){
            if(user.getUserId().equals(userId)){
                return user;
            }
        }
        return null;
    }

    public void addFriend(User user){
        friends.add(user);
        saveFriends();
    }

    public void removeFriend(User user){
        friends.remove(user);
        saveFriends();
    }

    public void sendFriendRequest(User user){
        sentRequests.add(user);
        saveFriends();
    }

    public void cancelFriendRequest(User user){
        sentRequests.remove(user);
        saveFriends();
    }

    public void acceptFriendRequest(User user){
        receivedRequests.remove(user);
        friends.add(user);
        saveFriends();
    }

    public void declineFriendRequest(User user){
        receivedRequests.remove(user);
        saveFriends();
    }

    public ArrayList<User> getBlockedUsers() {
        return new ArrayList<>(blockedUsers);
    }

    public ArrayList<User> getFriends() {
        return new ArrayList<>(friends);
    }

    public ArrayList<User> getReceivedRequests() {
        return new ArrayList<>(receivedRequests);
    }

    public ArrayList<User> getSentRequests() {
        return new ArrayList<>(sentRequests);
    }

    public ArrayList<User> getSuggestedFriends() {
        return new ArrayList<>(suggestedFriends);
    }

    public void blockUser(User user){
        blockedUsers.add(user);
        friends.remove(user);
        saveFriends();
    }

    public void unblockUser(User user){
        blockedUsers.remove(user);
        saveFriends();
    }

    public Map<String,String> getFriendsStatus(){
        Map<String, String> friendsStatus = new HashMap<>();
        for(User friend : friends){
            friendsStatus.put(friend.getUserId(), friend.getStatus());
        }
        return friendsStatus;
    }

    public boolean isFriend(User user){
        return friends.contains(user);
    }

    public ArrayList<User> getSuggestedFriends(User user){
        return new ArrayList<>(suggestedFriends);
    }

    public void fillSuggestedFriends(User user){
        suggestedFriends.clear();
        for(User friend : friends){
            for(User suggestedFriend : friend.getFriendManagement().getFriends()){
                if(!suggestedFriends.contains(suggestedFriend) && !suggestedFriend.equals(user) && !friends.contains(suggestedFriend) && !sentRequests.contains(suggestedFriend) && !receivedRequests.contains(suggestedFriend) && !blockedUsers.contains(suggestedFriend)){
                    suggestedFriends.add(suggestedFriend);
                }
            }
        }
    }

    public void loadFriends(){
            File file = new File(FRIENDS_FILE_PATH);
            if (file.exists()) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    JSONObject json = new JSONObject(content);

                    // Populate friends list
                    JSONArray friendsArray = json.getJSONArray("friends");
                    for (int i = 0; i < friendsArray.length(); i++) {
                        friends.add(findUser((String) friendsArray.get(i)));
                    }

                    // Populate received requests
                    JSONArray receivedArray = json.getJSONArray("receivedRequests");
                    for (int i = 0; i < receivedArray.length(); i++) {
                        receivedRequests.add(findUser((String) receivedArray.get(i)));
                    }

                    // Populate sent requests
                    JSONArray sentArray = json.getJSONArray("sentRequests");
                    for (int i = 0; i < sentArray.length(); i++) {
                        sentRequests.add(findUser((String) sentArray.get(i)));
                    }

                } catch (IOException e) {
                    System.err.println("Error reading friends data file: " + e.getMessage());
                }
            }
    }

    public void saveFriends() {
        JSONObject json = new JSONObject();

        // Convert lists to JSON arrays
        JSONArray friendsArray = new JSONArray();
        for (User user : friends) {
            friendsArray.put(user.getUserId());
        }

        JSONArray receivedArray = new JSONArray();
        for (User user : receivedRequests) {
            receivedArray.put(user.getUserId());
        }

        JSONArray sentArray = new JSONArray();
        for (User user : sentRequests) {
            sentArray.put(user.getUserId());
        }

        // Build JSON object
        json.put("friends", friendsArray);
        json.put("receivedRequests", receivedArray);
        json.put("sentRequests", sentArray);

        // Write to file
        try (FileWriter writer = new FileWriter(FRIENDS_FILE_PATH)) {
            writer.write(json.toString(4)); // Pretty-print JSON
        } catch (IOException e) {
            System.err.println("Error saving friends data file: " + e.getMessage());
        }
    }

}

