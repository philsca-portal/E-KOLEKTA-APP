package com.ekolekta.e_kolekta;

public class Data {
    private String tag;
    private String type;
    private String lat;
    private String lang;
    private String date;
    private String image;
    private String sortvalue;
    private String loc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Data(){
    }

    public Data(String sortvalue) {
        this.sortvalue = sortvalue;
    }

    public String getSortvalue() {
        return sortvalue;
    }

    public void setSortvalue(String sortvalue) {
        this.sortvalue = sortvalue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}
