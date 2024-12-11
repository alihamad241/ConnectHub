package Backend;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import static Backend.ContentManager.getContent;

public class NotificationManager implements Subject{
    private static final String NOTIFICATIONS_FILE_PATH = "databases/notifications.json";
    private static final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static JSONArray notificationsArray = databaseManager.readJSONFile(NOTIFICATIONS_FILE_PATH);
    private final ArrayList<Observer> observers;
    private final ArrayList<Notification> notifications;
    private static NotificationManager instance;

    private NotificationManager() {
        observers = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public static void addNotification(Notification notification) {
        if (notificationsArray == null) {
            notificationsArray = new JSONArray();
        }
        JSONObject notificationJson = new JSONObject();
        notificationJson.put("SenderUserId", notification.getSenderUserId());
        notificationJson.put("message", notification.getMessage());
        notificationJson.put("type", notification.getType());
        notificationJson.put("RecipientId", notification.getRecipientId());
       if(notification.getType().equalsIgnoreCase("groupActivity")) {
            notificationJson.put("groupId", ((GroupNotification) notification).getGroupId());
        }
        else if(notification.getType().equalsIgnoreCase("Post")) {
            notificationJson.put("GroupId", ((GroupNotification) notification).getGroupId());
            notificationJson.put("ContentId", ((GroupPostNotifications) notification).getContent().getContentId());
        }

        notificationsArray.put(notificationJson);
        instance.notifyObservers();
        databaseManager.writeJSONFile(NOTIFICATIONS_FILE_PATH, notificationsArray);
    }

   public static ArrayList<Notification> getNotifications(String userId) {
        notificationsArray = databaseManager.readJSONFile(NOTIFICATIONS_FILE_PATH);
    ArrayList<Notification> userNotifications = new ArrayList<>();
    if (notificationsArray != null) {
        for (int i = 0; i < notificationsArray.length(); i++) {
            JSONObject notificationJson = notificationsArray.getJSONObject(i);
            if (notificationJson.getString("RecipientId").equals(userId)) {
                String type = notificationJson.getString("type");
                Notification notification;
                switch (type) {
                    case "friendRequest":
                        notification = new RequestNotification(
                                notificationJson.getString("SenderUserId"),
                                notificationJson.getString("message"),
                                type,
                                notificationJson.getString("RecipientId")
                        );
                        break;
                    case "groupActivity":
                        notification = new GroupNotification(
                                notificationJson.getString("SenderUserId"),
                                notificationJson.getString("RecipientId"),
                                notificationJson.getString("message"),
                                type,
                                notificationJson.getString("groupId")
                        );
                        break;
                    case "Post":
                        notification = new GroupPostNotifications(
                                notificationJson.getString("SenderUserId"),
                                notificationJson.getString("RecipientId"),
                                notificationJson.getString("message"),
                                type,
                                notificationJson.getString("GroupId"),
                                getContent(notificationJson.getString("ContentId"))
                        );
                        break;
                    default:
                        notification = new Notification(
                                notificationJson.getString("SenderUserId"),
                                notificationJson.getString("RecipientId"),
                                notificationJson.getString("message"),
                                type
                        );
                        break;
                }
                userNotifications.add(notification);
            }
        }
    }
    return userNotifications;
}

    public static void removeNotification(Notification notification) {
        if (notificationsArray != null) {
            for (int i = 0; i < notificationsArray.length(); i++) {
                JSONObject notificationJson = notificationsArray.getJSONObject(i);
                if (notificationJson.getString("SenderUserId").equals(notification.getSenderUserId()) && notificationJson.getString("message").equals(notification.getMessage())) {
                    notificationsArray.remove(i);
                    databaseManager.writeJSONFile(NOTIFICATIONS_FILE_PATH, notificationsArray);
                    break;
                }
            }
        }
    }


    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            for (Notification notification : notifications) {
                observer.update(notification);
            }
        }
    }
}
