package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                tvError.setText("Không được để trống tên đăng nhập hoặc mật khẩu");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            if ("admin".equals(username) && "123456".equals(password)) {
                tvError.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Ngăn quay lại màn hình đăng nhập
            } else {
                tvError.setText("Sai tên đăng nhập hoặc mật khẩu");
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}