package com.example.serviceapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceapp.R;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context mcontext;
    private View.OnClickListener mOnItemClickListener;


    public HistoryAdapter(Context mcontext) {

        this.mcontext = mcontext;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imgCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //   imgCardView = itemView.findViewById(R.id.itemImage);
          //  textView = itemView.findViewById(R.id.tvcardView);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);


        }


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(mcontext)
//                .asBitmap()
//                .load(images.get(position))
//                .into(holder.imgCardView);

      //  holder.textView.setText(names.get(position));


    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
        return 15;
    }


}
