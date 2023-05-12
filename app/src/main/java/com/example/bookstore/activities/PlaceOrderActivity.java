package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.OrderResponse;
import com.example.bookstore.models.Products;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity {
    private TextInputLayout layoutPhone, layoutAddress;
    private TextInputEditText editTextPhone, editTextAddress;
    private AppCompatButton purchaseBtn;
    private FirebaseUser user;
    private List<Products> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        getData();
        initView();
    }

    private void initView(){
        layoutPhone = findViewById(R.id.layout_place_order_phone);
        layoutAddress = findViewById(R.id.layout_place_order_address);
        editTextPhone = findViewById(R.id.textInput_place_order_phone);
        editTextAddress = findViewById(R.id.textInput_place_order_address);
        purchaseBtn = findViewById(R.id.purchaseBtn);
        user = FirebaseAuth.getInstance().getCurrentUser();


        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextPhone.getText().toString().isEmpty()){
                    layoutPhone.setError("Phone Number is require");
                    return;
                }
                if(editTextAddress.getText().toString().isEmpty()){
                    layoutAddress.setError("Address is require");
                    return;
                }
                String phone = editTextPhone.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                Order o = new Order(user.getUid(),phone,address,products);
                APIService.apiService.purchase(o).enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if(response.body()!=null){
                            OrderResponse orderResponse = response.body();
                            String link = orderResponse.getLink();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Toast.makeText(PlaceOrderActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void getData(){
        Intent i = getIntent();
        products = (List<Products> )i.getSerializableExtra("listProduct");
        for (Products p: products){
            System.out.println(p.getDes());
        }
    }
}