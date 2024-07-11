package com.example.trialone.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trialone.R;
import com.example.trialone.SearchActivity;
import com.example.trialone.domain.CategoryDomain;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain>categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryname.setText(categoryDomains.get(position).getTitle());
        String picUrl="";
        switch (position){
            case 0:{
                picUrl="pizzatwo";
                holder.itemlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.themebg));
                break;
            }
            case 1:{
                picUrl="thali";
                holder.itemlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.themebg));
                break;
            }
            case 2:{
                picUrl="cat_13";
                holder.itemlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.themebg));
                break;
            }
            case 3:{
                picUrl="eggroll1";
                holder.itemlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.themebg));
                break;
            }
            case 4:{
                picUrl="cat_13";
                holder.itemlayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.themebg));
                break;
            }
        }
        int drawableResuourceID=holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResuourceID).into(holder.categoryimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchActivity and pass specific text to be populated in the search bar
                Intent intent = new Intent(holder.itemView.getContext(), SearchActivity.class);
                intent.putExtra("searchText", categoryDomains.get(position).getTitle()); // Pass specific text here
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryname;
        ImageView categoryimg;
        ConstraintLayout itemlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryname=itemView.findViewById(R.id.title2);
            categoryimg=itemView.findViewById(R.id.img2);
            itemlayout=itemView.findViewById(R.id.itemlayout2);
        }
    }
}
