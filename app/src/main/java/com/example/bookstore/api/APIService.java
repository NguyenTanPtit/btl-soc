package com.example.bookstore.api;

import com.example.bookstore.models.AddBookResponse;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.BookList;
import com.example.bookstore.models.Categories;
import com.example.bookstore.models.GetCatResponse;
import com.example.bookstore.models.PostCatResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String BASE_URL = "http:10.0.2.2:4000";

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    APIService apiService = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("/product")
    Call<BookList> getAllBook();

    @POST("/product")
    Call<AddBookResponse> addBook(@Body Book b);

    @GET("/category")
    Call<GetCatResponse> getCart();

    @POST("/category")
    Call<PostCatResponse> addCat(@Body Categories c);

    @GET("/product/category/{cat}")
    Call<BookList> getBookByCat(@Path("cat") String name);
}
