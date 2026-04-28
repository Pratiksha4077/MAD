package com.example.rolex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth and Database Reference
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Bind UI elements
        etName = findViewById(R.id.etRegName);
        etEmail = findViewById(R.id.etRegEmail);
        etPassword = findViewById(R.id.etRegPass);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.regProgress);

        btnRegister.setOnClickListener(v -> {
            final String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            // Validation
            if (name.isEmpty() || email.isEmpty() || pass.length() < 6) {
                Toast.makeText(this, "Please fill all fields. Password min 6 chars.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show loading and disable button to prevent double-clicks
            progressBar.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(false);

            // Step 1: Create user in Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String uid = mAuth.getCurrentUser().getUid();

                    // Step 2: Save user name to Realtime Database
                    mDatabase.child("Users").child(uid).child("name").setValue(name)
                            .addOnSuccessListener(aVoid -> {
                                // Data saved successfully, move to Home
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                intent.putExtra("USER_NAME", name);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                // Handle database errors (check your Firebase Rules!)
                                progressBar.setVisibility(View.GONE);
                                btnRegister.setEnabled(true);
                                Toast.makeText(RegisterActivity.this, "Database Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                } else {
                    // Handle Authentication errors
                    progressBar.setVisibility(View.GONE);
                    btnRegister.setEnabled(true);
                    Toast.makeText(this, "Auth Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}