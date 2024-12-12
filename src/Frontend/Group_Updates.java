package Frontend;

import Backend.Group;
import Backend.ProxyGroup;
import Backend.RealGroup;
import Backend.User;

import javax.swing.*;
import java.awt.*;

public class Group_Updates {

    public static void updateGroupPageDetails(RealGroup group,User mainUser ,JLabel groupPhoto, JLabel groupName, JLabel groupDescription, JScrollPane groupMembersScroller) {
        ProxyGroup groupProxy = new ProxyGroup(group,mainUser);
        groupName.setText(group.getName());
        groupDescription.setText(group.getDescription());
        ImageIcon icon = new ImageIcon(group.getPhotoPath());
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(groupPhoto.getWidth(), groupPhoto.getHeight(), java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        groupPhoto.setIcon(icon);

        JPanel containerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);
        for (User user : group.getUserRoles().keySet()) {
            System.out.println(user.getName());
            JLabel userLabel = new JLabel(user.getName());
            JButton promoteButton = new JButton("Promote");
            promoteButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                groupProxy.promoteUser(user);
                if(groupProxy.isCreator(mainUser)){
                JOptionPane.showMessageDialog(null, "User promoted successfully");
                promoteButton.setEnabled(false);
                promoteButton.setText("Promoted");}
            });
            JButton demoteButton = new JButton("Demote");
            demoteButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                groupProxy.demoteUser(user);
                if(groupProxy.isCreator(mainUser)){
                JOptionPane.showMessageDialog(null, "User demoted successfully");
                demoteButton.setEnabled(false);
                demoteButton.setText("Demoted");}
            });
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                groupProxy.removeUser(user);
                if(groupProxy.isAdminOrCreator(mainUser)){
                JOptionPane.showMessageDialog(null, user.getName()+" removed successfully");
                removeButton.setEnabled(false);
                removeButton.setText("Removed");}
            });
            JPanel userPanel = new JPanel();
            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
            userPanel.add(userLabel);
            userPanel.add(Box.createHorizontalStrut(10));
            userPanel.add(promoteButton);
            userPanel.add(demoteButton);
            userPanel.add(removeButton);
            containerPanel.add(userPanel);
            containerPanel.add(Box.createVerticalStrut(10));
        }
        groupMembersScroller.setViewportView(containerPanel);
    }
}