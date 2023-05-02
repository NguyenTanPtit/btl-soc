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
import com.example.bookstore.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecHome1Adapter extends RecyclerView.Adapter<RecHome1Adapter.ViewHolder> {

    List<Book> list;
    Context context;
    OnItemClickListener listener;

    public RecHome1Adapter(List<Book> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecHome1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_rec,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecHome1Adapter.ViewHolder holder, int position) {
        Book b = list.get(position);
        holder.price.setText(String.valueOf(b.getPrice()));
        holder.name.setText(b.getName());
        holder.rate.setText(String.valueOf(b.getRate()));
        Picasso.with(context).load(b.getImgUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Book> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, rate, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.item_home_img);
            name =  itemView.findViewById(R.id.item_name_home);
            rate =  itemView.findViewById(R.id.item_rate_home);
            price =  itemView.findViewById(R.id.item_price_home);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }

    }
}
