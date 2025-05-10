package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Learn_Basics_L1 extends AppCompatActivity {

    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_basics_l1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView gif = findViewById(R.id.l1_gif1);

        Glide.with(this)
                .load(R.drawable.basics_l1_gif1)
                .into(gif);

    }
@Override
    public void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Basics.class);
            startActivity(intent);
        });

    }
}