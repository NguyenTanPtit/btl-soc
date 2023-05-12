package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.adapter.OnItemClickListener;
import com.example.bookstore.adapter.RecHome1Adapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.BookList;
import com.example.bookstore.models.Categories;
import com.example.bookstore.models.GetCatResponse;
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

    private List<Book> list = new ArrayList<>();
    private Spinner spinner;

    private FloatingActionButton add;
    private List<String> cat = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        cat.add("All");
        getData();
        getCatList();
        initView();
    }
    private void getData(){
        APIService.apiService.getAllBook().enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                BookList b = response.body();
                listAll = b.getList();
                list = listAll;
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
               i.putExtra("book",listAll.get(position).getId());
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!cat.get(position).equals("All")){
                    APIService.apiService.getBookByCat(cat.get(position)).enqueue(new Callback<BookList>() {
                        @Override
                        public void onResponse(Call<BookList> call, Response<BookList> response) {
                            if(response.body()!=null) {
                                BookList b = response.body();
                                listAll = b.getList();
                                adapter.setList(listAll);
                            }else {
                                listAll.clear();
                                adapter.setList(listAll);
                            }
                        }
                        @Override
                        public void onFailure(Call<BookList> call, Throwable t) {
                            Toast.makeText(ManageProductActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Log.d("Size pro", String.valueOf(list.size()));
                    adapter.setList(list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getCatList(){
        APIService.apiService.getCat().enqueue(new Callback<GetCatResponse>() {
            @Override
            public void onResponse(Call<GetCatResponse> call, Response<GetCatResponse> response) {
                GetCatResponse getCatResponse = response.body();
                if(getCatResponse.getStatus() == 200){
                    for (Categories categories: getCatResponse.getList()){
                        cat.add(categories.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCatResponse> call, Throwable t) {
                Toast.makeText(ManageProductActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}