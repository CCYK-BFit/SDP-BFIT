package com.example.sdp_bfit.calories;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.signupandlogin.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import static com.example.sdp_bfit.calories.CaloriesFragment.todayDate;
import static com.example.sdp_bfit.calories.CameraActivity.foodLabel;
import static com.example.sdp_bfit.calories.CameraActivity.kcal;


public class BfastForm extends Fragment  {
    public Button btn_scan,btn_save,btn_cancel;
    private EditText  editTextmealName,editTextmealCal, editTextmealSize, editTextmealRemark;
    Dialog alertDialog;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                   ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calories_form_bfast, container, false);
        //Edit Text
            editTextmealName = root.findViewById(R.id.editTextMealName);
             editTextmealCal = root.findViewById(R.id.editTextCalories);
            editTextmealSize = root.findViewById(R.id.editTextServingSize);
            editTextmealRemark = root.findViewById(R.id.editTextMealRemark);
            //button
            btn_scan = root.findViewById(R.id.btn_scan);
            btn_save = root.findViewById(R.id.btn_save);
            btn_cancel = root.findViewById(R.id.btn_cancel);


            btn_scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{ enableCamera();
                    }catch(Exception e){ }
                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(editTextmealCal.getText().toString())||
                            TextUtils.isEmpty(editTextmealName.getText().toString())||
                    TextUtils.isEmpty(editTextmealSize.getText().toString())    ||
                            TextUtils.isEmpty(editTextmealRemark.getText().toString())
                                   ) {
                        //open error dialog
                        openDialog();

                    }
                    else{ //validate success

                        try {
                            String mealName = editTextmealName.getText().toString();
                            int mealSize = Integer.parseInt(editTextmealSize.getText().toString());
                            int mealCal = Integer.parseInt(editTextmealCal.getText().toString());
                            String mealRemark = editTextmealRemark.getText().toString();
                            Meal meal = new Meal("Breakfast", mealName, mealSize, mealCal, mealRemark, todayDate);
                            Database db = new Database(getContext());
                            boolean success = db.insertMealDetails(meal);
                            if (success = true) {
                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e){
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        return root;
    }

    private void openDialog() {
        // login alert message
        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.fragment_calories_alertdialog);
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.setCancelable(false);

        Button login_alertdialog_btnCancel = alertDialog.findViewById(R.id.login_alertdialog_btnCancel);
        Button login_alertdialog_btnRetry = alertDialog.findViewById(R.id.login_alertdialog_btnRetry);

        login_alertdialog_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        login_alertdialog_btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }



    //get called when return to the previously loaded fragment
    @Override
    public void onResume() {
        super.onResume();
        if (foodLabel != null & kcal!=null){
            editTextmealName.setText(foodLabel);
            editTextmealCal.setText(kcal);
        }

    }
    private void enableCamera(){

        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivity(intent);
    }




}







