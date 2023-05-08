package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EnableActivity extends AppCompatActivity implements View.OnClickListener{
    CircleImageView profileImg;
    EditText edFName,edDate,edUName,edEmail,edPhone;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView change,back;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable);
        init();
        firebaseAuth = FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
//        String uid = "oY4KKHLpFSO9brlQpxHPxJtDY493";
        String uid=user.getUid();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference("Users").child(uid);
        edDate.setOnClickListener(this);
        databaseReference.child(uid);
        back = findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnableActivity.this, PersionalInfor.class));
                finish();
            }
        });
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null) {
                    edFName.setText(userprofile.getFullName());
                    edEmail.setText(userprofile.getEmail());
                    edPhone.setText(userprofile.getPhoneNumber());
                    edUName.setText(userprofile.getUserName());
                    edDate.setText(userprofile.getDateOfBirth());
                    Glide.with(EnableActivity.this).load(userprofile.getProfileImgUrl()).into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EnableActivity.this, "Lỗi" + error.getMessage() + "!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void init(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        edFName = findViewById(R.id.edFullname);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edDate = findViewById(R.id.edDate);
        edUName = findViewById(R.id.edUsername);
        change = findViewById(R.id.imgChang);
        back = findViewById(R.id.imgBack);
        profileImg = findViewById(R.id.imgProfile);
    }
    @Override
    public void onClick(View view) {
        if(view == edDate){
            Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    edDate.setText(yy+"/"+(mm+1) +"/"+dd);
                }
            },y,m,d);
            dialog.show();
        }
    }
}