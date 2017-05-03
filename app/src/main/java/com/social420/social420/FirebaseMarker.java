package com.social420.social420;

/**
 * Created by pradeeppj on 18/04/17.
 */

public class FirebaseMarker {


    public double lati;
    public double longi;


    //required empty constructor
    public FirebaseMarker() {
    }

    public FirebaseMarker( double lati, double longi) {

        this.lati = lati;
        this.longi = longi;
    }


    public double getLongitude() {
        return longi;
    }

    public void setLongitude(double longitude) {
        this.longi = longitude;
    }

    public double getLatitude() {
        return lati;
    }

    public void setLatitude(double latitude) {
        this.lati = latitude;
    }
}