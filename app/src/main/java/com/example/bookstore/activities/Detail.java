package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.adapter.CommentAdapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Comments;
import com.google.firebase.FirebaseApp;
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

    private RecyclerView recCmt;
    private CommentAdapter commentAdapter;
    private List<Comments> listCmt = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        getData();
//        FirebaseApp.initializeApp(this);
//        String userId = "2FSO8TdnsxvRs76o8R3v"; // ID của user cần lấy dữ liệu
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference userRef = mDatabase.child("Book").child(userId);


//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Lấy dữ liệu của user và xử lý
//                String name = dataSnapshot.child("name").getValue(String.class);
//                String author = dataSnapshot.child("author").getValue(String.class);
//
//                String img = dataSnapshot.child("imgUrl").getValue(String.class);
//                txtName.setText(name);
//                txtAuthor.setText(author);
//                Picasso.with(Detail.this).load(img).into(imageView);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Xử lý lỗi nếu có
//            }
//        });
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

        recCmt.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        commentAdapter = new CommentAdapter(listCmt,this);
        recCmt.setAdapter(commentAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}