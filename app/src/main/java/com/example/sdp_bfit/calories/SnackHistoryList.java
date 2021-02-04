package com.example.sdp_bfit.calories;

import android.os.Bundle;
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

public class SnackHistoryList extends Fragment {
    private ListView listView;
    private ArrayAdapter snackArrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview_snack, container, false);
        listView = v.findViewById(R.id.ls_snack);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Database db = new Database(getContext());
        ShowSnackList(db);
    }

    void ShowSnackList(Database db){
        snackArrayAdapter = new ArrayAdapter<Meal>(getContext(), android.R.layout.simple_list_item_1,db.getSnackDetails());
        listView.setAdapter(snackArrayAdapter);
        snackArrayAdapter.notifyDataSetChanged();
    }
}
