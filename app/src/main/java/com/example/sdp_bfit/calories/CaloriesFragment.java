package com.example.sdp_bfit.calories;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.sdp_bfit.calories.CameraActivity.UPCValue;
import static com.example.sdp_bfit.calories.CameraActivity.foodLabel;
import static com.example.sdp_bfit.calories.CameraActivity.kcal;

public class CaloriesFragment extends Fragment implements View.OnClickListener {

   public String BfastTag ="bfastForm" , LunchTag="lunchForm" ,SnackTag="snackForm" , DinnerTag="dinnerForm";

    private CaloriesViewModel CaloriesViewModel;
    ToggleButton btn_add_Bfast , btn_add_lunch,btn_add_snack,btn_add_dinner;
    public Button btn_scan_bfast , btn_scan_lunch , btn_scan_snack , btn_scan_dinner;
    ConstraintLayout card_bfast , card_lunch, card_snack,card_dinner;
    ToggleButton btnDropDown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CaloriesViewModel = new ViewModelProvider(this).get(CaloriesViewModel.class);
        View v = inflater.inflate(R.layout.fragment_calories_main, container, false);
        //toggle button
        btn_add_Bfast = v.findViewById(R.id.btn_add_bfast);
        btn_add_lunch = v.findViewById(R.id.btn_add_lunch);
        btn_add_snack = v.findViewById(R.id.btn_add_snack);
        btn_add_dinner = v.findViewById(R.id.btn_add_dinner);
         TextView textView = v.findViewById(R.id.calories);
        //button
        btn_scan_bfast = v.findViewById(R.id.btn_scan_bfast);
        btn_scan_lunch = v.findViewById(R.id.btn_scan_lunch);
        btn_scan_snack = v.findViewById(R.id.btn_scan_snack);
        btn_scan_dinner= v. findViewById(R.id.btn_scan_dinner);
        btnDropDown = v.findViewById(R.id.btnDropdown);
        //testing
        //Edittext

//        textView.setText("hI");

        //pane
        card_bfast = v.findViewById(R.id.card_breakfast);
        card_lunch = v.findViewById(R.id.card_lunch);
        card_snack= v.findViewById(R.id.card_snack);
        card_dinner = v.findViewById(R.id.card_dinner);
//        CaloriesViewModel.getmealName().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                mealName.setText(s);
//            }
//        });
        btn_scan_bfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try { enableCamera(); }
                catch(Exception e){
                    Toast.makeText(container.getContext(), "Error while trying to access your camera", Toast.LENGTH_SHORT).show();
                }
            }
        });
            btn_scan_lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try { enableCamera(); }
                    catch(Exception e){
                        Toast.makeText(container.getContext(), "Error while trying to access your camera", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//
        btn_add_Bfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //show calories from
                    insertNestedFragment();
                        getView().findViewById(R.id.bfast_form_container).bringToFront();
                    Toast.makeText(container.getContext(), foodLabel, Toast.LENGTH_SHORT).show();
                    //problematic code*********
//                    try{ if( foodLabel!= null){
//
////                        BfastForm bfastForm = (BfastForm) getChildFragmentManager().findFragmentByTag(BfastTag);
////                        bfastForm.UpdateValue(foodLabel, kcal);
////                        Toast.makeText(container.getContext(), foodLabel, Toast.LENGTH_SHORT).show();
//
//
//
//                    }}catch(Exception e){
//                        e.printStackTrace();
//                        Toast.makeText(container.getContext(), e.toString(), Toast.LENGTH_LONG).show();
//                    }

                    //end of problematic code********
                }
                else{
                    //hide calories form

                    removeNestedFragment(BfastTag);
                    card_lunch.bringToFront();
                    card_snack.bringToFront();
                    card_dinner.bringToFront();
                }
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
                    getView().findViewById(R.id.lunch_form_container).bringToFront();
                }
                else{
                    //hide calories form

                    removeNestedFragment(LunchTag);
                    card_snack.bringToFront();
                    card_dinner.bringToFront();
                }
            }
        });
        btn_add_snack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //show lunch calories from
                    Fragment snackFragment= new SnackForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.snack_fragment_container, snackFragment,SnackTag).show(snackFragment).commit();
                    getView().findViewById(R.id.snack_form_container).bringToFront();
                }
                else{
                    //hide calories form

                    removeNestedFragment(SnackTag);
                    card_dinner.bringToFront();
                }
            }
        });
        btn_add_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //show lunch calories from
                    Fragment dinnerFragment= new DinnerForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.dinner_fragment_container, dinnerFragment,DinnerTag).show(dinnerFragment).commit();
                    getView().findViewById(R.id.dinner_form_container).bringToFront();
                }
                else{
                    //hide calories form

                    removeNestedFragment(DinnerTag);
                }
            }
        });





        return v;


    }







//    public static class MealHistory extends Fragment{
//
//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            // Need to define the child fragment layout
//            return inflater.inflate(R.layout.fragment_calories_updated, container, true);
//        }
//    }

    private void insertNestedFragment() {
        Fragment bfastFragment= new BfastForm();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
        transaction.add(R.id.bfast_fragment_container, bfastFragment,BfastTag).show(bfastFragment).commit();

    }

    //remove child fragment
    private void removeNestedFragment(String tag){

        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(fragment).commit();
    }

   //start camera activity
    private void enableCamera(){

            Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivity(intent);
        }
    //send data to fragment
    public void sendDataToFragment(Fragment Fragment){
        Bundle bundle = new Bundle();
        bundle.putString("mealname",foodLabel);
        bundle.putString("mealcal",kcal);
        Fragment fragment = new Fragment();
        fragment.setArguments(bundle);

    }

    @Override
    public void onClick(View view) {

    }


}


