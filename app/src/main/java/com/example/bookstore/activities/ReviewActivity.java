package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.AddCmtRequest;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Comments;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class ReviewActivity extends AppCompatActivity {
    ImageView bookCover,back;
    AppCompatButton cancel, submit;
    RatingBar ratingBar;
    TextView bookName, bookAuthor, bookRate, bookPrice;
    TextInputEditText cmt;
    TextInputLayout layoutCmt;
    FirebaseAuth firebaseAuth;
    Book b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_acitvity);
        firebaseAuth = FirebaseAuth.getInstance();
        initView();
    }

    private void initView(){
        bookCover = findViewById(R.id.img);
        bookName = findViewById(R.id.txtName);
        bookAuthor = findViewById(R.id.txtAuthor);
        bookRate = findViewById(R.id.book_review_rate);
        bookPrice = findViewById(R.id.book_review_price);
        back = findViewById(R.id.back_btn);
        ratingBar = findViewById(R.id.review_book_rate_bar);
        cancel = findViewById(R.id.btn_review_book_cancel);
        submit = findViewById(R.id.btn_review_book_submit);
        cmt = findViewById(R.id.edt_review_book_cmt);
        Intent i = getIntent();
        b = (Book) i.getSerializableExtra("book");
        if(b!=null){
            setData();
        }
        setOnClick();
    }

    private void setOnClick(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bComment =null;
                if(!cmt.getText().toString().isEmpty()){
                    bComment = cmt.getText().toString();
                }

                String uid = firebaseAuth.getCurrentUser().getUid();
                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String currentDate = s.format(c.getTime());
                Comments comments = new Comments(uid,bComment,currentDate);
                Intent i = getIntent();
                String bookID = i.getStringExtra("id");
                Log.d("bcm", bComment);
                AddCmtRequest addCmtRequest = new AddCmtRequest(b.getId(),comments);
                APIService.apiService.addComment(addCmtRequest).enqueue(new Callback<AddBookResponse>() {
                    @Override
                    public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                        AddBookResponse addBookResponse = response.body();
                        if(addBookResponse!=null){
                            Toast.makeText(ReviewActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddBookResponse> call, Throwable t) {
                        Toast.makeText(ReviewActivity.this, "Fail to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setData(){
        Picasso.with(this).load(b.getImgUrl()).into(bookCover);
        bookPrice.setText(String.valueOf(b.getPrice()));
        bookAuthor.setText(b.getAuthor());
        bookRate.setText(String.valueOf(b.getRate()));
        bookName.setText(b.getName());
    }
}