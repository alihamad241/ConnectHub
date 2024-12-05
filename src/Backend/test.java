package Backend;



public class test {
    public static void main(String[] args) {

        UserManager userManager = UserManager.getInstance();

        userManager.signup("youssef", "1234@5", "joe","1","1-12-2024");
        userManager.signup("ahmed", "1234@4", "ahmed","1","1-12-2024");
        userManager.signup("mohamed", "1234@3", "mohamed","1","1-12-2024");
        userManager.signup("ali", "1234@2", "ali","1","1-12-2024");
        userManager.signup("omar", "1234@1", "omar","1","1-12-2024");

        User user1 = userManager.login("joe", "1");
        User user2 = userManager.login("ahmed", "1");
        User user3 = userManager.login("mohamed", "1");
        User user4 = userManager.login("ali", "1");
        User user5 = userManager.login("omar", "1");

        user1.addFriend(user2);
        user1.addFriend(user3);
        user1.addFriend(user4);
        user1.addFriend(user5);
    }
}
