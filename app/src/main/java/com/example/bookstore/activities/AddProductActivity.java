package com.example.bookstore.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Categories;
import com.example.bookstore.models.GetCatResponse;
import com.example.bookstore.models.PostCatResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    private Spinner spinner;
    private FirebaseStorage storage;
    private Book book=new Book();

    private Categories cat = new Categories();
    private AppCompatButton add;
    private FloatingActionButton addCat;

    private AlertDialog dialog;
    private ImageView addCatImg;
    private List<String> listCat= new ArrayList<>();
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
        addCat = findViewById(R.id.add_cat);
        spinner = findViewById(R.id.spinner_add_book);
        storage = FirebaseStorage.getInstance();

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
                Toast.makeText(AddProductActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
        listCat.add("Category");
        spinner.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,listCat));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                book.setCat(new Categories(listCat.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                if(onAddBook()){
                    APIService.apiService.addBook(book).enqueue(new Callback<AddBookResponse>() {
                        @Override
                        public void onResponse(Call<AddBookResponse> call, Response<AddBookResponse> response) {
                            AddBookResponse addBookResponse = response.body();
                            if(addBookResponse.getStatus()==200){
                                Toast.makeText(AddProductActivity.this,"Success",Toast.LENGTH_LONG).show();
                                Log.d("imgBook", addBookResponse.getB().getImgUrl());
//                            startActivity(new Intent(AddProductActivity.this, ManageProductActivity.class));
                                finish();
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
            }
        });

        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(AddProductActivity.this)
                        .inflate(R.layout.alert_dialog_add_cat,null);
                addCatImg = dialogView.findViewById(R.id.add_img_cat);
                TextInputLayout nameLayout = dialogView.findViewById(R.id.edt_name_add_cat);
                TextInputEditText nameEdit = dialogView.findViewById(R.id.textInputNameAddCat);
                AppCompatButton addCat = dialogView.findViewById(R.id.dialog_add_cat_btn);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                builder.setCancelable(true);
                dialog = builder.create();
                dialog.setView(dialogView);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                addCatImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i, 2);
                    }
                });

                addCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nameEdit.getText().toString().isEmpty()){
                            nameLayout.setError("Name is not empty");
                            return;
                        }else {
                            cat.setName(nameEdit.getText().toString());
                        }

                        APIService.apiService.addCat(cat).enqueue(new Callback<PostCatResponse>() {
                            @Override
                            public void onResponse(Call<PostCatResponse> call, Response<PostCatResponse> response) {
                                PostCatResponse postCatResponse = response.body();
                                if(postCatResponse.getStatus() == 200){
                                    Toast.makeText(AddProductActivity.this, "Add Category Success",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AddProductActivity.this, "Fail to add category", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<PostCatResponse> call, Throwable t) {
                                Toast.makeText(AddProductActivity.this, "Fail to connect to server",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });
               dialog.show();
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
        else if(requestCode == 2){
            if (data.getData() != null) {
                Uri img = data.getData();
                addCatImg.setImageURI(img);
                final String randomKey = UUID.randomUUID().toString();
                final StorageReference reference = storage.getReference("Cat_Cover").child(randomKey);

                reference.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                cat.setImgUrl(uri.toString());
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

    private boolean onAddBook(){
        if(book.getImgUrl()==null){
            Toast.makeText(this,"You need to add an image of book",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.getText().toString().isEmpty()){
            nameLayout.setError("Name is required");
            return false;
        }
        if(author.getText().toString().isEmpty()){
            authorLayout.setError("Author is required");
            return false;
        }
        if(datePublish.getText().toString().isEmpty()){
            datePublishLayout.setError("Date Publish is required");
            return false;
        }
        if(price.getText().toString().isEmpty()){
            priceLayout.setError("Price is required");
            return false;
        }
        if(pageNum.getText().toString().isEmpty()){
            pageNumberLayout.setError("Page Number is required");
            return false;
        }
        if(des.getText().toString().isEmpty()){
            desLayout.setError("Description is required");
            return false;
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
        return true;
    }
    @SuppressLint("DefaultLocale")
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%.2f",d);
    }
}