package Backend;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {


        UserManager manager = new UserManager();

        User user1 = manager.login("uehd","aef");
        User user2 = manager.login("uehd1","aef");
        User user3 = manager.login("ue2hd","aef");
        User user4 = manager.login("uehd3","aef");
        User user5 = manager.login("uehd4","aef");

        user1.setUserId("1");
        user2.setUserId("2");
        user3.setUserId("3");
        user4.setUserId("4");
        user5.setUserId("5");

        user1.addFriend(user2);
        user1.addFriend(user3);
        user1.addFriend(user4);
        user1.addFriend(user5);












    }
}
