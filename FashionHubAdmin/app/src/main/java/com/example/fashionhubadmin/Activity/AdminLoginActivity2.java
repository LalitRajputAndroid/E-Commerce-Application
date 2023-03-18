package com.example.fashionhubadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fashionhubadmin.R;
import com.example.fashionhubadmin.databinding.ActivityAdminLogin2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity2 extends AppCompatActivity {

    ActivityAdminLogin2Binding binding ;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLogin2Binding.inflate(getLayoutInflater());

        SharedPreferences preferences = getSharedPreferences("Admin",MODE_PRIVATE);
        boolean check = preferences.getBoolean("flag",false);

        if (check){
            startActivity(new Intent(AdminLoginActivity2.this,MainActivity.class));
        }
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        binding.loginbtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailId.getText().toString().trim();
                String pass = binding.passwordId.getText().toString().trim();

                if (email.isEmpty()||pass.isEmpty()){
                    Toast.makeText(AdminLoginActivity2.this, "Fill the Filed", Toast.LENGTH_SHORT).show();
                }else {

                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                SharedPreferences preferences = getSharedPreferences("Admin",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("flag",true);
                                editor.apply();

                                startActivity(new Intent(AdminLoginActivity2.this,MainActivity.class));
                                Toast.makeText(AdminLoginActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLoginActivity2.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}