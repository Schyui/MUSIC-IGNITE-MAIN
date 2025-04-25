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
    Button Basics, Rhythm, ScalesAndSignatures, Intervals, Chords;
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
        Basics = findViewById(R.id.basicBtn);
        Rhythm = findViewById(R.id.rhythmBtn);
        ScalesAndSignatures = findViewById(R.id.scales_signaturesBtn);
        Intervals = findViewById(R.id.intervalsBtn);
        Chords = findViewById(R.id.chordsBtn);

        backBtn = findViewById(R.id.backBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
        Basics.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Basics.class);
            startActivity(intent);
        });

        Rhythm.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Rhythm.class);
            startActivity(intent);
        });

        ScalesAndSignatures.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures.class);
            startActivity(intent);
        });

        Intervals.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Intervals.class);
            startActivity(intent);
        });

        Chords.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Chords.class);
            startActivity(intent);
        });
    }


}