package com.example.noteit.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteit.MainActivity;
import com.example.noteit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText rUserName,rUserEmail,rUserPass,rUserConfPass;
    Button syncAccount;
    TextView loginAct;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Create New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rUserName = findViewById(R.id.userName);
        rUserPass= findViewById(R.id.password);
        rUserConfPass = findViewById(R.id.passwordConfirm);
        rUserEmail = findViewById(R.id.userEmail);

        syncAccount = findViewById(R.id.createAccount);
        loginAct = findViewById(R.id.login);

        fAuth = FirebaseAuth.getInstance();
        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uUsername = rUserName.getText().toString();
                String uUserEmail = rUserEmail.getText().toString();
                String uUserPass = rUserPass.getText().toString();
                String uConfPass = rUserConfPass.getText().toString();
                if(uUserEmail.isEmpty()||uUsername.isEmpty()||uConfPass.isEmpty()||uUserPass.isEmpty()){
                    Toast.makeText(Register.this,"All fields are required ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!uUserPass.equals(uConfPass)){
                    rUserConfPass.setError("Password dont match");

                }

                AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail,uUserPass);
                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this,"Notes are synced ",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,"failed to connect.Try Again",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}