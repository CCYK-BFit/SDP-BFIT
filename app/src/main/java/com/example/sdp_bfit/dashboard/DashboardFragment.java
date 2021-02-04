package com.example.sdp_bfit.dashboard;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.graphics.vector.Fill;
import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.workout.Workout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;

import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment  {

    private DashboardViewModel DashboardViewModel;
    private TextView textView30;
    private TextView textView10;
    //newly added
    private BarChart barChart;
    private SQLiteDatabase sqLiteDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        barChart = (BarChart)view.findViewById(R.id.barchart_calories);


//        ar.setData(data); b

//        List<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, 100f,"Breakfast"));
//        entries.add(new BarEntry(1f, 82f,"Lunch"));
//        entries.add(new BarEntry(2f, 95f,"Dinner"));
//        entries.add(new BarEntry(3f, 69f,"Snack"));
//
//
//
//        BarDataSet bSet = new BarDataSet(entries, "calories");
//        bSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//
//        ArrayList<String> barFactors = new ArrayList<>();
//        barFactors.add("Breakfast");
//        barFactors.add("Lunch");
//        barFactors.add("Dinner");
//        barFactors.add("Snack");
//
//
//        XAxis xAxis = bar.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        BarData data = new BarData(bSet);
//        data.setBarWidth(0.9f); // set custom bar width
//        data.setValueTextSize(12);
//
        //cal and distance show
        textView30 = view.findViewById(R.id.textView30);
        textView10 = view.findViewById(R.id.textView10);

        displayCalories();
        displayDistance();




        addNutritionDataToChart();

        return view;
    }

    public void addNutritionDataToChart(){
        Database db = new Database(getActivity());
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        ArrayList<String> yData = db.getyVal();

        for(int i = 0; i<db.getyVal().size();i++){
            BarEntry newBarEntry = new BarEntry(i, Float.parseFloat(db.getyVal().get(i)));
            yVals.add(newBarEntry);
        }
        //x-axis data
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<String> xData = db.getXVal();

        for(int i =0; i<db.getXVal().size();i++){
            xVals.add(xData.get(i));
        }
        //pass y-value to bardataset
        BarDataSet dataset = new BarDataSet(yVals,"Calories");
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<IBarDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(dataset);
        //pass dataset into bardata
        BarData data = new BarData(dataSets1);
        //putting 4 meal type in x-axis
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
       //pass bardata into barchart
        barChart.setData(data);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        barChart.invalidate();
        //set description
        Description description = new Description();
        description.setTextColor(android.R.color.holo_blue_light);
        description.setText("Total calories according to meal types");
        barChart.setDescription(description);
        barChart.setDrawGridBackground(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        addNutritionDataToChart();
    }
    void displayCalories() {
        Database db = new Database(getContext());
        Workout workout = new Workout();
        textView30.setText(String.valueOf(db.displayCal(workout)));
    }
    void displayDistance() {
        Database db = new Database(getContext());
        Workout workout = new Workout();
        textView10.setText(String.valueOf(db.displayDis(workout)));
    }
}
