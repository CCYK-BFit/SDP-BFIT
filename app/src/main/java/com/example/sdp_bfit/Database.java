package com.example.sdp_bfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sdp_bfit.calories.Meal;

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


    }

