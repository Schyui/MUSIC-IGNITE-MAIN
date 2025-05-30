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

public class Learn_ScalesAndSignatures extends AppCompatActivity {

    Button scales_Lesson1, scales_Lesson2, scales_Lesson3, scales_Lesson4, scales_Lesson5;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_scales);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        scales_Lesson1 = findViewById(R.id.basics_Lesson1);
        scales_Lesson2 = findViewById(R.id.basics_Lesson2);
        scales_Lesson3 = findViewById(R.id.basics_Lesson3);
        scales_Lesson4 = findViewById(R.id.basics_Lesson4);
        scales_Lesson5 = findViewById(R.id.basics_Lesson5);

    }
    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Activity.class);
            startActivity(intent);
        });

        scales_Lesson1.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L1.class);
            startActivity(intent);
        });

        scales_Lesson2.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L2.class);
            startActivity(intent);
        });

        scales_Lesson3.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L3.class);
            startActivity(intent);
        });

        scales_Lesson4.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L4.class);
            startActivity(intent);
        });

        scales_Lesson5.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L5.class);
            startActivity(intent);
        });

    }
}