package com.example.serviceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.serviceapp.adapters.HistoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }

    RecyclerView historyRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView=(RecyclerView)view.findViewById(R.id.historyRecyclerView);
       // historyRecyclerView=(RecyclerView)findViewById(R.id.categoryListRecyclerView);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        historyRecyclerView.setAdapter(new HistoryAdapter(getContext()));
        return view;
    }
}
