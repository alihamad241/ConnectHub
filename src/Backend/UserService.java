/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import org.json.JSONArray;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */

public class UserService {
      public ArrayList<Content> getUserPosts(String userId) {
        // Fetch user's posts with timestamps and images
          JSONArray userPosts = DatabaseManager.readJSONFile("databases/content.json");
            ArrayList<Content> posts = new ArrayList<>();
            for (int i = 0; i < userPosts.length(); i++) {
                if (userPosts.getJSONObject(i).getString("authorId").equals(userId)) {
                    String content = userPosts.getJSONObject(i).getString("content");
                    String imagePath = userPosts.getJSONObject(i).getString("imagePath");
                    String contentId = userPosts.getJSONObject(i).getString("contentId");
                    String authorId = userPosts.getJSONObject(i).getString("authorId");
                    LocalDateTime time = LocalDateTime.parse(userPosts.getJSONObject(i).getString("time"));
                    boolean isStory = userPosts.getJSONObject(i).getBoolean("isStory");
                    if(!isStory){
                        Content post = new Content(content, imagePath, contentId, authorId, time, isStory);
                        posts.add(post);
                    }
                }
            }
          return null;


    }

    public ArrayList<User> getUserFriends(String userId) throws IOException {
        // Fetch user's friends and their online/offline status
        ProfileManager profileManager = new ProfileManager();
        User user = profileManager.findUserById(userId);
        FriendManagement friendManagement =new FriendManagement(user);
        return friendManagement.getFriends();

    }
}
