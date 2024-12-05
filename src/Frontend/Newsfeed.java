/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author DELL
 */
public final class Newsfeed extends javax.swing.JFrame {
    private final User user;
    /**
     * Creates new form newsfeed
     * @param user
     */
    
    public Newsfeed(User user) {
        initComponents();
        this.user = user;
        UpdateFriends();
        UpdateSuggestedFriends();
        UpdatePosts();
        UpdateStories();
    }

    public void UpdateFriends() {
        JPanel containerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);

        for (int i = 0; i < user.getFriendManagement().getFriends().size(); i++) {
            JLabel friendLabel = new JLabel(user.getFriendManagement().getFriends().get(i).getUsername());
            JLabel statusLabel = new JLabel(user.getFriendManagement().getFriends().get(i).getStatus());
            JPanel friendPanel = new JPanel();
            friendPanel.add(friendLabel);
            friendPanel.add(statusLabel);
            containerPanel.add(friendPanel);
        }
        friendsList.setViewportView(containerPanel);
    }

    public void UpdateSuggestedFriends(){
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
            UpdateSuggestedFriends();
        });
        JPanel suggestedFriendPanel = new JPanel();
        suggestedFriendPanel.add(friendLabel);
        suggestedFriendPanel.add(addFriendButton);
        containerPanel.add(suggestedFriendPanel);
    }
    suggestedFriendPanel.setViewportView(containerPanel);
}

    public void UpdatePosts() {
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
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));// Add padding
        postPanel.add(nameLabel);
        postPanel.add(photo);
        postPanel.add(postLabel);
        postPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        // Add margin between posts
        postPanel.setBorder(BorderFactory.createCompoundBorder(
                postPanel.getBorder(),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        containerPanel.add(postPanel);
    }
    postPanel.setViewportView(containerPanel);
}

    public void UpdateStories() {
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
        JPanel storyPanel = new JPanel();
        storyPanel.setLayout(new BoxLayout(storyPanel, BoxLayout.Y_AXIS));
        storyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        storyPanel.add(nameLabel);
        storyPanel.add(photo);
        storyPanel.add(storyLabel);
        storyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        // Add margin between stories
        storyPanel.setBorder(BorderFactory.createCompoundBorder(
                storyPanel.getBorder(),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        containerPanel.add(storyPanel);
    }
    storyPanel.setViewportView(containerPanel);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JButton addStoryOrPost = new javax.swing.JButton();
        javax.swing.JButton profileManagement = new javax.swing.JButton();
        javax.swing.JButton refresh = new javax.swing.JButton();
        javax.swing.JButton friendManagement = new javax.swing.JButton();
        postPanel = new javax.swing.JScrollPane();
        storyPanel = new javax.swing.JScrollPane();
        suggestedFriendPanel = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JScrollPane();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addStoryOrPost.setBackground(new java.awt.Color(0, 0, 0));
        addStoryOrPost.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addStoryOrPost.setForeground(new java.awt.Color(255, 255, 255));
        addStoryOrPost.setText("Add Story or Post");
        addStoryOrPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStoryOrPostActionPerformed(evt);
            }
        });

        profileManagement.setBackground(new java.awt.Color(0, 0, 0));
        profileManagement.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        profileManagement.setForeground(new java.awt.Color(255, 255, 255));
        profileManagement.setText("Profile Management");
        profileManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileManagementActionPerformed(evt);
            }
        });

        refresh.setBackground(new java.awt.Color(0, 0, 0));
        refresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        refresh.setForeground(new java.awt.Color(255, 255, 255));
        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        friendManagement.setBackground(new java.awt.Color(0, 0, 0));
        friendManagement.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        friendManagement.setForeground(new java.awt.Color(255, 255, 255));
        friendManagement.setText("Friends Management");
        friendManagement.setPreferredSize(new java.awt.Dimension(168, 31));
        friendManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendManagementActionPerformed(evt);
            }
        });

        postPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        postPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        storyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        storyPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        suggestedFriendPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        friendsList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Friends List");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Friends Suggesction");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(friendsList, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addStoryOrPost, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(friendManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(profileManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(storyPanel)
                    .addComponent(postPanel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(suggestedFriendPanel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(friendsList))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(suggestedFriendPanel)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(storyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(postPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(friendManagement, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(addStoryOrPost, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(profileManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addStoryOrPost, profileManagement, refresh});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addStoryOrPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStoryOrPostActionPerformed
        // TODO add your handling code here:
        new CreateContent(user).setVisible(true);
    }//GEN-LAST:event_addStoryOrPostActionPerformed

    private void profileManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileManagementActionPerformed
        // TODO add your handling code here:
        new ProfilePage(user, this).setVisible(true);
    }//GEN-LAST:event_profileManagementActionPerformed

    private void friendManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendManagementActionPerformed
        // TODO add your handling code here:
        new FriendManagement(user).setVisible(true);
    }//GEN-LAST:event_friendManagementActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        //REFRESH
        UpdateFriends();
        UpdateSuggestedFriends();
        UpdatePosts();
        UpdateStories();
    }//GEN-LAST:event_refreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane friendsList;
    private javax.swing.JScrollPane postPanel;
    private javax.swing.JScrollPane storyPanel;
    private javax.swing.JScrollPane suggestedFriendPanel;
    // End of variables declaration//GEN-END:variables
}
