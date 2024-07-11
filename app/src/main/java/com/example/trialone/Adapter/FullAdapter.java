package com.example.trialone.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trialone.R;
import com.example.trialone.ShowDetailsActivity;
import com.example.trialone.domain.FoodDomain;
import com.example.trialone.domain.FullDomain;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FullAdapter extends RecyclerView.Adapter<FullAdapter.ViewHolder> {
    ArrayList<FullDomain> fullFood;
    ArrayList<FullDomain> originalFullFood;

    public FullAdapter(ArrayList<FullDomain>fullFood) {
        this.fullFood = fullFood;
        this.originalFullFood=new ArrayList<>(fullFood);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_search,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FullAdapter.ViewHolder holder, int position) {
        holder.title.setText(fullFood.get(position).getTitle());
        holder.fee.setText(String.valueOf(fullFood.get(position).getFee()));

        int drawableResuourceID=holder.itemView.getContext().getResources().getIdentifier(fullFood.get(position).getImg(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResuourceID).into(holder.img);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition(); // Get the updated position
                if (position != RecyclerView.NO_POSITION) { // Check for valid position
                    FullDomain food = fullFood.get(position);
                    Intent intent = new Intent(holder.itemView.getContext(), ShowDetailsActivity.class);
                    intent.putExtra("object", food);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return fullFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, fee;
        ImageView img;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title2);
            fee=itemView.findViewById(R.id.fee);
            img=itemView.findViewById(R.id.img2);
            addBtn=itemView.findViewById(R.id.addBtn);



        }
    }
    public void filter(String query) {
        fullFood.clear();
        if (query.trim().isEmpty()) {
            fullFood.addAll(originalFullFood);
        } else {
            String searchQuery = query.toLowerCase();
            for (FullDomain food : originalFullFood) {
                if (food.getTitle().toLowerCase().contains(searchQuery)) {
                    fullFood.add(food);
                }
            }
        }
        notifyDataSetChanged();
    }
}
