package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Categories;
import com.example.bookstore.models.GetCatResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteActivity extends AppCompatActivity {
    private ImageView addImg;
    private TextInputLayout nameLayout, authorLayout, datePublishLayout,priceLayout,pageNumberLayout,
            desLayout;
    private TextInputEditText name, author, datePublish, price, pageNum, des;
    private AppCompatButton update, delete;
    private Spinner spinner;
    private List<String> listCat= new ArrayList<>();

    private Book b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        listCat.add("Category");

//        add = findViewById(R.id.add_book_btn);
//        addCat = findViewById(R.id.add_cat);
//
//        storage = FirebaseStorage.getInstance();
        initView();
    }

    private void initView(){
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
        spinner = findViewById(R.id.spinner_add_book);
        update = findViewById(R.id.update_book_btn);
        delete = findViewById(R.id.delete_book_btn);


        getCat();
        spinner.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,listCat));

        getData();

        setOnClickBtn();
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                b.setCat(new Categories(listCat.get(position)));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void setData(){
        Picasso.with(this).load(b.getImgUrl()).into(addImg);
        name.setText(b.getName());
        author.setText(b.getAuthor());
        datePublish.setText(b.getDatePublish());
        price.setText(String.valueOf(b.getPrice()));
        pageNum.setText(String.valueOf(b.getPageNumber()));
        des.setText(b.getDescription());
        spinner.setSelection(listCat.indexOf(b.getCat().getName()));
    }

    private void getData(){
        Intent i = getIntent();
        String id = i.getStringExtra("book");
        Log.d("BooKid", id);
        APIService.apiService.getProductByID(id).enqueue(new Callback<AddBookResponse>() {
            @Override
            public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                AddBookResponse addBookResponse = response.body();
                if(addBookResponse.getStatus() == 200){
                    b = addBookResponse.getB();
                    Log.d("Book123", b.getImgUrl());
                    if(b!=null) {
                        setData();
                    }else {
                        Toast.makeText(UpdateDeleteActivity.this, "NUll", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddBookResponse> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCat(){
        APIService.apiService.getCat().enqueue(new Callback<GetCatResponse>() {
            @Override
            public void onResponse(Call<GetCatResponse> call, Response<GetCatResponse> response) {
                GetCatResponse getCatResponse = response.body();
                if(getCatResponse.getStatus() == 200){
                    for (Categories categories: getCatResponse.getList()){
                        listCat.add(categories.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCatResponse> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onUpdateBook(){
        if(b.getImgUrl()==null){
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

        b.setName(bName);
        b.setAuthor(bAuthor);
        b.setPrice(bPrice);
        b.setDatePublish(bDate);
        b.setDescription(bDes);
        b.setPageNumber(bPageNum);
        b.setCat(new Categories(spinner.getSelectedItem().toString()));

        APIService.apiService.updateBook(b).enqueue(new Callback<AddBookResponse>() {
            @Override
            public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                AddBookResponse addBookResponse = response.body();
                if(addBookResponse.getStatus() == 200){
                    Toast.makeText(UpdateDeleteActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(UpdateDeleteActivity.this, "Update fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddBookResponse> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setOnClickBtn(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateBook();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService.apiService.deleteBook(b.getId()).enqueue(new Callback<AddBookResponse>() {
                    @Override
                    public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                        AddBookResponse addBookResponse = response.body();
                        if(addBookResponse.getStatus() == 200){
                            Toast.makeText(UpdateDeleteActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(UpdateDeleteActivity.this, "Delete Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddBookResponse> call, Throwable t) {
                        Toast.makeText(UpdateDeleteActivity.this, "Fail to connect", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}