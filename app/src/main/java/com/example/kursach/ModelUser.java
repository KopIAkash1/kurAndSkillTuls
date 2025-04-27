package com.example.kursach;

public class ModelUser {
    String email, image, name, phone, uid, groups;

    public ModelUser(){

    }

    public String getEmail() {
        return email;
    }
    public String getGroups(){
        return groups;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public ModelUser(String email, String image, String name, String phone, String uid, String groups){
        this.name = name;
        this.image = image;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
        this.groups = groups;
    }
}
