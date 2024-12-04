/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static Backend.UserManager.findUser;


/**
 *
 * @author DELL
 */
public class ContentManager {

    private ArrayList<Content> contents;
    private static final String CONTENTS_FILE = "databases/content.json";
    public static JSONArray contentsArray = DatabaseManager.readJSONFile(CONTENTS_FILE);

    public ContentManager() {
        contents = new ArrayList<>();
    }

    public void addContent(Content content) {
        contents.add(content);
        writeContent(content);
    }

    public void removeContent(Content content) {
        contents.remove(content);
        for(int i = 0; i < contentsArray.length(); i++){
            JSONObject contents = contentsArray.getJSONObject(i);
            if(contents.getString("contentId").equals(content.getContentId())){
                contentsArray.remove(i);
                break;
            }
        }
        DatabaseManager.writeJSONFile(CONTENTS_FILE, contentsArray);
    }

    public void removeStory() {
        Iterator<Content> iterator = contents.iterator();
        while (iterator.hasNext()) {
            Content content = iterator.next();
            if (content.getIsStory() && content.getTime().isBefore(LocalDateTime.now().minusDays(1))) {
                iterator.remove();
                removeContent(content);
            }
        }
    }

    public static void writeContent(Content content) {
        JSONObject newContentObject = new JSONObject();
        JSONArray contentsArray = DatabaseManager.readJSONFile(CONTENTS_FILE);
            newContentObject.put("content", content.getContent());
            if(content.getImagePath() == null){
                newContentObject.put("imagePath", "null");
            }
            else{
            newContentObject.put("imagePath", content.getImagePath());}
            newContentObject.put("contentId", content.getContentId());
            newContentObject.put("authorId", content.getAuthorId());
            newContentObject.put("time", content.getTime());
            newContentObject.put("isStory", content.getIsStory());
            contentsArray.put(newContentObject);


        DatabaseManager.writeJSONFile(CONTENTS_FILE, contentsArray);

    }

    public static ArrayList<Content> readContent(){
        ArrayList<Content> allContents = new ArrayList<>();
        try{
            String json=new String(Files.readAllBytes(Paths.get(CONTENTS_FILE)));
            JSONArray contentsArray = new JSONArray(json);
            DateTimeFormatter formatter=DateTimeFormatter.ISO_DATE;
            for(int i=0;i<contentsArray.length();i++){
                JSONObject content=contentsArray.getJSONObject(i);
                String contentId=content.getString("contentId");
                String authorId=content.getString("authorId");
                String contentText=content.getString("content");
                String imagePath=content.getString("imagePath");
                LocalDateTime time=LocalDateTime.parse(content.getString("time"));
                boolean isStory=content.getBoolean("isStory");
                Content newContent=new Content(contentText,imagePath,contentId,authorId,time,isStory, Objects.requireNonNull(findUser(authorId)).getUsername());
                allContents.add(newContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allContents;
    }

    public static ArrayList<Content> UserContent(String userId){
        ArrayList<Content> userContents=new ArrayList<>();
        ArrayList<Content> allContents=readContent();
        for(Content content:allContents){
            if(content.getAuthorId().equals(userId)){
                userContents.add(content);
            }
        }
        return userContents;
    }

    public static ArrayList<Content> getUserStories(User user){
        ArrayList<Content> stories=new ArrayList<>();
        ArrayList<Content> allContents = UserContent(user.getUserId());
        for(Content content:allContents){
            if(content.getIsStory() && content.getTime().isAfter(LocalDateTime.now().minusDays(1))){
                stories.add(content);
            }
        }
        return stories;
    }

    public static ArrayList<Content> getFriendsPosts(User user) {
        ArrayList<Content> mainfriendsPosts = new ArrayList<>();
        ArrayList<User> friends = user.getFriendManagement().getFriends();

        for (User friend : friends) {
            ArrayList<Content> friendPosts = UserContent(friend.getUserId());
            for(Content post: friendPosts){
                if(!post.getIsStory()){
                    mainfriendsPosts.add(post);
                }
            }
        }
        mainfriendsPosts.sort((c1, c2) -> c2.getTime().compareTo(c1.getTime()));
        return mainfriendsPosts;
    }

    public static ArrayList<Content> getFriendsStories(User user) {
        ArrayList<Content> mainFriendsStories = new ArrayList<>();
        ArrayList<User> friends = user.getFriendManagement().getFriends();

        for (User friend : friends) {
            ArrayList<Content> friendStories = getUserStories(friend);
            mainFriendsStories.addAll(friendStories);
        }
        mainFriendsStories.sort((c1, c2) -> c2.getTime().compareTo(c1.getTime()));
        return mainFriendsStories;
    }
}
