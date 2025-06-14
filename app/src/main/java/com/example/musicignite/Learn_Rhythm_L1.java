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

public class Learn_Rhythm_L1 extends AppCompatActivity {
    Button NextBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_rhythm_l1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        NextBtn = findViewById(R.id.nextBtn);

        }
    @Override
    public void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Rhythm.class);
            startActivity(intent);
        });

        NextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Rhythm_L2.class);
            startActivity(intent);
        });


        }
    }