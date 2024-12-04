package Backend;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {


        UserManager manager = new UserManager();

        User user1 = manager.login("uehd", "aef"); // 4
        User user2 = manager.login("ue2hd", "aef");
        User user3 = manager.login("uehd4", "aef");

        ArrayList<User> users = user2.getFriendManagement().getSuggestedFriends();
        for (User user : users) {
            System.out.println(user.getUsername());
        }


    }}
