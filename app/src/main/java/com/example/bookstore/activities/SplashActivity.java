package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    AppCompatButton btStart,btHave;
    LinearLayout btGG,btTw;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btGG=findViewById(R.id.btGoogle);
        btTw=findViewById(R.id.btTwit);
        btStart=findViewById(R.id.btStart);
        btHave=findViewById(R.id.btHave);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        databaseReference = firebaseDatabase.getReference("Users");
        btHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, RegistryActivity.class));
            }
        });
        btGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }


        });
        btTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this,TwitterActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null){
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    // Check credential
                    firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Check condition
                            if (task.isSuccessful()) {
                                // When task is successful redirect to profile activity display Toast
                                startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                displayToast("Firebase authentication successful");
                            } else {
                                // When task is unsuccessful display Toast
                                displayToast("Authentication Failed :" + task.getException().getMessage());
                            }
                        }
                    });

                }
//                HomeActivity();
            } catch (ApiException e) {
                Log.d("ApiException", e.getMessage());
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void displayToast(String s) {
        Toast.makeText(SplashActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}