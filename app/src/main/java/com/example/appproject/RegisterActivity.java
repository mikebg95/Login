package com.example.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;

    private EditText first_name_entry, last_name_entry, email_entry, username_entry, password_entry, password2_entry;
    private FloatingActionButton register_button;
    private TextView login_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();

        first_name_entry = findViewById(R.id.first_name_register);
        last_name_entry = findViewById(R.id.last_name_register);
        email_entry = findViewById(R.id.email_register);
        password_entry = findViewById(R.id.password_register);
        password2_entry = findViewById(R.id.password2_register);
        register_button = findViewById(R.id.button_register);
        login_link = findViewById(R.id.login_link);

        // automatically goes to homepage screen cause previous user is always logged in
//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
//            finish();
//        }

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = first_name_entry.getText().toString().trim();
                String lastName = last_name_entry.getText().toString().trim();
                String email = email_entry.getText().toString().trim();
                String password = password_entry.getText().toString().trim();
                String password2 = password2_entry.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    first_name_entry.setError("First name is required");
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    last_name_entry.setError("Last name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    email_entry.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    password_entry.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    password2_entry.setError("Password confirmation is required");
                    return;
                }
                if (!password.equals(password2)) {
                    password2_entry.setError("Passwords don't match");
                    return;
                }

                createAccount(email, password);
            }
        });

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }


    public void createAccount(String email, String password) {
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}