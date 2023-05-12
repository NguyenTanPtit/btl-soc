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
import com.example.bookstore.models.Order;
import com.example.bookstore.models.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    private List<Order> list;
    private Context context;

    public PurchaseAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.purchase_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ViewHolder holder, int position) {
        Order o = list.get(position);
        List<Products> p = o.getList();
        Picasso.with(context).load(p.get(0).getProductImg()).into(holder.img);
        holder.phoneNumber.setText(o.getPhone());
        int quantity = 0;
        float total = 0;
        for(Products products: p){
            quantity += products.getQuantity();
            total += products.getProductPrice()*products.getQuantity();
        }
        holder.quantity.setText(String.valueOf(quantity));
        holder.total.setText(String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView phoneNumber, quantity, total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_img);
            phoneNumber = itemView.findViewById(R.id.item_phone);
            quantity = itemView.findViewById(R.id.item_quantity);
            total = itemView.findViewById(R.id.item_price);
        }
    }
}
