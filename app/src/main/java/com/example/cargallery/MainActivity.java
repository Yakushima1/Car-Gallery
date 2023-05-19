package com.example.cargallery;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private TextView forgotPasswordTextView;
    ProgressBar progressBar;
    FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(), UserInterface.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.passwordr);
        loginButton = findViewById(R.id.Register);
        registerTextView = findViewById(R.id.textView);
        forgotPasswordTextView = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration action
                register();
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform forgot password action
                    forgotPassword();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login action
                login();
            }
        });
    }



    private void openuserInterface() {
        Intent intent = new Intent(this,UserInterface.class);
        startActivity(intent);
        finish();
    }

    private void openAdminInterface(){
        Intent intent = new Intent(this, adminInterface.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());


        if (TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            showMessage("Login Successful");
                            openuserInterface();
                        } else {

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });




    }

    private void register() {
        // Perform registration logic here
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }

    private void forgotPassword() {
        // Perform forgot password logic here
        showMessage("Forgot password action clicked!");
    }

    public void showMessage(String message) {
        // Show a toast message or any other way to display a message
        // Replace the following line with your actual implementation
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}