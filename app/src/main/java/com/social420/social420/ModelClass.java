package com.social420.social420;

/**
 * Created by pradeeppj on 23/03/17.
 */
public class ModelClass {
    String name,image, price, quantity, storename, thc, cbd, cbn, claims, dist;



    public ModelClass(String image, String name, String price, String quantity, String storename, String thc, String cbd, String cbn, String claims, String dist ) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.storename = storename;
        this.dist = dist;

        this.thc = thc;
        this.cbn = cbn;
        this.cbd = cbd;
        this.claims = claims;
    }

    public ModelClass() {
    }


    //dealname
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //dealprice

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    //quantity

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    //storename

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }


    //claims

    public String getClaims() {
        return claims;
    }

    public void setClaims(String claims) {
        this.claims = claims;
    }



    //thc

    public String getThc() {
        return thc;
    }

    public void setThc(String thc) {
        this.thc = thc;
    }

    //cbd

    public String getCbd() {
        return cbd;
    }

    public void setCbd(String cbd) {
        this.cbd = cbd;
    }


    //cbn

    public String getCbn() {
        return cbn;
    }

    public void setCbn(String cbn) {
        this.cbn = cbn;
    }


    //dist

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }





    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}

