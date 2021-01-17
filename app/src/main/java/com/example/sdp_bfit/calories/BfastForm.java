package com.example.sdp_bfit.calories;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.tabs.TabLayout;

import static com.example.sdp_bfit.calories.CameraActivity.foodLabel;
import static com.example.sdp_bfit.calories.CameraActivity.kcal;


public class BfastForm extends Fragment  {
    public Button btn_scan,btn_save,btn_cancel;
    private EditText  editTextmealName,editTextmealCal, editTextmealSize, editTextmealRemark;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                   ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calories_form_bfast, container, false);
        //Edit Text
            editTextmealName = root.findViewById(R.id.editTextMealName);
             editTextmealCal = root.findViewById(R.id.editTextCalories);
            editTextmealSize = root.findViewById(R.id.editTextServingSize);
            editTextmealRemark = root.findViewById(R.id.editTextRemark);
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
                    if (editTextmealCal.getText()!=null&&editTextmealName.getText()!=null&&editTextmealSize.getText()!=null
                            &&editTextmealRemark.getText()!=null) {
                        //getText
                        String mealName=editTextmealName.getText().toString();
                        int mealSize=Integer.parseInt(editTextmealSize.getText().toString());
                        int mealCal = Integer.parseInt(editTextmealCal.getText().toString());
                        String mealRemark = editTextmealRemark.getText().toString();
                        Meal meal = new Meal("Breakfast", mealName, mealSize, mealCal, mealRemark);
                        Database db = new Database(getContext());
                        boolean success = db.insertMealDetails(meal);
                        if (success = true) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{}
                }
            });

        return root;
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







