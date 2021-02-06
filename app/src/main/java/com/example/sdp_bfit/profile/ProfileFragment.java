package com.example.sdp_bfit.profile;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.landingpage.LandingpageActivity;
import com.example.sdp_bfit.signupandlogin.LoginActivity;

import static com.example.sdp_bfit.signupandlogin.LoginActivity.user;
import static com.example.sdp_bfit.profile.editProfileForm.name;
import static com.example.sdp_bfit.profile.editProfileForm.weight;
import static com.example.sdp_bfit.profile.editProfileForm.height;
import static com.example.sdp_bfit.profile.editProfileForm.btnSave;

public class ProfileFragment extends Fragment {

    public String eProfileTag ="editProfileForm", customizationTag = "customizationForm", notificationTag = "notificationForm", logoutTag = "logoutForm";

    private ProfileViewModel ProfileViewModel;
    public static  TextView UserID, ShowUsername, ShowUserEmail, ShowWeight, ShowHeight, ShowBMI, profile_txtBMI;
    ToggleButton tbtn_editprofile, tbtn_notification, tbtn_notification_sleepreminder, tbtn_notification_workoutreminder, tbtn_notification_periodreminder;
    ToggleButton tbtn_customization, tbtn_customization_dailyquote, tbtn_logout;
    Button profile_btnSave, profile_btnCancel, notification_btnSave, notification_btnCancel, customization_btnSave, customization_btnCancel;
    EditText editUserName, editWeight, editHeight;
    View card_editProfile, card_notification, card_customization, card_logout;
    Dialog LogoutConfirmationDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        UserID = root.findViewById(R.id.txtUserID);
        ShowUsername = root.findViewById(R.id.profile_txtShowUsername);
        ShowUserEmail = root.findViewById(R.id.profile_txtShowUserEmail);
        ShowWeight = root.findViewById(R.id.profile_txtShowUserWeight);
        ShowHeight = root.findViewById(R.id.profile_txtShowUserHeight);
        ShowBMI = root.findViewById(R.id.profile_txtShowUserBMI);
        profile_txtBMI = root.findViewById(R.id.profile_txtBMI);
        // edit profile fragment
        tbtn_editprofile = root.findViewById(R.id.profile_editprofile_tbtn_add_minus);
//        editUserName = root.findViewById(R.id.profile_editprofile_edittxtName);
//        editWeight = root.findViewById(R.id.profile_editprofile_edittxtWeight);
//        editHeight = root.findViewById(R.id.profile_editprofile_edittxtHeight);
//        profile_btnSave = root.findViewById(R.id.profile_editprofile_btnSave);

        // edit notification fragment
        tbtn_notification = root.findViewById(R.id.profile_notification_tbtn_add_minus);
        tbtn_notification_sleepreminder = root.findViewById(R.id.profile_notification_radbtn1);
        tbtn_notification_workoutreminder = root.findViewById(R.id.profile_notification_radbtn2);
        tbtn_notification_periodreminder = root.findViewById(R.id.profile_notification_radbtn3);
        notification_btnSave = root.findViewById(R.id.profile_notification_btnSave);
        notification_btnCancel = root.findViewById(R.id.profile_notification_btnCancel);

        // edit customization fragment
        tbtn_customization = root.findViewById(R.id.profile_customization_tbtn_add_minus);
        tbtn_customization_dailyquote = root.findViewById(R.id.profile_customization_radbtn1);
        customization_btnSave = root.findViewById(R.id.profile_customization_btnSave);
        customization_btnCancel = root.findViewById(R.id.profile_customization_btnCancel);

        tbtn_logout = root.findViewById(R.id.profile_logout_tbtn_add_minus);
        card_editProfile = root.findViewById(R.id.card_editprofile);
        card_notification = root.findViewById(R.id.card_notification);
        card_customization = root.findViewById(R.id.card_customization);
        card_logout = root.findViewById(R.id.card_logout);


        tbtn_editprofile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //show editprofile child fragment
                    Fragment editProfileFragment = new editProfileForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.editProfile_fragment_container, editProfileFragment, eProfileTag).show(editProfileFragment).commit();

                    getView().findViewById(R.id.editProfile_form_container).bringToFront();
                } else {
                    //hide child fragment
                    removeNestedFragment(eProfileTag);
                    card_notification.bringToFront();
                    card_customization.bringToFront();
                    card_logout.bringToFront();
                }
            }
        });

        tbtn_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //show notification child fragment
                if (isChecked) {
                    Fragment notificationFragment = new notificationForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.notification_fragment_container, notificationFragment, notificationTag).show(notificationFragment).commit();

                    getView().findViewById(R.id.notification_form_container).bringToFront();
                } else {
                    //hide child fragment
                    removeNestedFragment(notificationTag);
                    card_customization.bringToFront();
                    card_logout.bringToFront();
                }
            }
        });


        tbtn_customization.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //show customization child fragment
                if (isChecked) {
                    Fragment customizationFragment = new customizationForm();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
                    transaction.add(R.id.customization_fragment_container, customizationFragment, customizationTag).show(customizationFragment).commit();

                    getView().findViewById(R.id.customization_form_container).bringToFront();
                } else {
                    //hide child fragment
                    removeNestedFragment(customizationTag);
                    card_logout.bringToFront();
                }
            }
        });

        tbtn_logout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // show dialog box
                if (isChecked) {
                    openDialog();
                } else {
                    openDialog();
                }
            }
        });

        // get from login and set text
        String uemail = user.getUserEmail();
        String uname = user.getUserFName();
        String ugender = user.getUserGender();
        Double uweight = user.getUserWeight();
        Double uheight = user.getUserHeight();

        ShowUserEmail.setText(uemail.toString());
        ShowUsername.setText(uname.toString());
        ShowWeight.setText(uweight.toString());
        ShowHeight.setText(uheight.toString());

        calculateBMI();

//        String txtBMI = "BMI";
//
//        SpannableString ss = new SpannableString(txtBMI);
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(@NonNull View widget) {
//                calculateBMI();
//            }
//        };
//
//        ss.setSpan(clickableSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        profile_txtBMI.setText(ss);
//        profile_txtBMI.setMovementMethod(LinkMovementMethod.getInstance());

        return root;
    }

    //remove child fragment
    private void removeNestedFragment(String tag){
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(fragment).commit();
    }


    public void openDialog() {
        // logout confirmation
        LogoutConfirmationDialog = new Dialog(getActivity());
        LogoutConfirmationDialog.setContentView(R.layout.fragment_logout_confirmation);
        LogoutConfirmationDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LogoutConfirmationDialog.setCancelable(false);
        LogoutConfirmationDialog.show();

        Button logout_confirmation_btnYes = LogoutConfirmationDialog.findViewById(R.id.logout_confirmation_btnYes);
        Button logout_confirmation_btnCancel = LogoutConfirmationDialog.findViewById(R.id.logout_confirmation_btnCancel);


        logout_confirmation_btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LandingpageActivity.class);
                startActivity(intent);
            }
        });

        logout_confirmation_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutConfirmationDialog.dismiss();
            }
        });

    }
    //calculate BMI
    public void calculateBMI(){
        String weight = ShowWeight.getText().toString();
        String height = ShowHeight.getText().toString();

            float weightValue = Float.parseFloat(weight);
            float heightValue = Float.parseFloat(height) / 100;

            float BMI = weightValue / (heightValue * heightValue);
            ShowBMI.setText(String.valueOf(BMI));
    }

    //Display updated data
//    public void showModifiedDetails(){
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowUsername.setText(name.toString());
//                ShowWeight.setText(weight.toString());
//                ShowHeight.setText(height.toString());
//
//                float uweight = Float.parseFloat(weight);
//                float uheight = Float.parseFloat(height) / 100;
//                float uBMI = uweight / (uheight * uheight);
//                ShowBMI.setText(String.valueOf(uBMI));
//            }
//        });
//    }
}

