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
import static com.example.sdp_bfit.profile.ProfileFragment.ShowUsername;
import static com.example.sdp_bfit.profile.ProfileFragment.ShowWeight;
import static com.example.sdp_bfit.profile.ProfileFragment.ShowHeight;
import static com.example.sdp_bfit.profile.ProfileFragment.ShowBMI;

public class editProfileForm extends Fragment {
    private ProfileViewModel ProfileViewModel;
    public static EditText editName, editWeight, editHeight;
    public static String name, weight, height;
    public static Button btnSave, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_form_editprofile, container, false);
        editName = v.findViewById(R.id.profile_editprofile_edittxtName);
        editWeight= v.findViewById(R.id.profile_editprofile_edittxtWeight);
        editHeight = v.findViewById(R.id.profile_editprofile_edittxtHeight);
        btnSave = v.findViewById(R.id.profile_editprofile_btnSave);
        btnCancel = v.findViewById(R.id.profile_editprofile_btnCancel);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editName.getText().toString();
                weight = editWeight.getText().toString();
                height = editHeight.getText().toString();

                if (!TextUtils.isEmpty(name)) {
                    ShowUsername.setText(name);
                }
                    ShowWeight.setText(weight);
                    ShowHeight.setText(height);

                    float uweight = Float.parseFloat(weight);
                    float uheight = Float.parseFloat(height) / 100;
                    float uBMI = uweight / (uheight * uheight);
                    ShowBMI.setText(String.valueOf(uBMI));
            }
        });

        return v;
    }
}
