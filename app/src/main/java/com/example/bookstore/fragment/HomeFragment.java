package com.example.bookstore.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.adapter.AdapterExplore;
import com.example.bookstore.adapter.OnItemClickListener;
import com.example.bookstore.adapter.RecHome1Adapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.BookList;
import com.example.bookstore.models.Categories;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View v;

    RecyclerView rec1, recCat, recRecommend, recFavorite ;
    RecHome1Adapter adapter, adapterRecommend, adapterFavorite;
    private List<Book> listAll= new ArrayList<>();
    private List<Categories> listCat = new ArrayList<>();
    private List<Book> listRecommend = new ArrayList<>();
    private List<Book> listFavorite = new ArrayList<>();
    private AdapterExplore adapterExplore;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        initData();
        initView();
        return v;
    }

    private void initView(){
        rec1 = v.findViewById(R.id.rec_home_1);
        rec1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        getData();
        Log.d("size2", String.valueOf(listAll.size()));
        adapter = new RecHome1Adapter(listAll, getContext(), new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        rec1.setAdapter(adapter);

        recCat = v.findViewById(R.id.rec_home_explore);
        recCat.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterExplore = new AdapterExplore(listCat,getContext());
        recCat.setAdapter(adapterExplore);
        Log.d("size3", String.valueOf(listCat.size()));

        recRecommend = v.findViewById(R.id.rec_home_recommend);
        recRecommend.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterRecommend = new RecHome1Adapter(listRecommend, getContext(), new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        recRecommend.setAdapter(adapterRecommend);

        recFavorite = v.findViewById(R.id.rec_home_favorite);
        recFavorite.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterFavorite= new RecHome1Adapter(listFavorite, getContext(), new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        recFavorite.setAdapter(adapterFavorite);
    }

    //test
    private void initData(){
        listCat.add(new Categories("https://www.pngall.com/wp-content/uploads/2/Romantic-PNG-File.png","romance"));
        listCat.add(new Categories("https://www.pngall.com/wp-content/uploads/2/Romantic-PNG-File.png","romance"));
        listCat.add(new Categories("https://www.pngall.com/wp-content/uploads/2/Romantic-PNG-File.png","romance"));
        listCat.add(new Categories("https://www.pngall.com/wp-content/uploads/2/Romantic-PNG-File.png","romance"));
    }

    private void getData(){
        APIService.apiService.getAllBook().enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                BookList b = response.body();
                listAll = b.getList();
                for (int i = listAll.size()/2;i<listAll.size();i++){
                    listRecommend.add(listAll.get(i));
                }
                for(Book book: listAll){
                    if(book.getRate() >= 4.5f){
                        listFavorite.add(book);
                    }
                }
                adapterFavorite.setList(listFavorite);
                adapterRecommend.setList(listRecommend);
                adapter.setList(listAll);
                Log.d("size", String.valueOf(listAll.size()));
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Toast.makeText(getContext(),"lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}