package com.example.serviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceapp.adapters.DropLocationAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    TextView editTextPickUp;
    ListView dropLocationListView;
    MaterialButton addLocationButton;
    DropLocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editTextPickUp = (TextView) findViewById(R.id.editTextPickUpLocatio);
        editTextPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, DeliveryLocationPicker.class);
                startActivity(intent);
            }
        });
        dropLocationListView = (ListView) findViewById(R.id.dropLocationListView);
        addLocationButton=(MaterialButton)findViewById(R.id.addMultipleLocation);
        final ArrayList<String> dataModels = new ArrayList<>();
        dataModels.add("Drop Location 1");
        dataModels.add("Drop Location 2");
        adapter=new DropLocationAdapter(dataModels, getApplicationContext());
        dropLocationListView.setAdapter(adapter);
        dropLocationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this, DeliveryLocationPicker.class);
                startActivity(intent);
//                View view1 = view.getRootView();
//                final TextInputEditText editText = getViewByPosition(position,dropLocationListView)..findViewById(R.id.editTextDropLocation1);
//               // Toast.makeText(Main2Activity.this, "" +editText.getHint(), Toast.LENGTH_SHORT).show();
            }
        });
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModels.add("Drop Location "+(dataModels.size()+1));
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(dropLocationListView);

            }
        });
        setListViewHeightBasedOnChildren(dropLocationListView);
    }
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


}
