/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 *
 * @author DELL
 */
public class ContentManager {

    private ArrayList<Content> contents;
    private static final String CONTENTS_FILE = "databases/content.json";
    private static final JSONArray contentsArray = DatabaseManager.readJSONFile(CONTENTS_FILE);

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

    public void removeStory(){
        for(Content content : contents){
            if(content.getIsStory()){
                if(content.getTime().isBefore(LocalDateTime.now().minusDays(1))){ //If the story is older than 24 hours it will be removed
                    removeContent(content);
                }
            }
        }
    }

    public static void writeContent(Content content) {
        JSONObject newContentObject = new JSONObject();
            newContentObject.put("content", content.getContent());
            newContentObject.put("imagePath", content.getImagePath());
            newContentObject.put("contentId", content.getContentId());
            newContentObject.put("authorId", content.getAuthorId());
            newContentObject.put("time", content.getTime());
            newContentObject.put("isStory", content.getIsStory());
            contentsArray.put(newContentObject);


        DatabaseManager.writeJSONFile(CONTENTS_FILE, contentsArray);

    }



    
}
