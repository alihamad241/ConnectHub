package Frontend;

import Backend.ContentManager;
import Backend.User;
import Backend.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class NewsFeed_Updates {
    private static final UserManager userManager = UserManager.getInstance();
    private static final ContentManager contentManager = ContentManager.getInstance();
    private static Timer statusUpdateTimer;

   public static void RefreshNewsFeed(User user, JScrollPane friendsList, JScrollPane suggestedFriendPanel, JScrollPane postPanel, JScrollPane storyPanel) {
       contentManager.readContent();
       userManager.loadAllUsers();
       userManager.loadAllFriends();
       UpdatePosts(user, postPanel);

       // Start the timer to update friend statuses periodically
       if (statusUpdateTimer == null) {
           statusUpdateTimer = new Timer(1000, new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   contentManager.readContent();
                   userManager.loadAllUsers();
                   userManager.loadAllFriends();
                   UpdateFriends(user, friendsList);
                   UpdateStories(user, storyPanel);
                   UpdateSuggestedFriends(user, suggestedFriendPanel);
               }
           });
           statusUpdateTimer.start();
       }
}

    public static void UpdateFriends(User user, JScrollPane friendsList) {
        JPanel containerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);

        for (int i = 0; i < user.getFriendManagement().getFriends().size(); i++) {
            JLabel friendLabel = new JLabel(user.getFriendManagement().getFriends().get(i).getUsername());
            System.out.println(user.getFriendManagement().getFriends().get(i).getStatus());
            JLabel statusLabel = new JLabel(user.getFriendManagement().getFriends().get(i).getStatus());
            JPanel friendPanel = new JPanel();
            friendPanel.add(friendLabel);
            friendPanel.add(statusLabel);
            containerPanel.add(friendPanel);
        }
        friendsList.setViewportView(containerPanel);
    }

    public static void UpdateSuggestedFriends(User user, JScrollPane suggestedFriendPanel) {
        JPanel containerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);

        for (int i=0;i<user.getFriendManagement().getSuggestedFriends().size();i++){
            User suggestedFriend = user.getFriendManagement().getSuggestedFriends().get(i);
            JLabel friendLabel = new JLabel(suggestedFriend.getUsername());
            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                user.getFriendManagement().sendFriendRequest(suggestedFriend);
                JOptionPane.showMessageDialog(null, "Friend Request Sent");
                user.getFriendManagement().fillSuggestedFriends();
                UpdateSuggestedFriends(user, suggestedFriendPanel);
            });
            JPanel innerSuggestedFriendPanel = new JPanel();
            innerSuggestedFriendPanel.add(friendLabel);
            innerSuggestedFriendPanel.add(addFriendButton);
            containerPanel.add(innerSuggestedFriendPanel);
        }
        suggestedFriendPanel.setViewportView(containerPanel);
    }

    public static void UpdatePosts(User user, JScrollPane postPanel) {
        JPanel containerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);

        for (int i = 0; i < user.getFriendsPosts().size(); i++) {
            JLabel postLabel = new JLabel(user.getFriendsPosts().get(i).getContent());
            JLabel nameLabel = new JLabel(user.getFriendsPosts().get(i).getAuthorUserName());
            long  time = user.getFriendsPosts().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
            if(time>60 && time <120){
                time = user.getFriendsPosts().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.HOURS);
                nameLabel.setText(nameLabel.getText() + " " + time + " hour ago");
            }else if(time>120 && time <1440) {
                time = user.getFriendsPosts().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.HOURS);
                nameLabel.setText(nameLabel.getText() + " " + time + " hours ago");
            }else if(time>1440 && time <2880) {
                time = user.getFriendsPosts().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.DAYS);
                nameLabel.setText(nameLabel.getText() + " " + time + " day ago");
            }else if(time>2880) {
                time = user.getFriendsPosts().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.DAYS);
                nameLabel.setText(nameLabel.getText() + " " + time + " days ago");
            }else {
                nameLabel.setText(nameLabel.getText() + " " + time + " minutes ago");
            }
            // Resize the image
            ImageIcon imageIcon = new ImageIcon(user.getFriendsPosts().get(i).getImagePath());
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(postPanel.getWidth(), 300, Image.SCALE_SMOOTH);
            ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

            JLabel photo = new JLabel(resizedImageIcon);
            JPanel innerPostPanel = new JPanel();
            innerPostPanel.setLayout(new BoxLayout(innerPostPanel, BoxLayout.Y_AXIS));
            innerPostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));// Add padding
            innerPostPanel.add(nameLabel);
            innerPostPanel.add(photo);
            innerPostPanel.add(postLabel);
            innerPostPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

            // Add margin between posts
            innerPostPanel.setBorder(BorderFactory.createCompoundBorder(
                    innerPostPanel.getBorder(),
                    BorderFactory.createEmptyBorder(10, 0, 10, 0)
            ));

            containerPanel.add(innerPostPanel);
        }
        postPanel.setViewportView(containerPanel);
    }

    public static void UpdateStories(User user, JScrollPane storyPanel) {
        JPanel containerPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        containerPanel.setLayout(flowLayout);

        for (int i = 0; i < user.getFriendsStories().size(); i++) {
            JLabel storyLabel = new JLabel(user.getFriendsStories().get(i).getContent());
            JLabel nameLabel = new JLabel(user.getFriendsStories().get(i).getAuthorUserName());
            long time = user.getFriendsStories().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
            if (time > 60 && time < 120) {
                time = user.getFriendsStories().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.HOURS);
                nameLabel.setText(nameLabel.getText() + " " + time + " hour ago");
            } else if (time > 120 && time < 1440) {
                time = user.getFriendsStories().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.HOURS);
                nameLabel.setText(nameLabel.getText() + " " + time + " hours ago");
            } else if (time > 1440 && time < 2880) {
                time = user.getFriendsStories().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.DAYS);
                nameLabel.setText(nameLabel.getText() + " " + time + " day ago");
            } else if (time > 2880) {
                time = user.getFriendsStories().get(i).getTime().until(LocalDateTime.now(), ChronoUnit.DAYS);
                nameLabel.setText(nameLabel.getText() + " " + time + " days ago");
            } else {
                nameLabel.setText(nameLabel.getText() + " " + time + " minutes ago");
            }
            // Resize the image
            ImageIcon imageIcon = new ImageIcon(user.getFriendsStories().get(i).getImagePath());
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(100, storyPanel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

            JLabel photo = new JLabel(resizedImageIcon);
            JPanel innerStoryPanel = new JPanel();
            innerStoryPanel.setLayout(new BoxLayout(innerStoryPanel, BoxLayout.Y_AXIS));
            innerStoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
            innerStoryPanel.add(nameLabel);
            innerStoryPanel.add(photo);
            innerStoryPanel.add(storyLabel);
            innerStoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

            // Add margin between stories
            innerStoryPanel.setBorder(BorderFactory.createCompoundBorder(
                    innerStoryPanel.getBorder(),
                    BorderFactory.createEmptyBorder(10, 0, 10, 0)
            ));

            containerPanel.add(innerStoryPanel);
        }
        storyPanel.setViewportView(containerPanel);
    }

}
