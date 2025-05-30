package com.example.musicignite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Learn_ScalesAndSignatures_L5 extends AppCompatActivity {
    Button PreviousBtn;

    ImageView BackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_scales_l5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BackBtn = findViewById(R.id.backBtn);
        PreviousBtn = findViewById(R.id.previousBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures.class);
            startActivity(intent);
        });

        PreviousBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L4.class);
            startActivity(intent);
        });

    }
}