package com.example.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterExplore extends RecyclerView.Adapter<AdapterExplore.ViewHolder> {

    List<Categories> list;
    Context context;

    public AdapterExplore(List<Categories> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterExplore.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_rec_explore,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExplore.ViewHolder holder, int position) {
        Categories c = list.get(position);
        Picasso.with(context).load(c.getImgUrl()).into(holder.img);
        holder.name.setText(c.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_cat_home_img);
            name = itemView.findViewById(R.id.item_cat_home_name);
        }
    }
}
