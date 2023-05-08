package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.adapter.CommentAdapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.fragment.CartFragment;
import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.AddCartRes;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Comments;
import com.example.bookstore.models.Products;
import com.example.bookstore.models.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {
    TextView txtName,txtAuthor,rate,pageNum,buyNum,des,rate2,review;
    ImageView imageView, back;
    Book b;
    Button btnAdd;

    private RecyclerView recCmt;
    private CommentAdapter commentAdapter;
    private List<Comments> listCmt = new ArrayList<>();
    FirebaseDatabase db;
    FirebaseUser user;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        getData();
    }

    private void initView(){
        txtName =findViewById(R.id.txtName);
        txtAuthor =findViewById(R.id.txtAuthor);
        imageView =findViewById(R.id.img);
        rate = findViewById(R.id.rate_book);
        pageNum = findViewById(R.id.book_pageNum);
        buyNum = findViewById(R.id.book_buyNum);
        des = findViewById(R.id.book_des);
        rate2 = findViewById(R.id.book_rate2);
        review = findViewById(R.id.book_review_num);
        back = findViewById(R.id.back_btn);
        recCmt = findViewById(R.id.rec_cmt);
        btnAdd=findViewById(R.id.btnAdd);

        recCmt.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        commentAdapter = new CommentAdapter(listCmt,this);
        recCmt.setAdapter(commentAdapter);
        db=FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        String UserId=user.getUid();
        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if(userprofile!=null){
                    if(userprofile.getEmail().equals("riptan2001@gmail.com")){
                        btnAdd.setVisibility(View.GONE);
                    }else {
                        btnAdd.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Detail.this, "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCart();
            }
        });
    }

    private void getData(){
        Intent i = getIntent();
        String id = i.getStringExtra("item");
        APIService.apiService.getProductByID(id).enqueue(new Callback<AddBookResponse>() {
            @Override
            public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                AddBookResponse addBookResponse = response.body();
                if(addBookResponse.getStatus() == 200){
                    b = addBookResponse.getB();
                    listCmt = b.getComments();
                    commentAdapter.setList(listCmt);
                    setData();
                }
            }

            @Override
            public void onFailure(Call<AddBookResponse> call, Throwable t) {
                Toast.makeText(Detail.this, "Fail to connect server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(){
        Picasso.with(this).load(b.getImgUrl()).into(imageView);
        txtName.setText(b.getName());
        txtAuthor.setText(b.getAuthor());
        rate.setText(String.valueOf(b.getRate()));
        rate2.setText(String.valueOf(b.getRate()));
        pageNum.setText(String.valueOf(b.getPageNumber()));
        buyNum.setText(String.valueOf(b.getBuyNumber()));
        des.setText(b.getDescription());
    }
    private void sendCart() {
        String UserId=user.getUid();
        Products product = new Products(b.getId(),b.getName(),b.getImgUrl(),b.getPrice(),1);
        Cart cart= new Cart(UserId,product);

        APIService.apiService.sendCarts(cart).enqueue(new Callback<AddCartRes>() {
            @Override
            public void onResponse(Call<AddCartRes> call, Response<AddCartRes> response) {
                AddCartRes addCartRes = response.body();
                Log.d("response", String.valueOf(response.body().getStatus()));
                if(addCartRes.getStatus()==200){
//                    Log.d("")
                    Toast.makeText(Detail.this,"Success",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Detail.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCartRes> call, Throwable t) {
                Log.d("Loi",t.toString());
            }
        });
        Intent intent = new Intent(Detail.this, CartFragment.class);
        startActivity(intent);
    }
}