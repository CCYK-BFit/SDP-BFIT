package com.example.sdp_bfit.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.sdp_bfit.R;

public class editProfileForm extends Fragment {
    private ProfileViewModel ProfileViewModel;
    EditText editName, editWeight, editHeight;
    Button btnSave, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_form_editprofile, container, false);
        editName = v.findViewById(R.id.profile_editprofile_edittxtName);
        editWeight= v.findViewById(R.id.profile_editprofile_edittxtWeight);
        editHeight = v.findViewById(R.id.profile_editprofile_edittxtHeight);
        btnSave = v.findViewById(R.id.profile_editprofile_btnSave);
        btnCancel = v.findViewById(R.id.profile_editprofile_btnCancel);
        // Need to define the child fragment layout
        return inflater.inflate(R.layout.fragment_profile_form_editprofile, container, false);


//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(editName.getText().toString())) {
//                    ProfileFragment.ShowUsername.setText(editName.getText().toString());
//                } else if (!TextUtils.isEmpty(editWeight.getText().toString())) {
//                    ProfileFragment.ShowWeight.setText(editWeight.getText().toString());
//                } else if (!TextUtils.isEmpty(editHeight.getText().toString())) {
//                    ProfileFragment.ShowHeight.setText(editHeight.getText().toString());
//                } else {
//
//                }
//            }
//        });
    }
}
