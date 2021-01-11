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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;

public class CaloriesFragment extends Fragment {

    private String BfastTag ="bfastForm" , LunchTag="lunchForm" ,SnackTag="snackForm" , DinnerTag="dinnerForm";

    public interface FragmentListener{

    }

    private CaloriesViewModel CaloriesViewModel;
    ToggleButton btn_add_Bfast , btn_add_lunch,btn_add_snack,btn_add_dinner;
    Button btn_scan_bfast , btn_scan_lunch , btn_scan_snack , btn_scan_dinner;
    ConstraintLayout card_bfast , card_lunch, card_snack,card_dinner;

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
        //testing
//        textView.setText("hI");

        //pane
        card_bfast = v.findViewById(R.id.card_breakfast);
        card_lunch = v.findViewById(R.id.card_lunch);
        card_snack= v.findViewById(R.id.card_snack);
        card_dinner = v.findViewById(R.id.card_dinner);
        CaloriesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        btn_scan_bfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    Intent intent = new Intent();
//                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivity(intent);
                    enableCamera();

                }
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
//                        getView().findViewById(R.id.lunch_fragment_container).setVisibility(View.INVISIBLE);
//                        getView().findViewById(R.id.card_lunch).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.bfast_form_container).bringToFront();

//                    ViewCompat.setTranslationZ(getView().findViewById(R.id.bfast_fragment_container), 1);
                }
                else{
                    //hide calories form

                    removeNestedFragment(BfastTag);
//                    getView().findViewById(R.id.lunch_fragment_container).setVisibility(View.VISIBLE);
//                    getView().findViewById(R.id.card_lunch).setVisibility(View.VISIBLE);
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
    public static class BfastForm extends Fragment{
        private CaloriesViewModel CaloriesViewModel;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Need to define the child fragment layout
            return inflater.inflate(R.layout.fragment_calories_form_bfast, container, false);
        }
    }
    public static class LunchForm extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Need to define the child fragment layout
            return inflater.inflate(R.layout.fragment_calories_form_lunch, container, false);
        }
    }
    public static class SnackForm extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Need to define the child fragment layout
            return inflater.inflate(R.layout.fragment_calories_form_snack, container, false);
        }
    }
    public static class DinnerForm extends Fragment{
        private CaloriesViewModel CaloriesViewModel;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Need to define the child fragment layout
            return inflater.inflate(R.layout.fragment_calories_form_dinner, container, false);
        }
    }

    private void insertNestedFragment() {
        Fragment bfastFragment= new BfastForm();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
        transaction.add(R.id.bfast_fragment_container, bfastFragment,BfastTag).show(bfastFragment).commit();

    }
    private void removeNestedFragment(String tag){

        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(fragment).commit();
    }
    private void enableCamera(){

            Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivity(intent);
        }

    }


