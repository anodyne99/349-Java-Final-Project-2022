package com.example.exerciseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// I used multiple sources for this:
// https://www.freshbytelabs.com/2018/12/android-recyclerview-with-cardview.html
// https://www.youtube.com/watch?v=UZwiKdrm768
// https://www.youtube.com/watch?v=Wq2o4EbM74k

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<String> data;
    private final LayoutInflater inflater;
    private ItemClickListener clickListener;

    RecyclerViewAdapter(Context context, List<String> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // @NonNull instances were recommended by compiler
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String country = data.get(position);
        holder.textView.setText(country);
    }

    // Total number of rows. Obnoxious but apparently required.
    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.exerciseName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onRecyclerItemClick(view, getAbsoluteAdapterPosition());
        }
    }

    // For getting the item's data on click
    String getItem(int id) {
        return data.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onRecyclerItemClick(View view, int position);
    }


}