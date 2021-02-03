package com.example.sdp_bfit.dashboard;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.calories.Meal;
import com.example.sdp_bfit.workout.Workout;

public class DashboardFragment extends Fragment {
    private TextView textView30;
    private TextView textView10;



    private DashboardViewModel DashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.txt_dashboard);
        DashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });
        //cal and distance show
        textView30 = root.findViewById(R.id.textView30);
        textView10 = root.findViewById(R.id.textView10);

        displayCalories();
        displayDistance();


        return root;




    }
    void displayCalories() {
        Database db = new Database(getContext());
        Workout workout = new Workout();
        textView30.setText(String.valueOf(db.displayCal(workout)));
    }
    void displayDistance() {
        Database db = new Database(getContext());
        Workout workout = new Workout();
        textView10.setText(String.valueOf(db.displayDis(workout)));
    }



}

