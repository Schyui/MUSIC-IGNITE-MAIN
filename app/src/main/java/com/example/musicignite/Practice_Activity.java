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

public class Practice_Activity extends AppCompatActivity {

    ImageView backBtn;
    Button PerfectPitchBtn, GuessTheNoteBtn;
    ImageView BookIconBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        PerfectPitchBtn = findViewById(R.id.perfectpitchBtn);
        GuessTheNoteBtn = findViewById(R.id.guessBtn);
        BookIconBtn = findViewById(R.id.BookIconBtn);
    }
    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        PerfectPitchBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, PerfectPitch.class);
            startActivity(intent);
        });
        GuessTheNoteBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, GuessTheNote.class);
            startActivity(intent);
        });
        BookIconBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, BookIcon.class);
            startActivity(intent);
        });
    }
}