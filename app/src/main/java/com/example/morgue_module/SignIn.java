package com.example.morgue_module;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignIn extends AppCompatActivity {
    MaterialButton btnSignIn;
    ImageView ivGorkan_Clip;
    TextInputEditText etUsernameSignin,etPasswordSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.BG));
        btnSignIn = findViewById(R.id.btnSignIn);
        ivGorkan_Clip = findViewById(R.id.ivGorkan_Clip);
        etPasswordSignin = findViewById(R.id.etPasswordSignin);
        etUsernameSignin = findViewById(R.id.etUsernameSignin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final View rootView = findViewById(android.R.id.content).getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) {
                    // Keyboard is opened, hide ImageView and TextViews
                    ivGorkan_Clip.setVisibility(View.GONE);
                } else {
                    // Keyboard is closed, show ImageView and TextV
                    ivGorkan_Clip.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}