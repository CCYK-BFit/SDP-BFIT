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
    //newly added
    private BarChart barChart;
    private SQLiteDatabase sqLiteDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ProductSans.ttf");

        barChart = (BarChart)view.findViewById(R.id.barchart_calories);

        //need to change this
//
//            ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
//
//            for (int i = 0; i < dbHandler.queryYData().size(); i++)
//                yVals.add(new BarEntry(dbHandler.queryYData().get(i), i));
//
//            ArrayList<String> xVals = new ArrayList<String>();
//            for(int i = 0; i < dbHandler.queryXData().size(); i++)
//                xVals.add(dbHandler.queryXData().get(i));
//
//            BarDataSet dataSet = new BarDataSet(yVals, "Pijnschaal");
//            BarData data = new BarData(xVals, dataSet);

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
//        Description description = new Description();
//        description.setTextColor(android.R.color.holo_blue_light);
//        description.setText("All values in marks");
//        bar.setDescription(description);
//        bar.setDrawGridBackground(false);
//
//        bar.setData(data);
//        bar.setFitBars(true); // make the x-axis fit exactly all bars
//        bar.invalidate(); // refresh
//        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));
//
//        Legend l = bar.getLegend();
//        l.setFormSize(10f); // set the size of the legend forms/shapes
//        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
////        l.setTypeface(font);
//        l.setTextSize(12f);
//        l.setTextColor(Color.BLACK);
//        List<LegendEntry> lentries = new ArrayList<>();
//        for (int i = 0; i < barFactors.size(); i++) {
//            LegendEntry entry = new LegendEntry();
//            entry.formColor = ColorTemplate.VORDIPLOM_COLORS[i];
//            entry.label = barFactors.get(i);
//            lentries.add(entry);
//        }
//        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
//        l.setYEntrySpace(5f);
//        l.setCustom(lentries);

        addDataToChart();
        return view;
    }

    public void addDataToChart(){
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



    }

    @Override
    public void onResume() {
        super.onResume();
        addDataToChart();
    }
}
