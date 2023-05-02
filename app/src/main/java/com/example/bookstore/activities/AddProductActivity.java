package com.example.bookstore.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    private ImageView addImg;
    private TextInputLayout nameLayout, authorLayout, datePublishLayout,priceLayout,pageNumberLayout,
                            desLayout;
    private TextInputEditText name, author, datePublish, price, pageNum, des;
    private FirebaseStorage storage;
    private Book book=new Book();
    private AppCompatButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
    }

    private void initView() {
        addImg = findViewById(R.id.add_book_img);
        nameLayout = findViewById(R.id.edt_add_book_name);
        authorLayout = findViewById(R.id.edt_add_book_author);
        datePublishLayout = findViewById(R.id.edt_add_book_datePublish);
        priceLayout = findViewById(R.id.edt_add_book_price);
        pageNumberLayout = findViewById(R.id.edt_add_book_pageNumber);
        desLayout = findViewById(R.id.edt_add_book_des);
        name = findViewById(R.id.textInput_add_book_name);
        author = findViewById(R.id.textInput_add_book_author);
        datePublish = findViewById(R.id.textInput_add_book_datePublish);
        price = findViewById(R.id.textInput_add_book_price);
        pageNum = findViewById(R.id.textInput_add_book_pageNumber);
        des = findViewById(R.id.textInput_add_book_des);
        add = findViewById(R.id.add_book_btn);
        storage = FirebaseStorage.getInstance();
        setOnClick();
    }

    private void setOnClick(){
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 1);
            }
        });

        datePublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddProductActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datePublish.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddBook();
                APIService.apiService.addBook(book).enqueue(new Callback<AddBookResponse>() {
                    @Override
                    public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                        Log.d("json", call.toString());
                        AddBookResponse addBookResponse = response.body();
                        Log.d("response", String.valueOf(response.body().getStatus()));
                        if(addBookResponse.getStatus()==200){
                            Toast.makeText(AddProductActivity.this,"Success",Toast.LENGTH_LONG).show();
                            Log.d("imgBook", addBookResponse.getB().getImgUrl());
                        }else {
                            Toast.makeText(AddProductActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddBookResponse> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Fail to connect server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Đang tải ảnh...");
        if(requestCode == 1) {
            if (data.getData() != null) {
                Uri img = data.getData();
                addImg.setImageURI(img);
                final String randomKey = UUID.randomUUID().toString();
                final StorageReference reference = storage.getReference("Book_Cover").child(randomKey);

                reference.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                book.setImgUrl(uri.toString());
                                book.setId(randomKey);
                                pd.dismiss();
                                Toast.makeText(AddProductActivity.this, "Tải thành công"
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }

    private void onAddBook(){
        if(book.getImgUrl()==null){
            Toast.makeText(this,"You need to add an image of book",Toast.LENGTH_SHORT).show();
            return;
        }
        if(name.getText().toString().isEmpty()){
            nameLayout.setError("Name is required");
            return;
        }
        if(author.getText().toString().isEmpty()){
            authorLayout.setError("Author is required");
            return;
        }
        if(datePublish.getText().toString().isEmpty()){
            datePublishLayout.setError("Date Publish is required");
            return;
        }
        if(price.getText().toString().isEmpty()){
            priceLayout.setError("Price is required");
            return;
        }
        if(pageNum.getText().toString().isEmpty()){
            pageNumberLayout.setError("Page Number is required");
            return;
        }
        if(des.getText().toString().isEmpty()){
            desLayout.setError("Description is required");
            return;
        }
        float bPrice = Float.parseFloat(price.getText().toString());
        int bPageNum = Integer.parseInt(pageNum.getText().toString());
        String bName = name.getText().toString();
        String bAuthor = author.getText().toString();
        String bDate = datePublish.getText().toString();
        String bDes = des.getText().toString();

        book.setName(bName);
        book.setAuthor(bAuthor);
        book.setPrice(bPrice);
        book.setDatePublish(bDate);
        book.setDescription(bDes);
        book.setPageNumber(bPageNum);
        Random r = new Random();
        float rate = Float.parseFloat(fmt(0f + r.nextFloat() * (5f - 0f)));
        book.setRate(rate);
        book.setBuyNumber(0);
    }
    @SuppressLint("DefaultLocale")
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}