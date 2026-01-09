package com.example.uas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etNewPassword, etConfirmPassword;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String newPass = etNewPassword.getText().toString();
            String confirm = etConfirmPassword.getText().toString();

            if (user.isEmpty() || email.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirm)) {
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Password berhasil direset (dummy)", Toast.LENGTH_SHORT).show();
                finish(); // balik ke Login
            }
        });
    }
}
