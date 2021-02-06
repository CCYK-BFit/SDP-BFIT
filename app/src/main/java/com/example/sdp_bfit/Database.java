package com.example.sdp_bfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sdp_bfit.calories.Meal;
import com.example.sdp_bfit.profile.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.sdp_bfit.calories.CaloriesFragment.todayDate;

public class Database extends SQLiteOpenHelper {
     public static final String MEAL_TABLE = "MEAL_TABLE";
     public static final String MEAL_ID ="MEAL_ID";
     public static final String MEAL_TYPE = "MEAL_TYPE";
     public static final String MEAL_NAME = "MEAL_NAME";
     public static final String MEAL_SIZE ="MEAL_SIZE";
     public static final String MEAL_CAL = "MEAL_CAL";
     public static final String MEAL_REMARK = "MEAL_REMARK";
    public static final String MEAL_DATE = "MEAL_DATE";

    //User Profile
    private static final String USER_PROFILE ="USER_PROFILE";
    private static final String USER_ID ="USER_ID";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_FNAME = "USER_FNAME";
    private static final String USER_PASSWORD ="USER_PASSWORD";
    private static final String USER_GENDER ="USER_GENDER";
    private static final String USER_WEIGHT = "USER_WEIGHT";
    private static final String USER_HEIGHT = "USER_HEIGHT";

    public Database(@Nullable Context context){super(context,"BFIT.db",null,1);}

    //this is called the first time the db is access, the code inside will create a new database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         //Nutrition Table // do not alter the code here!!!!!!!
        String createTableStatement = "CREATE TABLE " +MEAL_TABLE+ "(" +
                                        MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        MEAL_TYPE + " TEXT," +
                                        MEAL_NAME + " TEXT," +
                                        MEAL_SIZE + " INTEGER," +
                                        MEAL_CAL + " INTEGER," +
                                        MEAL_REMARK + " TEXT," +
                                        MEAL_DATE + " TEXT)";

            sqLiteDatabase.execSQL(createTableStatement);
            //workout table
        /*String createWorkoutTableStatement = "CREATE TABLE " +MEAL_TABLE+ "(" +
                MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MEAL_TYPE + " TEXT," +
                MEAL_NAME + " TEXT," +
                MEAL_SIZE + " INTEGER," +
                MEAL_CAL + " INTEGER," +
                MEAL_REMARK + " TEXT," +
                MEAL_DATE + " TEXT)";

        sqLiteDatabase.execSQL(createWorkoutTableStatement);*/
        //sleep table
        //User Table
        String createUserTableStatement = "CREATE TABLE " +USER_PROFILE+ "(" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_EMAIL + " TEXT," +
                USER_FNAME + " TEXT," +
                USER_PASSWORD + " TEXT," +
                USER_GENDER + " TEXT," +
                USER_WEIGHT + " DOUBLE," +
                USER_HEIGHT + " DOUBLE)";

        sqLiteDatabase.execSQL(createUserTableStatement);
    }

    //this is called if the database version number changes, it prevents user data from corrupting
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //any changes to the ddl must be implemented here
        //any changes made to the create table statement must also code here
        // or else system would output error
        //can uninstall app on device to solve this prob temp
    }

    public boolean insertMealDetails (Meal meal){
         try {
             SQLiteDatabase db = this.getWritableDatabase(); // insert action
             ContentValues cv = new ContentValues();
             cv.put(MEAL_TYPE, meal.getMealType());
             cv.put(MEAL_NAME, meal.getMealName());
             cv.put(MEAL_SIZE, meal.getMealSize());
             cv.put(MEAL_CAL, meal.getMealCal());
             cv.put(MEAL_REMARK, meal.getMealRemark());
             cv.put(MEAL_DATE,meal.getMealDate());

             db.insert(MEAL_TABLE, null, cv);
             return true;
         }catch(SQLException e){

         }
         return  false;
    }

    public int calcCalories (Meal meal){
         try{
             SQLiteDatabase db = this.getReadableDatabase();
            String query ="SELECT SUM(MEAL_CAL) AS TotalCal FROM MEAL_TABLE ";
            Cursor cs = db.rawQuery(query,null);
            if(cs.moveToFirst()){
                do {
                    int calories = cs.getInt(0);
                    cs.close();
                    db.close();
                    return calories;

                }while(cs.moveToNext());

            }

             
         }catch(SQLException e){

         }

            return 0;

    }

    public List<Meal> getBfastDetails(){
         List <Meal> mealList = new ArrayList<>();
         //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT * FROM MEAL_TABLE WHERE MEAL_TYPE='Breakfast'";
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
                String mealDate = String.valueOf(todayDate);
                Meal meal = new Meal(mealType,mealName,mealSize,mealCal,mealRemark,mealDate);
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

    public List<Meal> getLunchDetails(){
        List <Meal> mealList = new ArrayList<>();
        //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT * FROM MEAL_TABLE WHERE MEAL_TYPE='Lunch'";
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
                String mealDate = String.valueOf(todayDate);
                Meal meal = new Meal(mealType,mealName,mealSize,mealCal,mealRemark,mealDate);
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
    public List<Meal> getDinnerDetails(){
        List <Meal> mealList = new ArrayList<>();
        //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT * FROM MEAL_TABLE WHERE MEAL_TYPE='Dinner'";
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
                String mealDate = String.valueOf(todayDate);
                Meal meal = new Meal(mealType,mealName,mealSize,mealCal,mealRemark,mealDate);
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
    public List<Meal> getSnackDetails(){
        List <Meal> mealList = new ArrayList<>();
        //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT * FROM MEAL_TABLE WHERE MEAL_TYPE='Snack'";
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
                String mealDate = String.valueOf(todayDate);
                Meal meal = new Meal(mealType,mealName,mealSize,mealCal,mealRemark,mealDate);
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

//    public void deleteItem(Object item) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(MEAL_TABLE, MEAL_ID+ " = ?",
//                new String[]{String.valueOf(item.getId())});
//        db.close();
//    }

    //Insert user details
    public boolean insertUserDetails (User user){
        try {
            SQLiteDatabase db = this.getWritableDatabase(); // insert action
            ContentValues cv = new ContentValues();
            cv.put(USER_EMAIL, user.getUserEmail());
            cv.put(USER_FNAME, user.getUserFName());
            cv.put(USER_PASSWORD, user.getUserPassword());
            cv.put(USER_GENDER, user.getUserGender());
            cv.put(USER_WEIGHT, user.getUserWeight());
            cv.put(USER_HEIGHT,user.getUserHeight());

            db.insert(USER_PROFILE, null, cv);
            return true;
        }catch(SQLException e){

        }
        return  false;
    }
    // Read Data from db
    public Cursor showUserDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery ="SELECT * FROM USER_PROFILE";
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }


    public String showPassword(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_PROFILE WHERE USER_EMAIL="+"'"+email+"'";
        Cursor cs = db.rawQuery(query,null);
        String password="";
        if (cs.moveToFirst()) {
            do {
                password = cs.getString(3);
            } while (cs.moveToNext());
            cs.close();
            db.close();
        }
        return password;
    }

    public String showUserName(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_PROFILE WHERE USER_EMAIL="+"'"+email+"'";
        Cursor cs = db.rawQuery(query,null);
        String uname="";
        if (cs.moveToFirst()) {
            do {
                uname = cs.getString(2);

                cs.close();
                db.close();

            } while (cs.moveToNext());

        }
        return uname;
    }

    public String showUserGender (String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_PROFILE WHERE USER_EMAIL="+"'"+email+"'";
        Cursor cs = db.rawQuery(query,null);
        String ugender="";
        if (cs.moveToFirst()) {
            do {
                ugender = cs.getString(4);

                cs.close();
                db.close();

            } while (cs.moveToNext());

        }
        return ugender;
    }

    public Double showUserWeight (String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_PROFILE WHERE USER_EMAIL="+"'"+email+"'";
        Cursor cs = db.rawQuery(query,null);
        Double uweight= 0.0;
        if (cs.moveToFirst()) {
            do {
                uweight = cs.getDouble(5);

                cs.close();
                db.close();

            } while (cs.moveToNext());

        }
        return uweight;
    }

    public Double showUserHeight (String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_PROFILE WHERE USER_EMAIL="+"'"+email+"'";
        Cursor cs = db.rawQuery(query,null);
        Double uheight=0.0;
        if (cs.moveToFirst()) {
            do {
                uheight = cs.getDouble(6);

                cs.close();
                db.close();

            } while (cs.moveToNext());

        }
        return uheight;
    }

    // Update user password
    public boolean updateUserPassword(String uEmail, String uPassword){
        SQLiteDatabase db = this.getWritableDatabase(); // insert action
        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL, uEmail);
        cv.put(USER_PASSWORD, uPassword);
        db.update("USER_PROFILE", cv, "WHERE USER_EMAIL=?", new String[]{uEmail});
        return true;
    }
}



