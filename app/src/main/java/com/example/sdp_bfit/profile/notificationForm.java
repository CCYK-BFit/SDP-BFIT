package com.example.sdp_bfit.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sdp_bfit.R;

public class notificationForm extends Fragment {
    private ProfileViewModel ProfileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Need to define the child fragment layout

        return inflater.inflate(R.layout.fragment_profile_form_notification, container, false);
    }
}
