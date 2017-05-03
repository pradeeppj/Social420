package com.social420.social420;

public class Groupsclass {

    String name,image, members, status;



    public Groupsclass(String image, String name, String members, String status) {
        this.image = image;
        this.name = name;
        this.members = members;
        this.status = status;


    }

    public Groupsclass() {
    }


    //name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //members

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }


    //status

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //GroupImage

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }




}
