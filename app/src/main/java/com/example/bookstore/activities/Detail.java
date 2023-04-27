package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookstore.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {
    TextView txtName,txtAuthor;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FirebaseApp.initializeApp(this);
        String userId = "2FSO8TdnsxvRs76o8R3v"; // ID của user cần lấy dữ liệu
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("Book").child(userId);
        txtName =findViewById(R.id.txtName);
        txtAuthor =findViewById(R.id.txtAuthor);
            imageView =findViewById(R.id.img);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lấy dữ liệu của user và xử lý
                String name = dataSnapshot.child("name").getValue(String.class);
                String author = dataSnapshot.child("author").getValue(String.class);

                String img = dataSnapshot.child("imgUrl").getValue(String.class);
                txtName.setText(name);
                txtAuthor.setText(author);
                Picasso.with(Detail.this).load(img).into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }
}