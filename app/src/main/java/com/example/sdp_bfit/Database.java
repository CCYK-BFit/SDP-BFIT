package com.example.sdp_bfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sdp_bfit.calories.Meal;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
     public static final String MEAL_TABLE = "MEAL_TABLE";
     public static final String MEAL_ID ="MEAL_ID";
     public static final String MEAL_TYPE = "MEAL_TYPE";
     public static final String MEAL_NAME = "MEAL_NAME";
     public static final String MEAL_SIZE ="MEAL_SIZE";
     public static final String MEAL_CAL = "MEAL_CAL";
     public static final String MEAL_REMARK = "MEAL_REMARK";

    //User Profile
    public static final String USER_PROFILE ="USER_PROFILE";
    public static final String USER_ID ="USER_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_FNAME = "USER_FNAME";
    public static final String USER_PASSWORD ="USER_PASSWORD";
    public static final String USER_WEIGHT = "USER_WEIGHT";
    public static final String USER_HEIGHT = "USER_HEIGHT";

     public Database(@Nullable Context context){super(context,"BFIT.db",null,1);}

    //this is called the first time the db is access, the code inside will create a new database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         //Nutrition Table
        String createTableStatement = "CREATE TABLE" +MEAL_TABLE+ "(" +
                                        MEAL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        MEAL_TYPE + "TEXT," +
                                        MEAL_NAME + "TEXT," +
                                        MEAL_CAL + "INTEGER," +
                                        MEAL_REMARK + "TEXT)"  ;

        //User Table
        String createUserTableStatement = "CREATE TABLE" +USER_PROFILE+ "("+
                                            USER_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            USER_EMAIL + "TEXT" +
                                            USER_FNAME + "TEXT" +
                                            USER_PASSWORD + "TEXT" +
                                            USER_WEIGHT + "DOUBLE" +
                                            USER_HEIGHT + "DOUBLE)" ;
    }

    //this is called if the database version number changes, it prevents user data from corrupting
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertMealDetails (Meal meal){
         SQLiteDatabase db = this.getWritableDatabase(); // insert action
        ContentValues cv = new ContentValues();
        cv.put(MEAL_TYPE,meal.getMealType());
        cv.put(MEAL_NAME,meal.getMealName());
        cv.put(MEAL_SIZE,meal.getMealSize());
        cv.put(MEAL_CAL,meal.getMealCal());
        cv.put(MEAL_REMARK,meal.getMealRemark());
        db.insert(MEAL_TABLE,null,cv);
    }

    public List<Meal> getMealDetails(){
         List <Meal> mealList = new ArrayList<>();
         //get data from the db
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM MEAL_TABLE";
        Cursor cs = db.rawQuery(query,null);
        //if cursor is able to move to the first row, then the result is present
        if (cs.moveToFirst()) {
            //loop through the result set and create new meal object, put them into mealList
            do{
//                int mealID = cs.getInt(0);
                String mealType = cs.getString(1);
                String mealName = cs.getString(2);
                int mealSize = cs.getInt(3);
                int mealCal = cs.getInt(4);
                String mealRemark = cs.getString(5);

                Meal meal = new Meal(mealType,mealName,mealSize,mealCal,mealRemark);
                mealList.add(meal);
            }while(cs.moveToNext());
        }else{
            //failure, do not do anything to the list
        }
        //close cursor and database
        cs.close();
        db.close();
        return mealList;
        }


    }

