package com.ekolekta.e_kolekta;

public class userHelperClass {

    String baranggayid,fullname,username,email,phoneno,password,points;

    public userHelperClass() {
    }

    public userHelperClass(String baranggayid,String fullname, String username, String email, String phoneno,String points) {
        this.baranggayid = baranggayid;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phoneno = phoneno;
        this.points = points;
        this.password = password;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaranggayid() {
        return baranggayid;
    }

    public void setBaranggayid(String baranggayid) {
        this.baranggayid = baranggayid;
    }
}


