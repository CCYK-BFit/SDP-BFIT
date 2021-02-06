package com.example.sdp_bfit.dashboard;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.graphics.vector.Fill;
import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.R;
import com.github.mikephil.charting.animation.Easing;
import com.example.sdp_bfit.workout.Workout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.utils.MPPointF;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class DashboardFragment extends Fragment  {

    private DashboardViewModel DashboardViewModel;
    private TextView textView30;
    private TextView textView10;
    private MaterialCalendarView materialCalView;
    //newly added
    private BarChart barChart;
    private PieChart pieChart;
    private LineChart lineChart;
    private Database db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        barChart = (BarChart)view.findViewById(R.id.barchart_calories);
        pieChart = view.findViewById(R.id.piechart_workout);
        lineChart = view.findViewById(R.id.linechart_sleep);
        //cal and distance show
        textView30 = view.findViewById(R.id.textView30);
        textView10 = view.findViewById(R.id.textView10);

        displayCalories();
        displayDistance();




        addNutritionDataToChart();
        addWorkoutDataToChart();
        addSleepDataToChart();

        //Period Calendar
        materialCalView = view.findViewById(R.id.materialCalView);
        materialCalView.setDateSelected(CalendarDay.today(), true);

        materialCalView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setTitle("Set Period Date")
                        .setMessage("Start Period?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                materialCalView.removeDecorators();


                                materialCalView.addDecorator(new EventDecorator(000000, Collections.singleton(date)));
                                LocalDate localdate= date.getDate();
                                for(int i=0;i<7;i++){
                                    LocalDate adddate = localdate.plusDays(i);
                                    CalendarDay ii=CalendarDay.from(adddate);
                                    materialCalView.addDecorator(new EventDecorator(000000, Collections.singleton(ii)));
                                }


                                for(int i=30;i<1000;i+=30){
                                    LocalDate thrityday = localdate.plusDays(i);
                                    CalendarDay dayss=CalendarDay.from(thrityday);
                                    materialCalView.addDecorator(new EventDecorator(000000, Collections.singleton(dayss)));

                                    for(int j=0;j<7;j++){
                                        LocalDate adddate = thrityday.plusDays(j);
                                        CalendarDay ii=CalendarDay.from(adddate);
                                        materialCalView.addDecorator(new EventDecorator(000000, Collections.singleton(ii)));
                                    }


                                }
                                String startdate = localdate.toString();

                                Period period = new Period(startdate);
                                Database db = new Database(getContext());
                                boolean success = db.insertPeriod(period);
                                if (success = true) {
                                    Toast.makeText(getContext(),"Period Date Saved",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getContext(),"Save Period Date failed",Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();


            }
        });


        return view;
    }

    public void addNutritionDataToChart(){
         db = new Database(getActivity());
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        ArrayList<String> yData = db.getyVal();

        for(int i = 0; i<db.getyVal().size();i++){
            BarEntry newBarEntry = new BarEntry(i, Float.parseFloat(yData.get(i)));
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
        //set description
        Description description = new Description();
        description.setTextColor(android.R.color.holo_blue_light);
        description.setText("Total calories according to meal types");
        barChart.setDescription(description);
        barChart.setDrawGridBackground(false);
        barChart.setNoDataText("Start tracking your calories to view this report!");
        barChart.setNoDataTextColor(Color.rgb(0,230,93));

        barChart.invalidate();



    }

    public void addWorkoutDataToChart(){
       db = new Database(getActivity());
       //y-axis data
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        ArrayList<String> yData = db.getWorkoutyVal();

        for(int i = 0; i<yData.size();i++){
            PieEntry newPieEntry = new PieEntry(i, Float.parseFloat(yData.get(i)));
            yVals.add(newPieEntry);
        }
        //x-axis data
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<String> xData = db.getWorkoutxVal();

        for(int i =0; i<xData.size();i++){
            xVals.add(xData.get(i));
        }
        //pass y-value to Piedataset
        PieDataSet dataset = new PieDataSet(yVals,"Step Count");
        //set pie colour theme
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        ArrayList<IPieDataSet> dataSets1 = new ArrayList<>();
//        dataSets1.add(dataset);
        //pass dataset into pie data
        PieData data = new PieData(dataset);

        //pass piedata into piechart
       pieChart.setData(data);


        Legend l = pieChart.getLegend();

        //set description
        Description description = new Description();
        description.setTextColor(android.R.color.holo_blue_light);
        description.setText("Step Count");
        pieChart.setDescription(description);
        pieChart.setNoDataText("Start tracking your step count to view this report!");
        pieChart.invalidate();
    
    }
    public void addSleepDataToChart(){
        ArrayList<Entry> entryList = new ArrayList<>();
        entryList.add(new Entry(0f,5f,"Monday"));
        entryList.add(new Entry(1f,6f,"Tuesday"));
        entryList.add(new Entry(2f,4f,"Wednesday"));
        entryList.add(new Entry(3f,7f,"Thursday"));
        entryList.add(new Entry(4f,8f,"Friday"));
        entryList.add(new Entry(5f,9f,"Saturday"));
        entryList.add(new Entry(6f,4f,"Sunday"));
        ArrayList<String> weekDay = new ArrayList<>();
        weekDay.add("M");
        weekDay.add("T");
        weekDay.add("W");
        weekDay.add("T");
        weekDay.add("F");
        weekDay.add("S");
        weekDay.add("S");

        LineDataSet lineDataSet = new LineDataSet(entryList,"Day of the week");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.gradient));
        LineData lineData = new LineData(lineDataSet);


        lineChart.setNoDataText("Start tracking your sleep hours to view the chart!");
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(10);
        lineChart.setDrawBorders(false);
        lineChart.setDrawGridBackground(false);

        // Controlling left side of y axis
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();

        yAxisLeft.setGranularity(1f);
        yAxisRight.setEnabled(false);
        //customize x-axis
        XAxis xAxis=lineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekDay));
        // draw limit lines behind data instead of on top

        xAxis.setDrawLimitLinesBehindData(false);
        //customize legend
        Legend legend = lineChart.getLegend();

        lineChart.invalidate();
    }
// void Periodr(){
//        Database db = new Database(getContext());
//        Period period = new Period();
//        String dates=String.valueOf(db.getLastPeriod(period));
//     LocalDate datel = LocalDate.parse(dates);
//     CalendarDay datec=CalendarDay.from(datel);
//     materialCalView.addDecorator(new EventDecorator(000000, Collections.singleton(datec)));
//
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        addNutritionDataToChart();
        addWorkoutDataToChart();
        addSleepDataToChart();
//        Periodr();
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
    public static class EventDecorator implements DayViewDecorator {
        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
}
