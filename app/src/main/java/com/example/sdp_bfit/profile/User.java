package com.example.sdp_bfit.profile;

public class User {
    private int userID;
    private String userEmail;
    private String userFName;
    private String userPassword;
    private String userGender;
    private Double userWeight;
    private Double userHeight;

    //constructor
    public User(String userEmail, String userFName, String userPassword, String userGender, Double userWeight, Double userHeight) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userFName = userFName;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
    }
    public User(){

    }

    //setter
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setUserFName(String userFName){
        this.userFName = userFName;
    }
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
    public void setUserGender(String userGender){
        this.userGender = userGender;
    }
    public void setUserWeight(Double userWeight){
        this.userWeight = userWeight;
    }
    public void setUserHeight(Double userHeight){
        this.userHeight = userHeight;
    }

    //getter
    public Integer getUserID(){
        return userID;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public String getUserFName(){
        return userFName;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public String getUserGender(){
        return userGender;
    }
    public Double getUserWeight(){
        return userWeight;
    }
    public Double getUserHeight(){
        return userHeight;
    }

    //TOSTRING
    public String toString(){
        return   "" +
                "User ID: " + userID +"\r\n"+
                "User Email: " + userEmail +"\r\n"+
                "Full Name: " + userFName +"\r\n"+
                "Password: " + userPassword +"\r\n"+
                "Gender: " + userGender +"\r\n"+
                "Weight: " + userWeight+"\r\n"+
                "Height : " + userHeight +
                "";
    }

}
