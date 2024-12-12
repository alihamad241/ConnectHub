package Backend.Notifications;

import Backend.Content;

public class GroupPostNotifications extends GroupNotification {
    Content content;
    String authorId;

    public GroupPostNotifications(String authorId,String recipientId, String message, String type, String groupId, Content content) {
        super(recipientId, message, type, groupId);
        this.authorId = authorId;
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public String getAuthorId() {
        return authorId;
    }
}
