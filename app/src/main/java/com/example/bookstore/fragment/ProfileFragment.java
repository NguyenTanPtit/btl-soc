package com.example.bookstore.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.activities.ChangePassword;
import com.example.bookstore.activities.EnableActivity;
import com.example.bookstore.activities.LoginActivity;
import com.example.bookstore.activities.MainActivity;
import com.example.bookstore.activities.ManageProductActivity;
import com.example.bookstore.activities.PersionalInfor;
import com.example.bookstore.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CircleImageView profileImg;
    TextView userName, Email;
    ImageView change;
    LinearLayout person,product,logOut,changPass;
    FirebaseDatabase db;
    FirebaseUser user;
    DatabaseReference reference;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImg = root.findViewById(R.id.imgProfile);
        userName = root.findViewById(R.id.text1);
        Email = root.findViewById(R.id.text2);
        change = root.findViewById(R.id.imgChange);
        person = root.findViewById(R.id.linear3);
        product = root.findViewById(R.id.linear5);
        logOut = root.findViewById(R.id.linear4);
        changPass = root.findViewById(R.id.linear6);
        db=FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        String UserId=user.getUid();
        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                User userprofile=snapshot.getValue(User.class);
                if(userprofile!=null){
                    if(userprofile.getEmail().equals("riptan2001@gmail.com")){
                        product.setVisibility(View.VISIBLE);
                    }else {
                        product.setVisibility(View.GONE);
                    }
                    userName.setText(userprofile.getUserName());
                    Email.setText(userprofile.getEmail());
                    Glide.with(getContext()).load(userprofile.getProfileImgUrl()).into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PersionalInfor.class);
                startActivity(i);
            }
        });
        changPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChangePassword.class);
                startActivity(i);
            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EnableActivity.class);
                startActivity(i);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ManageProductActivity.class));
                getActivity().finish();
            }
        });
        return root;
    }


}