package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.adapter.OnItemClickListener;
import com.example.bookstore.adapter.RecHome1Adapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.BookList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProductActivity extends AppCompatActivity {
    private RecyclerView rec;
    private RecHome1Adapter adapter;
    private List<Book> listAll= new ArrayList<>();
    private Spinner spinner;

    private FloatingActionButton add;
    private String [] cat = {"All","Romance","Thriller","Something"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        getData();
        initView();
    }
    private void getData(){
        APIService.apiService.getAllBook().enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                BookList b = response.body();
                listAll = b.getList();
                adapter.setList(listAll);
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Toast.makeText(ManageProductActivity.this,"lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(){
        rec = findViewById(R.id.rec_manage_product);
        spinner = findViewById(R.id.spinner);
        add = findViewById(R.id.add_fab);
        spinner.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,cat));

        rec.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecHome1Adapter(listAll, this, new OnItemClickListener() {
            @Override
            public void onClick(int position) {
               Intent i = new Intent(ManageProductActivity.this,UpdateDeleteActivity.class);
               i.putExtra("book",listAll.get(position));
               startActivity(i);
            }
        });
        rec.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageProductActivity.this,AddProductActivity.class));
            }
        });
    }
}