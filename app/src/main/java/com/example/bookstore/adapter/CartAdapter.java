package com.example.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.CartAll;
import com.example.bookstore.models.Products;
import com.squareup.picasso.Picasso;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private CartAll cartAll;
    private Context context;
    private OnItemClickListener listener;

    public CartAdapter(CartAll cartAll) {
        this.cartAll = cartAll;
    }

    public CartAdapter(CartAll cartAll, Context context) {
        this.cartAll = cartAll;
        this.context = context;
    }

    public CartAdapter(CartAll cartAll, Context context, OnItemClickListener listener) {
        this.cartAll = cartAll;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_rec,parent,false);
        return new  CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Products product= cartAll.getProducts().get(position);
        if(product==null){
            return;
        }

//        Picasso.with(context).load(product.getProductImg()).into(holder.img);
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(String.valueOf(product.getProductPrice()));
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                if(quantity>1) {
                    quantity--;
                }
                holder.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                if(quantity<10) {
                    quantity++;
                }
                holder.tvQuantity.setText(String.valueOf(quantity));
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cartAll.getProducts() != null){
            return cartAll.getProducts().size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvRate,tvPrice, tvQuantity;
        private ImageView img,imgDel;
        private Button btnMinus,btnPlus;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.item_name);
            tvPrice=itemView.findViewById(R.id.item_price);
            img=itemView.findViewById(R.id.img);
            btnPlus = itemView.findViewById(R.id.item_Plus);
            btnMinus = itemView.findViewById(R.id.item_Minus);
            tvRate = itemView.findViewById(R.id.item_rate);
            tvQuantity = itemView.findViewById(R.id.item_values);
            imgDel = itemView.findViewById(R.id.item_delete);
        }
    }
}
