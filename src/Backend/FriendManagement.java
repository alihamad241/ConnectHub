package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
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
    private static JSONArray userFriends = DatabaseManager.readJSONFile(FRIENDS_FILE_PATH);

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
            if(((User)obj).getUserId().equals(userId)){
                return (User)obj;
            }
        }
        return null;
    }

    public void addFriend(User user){
        friends.add(user);
//        user.getFriendManagement().friends.add(this.user);
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

    public void blockUser(User user){
        blockedUsers.add(user);
        friends.remove(user);
        saveFriends();
    }

    public void unblockUser(User user){
        blockedUsers.remove(user);
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
                userFriends = DatabaseManager.readJSONFile(FRIENDS_FILE_PATH);

                for (int i = 0; i < userFriends.length(); i++) {
                    JSONObject existingUser = userFriends.getJSONObject(i);
                    if (existingUser.getString("userId").equals(user.getUserId())) {
                        JSONArray friendsArray = existingUser.getJSONArray("friends");
                        JSONArray receivedArray = existingUser.getJSONArray("receivedRequests");
                        JSONArray sentArray = existingUser.getJSONArray("sentRequests");
                        JSONArray blockedArray = existingUser.getJSONArray("blockedUsers");

                        for (int j = 0; j < friendsArray.length(); j++) {
                            friends.add(findUser(friendsArray.getString(j)));
                        }

                        for (int j = 0; j < receivedArray.length(); j++) {
                            receivedRequests.add(findUser(receivedArray.getString(j)));
                        }

                        for (int j = 0; j < sentArray.length(); j++) {
                            sentRequests.add(findUser(sentArray.getString(j)));
                        }

                        for (int j = 0; j < blockedArray.length(); j++) {
                            blockedUsers.add(findUser(blockedArray.getString(j)));
                        }
                        break; // Exit loop after finding the matching entry
                    }
                }

            }
    }

    public void saveFriends() {
        JSONObject json = new JSONObject();


        for (int i = 0; i < userFriends.length(); i++) {
            JSONObject existingUser = userFriends.getJSONObject(i);
            if (existingUser.getString("userId").equals(user.getUserId())) {
                userFriends.remove(i);
                break; // Exit loop after removing the matching entry
            }
        }

        json.put("userId",user.getUserId());
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

        JSONArray blockedArray = new JSONArray();
        for (User user : blockedUsers) {
            blockedArray.put(user.getUserId());
        }

        // Build JSON object
        json.put("friends", friendsArray);
        json.put("receivedRequests", receivedArray);
        json.put("sentRequests", sentArray);
        json.put("blockedUsers", blockedArray);

        userFriends.put(json);

        // Write to file
        DatabaseManager.writeJSONFile(FRIENDS_FILE_PATH, userFriends);

}}

