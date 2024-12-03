package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


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
        this.fillSuggestedFriends();
    }


    public User findUser(String userId) {
      for(User user : UserManager.allUsers){
          if(user.getUserId().equals(userId)){
              return user;
          }
      }
        return null;
    }

    public boolean checkDupe(User user){
        return !this.user.getUserId().equals(user.getUserId());
    }


    public void addFriend(User user){
        if(checkDupe(user)){
        friends.add(user);
        user.getFriendManagement().friends.add(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }

    }

    public void removeFriend(User user){
        friends.remove(user);
        user.getFriendManagement().friends.remove(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }

    public void sendFriendRequest(User user){
       if(checkDupe(user)){
        sentRequests.add(user);
        user.getFriendManagement().receivedRequests.add(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }
        else
        {
            System.out.println("error");
        }
 }

    public void cancelFriendRequest(User user){
        sentRequests.remove(user);
        user.getFriendManagement().receivedRequests.remove(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }

    public void acceptFriendRequest(User user){
        if(checkDupe(user)){
        receivedRequests.remove(user);
        friends.add(user);
        user.getFriendManagement().sentRequests.remove(this.user);
        user.getFriendManagement().friends.add(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }}

    public void declineFriendRequest(User user){
        receivedRequests.remove(user);
        user.getFriendManagement().sentRequests.remove(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }

    public void blockUser(User user){
        blockedUsers.add(user);
        friends.remove(user);
        user.getFriendManagement().friends.remove(this.user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }

    public void unblockUser(User user){
        blockedUsers.remove(user);
        saveFriends();
        user.getFriendManagement().saveFriends();
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getReceivedRequests() {
        return receivedRequests;
    }

    public ArrayList<User> getSentRequests() {
        return sentRequests;
    }

    public ArrayList<User> getSuggestedFriends() {
        return suggestedFriends;
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
        return suggestedFriends;
    }

  public void fillSuggestedFriends(){
    suggestedFriends.clear();
    for(User friend : friends){
        if (friend != null && friend.getFriendManagement() != null) {
            for(User suggestedFriend : friend.getFriendManagement().getFriends()){
                if(!suggestedFriends.contains(suggestedFriend) && !suggestedFriend.equals(user) && !friends.contains(suggestedFriend) && !sentRequests.contains(suggestedFriend) && !receivedRequests.contains(suggestedFriend) && !blockedUsers.contains(suggestedFriend)){
                    suggestedFriends.add(suggestedFriend);
                }
            }
        }
    }
}

    public void loadFriends(){
            File file = new File(FRIENDS_FILE_PATH);
            if (file.exists()) {
                userFriends = DatabaseManager.readJSONFile(FRIENDS_FILE_PATH);

                for (int i = 0; i < Objects.requireNonNull(userFriends).length(); i++) {
                    JSONObject existingUser = userFriends.getJSONObject(i);
                    if (existingUser.getString("userId").equals(user.getUserId())) {
                        JSONArray friendsArray = existingUser.getJSONArray("friends");
                        JSONArray receivedArray = existingUser.getJSONArray("receivedRequests");
                        JSONArray sentArray = existingUser.getJSONArray("sentRequests");
                        JSONArray blockedArray = existingUser.getJSONArray("blockedUsers");

                        for (int j = 0; j < friendsArray.length(); j++) {
                            if(findUser(friendsArray.getString(j)) != null)
                            {
                                friends.add(findUser(friendsArray.getString(j)));
                            }
                        }

                        for (int j = 0; j < receivedArray.length(); j++) {
                            if(findUser(receivedArray.getString(j)) != null)
                             receivedRequests.add(findUser(receivedArray.getString(j)));
                        }

                        for (int j = 0; j < sentArray.length(); j++) {
                            if(findUser(sentArray.getString(j)) != null)
                             sentRequests.add(findUser(sentArray.getString(j)));
                        }

                        for (int j = 0; j < blockedArray.length(); j++) {
                            if(findUser(blockedArray.getString(j)) != null) {
                                blockedUsers.add(findUser(blockedArray.getString(j)));
                            }
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

}

}

