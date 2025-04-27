package com.example.kursach;

import java.util.List;

public class ModelGroups {
    String name, image, skill;
    List<ModelUser> usersList;

    public ModelGroups(){

    }

    public ModelGroups(String name, String image, String skill, List<ModelUser> UsersList){
        this.name = name;
        this.image = image;
        this.skill = skill;
        this.usersList = UsersList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }


}
