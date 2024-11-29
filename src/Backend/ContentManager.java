/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 *
 * @author DELL
 */
public class ContentManager {

    private ArrayList<Content> contents;

    public ContentManager() {
        contents = new ArrayList<>();
    }

    public void addContent(Content content) {
        contents.add(content);
    }

    public void removeContent(Content content) {
        contents.remove(content);
    }

    public void removeStory(){
        for(Content content : contents){
            if(content.getIsStory()){
                if(content.getTime().isBefore(LocalDateTime.now().minusDays(1))){ //If the story is older than 24 hours it will be removed
                    contents.remove(content);
                }
            }
        }
    }
    
}
