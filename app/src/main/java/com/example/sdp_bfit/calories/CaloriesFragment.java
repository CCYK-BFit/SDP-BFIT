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
//        btn_add_lunch = v.findViewById(R.id.btn_add_lunch);
//        btn_add_snack = v.findViewById(R.id.btn_add_snack);
//        btn_add_dinner = v.findViewById(R.id.btn_add_dinner);
         txt_calories_count = v.findViewById(R.id.txt_calories_count);
        //button
//        btn_scan_lunch = v.findViewById(R.id.btn_scan_lunch);
//        btn_scan_snack = v.findViewById(R.id.btn_scan_snack);
//        btn_scan_dinner= v. findViewById(R.id.btn_scan_dinner);
//        btnDropDown = v.findViewById(R.id.btnDropdown);
        //testing
        //Edittext
            mealname = v.findViewById(R.id.editTextMealName);
            mealcal = v.findViewById(R.id.editTextCalories);
            mealsize=v.findViewById(R.id.editTextServingSize);


        //pane
        card_bfast = v.findViewById(R.id.card_breakfast);
//        card_lunch = v.findViewById(R.id.card_lunch);
//        card_snack= v.findViewById(R.id.card_snack);
//        card_dinner = v.findViewById(R.id.card_dinner);
        card_meal_history = v.findViewById(R.id.card_meal_history);
        mealhistoryContainer=v.findViewById(R.id.meal_history_container);
        //tablayout
        tabLayout=v.findViewById(R.id.tabLayout_meal_history);
        viewPager=v.findViewById(R.id.viewPager_Meal_history);


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
//                    card_snack.bringToFront();
//                    card_dinner.bringToFront();
                    displayCalories();
                }
            }
        });
        demoFragmentAdapter = new DemoFragmentAdapter(this);
        viewPager = v.findViewById(R.id.viewPager_Meal_history);
        viewPager.setAdapter(demoFragmentAdapter);

        TabLayout tabLayout = v.findViewById(R.id.tabLayout_meal_history);

//        new TabLayoutMediator(tabLayout, viewPager,
//                (tab, position) ->{
//                    (tab.setText("Breakfast"[0]))
//                }
//
//                }).attach();
         new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();
//        btn_add_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){
//                    //show lunch calories from
//                    Fragment lunchFragment= new LunchForm();
//                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
//                    transaction.add(R.id.lunch_fragment_container, lunchFragment,LunchTag).show(lunchFragment).commit();
//                    getView().findViewById(R.id.lunch_form_container).bringToFront(); }
//                else{//hide calories form
//                    removeNestedFragment(LunchTag);
//                    card_snack.bringToFront();
//                    card_dinner.bringToFront();
//                }
//            }
//        });
//        btn_add_snack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){//show lunch calories from
//                    Fragment snackFragment= new SnackForm();
//                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
//                    transaction.add(R.id.snack_fragment_container, snackFragment,SnackTag).show(snackFragment).commit();
//                    getView().findViewById(R.id.snack_form_container).bringToFront(); }
//                else{//hide calories form
//                    removeNestedFragment(SnackTag);
//                    card_dinner.bringToFront(); }
//            }
//        });
//        btn_add_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){//show lunch calories from
//                    Fragment dinnerFragment= new DinnerForm();
//                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setReorderingAllowed(true);
//                    transaction.add(R.id.dinner_fragment_container, dinnerFragment,DinnerTag).show(dinnerFragment).commit();
//                    getView().findViewById(R.id.dinner_form_container).bringToFront();
//                }
//                else{//hide calories form
//                    removeNestedFragment(DinnerTag); }
//            }
//        });
        displayCalories();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Kuala_Lumpur"));
           todayDate = String.valueOf(today);
        }






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
        //swipe refresh layout
//        swipeRefreshLayout=view.findViewById(R.id.swipe_container);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                displayCalories();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
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
//        Fragment fragment = new LunchForm();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(DemoObjectFragment.ARG_OBJECT, position + 1);
//        fragment.setArguments(args);
//        return fragment;
        switch (position)
        {
            case 0:
                return new MealHistoryList(); //ChildFragment1 at position 0
            case 1:
                return new ItemFragment(); //ChildFragment2 at position 1
            case 2:
                return new ItemFragment(); //ChildFragment3 at position 2
            case 3:
                return new ItemFragment();
        }
        return null; //does not happen
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}


