package com.social420.social420;

/**
 * Created by pradeeppj on 26/03/17.
 */

public class ModelClass1 {
    String username,image, time, likes, comments, propic, description, filtername;



    public ModelClass1(String image, String username, String time, String likes, String comments, String propic, String desription, String filtername) {
        this.image = image;
        this.username = username;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
        this.propic = propic;
        this.description = desription;
        this.filtername = filtername;

    }

    public ModelClass1() {
    }


    //dealname
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiltername() {
        return filtername;
    }

    public void setFiltername(String filtername) {
        this.filtername = filtername;
    }



    //dealprice

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    //quantity

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    //storename

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    //image

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    //propic

    public String getPropic() {
        return propic;
    }
    public void setPropic(String propic) {
        this.propic = propic;
    }
}

