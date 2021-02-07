package com.example.sdp_bfit.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sdp_bfit.dashboard.DashboardFragment;
import com.example.sdp_bfit.R;


public class customizationForm extends Fragment {
    private ProfileViewModel ProfileViewModel;
    private ToggleButton tbtn_customization_dailyquotes;
    private Button customization_btnSave, customization_btnCancel;
    public static boolean a, b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_form_customization, container, false);
        tbtn_customization_dailyquotes = root.findViewById(R.id.profile_customization_radbtn1);
        customization_btnSave = root.findViewById(R.id.profile_customization_btnSave);
        customization_btnCancel = root.findViewById(R.id.profile_customization_btnCancel);


        customization_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_customization_dailyquotes.isChecked()){
                    a = true;
                } else {
                    a = false;
                }
            }
        });

        return root;
    }
}
