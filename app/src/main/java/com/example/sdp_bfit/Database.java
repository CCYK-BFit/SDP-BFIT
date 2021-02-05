package com.example.sdp_bfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.sdp_bfit.calories.Meal;
import com.example.sdp_bfit.workout.Workout;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import static com.example.sdp_bfit.calories.CaloriesFragment.todayDate;

public class Database extends SQLiteOpenHelper {
    public static final String MEAL_TABLE = "MEAL_TABLE";
    public static final String MEAL_ID = "MEAL_ID";
    public static final String MEAL_TYPE = "MEAL_TYPE";
    public static final String MEAL_NAME = "MEAL_NAME";
    public static final String MEAL_SIZE = "MEAL_SIZE";
    public static final String MEAL_CAL = "MEAL_CAL";
    public static final String MEAL_REMARK = "MEAL_REMARK";
    public static final String MEAL_DATE = "MEAL_DATE";
    public static final String WORKOUT_TABLE = "WORKOUT_TABLE";
    public static final String WORKOUT_STEP = "WORKOUT_STEP";
    public static final String WORKOUT_DATE = "WORKOUT_DATE";
    public static final String WORKOUT_CAL = "WORKOUT_CAL";
    public static final String WORKOUT_DIS = "WORKOUT_DIS";

    public static final String SLEEP_TABLE = "SLEEP_TABLE";
    public static String SLEEP_ID = "SLEEP_ID";
    public static final String WAKE_TIME = "WAKE_TIME";
    public static final String SLEEP_TIME = "SLEEP_TIME";
    public static final String HOUR_DIFF = "HOUR_DIFF";



    public Database(@Nullable Context context) {
        super(context, "BFIT.db", null, 1);
    }

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
        String createWorkoutTableStatement = "CREATE TABLE " +WORKOUT_TABLE+ "(" +
                WORKOUT_STEP + " INTEGER," +
                WORKOUT_CAL + " DOUBLE," +
                WORKOUT_DIS + " DOUBLE," +
                WORKOUT_DATE + " TEXT)";

        sqLiteDatabase.execSQL(createWorkoutTableStatement);
        //sleep table

        String createSleepTable = "CREATE TABLE " + SLEEP_TABLE+ "(" +
                SLEEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SLEEP_TIME + " TEXT," +
                WAKE_TIME + " TEXT," +
                HOUR_DIFF + " TEXT)" ;

        sqLiteDatabase.execSQL(createSleepTable);

        //user table
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

    public boolean insertWorkout (Workout workout){
        try {
            SQLiteDatabase db = this.getWritableDatabase(); // insert action
            ContentValues cv = new ContentValues();
            cv.put(WORKOUT_STEP, workout.getStep());
            cv.put(WORKOUT_CAL, workout.getCal());
            cv.put(WORKOUT_DIS, workout.getDis());
            cv.put(WORKOUT_DATE, workout.getDate());

            db.insert(WORKOUT_TABLE, null, cv);
            return true;
        }catch(SQLException e){

        }
        return  false;
    }

    public double displayCal (Workout workout) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT AVG(WORKOUT_CAL) AS AvgCal FROM WORKOUT_TABLE ";
            Cursor cs = db.rawQuery(query, null);
            if (cs.moveToFirst()) {
                do {
                    double calw = cs.getDouble(0);
                    cs.close();
                    db.close();
                    return calw;

                } while (cs.moveToNext());

            }
        }catch(SQLException e){
        }
        return 0.00;
    }
    public double displayDis (Workout workout) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT AVG(WORKOUT_DIS) AS AvgCal FROM WORKOUT_TABLE ";
            Cursor cs = db.rawQuery(query, null);
            if (cs.moveToFirst()) {
                do {
                    double disw = cs.getDouble(0);
                    cs.close();
                    db.close();
                    return disw;

                } while (cs.moveToNext());

            }
        }catch(SQLException e){
        }
        return 0.00;
    }

    public int calcCalories (Meal meal){
         try{
             SQLiteDatabase db = this.getReadableDatabase();
            String query ="SELECT SUM(MEAL_CAL) FROM MEAL_TABLE WHERE MEAL_DATE ="+"'"+todayDate+"';";
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
        List<Meal> mealList = new ArrayList<>();
        //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT * FROM MEAL_TABLE WHERE MEAL_TYPE='Breakfast' AND MEAL_DATE="+"'"+todayDate+"';";
        Cursor cs = db.rawQuery(query, null);
        //if cursor is able to move to the first row, then the result is present
        if (cs.moveToFirst()) {
            //loop through the result set and create new meal object, put them into mealList
            do {
//                int mealID = cs.getInt(0);
                String mealType = cs.getString(1);
                String mealName = cs.getString(2);
                int mealSize = cs.getInt(3);
                int mealCal = cs.getInt(4);
                String mealRemark = cs.getString(5);
                String mealDate = cs.getString(6);
                Meal meal = new Meal(mealType,mealName,mealSize,mealCal,mealRemark,mealDate);
                mealList.add(meal);
            } while (cs.moveToNext());
        } else {
            //failure, do not do anything to the list
        }
        //close cursor and database
        cs.close();
        db.close();
        return mealList;
    }

    public boolean recordSleepTime(String sleeptime) {
        try {
            SQLiteDatabase db = this.getWritableDatabase(); // insert action
            ContentValues cv = new ContentValues();
//            String waketime = "08:01";
//            cv.put(WAKE_TIME,waketime);
            cv.put(SLEEP_TIME, sleeptime);
            // cv.put(SLEEP_HOURS,sleephours);
            db.insert(SLEEP_TABLE, null, cv);
            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean updateWakeTime(String waketime) {

        try {

            String maxID = "";

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT max(SLEEP_ID) FROM SLEEP_TABLE";
            Cursor cs = db.rawQuery(query, null);

            if (cs.moveToFirst()) {
                do {
                    maxID = cs.getString(0);
                    System.out.println(cs.toString());
                    cs.close();
                    db.close();

                } while (cs.moveToNext());

            }
            System.out.println("Max ID is :" + maxID);


            SQLiteDatabase dbWrite = this.getWritableDatabase(); // insert action
            ContentValues cv = new ContentValues();
            cv.put(WAKE_TIME, waketime);
            //cv.put(SLEEP_TIME,sleeptime);
            // cv.put(SLEEP_HOURS,sleephours);
            dbWrite.update(SLEEP_TABLE, cv, SLEEP_ID = maxID, null);


            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean updateHourDiff(String hourDiff) {

        try {

            String maxID = "";

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT max(SLEEP_ID) FROM SLEEP_TABLE";
            Cursor cs = db.rawQuery(query, null);

            if (cs.moveToFirst()) {
                do {
                    maxID = cs.getString(0);
                    System.out.println(cs.toString());
                    cs.close();
                    db.close();

                } while (cs.moveToNext());

            }
            System.out.println("Max ID is :" + maxID);


            SQLiteDatabase dbWrite = this.getWritableDatabase(); // insert action
            ContentValues cv = new ContentValues();
            cv.put(HOUR_DIFF, hourDiff);

            dbWrite.update(SLEEP_TABLE, cv, SLEEP_ID = maxID, null);

            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    public Double displaySleepHours() {

        String result = "";
        Double hourDiff = 0.0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT round((strftime('%s', WAKE_TIME) - strftime('%s', SLEEP_TIME)) / 3600.0, 2) AS 'duration' FROM SLEEP_TABLE WHERE SLEEP_ID =(SELECT max(SLEEP_ID) FROM SLEEP_TABLE)";
        Cursor cs = db.rawQuery(query, null);

        if (cs.moveToFirst()) {
            do {
                hourDiff = cs.getDouble(0);
                System.out.println(cs.toString());
                cs.close();
                db.close();

            } while (cs.moveToNext());

        }
        System.out.println("hour diff is :" + hourDiff);
        return hourDiff;
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
                String mealDate = cs.getString(6);
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
                String mealDate = cs.getString(6);
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


    //getting data for x-axis : breakfast , lunch , dinner, snack
    public ArrayList<String> getXVal(){
        ArrayList<String> xVal = new ArrayList<String>();
        //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT MEAL_TYPE FROM MEAL_TABLE GROUP BY MEAL_TYPE";
        Cursor cs = db.rawQuery(query,null);
        //if cursor is able to move to the first row, then the result is present
        for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()){
            xVal.add(cs.getString(0));
        }
        //close cursor and database
        cs.close();
        db.close();
        return xVal;
    }


    //getting data for y-axis : count calories of each meal type
    public ArrayList<String> getyVal(){
        ArrayList<String> yVal = new ArrayList<String>();
        //get data from the db
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="SELECT SUM(MEAL_CAL) FROM MEAL_TABLE GROUP BY MEAL_TYPE";
        Cursor cs = db.rawQuery(query,null);
        //if cursor is able to move to the first row, then the result is present
        for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()){
            yVal.add(cs.getString(0));
        }
        //close cursor and database
        cs.close();
        db.close();
        return yVal;
    }
    public ArrayList<String> getWorkoutxVal(){
        ArrayList<String> yVal = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT WORKOUT_DATE FROM WORKOUT_TABLE GROUP BY WORKOUT_DATE";
        Cursor cs = db.rawQuery(query,null);
        for(cs.moveToFirst();!cs.isAfterLast();cs.moveToNext()){
            yVal.add(cs.getString(0));
        }
        cs.close();
        db.close();
        return yVal;
    }
    public ArrayList<String> getWorkoutyVal(){
        ArrayList<String> yVal = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(WORKOUT_DIS)  FROM WORKOUT_TABLE GROUP BY WORKOUT_DATE ";
        Cursor cs = db.rawQuery(query,null);
        for(cs.moveToFirst();!cs.isAfterLast();cs.moveToNext()){
            yVal.add(cs.getString(0));
        }
        cs.close();
        db.close();
        return yVal;
    }








    }

