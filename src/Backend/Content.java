/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.time.LocalDateTime;


/**
 *
 * @author DELL
 */
public class Content {

    private String content;
    private String imagePath;
    private String contentId;
    private String authorId;
    private LocalDateTime time;
    private boolean isStory;

    public Content(String content, String imagePath, String contentId, String authorId, LocalDateTime time, boolean isStory) {
        this.content = content;
        this.imagePath = imagePath;
        this.contentId = contentId;
        this.authorId = authorId;
        this.time = time;
        this.isStory = isStory;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getContentId() {
        return contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean getIsStory() {
        return isStory;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setIsStory(boolean isStory) {
        this.isStory = isStory;
    }

    public String toString(){
        return "Content: " + content + " ImagePath: " + imagePath + " ContentId: " + contentId + " AuthorId: " + authorId + " Time: " + time + " IsStory: " + isStory;
    }
    
}
