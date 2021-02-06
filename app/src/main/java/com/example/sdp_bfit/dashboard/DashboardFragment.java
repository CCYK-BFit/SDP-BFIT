package com.example.sdp_bfit.dashboard;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.R;
import com.example.sdp_bfit.profile.customizationForm;
import static com.example.sdp_bfit.profile.customizationForm.a;
import static com.example.sdp_bfit.signupandlogin.LoginActivity.user;

public class DashboardFragment extends Fragment {

    private DashboardViewModel DashboardViewModel;
    public static TextView txtDailyQuotes, txtAuthor, txtWelcome, txtuname;
    public ImageView profilepic;
    public static View cardCalendar, childcardCalendar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.txt_dashboard);
        DashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        txtDailyQuotes = root.findViewById(R.id.txtDashboardDailyQuotes);
        txtAuthor = root.findViewById(R.id.txtAuthor);
        profilepic = root.findViewById(R.id.imageViewProfile);
        txtWelcome = root.findViewById(R.id.txtWelcome);
        txtuname = root.findViewById(R.id.txtUserName);
//        cardCalendar = root.findViewById(R.id.card_Calendar);
//        childcardCalendar = root.findViewById(R.id.calendarView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (a) {
            removeDailyQuotes();
        }
    }

    public void removeDailyQuotes() {
        txtDailyQuotes.setVisibility(View.INVISIBLE);
        txtAuthor.setVisibility(View.INVISIBLE);
        txtWelcome.setVisibility(View.VISIBLE);
        txtuname.setVisibility(View.VISIBLE);
        String uname = user.getUserFName();
        txtuname.setText(uname.toString());
    }
}
