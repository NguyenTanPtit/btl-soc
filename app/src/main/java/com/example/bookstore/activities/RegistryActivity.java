package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegistryActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText edUsername, edPassword,edEmail,edCfPass;
    AppCompatButton btRegistry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        init();
        ImageView back = findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, confirmP, password;
                username = edUsername.getText().toString();
                email = edEmail.getText().toString();
                confirmP = edCfPass.getText().toString();
                password = edPassword.getText().toString();
                if (validate(email, username, confirmP, password)) {
                    Query checkUsername = databaseReference.orderByChild("username").equalTo(username);

                    checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                edUsername.setError("Username already exists");
                                edUsername.requestFocus();
                            } else {
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    User user = new User(username, password,  email);
                                                    user.setId(firebaseAuth.getCurrentUser().getUid());

                                                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(RegistryActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                                startActivity(new Intent(RegistryActivity.this, ProfileActivity.class));
                                                            } else {
                                                                Toast.makeText(RegistryActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                                            }
                                                        }

                                                    });

                                                }else {
                                                    edEmail.setError("This email has been registered");
                                                    edEmail.requestFocus();
                                                }
                                            }
                                        });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    public void init(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPass);
        edEmail = findViewById(R.id.edEmail);
        edCfPass = findViewById(R.id.edConPass);
        btRegistry = findViewById(R.id.btnRegistry);

    }
    public boolean validate(String email, String username, String confirmPass, String password) {
        if (!password.equals(confirmPass)){
            edCfPass.setError("Confirm Password is not correct");
            edCfPass.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            edEmail.setError("Cannot be empty");
            edEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Email is incorrect");
            edEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            edPassword.setError("Cannot be empty");
            edPassword.requestFocus();
            return false;
        }
        if (password.length() < 8 || password.length() > 16) {
            edPassword.setError("Password must be between 8-16 characters");
            edPassword.requestFocus();
            return false;
        }
        return true;
    }

}