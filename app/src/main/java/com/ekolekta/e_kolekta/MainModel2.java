package com.ekolekta.e_kolekta;

public class MainModel2 {
    String tag, type, image, date;

    MainModel2(){

    }
    public MainModel2(String tag, String type, String image,String date) {
        this.tag = tag;
        this.type = type;
        this.image = image;
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }
}
