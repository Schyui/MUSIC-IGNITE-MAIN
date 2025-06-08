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

public class Learn_Rhythm extends AppCompatActivity {

    Button lesson1, lesson2;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_rhythm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        lesson1 = findViewById(R.id.rhythm_Lesson1);
        lesson2 = findViewById(R.id.rhythm_Lesson2);

    }
    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Activity.class);
            startActivity(intent);
        });
        lesson1.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Rhythm_L1.class);
            startActivity(intent);
        });

        lesson2.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Rhythm_L2.class);
            startActivity(intent);
        });
    }
}