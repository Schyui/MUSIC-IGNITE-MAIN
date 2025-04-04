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

public class Learn_Activity extends AppCompatActivity {

    ImageView backBtn;
    Button beginnerBtn, intermediateBtn, advancedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        beginnerBtn = findViewById(R.id.beginnerBtn);
        intermediateBtn = findViewById(R.id.intermediateBtn);
        advancedBtn = findViewById(R.id.advancedBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
        beginnerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Beginner_path.class);
            startActivity(intent);
        });

        intermediateBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Intermediate_path.class);
            startActivity(intent);
        });

        advancedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Advanced_path.class);
            startActivity(intent);
        });
    }


}