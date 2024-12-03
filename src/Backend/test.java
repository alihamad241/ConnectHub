package Backend;

public class test {
    public static void main(String[] args) {
        User user1 = new User("Ahmed", "1", "aef@daef", "aef", "aef", "aef");
        User user2 = new User("Ahmed", "2", "aef4@daef", "aef", "aef", "aef");
        User user3 = new User("Ahmed", "3", "aef3@daef", "aef", "aef", "aef");
        User user4 = new User("Ahmed", "4", "aef2@daef", "aef", "aef", "aef");
        User user5 = new User("Ahmed", "5", "ae1f@daef", "aef", "aef", "aef");

        UserManager manager = new UserManager();



        User user6 = manager.login("uehd","aef");
        User user7 = manager.login("uehd1","aef");



    }
}
