package com.example.sdp_bfit.calories;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sdp_bfit.R;

public class CaloriesFragment extends Fragment  {

    public String BfastTag ="bfastForm" , LunchTag="lunchForm" ,SnackTag="snackForm" , DinnerTag="dinnerForm";
    ToggleButton btn_add_Bfast , btn_add_lunch,btn_add_snack,btn_add_dinner;
    ConstraintLayout card_bfast , card_lunch, card_snack,card_dinner;
    public EditText mealname , mealcal , mealsize ;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calories_main, container, false);
        //toggle button
        btn_add_Bfast = v.findViewById(R.id.btn_add_bfast);
        btn_add_lunch = v.findViewById(R.id.btn_add_lunch);
        btn_add_snack = v.findViewById(R.id.btn_add_snack);
        btn_add_dinner = v.findViewById(R.id.btn_add_dinner);
         TextView textView = v.findViewById(R.id.calories);
        //button
//        btn_scan_lunch = v.findViewById(R.id.btn_scan_lunch);
//        btn_scan_snack = v.findViewById(R.id.btn_scan_snack);
//        btn_scan_dinner= v. findViewById(R.id.btn_scan_dinner);
//        btnDropDown = v.findViewById(R.id.btnDropdown);
        //testing
        //Edittext
            mealname = v.findViewById(R.id.editTextMealName);
            mealcal = v.findViewById(R.id.editTextCalories);
            mealsize=v.findViewById(R.id.editTextServingSize);


        //pane
        card_bfast = v.findViewById(R.id.card_breakfast);
        card_lunch = v.findViewById(R.id.card_lunch);
        card_snack= v.findViewById(R.id.card_snack);
        card_dinner = v.findViewById(R.id.card_dinner);


        //Toogle Button Action
        btn_add_Bfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){//show bfast calories from
            Fragment bfastFragment= new BfastForm();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
            transaction.replace(R.id.bfast_fragment_container, bfastFragment,BfastTag).addToBackStack("MEAL_FORM_FRAGMENT")
                    .show(bfastFragment).commit();

                    getView().findViewById(R.id.bfast_form_container).bringToFront(); }
                else{//hide calories form
                    removeNestedFragment(BfastTag);
                    card_lunch.bringToFront();
                    card_snack.bringToFront();
                    card_dinner.bringToFront(); }
            }
        });
        btn_add_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //show lunch calories from
                    Fragment lunchFragment= new LunchForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.lunch_fragment_container, lunchFragment,LunchTag).show(lunchFragment).commit();
                    getView().findViewById(R.id.lunch_form_container).bringToFront(); }
                else{//hide calories form
                    removeNestedFragment(LunchTag);
                    card_snack.bringToFront();
                    card_dinner.bringToFront();
                }
            }
        });
        btn_add_snack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){//show lunch calories from
                    Fragment snackFragment= new SnackForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.snack_fragment_container, snackFragment,SnackTag).show(snackFragment).commit();
                    getView().findViewById(R.id.snack_form_container).bringToFront(); }
                else{//hide calories form
                    removeNestedFragment(SnackTag);
                    card_dinner.bringToFront(); }
            }
        });
        btn_add_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){//show lunch calories from
                    Fragment dinnerFragment= new DinnerForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.dinner_fragment_container, dinnerFragment,DinnerTag).show(dinnerFragment).commit();
                    getView().findViewById(R.id.dinner_form_container).bringToFront();
                }
                else{//hide calories form
                    removeNestedFragment(DinnerTag); }
            }
        });


        return v;


    }

    //remove child fragment
    private void removeNestedFragment(String tag){

        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(fragment).commit();
    }


}


