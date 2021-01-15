package com.example.sdp_bfit.calories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.R;
import com.example.sdp_bfit.profile.ProfileViewModel;

public class CaloriesViewModel extends ViewModel {

   public MutableLiveData<String> mText,mealName,mealSize,mealCal;





    public CaloriesViewModel() {
        mText = new MutableLiveData<String>();
        mealName = new MutableLiveData<String>();
        mealSize= new MutableLiveData<String>();
        mealCal = new MutableLiveData<String>();

        //initialze value
        mealName.setValue(null);
        mealSize.setValue(null);
        mealCal.setValue(null);



//        mealCal = meal.getMealCal();
    }
    //setters
    public void setMealName(MutableLiveData<String> mealName){ this.mealName = mealName ;}
    public void setMealSize(MutableLiveData<String> mealSize){ this.mealSize = mealSize ;}
    public void setMealCal(MutableLiveData<String> mealCal){ this.mealCal = mealCal ;}
    //getters
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getmealName(){return mealName;}
    public LiveData<String> getMealSize(){return mealSize;}
    public LiveData<String> getMealCal(){return mealCal;}


}