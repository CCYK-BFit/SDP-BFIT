package com.example.sdp_bfit.profile;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.R;
import com.example.sdp_bfit.landingpage.LandingpageActivity;

public class ProfileFragment extends Fragment {

    public String eProfileTag ="editProfileForm", customizationTag = "customizationForm", notificationTag = "notificationForm", logoutTag = "logoutForm";

    private ProfileViewModel ProfileViewModel;
    public static  TextView UserID, ShowUsername, ShowUserEmail, ShowWeight, ShowHeight, ShowBMI, profile_txtBMI;
    ToggleButton tbtn_editprofile, tbtn_notification, tbtn_notification_sleepreminder, tbtn_notification_workoutreminder, tbtn_notification_periodreminder;
    ToggleButton tbtn_customization, tbtn_customization_dailyquote, tbtn_customization_calander, tbtn_customization_averagesteps, tbtn_customization_dailycalories, tbtn_customization_averagesleep, tbtn_logout;
    Button profile_btnSave, profile_btnCancel, notification_btnSave, notification_btnCancel, customization_btnSave, customization_btnCancel;
    EditText editUserName, editWeight, editHeight;
    View card_editProfile, card_notification, card_customization, card_logout;
    Dialog LogoutConfirmationDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        UserID = root.findViewById(R.id.txtUserID);
        ShowUsername = root.findViewById(R.id.profile_txtShowUsername);
        ShowUserEmail = root.findViewById(R.id.profile_txtShowUserEmail);
        ShowWeight = root.findViewById(R.id.profile_txtShowUserWeight);
        ShowHeight = root.findViewById(R.id.profile_txtShowUserHeight);
        ShowBMI = root.findViewById(R.id.profile_txtShowUserBMI);
        profile_txtBMI = root.findViewById(R.id.profile_txtBMI);
        // edit profile fragment
        tbtn_editprofile = root.findViewById(R.id.profile_editprofile_tbtn_add_minus);
        editUserName = root.findViewById(R.id.profile_editprofile_edittxtName);
        editWeight = root.findViewById(R.id.profile_editprofile_edittxtWeight);
        editHeight = root.findViewById(R.id.profile_editprofile_edittxtHeight);
        profile_btnSave = root.findViewById(R.id.profile_editprofile_btnSave);

        // edit notification fragment
        tbtn_notification = root.findViewById(R.id.profile_notification_tbtn_add_minus);

        // edit customization fragment
        tbtn_customization = root.findViewById(R.id.profile_customization_tbtn_add_minus);
        tbtn_logout = root.findViewById(R.id.profile_logout_tbtn_add_minus);
        card_editProfile = root.findViewById(R.id.card_editprofile);
        card_notification = root.findViewById(R.id.card_notification);
        card_customization = root.findViewById(R.id.card_customization);
        card_logout = root.findViewById(R.id.card_logout);

        UserID.setText("A1001");
        ShowUsername.setText("Ashlyn Yeoh");
        ShowUserEmail.setText("ashlynyy@gmail.com");
        ShowWeight.setText("50");
        ShowHeight.setText("160");

        String txtBMI = "BMI";

        SpannableString ss = new SpannableString(txtBMI);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                displayBMI();
            }
        };

        ss.setSpan(clickableSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_txtBMI.setText(ss);
        profile_txtBMI.setMovementMethod(LinkMovementMethod.getInstance());

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
    public void calculateBMI(View V){
        String weight = ShowWeight.getText().toString();
        String height = ShowHeight.getText().toString();
//        if(TextUtils.isEmpty(ShowWeight.getText().toString()) && TextUtils.isEmpty(ShowHeight.getText().toString())){
//            new AlertDialog.Builder(getActivity())
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Null Value")
//                    .setMessage("Weight/Height field cannot be empty.")
//                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .setNegativeButton("Cancel", null)
//                    .show();
//        }
            float weightValue = Float.parseFloat(weight);
            float heightValue = Float.parseFloat(height) / 100;

            float BMI = weightValue / (heightValue * heightValue);
            String BMIText = Float.toString(BMI);
            ShowBMI.setText((int) BMI);

    }
    // convert float to string and display BMI
    public void displayBMI(){
        float BMI = (float) 0.00652;
        String BMIText=Float.toString(BMI);
        ShowBMI.setText(BMIText);
    }
}

