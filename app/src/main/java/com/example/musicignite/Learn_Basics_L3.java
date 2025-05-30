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

public class Learn_Basics_L3 extends AppCompatActivity {
    Button NextBtn, PreviousBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_basics_l3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        NextBtn = findViewById(R.id.nextBtn);
        PreviousBtn = findViewById(R.id.previousBtn);
    }

    @Override
    public void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L3.this, Learn_Basics.class);
            startActivity(intent);
        });

        NextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L3.this, Learn_Basics_L4.class);
            startActivity(intent);
        });

        PreviousBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L3.this, Learn_Basics_L2.class);
            startActivity(intent);
        });


    }
}