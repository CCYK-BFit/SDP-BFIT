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
import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;

public class BfastHistoryList extends Fragment {
private ListView ls_bfast;
private ArrayAdapter mealArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview_bfast, container, false);
       ls_bfast= v.findViewById(R.id.ls_bfast);
       Database db = new Database(getContext());
        ShowBfastList(db);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Database db = new Database(getContext());
        ShowBfastList(db);

    }

    private void ShowBfastList(Database db) {
        mealArrayAdapter = new ArrayAdapter<Meal>(getContext(), android.R.layout.simple_list_item_1, db.getBfastDetails());
        ls_bfast.setAdapter(mealArrayAdapter);
        mealArrayAdapter.notifyDataSetChanged();
    }
}
