package com.example.sdp_bfit.workout;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.calories.Meal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sdp_bfit.MainActivity.stepCount;
import static com.example.sdp_bfit.calories.CaloriesFragment.todayDate;


public class WorkoutFragment extends Fragment  implements TimePickerDialog.OnTimeSetListener{
    private TextView SC;
    private TextView textView3;
    private TextView textView10;
    private Button Savew;
    Context context;
    Database database;
    SQLiteDatabase sqlLiteDatabase;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    public static ListView list_item;
    public static ArrayList<String> arrayList;
    public static ArrayAdapter adapter;
    private Button tbtn_add_minus2;



    private WorkoutViewModel WorkoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutViewModel =
                new ViewModelProvider(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        final TextView textView = root.findViewById(R.id.txt_workout);
        WorkoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//        Intent intent = new Intent(getActivity(),StepCounter.class);
//        startActivity(intent);


        //StepCounter
        SC = root.findViewById(R.id.SC);
        SC.setText(stepCount.toString());

        Savew = root.findViewById(R.id.Savew);

        Savew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int step = Integer.parseInt(SC.getText().toString());
                Double dis=Double.valueOf(textView10.getText().toString());
                Double cal=Double.valueOf(textView3.getText().toString());

                Workout workout = new Workout( step, dis, cal, todayDate);
                Database db = new Database(getContext());
                boolean success = db.insertWorkout(workout);
                if (success = true) {
                    Toast.makeText(getContext(),"Data Saved",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Save failed",Toast.LENGTH_LONG).show();
                }



                //reset
                SharedPreferences sharedPreferences=getActivity().getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.putInt("stepCount",stepCount);
                editor.apply();
                SC.setText(String.valueOf(0));
                stepCount=0;
                textView10.setText("0.00");
                textView3.setText("0.00");

            }
        });

        //distance
        textView10 = root.findViewById(R.id.textView10);

        String stepS = SC.getText().toString();
        double stepI = Integer.parseInt(stepS);
        double distanceCal = (stepI / 2000) * 1.609;
        String distance = String.format(Locale.CANADA, "%.2f", distanceCal);
//        String distance = String.valueOf(distanceCal);
        textView10.setText(distance.toString());

        //cal
        textView3 = root.findViewById(R.id.textView3);
        double caloriesCal = distanceCal * 55;
        String calories = String.format(Locale.CANADA, "%.2f", caloriesCal);
//        String calories = String.valueOf(caloriesCal);
        textView3.setText(distance.toString());

        //ListView
        list_item = root.findViewById(R.id.list_item);
        tbtn_add_minus2 = root.findViewById(R.id.tbtn_add_minus2);

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        list_item.setAdapter(adapter);


        tbtn_add_minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerDialog = new TimePickerFragment();
                timePickerDialog.show(getFragmentManager(), "time picker");
//                int count = arrayList.size()+1;
//
//                arrayList.add("Item: "+count);
//                adapter.notifyDataSetChanged();

            }
        });
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this Item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(which_item);
                                adapter.notifyDataSetChanged();
                                final Intent intent = new Intent(getActivity(), MyService.class);
                                getActivity().stopService(intent);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });


        return root;


    }
    public void bello( int intHourOfDay, int intMinute){

        arrayList.add(( intHourOfDay + ":" + intMinute ));
        adapter.notifyDataSetChanged();



    }

    public void onTimeSet(TimePicker timePicker, int intHourOfDay, int intMinute) {
//        textViewPicked.setText(("TimePicked=" + intHourOfDay + ":" + intMinute ));

        arrayList.add(( intHourOfDay + ":" + intMinute ));
        adapter.notifyDataSetChanged();

//        final Intent intent = new Intent(getActivity(), MyService.class);
//        stopService(intent);
//
//        Integer alarmHour = intHourOfDay;
//        Integer alarmMinute = intMinute;
//        intent.putExtra("alarmHour", alarmHour);
//        intent.putExtra("alarmMinute", alarmMinute);
//        startService(intent);
    }

    }



