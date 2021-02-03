package com.example.sdp_bfit.calories;

public class Meal {
    private int mealID;
    private String mealType;
    private String mealName;
    private int mealSize;
    private int mealCal;
    private String mealRemark;
    private String mealDate;

    //constructor
    public Meal(String mealType, String mealName, int mealSize, int mealCal, String mealRemark,String mealDate) {
        this.mealType = mealType;
        this.mealName = mealName;
        this.mealSize = mealSize;
        this.mealCal = mealCal;
        this.mealRemark = mealRemark;
        this.mealDate = mealDate;

    }

    public Meal(){

    }

    //setter
    public void setMealName(String mealName){this.mealName = mealName;}
    public void setMealSize(int mealSize){this.mealSize = mealSize;}
    public void setMealCal(int mealCal){this.mealCal = mealCal;}

    //getter
    public String getMealType(){return mealType;}
    public String getMealName(){return mealName;}
    public int getMealSize(){return mealSize;}
    public int getMealCal(){return mealCal;}
    public String getMealRemark(){return mealRemark;}
    public String getMealDate(){return mealDate;}

    //TOSTRING
    public String toString(){
        return   "" +
                "MealType: " + mealType +"\r\n"+
                "Food: " + mealName +"\r\n"+
                "Serving size: " + mealSize +"\r\n"+
                "Calories: " + mealCal + "kcal" +"\r\n"+
                "Remark: " + mealRemark+"\r\n"+
                "Date : " + mealDate +
                  "";
    }



}
