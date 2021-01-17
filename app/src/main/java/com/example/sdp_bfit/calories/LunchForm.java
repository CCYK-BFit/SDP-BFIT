package com.example.sdp_bfit.calories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sdp_bfit.R;
import static com.example.sdp_bfit.calories.CameraActivity.foodLabel;
import static com.example.sdp_bfit.calories.CameraActivity.kcal;

public  class LunchForm extends Fragment {

    public Button btn_scan,btn_save,btn_cancel;
    private EditText editTextmealName,editTextmealCal, editTextmealSize, editTextmealRemark;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calories_form_lunch, container, false);
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
                }catch(Exception e){

                }
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
