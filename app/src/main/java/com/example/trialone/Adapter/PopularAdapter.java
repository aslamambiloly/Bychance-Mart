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
import com.example.trialone.domain.CategoryDomain;
import com.example.trialone.domain.FoodDomain;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<FoodDomain> popularFood;
    ArrayList<FoodDomain> originalPopularFood;

    public PopularAdapter(ArrayList<FoodDomain>popularFood) {
        this.popularFood = popularFood;
        this.originalPopularFood=new ArrayList<>(popularFood);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder,int position) {
        holder.title.setText(popularFood.get(position).getTitle());
        holder.fee.setText(String.valueOf(popularFood.get(position).getFee()));

        int drawableResuourceID=holder.itemView.getContext().getResources().getIdentifier(popularFood.get(position).getImg(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResuourceID).into(holder.img);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition(); // Get the updated position
                if (position != RecyclerView.NO_POSITION) { // Check for valid position
                    FoodDomain food = popularFood.get(position);
                    Intent intent = new Intent(holder.itemView.getContext(), ShowDetailsActivity.class);
                    intent.putExtra("object", food);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return popularFood.size();
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
        popularFood.clear();
        if (query.trim().isEmpty()) {
            popularFood.addAll(originalPopularFood);
        } else {
            String searchQuery = query.toLowerCase();
            for (FoodDomain food : originalPopularFood) {
                if (food.getTitle().toLowerCase().contains(searchQuery)) {
                    popularFood.add(food);
                }
            }
        }
        notifyDataSetChanged();
    }
}
