package Backend;

import Frontend.Posts;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {


        UserManager manager = new UserManager();



        User user6 = manager.login("uehd","aef");
        User user7 = manager.login("uehd1","aef");
        User user8 = manager.login("ue2hd","aef");
        User user9 = manager.login("uehd3","aef");
        User user10 = manager.login("uehd4","aef");



        ArrayList<User> suggested = user8.getFriendManagement().getSuggestedFriends();
        for (User user : suggested) {
            System.out.println(user.getName());
        }


//        Content content1=new Content("Hello", "C:\\Users\\DELL\\Desktop\\images\\image1.jpg", "1", "1", LocalDateTime.now(), false);
//        Content content2=new Content("Hello", "C:\\Users\\DELL\\Desktop\\images\\image2.jpg", "2", "2", LocalDateTime.now().minusDays(2), true);
//        Content content3=new Content("Hello", "", "3", "3", LocalDateTime.now(), true);
//        ContentManager manager1=new ContentManager();
//        manager1.addContent(content1);
//        manager1.addContent(content2);
//        manager1.addContent(content3);
        ArrayList<Content>posts=ContentManager.readContent();
        System.out.println("All posts");
        for (Content post : posts) {
            System.out.println(post.getContent());
            System.out.println(post.getTime());
            System.out.println(post.getAuthorId());
            System.out.println(post.getContentId());
            System.out.println(post.getImagePath());
            System.out.println(post.getIsStory());

        }
        System.out.println("User posts");
        ArrayList<Content> userPosts = ContentManager.UserContent("1");
        for (Content post : userPosts) {
            System.out.println(post.getContent());
            System.out.println(post.getTime());
            System.out.println(post.getAuthorId());
            System.out.println(post.getContentId());
            System.out.println(post.getImagePath());
            System.out.println(post.getIsStory());
        }



    }
}
