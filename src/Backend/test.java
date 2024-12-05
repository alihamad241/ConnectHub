package Backend;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {


        UserManager manager = new UserManager();

        manager.signup("youssef", "1234@5", "joe","1","1-12-2024");
        manager.signup("ahmed", "1234@4", "ahmed","1","1-12-2024");
        manager.signup("mohamed", "1234@3", "mohamed","1","1-12-2024");
        manager.signup("ali", "1234@2", "ali","1","1-12-2024");
        manager.signup("omar", "1234@1", "omar","1","1-12-2024");

        User user1 = manager.login("joe", "1");
        User user2 = manager.login("ahmed", "1");
        User user3 = manager.login("mohamed", "1");
        User user4 = manager.login("ali", "1");
        User user5 = manager.login("omar", "1");

        user1.addFriend(user2);
        user1.addFriend(user3);
        user1.addFriend(user4);
        user1.addFriend(user5);



    }}
