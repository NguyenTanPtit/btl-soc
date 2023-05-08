package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    CircleImageView img;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText edFullname, edPhone,edDate;
    Spinner spCountry;
    FirebaseUser user;
    FirebaseStorage storage;
    FirebaseDatabase db;
    AppCompatButton btContinue;
    private static final int IMAGE_REQUEST = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        ImageView back = findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
//        String uid = "oY4KKHLpFSO9brlQpxHPxJtDY493";
        String uid=user.getUid();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference("Users").child(uid);
        edDate.setOnClickListener(this);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();

            }
        });
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fullname, Phone,Date,Country;
                Fullname = edFullname.getText().toString();
                Phone = edPhone.getText().toString();
                Date = edDate.getText().toString();
                Country = spCountry.getSelectedItem().toString();

                myref.addValueEventListener (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userprofile=snapshot.getValue(User.class);
                        Map<String,Object> map = new HashMap<>();
                        map.put("fullName",Fullname);
                        map.put("phoneNumber",Phone);
                        map.put("dateOfBirth",Date);
                        map.put("country",Country);
                        myref.updateChildren(map, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }
    public void init(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        edFullname = findViewById(R.id.edFullname);
        edPhone = findViewById(R.id.edPhone);
        edDate = findViewById(R.id.edDate);
        spCountry = findViewById(R.id.spCountry);
        storage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        img = findViewById(R.id.imgProfile);
        btContinue = findViewById(R.id.btnContinue);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            Uri profileUri=data.getData();
            img.setImageURI(profileUri);
            final StorageReference reference= storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfileActivity.this, "Đang upload", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImgUrl").setValue(uri.toString());

                            Toast.makeText(ProfileActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}