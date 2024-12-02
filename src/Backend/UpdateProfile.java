/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.io.IOException;

import java.util.List;

/**
 *
 * @author DELL
 */
public class UpdateProfile {
    private static final String USERS_FILE = "databases/users.json";


    public void updateProfilePhoto( String profilePhotoPath, String userId) throws IOException {

        ProfileManager profileManager = new ProfileManager();
        User user = profileManager.findUserById(userId);
        if (user != null) {
            user.getUserProfile().setProfilePhotoPath(profilePhotoPath);
            profileManager.saveUsers(profileManager.getUsers());
            JOptionPane.showMessageDialog(null, "Profile photo updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




        public void updateCoverPhoto( String coverPhotoPath, String userId) throws IOException {

            ProfileManager profileManager = new ProfileManager();
            User user = profileManager.findUserById(userId);
            if (user != null) {
                user.getUserProfile().setCoverPhotoPath(coverPhotoPath);
                profileManager.saveUsers(profileManager.getUsers());
                JOptionPane.showMessageDialog(null, "Cover photo updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }



            public void updateBio( String bio , String userId) throws IOException {

                ProfileManager profileManager = new ProfileManager();
                User user = profileManager.findUserById(userId);
                if (user != null) {
                    user.getUserProfile().setBio(bio);
                    profileManager.saveUsers(profileManager.getUsers());
                    JOptionPane.showMessageDialog(null, "Bio updated successfully.");
                }else {
                    JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
                public void updatePassword( String password, String userId) throws IOException {

                    ProfileManager profileManager = new ProfileManager();
                    User user = profileManager.findUserById(userId);
                    if (user != null ) {
                        user.setHashedPassword(PasswordHashing.hashPassword(password));
                        profileManager.saveUsers(profileManager.getUsers());
                        JOptionPane.showMessageDialog(null, "Password updated successfully.");
                    }else {
                        JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }




}
