package com.example.sdp_bfit.calories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sdp_bfit.R;

public  class SnackForm extends Fragment {
    private CaloriesViewModel CaloriesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Need to define the child fragment layout

        return inflater.inflate(R.layout.fragment_calories_form_snack, container, false);
    }
}