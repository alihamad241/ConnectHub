package Backend.Notifications;

public class RequestNotification extends Notification {

    public RequestNotification(String userId, String recipientId, String message, String type) {
        super(userId,recipientId, message, type);
    }

}
