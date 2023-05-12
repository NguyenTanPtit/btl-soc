package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.bookstore.R;

import java.util.List;

public class PurchaseSuccessActivity extends AppCompatActivity {
    AppCompatButton continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_success);
        continueBtn = findViewById(R.id.btnContinue);

        Intent i = getIntent();
        Uri uri = i.getData();
        if(uri!=null){
            List<String> param = uri.getPathSegments();
            String status = param.get(0);
            if(status.equals("cancel")){
                startActivity(new Intent(PurchaseSuccessActivity.this,MainActivity.class));
                finish();
            }
        }
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PurchaseSuccessActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}