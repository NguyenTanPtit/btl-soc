package com.example.bookstore.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.adapter.PurchaseAdapter;
import com.example.bookstore.api.APIService;
import com.example.bookstore.models.GetOrderResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DicoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DicoverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rec;
    private PurchaseAdapter adapter;
    private View root;

    private FirebaseUser user;

    public DicoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DicoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DicoverFragment newInstance(String param1, String param2) {
        DicoverFragment fragment = new DicoverFragment();
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
        root = inflater.inflate(R.layout.fragment_dicover, container, false);
        rec = root.findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        user = FirebaseAuth.getInstance().getCurrentUser();
        getData();
        return root;
    }
    private void getData(){
        String uID = user.getUid();
        APIService.apiService.getOrder(uID).enqueue(new Callback<GetOrderResponse>() {
            @Override
            public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                if (response.body()!=null){
                    GetOrderResponse res = response.body();
                    if(res.getStatus() == 200){
                        adapter = new PurchaseAdapter(res.getList(),getContext());
                        rec.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(getContext(), "You don't have any orders yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Fail to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}