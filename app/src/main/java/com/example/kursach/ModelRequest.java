package com.example.kursach;

public class ModelRequest {
    String groupName, groupImage, userName, userImage;

    public ModelRequest() {
    }

    public ModelRequest(String groupName, String groupImage, String userName, String userImage) {
        this.groupName = groupName;
        this.groupImage = groupImage;
        this.userName = userName;
        this.userImage = userImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
