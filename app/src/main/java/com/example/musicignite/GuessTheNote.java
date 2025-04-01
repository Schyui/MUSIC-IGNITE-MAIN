package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GuessTheNote extends AppCompatActivity {

    ImageView backBtn;
    Button easyBtn;
    Button mediumBtn;
    Button hardBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guess_the_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        easyBtn = findViewById(R.id.easyBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        hardBtn = findViewById(R.id.hardBtn);
    }

    @Override
    protected void  onStart(){
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Practice_Activity.class);
            startActivity(intent);
        });
        easyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, GtnEasy.class);
            startActivity(intent);
        });
        mediumBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, GtnMedium.class);
            startActivity(intent);
        });
        hardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, GtnHard.class);
            startActivity(intent);
        });
    }
}