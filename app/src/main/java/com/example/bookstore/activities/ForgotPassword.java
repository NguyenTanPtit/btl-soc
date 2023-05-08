package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailuser;
    private AppCompatButton resetpass;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailuser=(EditText)findViewById(R.id.txtEmail);
        resetpass= findViewById(R.id.button);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        resetpass.setOnClickListener(v->reset());
        ImageView back = findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void reset(){
        String email= emailuser.getText().toString().trim();
        if(email.isEmpty()){
            emailuser.setError("Bạn cần nhập email!");
            emailuser.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailuser.setError("Email không đúng vui lòng kiểm tra lại!");
            emailuser.requestFocus();
            return;
        }
        progressDialog.setMessage("Đang gửi yêu cầu...");
        progressDialog.show();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.cancel();
                    Toast.makeText(ForgotPassword.this, "Kiểm tra email để khôi phục", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                    finish();
                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(ForgotPassword.this, "Thất bại vui lòng kiểm tra lại thông tin", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}