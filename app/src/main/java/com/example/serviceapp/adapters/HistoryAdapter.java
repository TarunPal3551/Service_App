package com.example.serviceapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceapp.OrderInfoActivity;
import com.example.serviceapp.R;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context mcontext;
    private View.OnClickListener mOnItemClickListener;


    public HistoryAdapter(Context mcontext) {

        this.mcontext = mcontext;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMore;
        public ImageView imgCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //   imgCardView = itemView.findViewById(R.id.itemImage);
            textViewMore = itemView.findViewById(R.id.textViewMore);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, OrderInfoActivity.class);
                    mcontext.startActivity(intent);
                }
            });


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
        holder.textViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, OrderInfoActivity.class);
                mcontext.startActivity(intent);
            }

        });



    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
        return 15;
    }


}
