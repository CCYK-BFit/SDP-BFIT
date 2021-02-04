package com.example.sdp_bfit.calories;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.ItemFragment;
import com.example.sdp_bfit.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;
import java.time.ZoneId;

public class CaloriesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public String BfastTag ="bfastForm" , LunchTag="lunchForm" ,SnackTag="snackForm" , DinnerTag="dinnerForm";
    ToggleButton btn_add_Bfast , btn_add_lunch,btn_add_snack,btn_add_dinner;
    ConstraintLayout card_bfast , card_lunch, card_snack,card_dinner,card_meal_history;
    public EditText mealname , mealcal , mealsize ;
    public TextView txt_calories_count;
    private int calories_count;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ScrollView mealhistoryContainer;
    DemoFragmentAdapter demoFragmentAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    // tab titles
    private String[] titles = new String[]{"Breakfast", "Lunch", "Dinner","Snack"};
    public static String todayDate;
    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calories_main, container, false);
        //toggle button
        btn_add_Bfast = v.findViewById(R.id.btn_add_bfast);
         txt_calories_count = v.findViewById(R.id.txt_calories_count);
        //Edittext
            mealname = v.findViewById(R.id.editTextMealName);
            mealcal = v.findViewById(R.id.editTextCalories);
            mealsize=v.findViewById(R.id.editTextServingSize);
        //pane
        card_bfast = v.findViewById(R.id.card_breakfast);
        card_meal_history = v.findViewById(R.id.card_meal_history);
        mealhistoryContainer=v.findViewById(R.id.meal_history_container);
        //tablayout
        tabLayout=v.findViewById(R.id.tabLayout_meal_history);
        viewPager=v.findViewById(R.id.viewPager_Meal_history);
        //set today date
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Kuala_Lumpur"));
            todayDate = String.valueOf(today);
        }
        //Toogle Button Action
        btn_add_Bfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){//show bfast calories from
            Fragment bfastFragment= new MealForm();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
            transaction.replace(R.id.bfast_fragment_container, bfastFragment,BfastTag).addToBackStack("MEAL_FORM_FRAGMENT")
                    .show(bfastFragment).commit();

                    getView().findViewById(R.id.bfast_form_container).bringToFront(); }
                else {//hide calories form
                    removeNestedFragment(BfastTag);
                    card_meal_history.bringToFront();
                    mealhistoryContainer.bringToFront();
                    displayCalories();
                }
            }
        });
        demoFragmentAdapter = new DemoFragmentAdapter(this);
        viewPager = v.findViewById(R.id.viewPager_Meal_history);
        viewPager.setAdapter(demoFragmentAdapter);

        TabLayout tabLayout = v.findViewById(R.id.tabLayout_meal_history);

         new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();

        displayCalories();


        return v;


    }


    //remove child fragment
    private void removeNestedFragment(String tag){

        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.hide(fragment).commit();
    }

    void displayCalories() {
        Database db = new Database(getContext());
        Meal meal = new Meal();
        txt_calories_count.setText(String.valueOf(db.calcCalories(meal)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        displayCalories();
    }

    @Override
    public void onRefresh() {
        displayCalories();
    }
}




class DemoFragmentAdapter extends FragmentStateAdapter {
    public DemoFragmentAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 0:
                return new BfastHistoryList(); //ChildFragment1 at position 0
            case 1:
                return new LunchHistoryList(); //ChildFragment2 at position 1
            case 2:
                return new DinnerHistoryList(); //ChildFragment3 at position 2
            case 3:
                return new SnackHistoryList();
        }
        return null; //does not happen
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}


