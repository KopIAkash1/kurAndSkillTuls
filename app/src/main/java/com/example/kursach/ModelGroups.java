package com.example.kursach;

public class ModelGroups {
    String name, image, skill;

    public ModelGroups(){

    }

    public ModelGroups(String name, String image, String skill){
        this.name = name;
        this.image = image;
        this.skill = skill;
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
