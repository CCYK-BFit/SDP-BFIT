package com.example.sdp_bfit.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;

public class notificationForm extends Fragment {
    private ProfileViewModel ProfileViewModel;
    private ToggleButton tbtn_notification_sleepreminder, tbtn_notification_workoutreminder, tbtn_notification_periodreminder;
    private Button notification_btnSave, notification_btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_form_notification, container, false);

        tbtn_notification_sleepreminder = root.findViewById(R.id.profile_notification_radbtn1);
        tbtn_notification_workoutreminder = root.findViewById(R.id.profile_notification_radbtn2);
        tbtn_notification_periodreminder = root.findViewById(R.id.profile_notification_radbtn3);
        notification_btnSave = root.findViewById(R.id.profile_notification_btnSave);
        notification_btnCancel = root.findViewById(R.id.profile_notification_btnCancel);

        notification_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbtn_notification_sleepreminder.isChecked()){
                    ((MainActivity) getActivity()).sendOnSleepChannel(v);
                }
                if (tbtn_notification_workoutreminder.isChecked()){
                    ((MainActivity) getActivity()).sendOnWorkoutChannel(v);
                }
                if (tbtn_notification_periodreminder.isChecked()){
                    ((MainActivity) getActivity()).sendOnPeriodChannel(v);
                }
            }
        });

        return root;
    }
}




