package com.example.bookstore.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.activities.PlaceOrderActivity;
import com.example.bookstore.adapter.CartAdapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.AddCartRes;
import com.example.bookstore.models.DeleteCartRequest;
import com.example.bookstore.models.GetCartRes;
import com.example.bookstore.models.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    FirebaseUser user;
    private CartAdapter cartAdapter;
    private TextView total;

    private List<Products> products;

    private AppCompatButton buy;

    public CartFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        total = view.findViewById(R.id.tvgiatri);
        buy = view.findViewById(R.id.buy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        user = FirebaseAuth.getInstance().getCurrentUser();
        callApiGetProduct();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlaceOrderActivity.class);
                i.putExtra("listProduct",(Serializable)products);
                startActivity(i);
            }
        });
        return view;
    }
    private void callApiGetProduct() {
        Log.d("userID", user.getUid());
        APIService.apiService.getCart(user.getUid()).enqueue(new Callback<GetCartRes>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetCartRes> call, Response<GetCartRes> response) {

                if(response.body()!=null) {
                    GetCartRes getCartRes = response.body();
                    if (getCartRes.getStatus() == 200) {
                        cartAdapter = new CartAdapter(getCartRes.getC(), getContext(), position -> {
                            deleteCart(getCartRes, position);
                        });
                        recyclerView.setAdapter(cartAdapter);
                        products = getCartRes.getC().getProducts();
                        int mTotal = 0;
                        for (Products p : products) {
                            mTotal += p.getProductPrice() * p.getQuantity();
                        }
                        total.setText("$ "+mTotal);
//                    Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(),"You don't have any product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCartRes> call, Throwable t) {

            }
        });
    }

    private void deleteCart(GetCartRes getCartRes, int position){
        List<String> dsID = new ArrayList<>();
        dsID.add(getCartRes.getC().getProducts().get(position).getProductId());
        DeleteCartRequest deleteCartRequest = new DeleteCartRequest(
                getCartRes.getC().getUserId(), dsID);
        APIService.apiService.deleteProduct(deleteCartRequest).enqueue(new Callback<AddCartRes>() {
            @Override
            public void onResponse(Call<AddCartRes> call, Response<AddCartRes> response1) {
                AddCartRes addCartRes = response1.body();
                if(addCartRes.getStatus() == 200){
                    getCartRes.getC().getProducts().remove(position);
                    cartAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Fail to remove!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCartRes> call1, Throwable t) {
                Toast.makeText(getContext(), "Fail to connect serer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}