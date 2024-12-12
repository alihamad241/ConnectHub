package Backend.Notifications;

public class GroupNotification extends Notification {
    private final String groupId;

    public GroupNotification(String recipientId, String message, String type, String groupId) {
        super(recipientId, message, type);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}
