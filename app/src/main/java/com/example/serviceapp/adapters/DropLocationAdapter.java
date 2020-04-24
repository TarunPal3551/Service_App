package com.example.serviceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.serviceapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class DropLocationAdapter extends ArrayAdapter<String> implements View.OnClickListener {
    private ArrayList<String> dataSet;
    Context mContext;

    public DropLocationAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.drop_location_layout, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

    }

    private static class ViewHolder {
        TextView txtDropLocation;
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String s = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.drop_location_layout, parent, false);
            viewHolder.txtDropLocation = (TextView) convertView.findViewById(R.id.editTextDropLocation1);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.txtDropLocation.setText(""+dataSet.get(position));
        // Return the completed view to render on screen
        return convertView;
    }
}
