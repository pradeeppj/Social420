package com.social420.social420;

/**
 * Created by pradeeppj on 13/04/17.
 */

public class chatfullclass {
    String image, message,propic, time;



    public chatfullclass(String image, String username, String time, String message,  String propic) {
        this.image = image;

        this.time = time;
        this.message = message;

        this.propic = propic;

    }

    public chatfullclass() {
    }


    //username



    //time

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    //message

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
