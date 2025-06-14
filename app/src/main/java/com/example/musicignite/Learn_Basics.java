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

public class Learn_Basics extends AppCompatActivity {

    ImageView backBtn;
    Button Lesson1, Lesson2, Lesson3, Lesson4, Lesson5, Lesson6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_basics);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        Lesson1 = findViewById(R.id.basics_Lesson1);
        Lesson2 = findViewById(R.id.basics_Lesson2);
        Lesson3 = findViewById(R.id.basics_Lesson3);
        Lesson4 = findViewById(R.id.basics_Lesson4);
        Lesson5 = findViewById(R.id.basics_Lesson5);
        Lesson6 = findViewById(R.id.basics_Lesson6);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Activity.class);
            startActivity(intent);
            });

            Lesson1.setOnClickListener(view -> {
                Intent intent = new Intent(this, Learn_Basics_L1.class);
                startActivity(intent);
            });

            Lesson2.setOnClickListener(view -> {
                Intent intent = new Intent(this, Learn_Basics_L2.class);
                startActivity(intent);
            });

            Lesson3.setOnClickListener(view -> {
                Intent intent = new Intent(this, Learn_Basics_L3.class);
                startActivity(intent);
            });

            Lesson4.setOnClickListener(view -> {
                Intent intent = new Intent(this, Learn_Basics_L4.class);
                startActivity(intent);
            });

            Lesson5.setOnClickListener(view -> {
                Intent intent = new Intent(this, Learn_Basics_L5.class);
                startActivity(intent);
            });

            Lesson6.setOnClickListener(view -> {
                Intent intent = new Intent(this, Learn_Basics_L6.class);
                startActivity(intent);
            });
    }
}