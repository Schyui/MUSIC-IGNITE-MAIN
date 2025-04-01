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

public class PerfectPitch extends AppCompatActivity {

    ImageView backBtn;
    Button TimeRushBtn;
    Button AllInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfect_pitch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        TimeRushBtn = findViewById(R.id.TimeRushBtn);
        AllInBtn = findViewById(R.id.allInBtn);
    }
    @Override
    protected void onStart(){
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Practice_Activity.class);
            startActivity(intent);
        });
        TimeRushBtn.setOnClickListener(v -> {
            Intent intent = new Intent (this, TimeRush.class);
            startActivity(intent);
        });
        AllInBtn.setOnClickListener(v -> {
            Intent intent = new Intent (this, AllIn.class);
            startActivity(intent);
        });
    }
}