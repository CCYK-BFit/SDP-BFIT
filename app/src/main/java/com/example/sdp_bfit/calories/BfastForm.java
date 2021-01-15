package com.example.sdp_bfit.calories;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraX;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.R;
import com.example.sdp_bfit.profile.ProfileViewModel;
import com.google.mlkit.vision.barcode.BarcodeScanner;


public class BfastForm extends Fragment implements CameraActivity.CameraActivityListener {
    public static final String ARG_PARAM_MEALNAME = "mealName";
    public static final String ARG_PARAM_MEALCAL = "mealCal";

    private CaloriesViewModel CaloriesViewModel;
        private String mealName , mealCal;
        private EditText  editTextmealName,editTextmealCal, editTextmealSize, editTextmealRemark;

        public static BfastForm newInstance(String mealName,String mealCal){
            BfastForm bfastForm = new BfastForm();
            Bundle args = new Bundle();

            args.putString(ARG_PARAM_MEALNAME, mealName);
            args.putString(ARG_PARAM_MEALCAL, mealCal);
            bfastForm.setArguments(args);
            return bfastForm;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments()!=null){
                this.mealName = getArguments().getString(ARG_PARAM_MEALNAME);
                this.mealCal = getArguments().getString(ARG_PARAM_MEALCAL);
            }

        }
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                   ViewGroup container, Bundle savedInstanceState) {
        CaloriesViewModel = new ViewModelProvider(this).get(CaloriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calories_form_bfast, container, false);
            editTextmealName = root.findViewById(R.id.editTextMealbfast);
             editTextmealCal = root.findViewById(R.id.editTextCalories);
            editTextmealSize = root.findViewById(R.id.editTextServingSize);
            editTextmealRemark = root.findViewById(R.id.editTextRemark);
            Button button = root.findViewById(R.id.buttonSaveBfast);
//           //get data from Camera Activity
            //null point exception
//            mealName = getArguments().getString("mealname");
//            mealCal = getArguments().getString("mealcal");
//            if (mealName != null && mealCal != null){
//                        editTextmealName.setText(mealName);
//                         editTextmealCal.setText(mealCal);
//            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editTextmealName.setText(mealName);
                    editTextmealCal.setText(mealCal);
                    editTextmealSize.setText("hi");
                    editTextmealRemark.setText("random");


                }
            });

//            if (mealName!=null || mealCal!=null) {
//                editTextmealName.setText(mealName);
//                editTextmealCal.setText(mealCal);
//                editTextmealSize.setText("hi");
//                editTextmealRemark.setText("random");
//            }

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof BfastFormListener){
//            listener = (BfastFormListener) context;
//        }else{
//            throw new RuntimeException(context.toString()
//            +"MUST IMPLEMENT BFASTFORM LISTENER ")
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        listener=null;
//    }

//    public void UpdateValue(String mealName, String mealCal){
////        View root = LayoutInflater.inflate(R.layout.fragment_calories_form_bfast, container, false);
////        final TextView textView = root.findViewById(R.id.profile_txtProfile);
//
//
//        this.mealName = mealName;
//        this.mealCal = mealCal;
//
//
//    }

    @Override
    public void onMealDataReceived(String mealName,String mealCal) {
        editTextmealName.setText(mealName);
        editTextmealCal.setText(mealCal);
    }


}





