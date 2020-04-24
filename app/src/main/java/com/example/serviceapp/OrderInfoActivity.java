package com.example.serviceapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceapp.adapters.DeliveryLocationAdapter;

public class OrderInfoActivity extends AppCompatActivity {
    RecyclerView orderLocationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        orderLocationRecyclerView=(RecyclerView)findViewById(R.id.locationRecyclerView);
        orderLocationRecyclerView.setHasFixedSize(true);
        orderLocationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderLocationRecyclerView.setAdapter(new DeliveryLocationAdapter(this));
    }
}
