package com.ekolekta.e_kolekta.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_kalat.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.recycleViewHolder> {
        ArrayList<RecyclerHelperClass> recyclerLocations;

    public RecyclerAdapter(ArrayList<RecyclerHelperClass> recyclerLocations) {
        this.recyclerLocations = recyclerLocations;
    }

    @NonNull
    @Override
    public recycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_design,parent,false);
        recycleViewHolder viewHolder = new recycleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recycleViewHolder holder, int position) {

        RecyclerHelperClass recyclerHelperClass = recyclerLocations.get(position);

        holder.image.setImageResource(recyclerHelperClass.getImage());
        holder.title.setText(recyclerHelperClass.getTitle());
        holder.desc.setText(recyclerHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return recyclerLocations.size();
    }

    public static class recycleViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,desc;

        public recycleViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.recycler_image);
            title = itemView.findViewById(R.id.recycler_title);
            desc = itemView.findViewById(R.id.recycler_description);
        }
    }
}
