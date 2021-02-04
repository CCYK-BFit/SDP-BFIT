package com.example.sdp_bfit.calories;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.R;

public class LunchHistoryList extends Fragment {
    private ListView listView;
    private ArrayAdapter lunchArrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview_lunch, container, false);
        listView = v.findViewById(R.id.ls_lunch);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Database db = new Database(getContext());
        ShowLunchList(db);
    }

    void ShowLunchList(Database db){
        lunchArrayAdapter = new ArrayAdapter<Meal>(getContext(), android.R.layout.simple_list_item_1,db.getLunchDetails());
        listView.setAdapter(lunchArrayAdapter);
        lunchArrayAdapter.notifyDataSetChanged();
    }
}
