package Backend;

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








    }
}
