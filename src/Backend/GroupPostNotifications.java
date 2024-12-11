package Backend;

public class GroupPostNotifications extends GroupNotification{
    Content content;

    public GroupPostNotifications(String senderUserId,String recipientId, String message, String type, String groupId, Content content) {
        super(senderUserId,recipientId, message, type, groupId);
        this.content = content;
    }

    public Content getContent() {
        return content;
    }
}
