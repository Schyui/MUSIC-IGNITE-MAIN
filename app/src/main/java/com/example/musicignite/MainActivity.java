package com.example.musicignite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    TextView username;
    String usernameStr;
    Button learnBtn, practiceBtn,musicSheetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //RECEIVE FROM LOGINACTIVITY: PARA MACHANGE ANG USERNAME SA NAVIGATION HEADER
        username = findViewById(R.id.usernameShow);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usernameStr = prefs.getString("username", "Guest");  // Default: "Guest"
        username.setText("@" + usernameStr);


        //IDS
        learnBtn = findViewById(R.id.learnBtn);
        practiceBtn = findViewById(R.id.practiceBtn);
        musicSheetBtn = findViewById(R.id.musicSheetBtn);


        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //SCALE UP HAMBURGER ICON UNG THREE LINES HEHE
        DrawerArrowDrawable arrowDrawable = toggle.getDrawerArrowDrawable();
        arrowDrawable.setBarLength(80f);
        arrowDrawable.setBarThickness(9f); // lines thicker
        arrowDrawable.setGapSize(20f); // gap between lines
        arrowDrawable.setColor(ContextCompat.getColor(this, R.color.black)); // change color
        toggle.setDrawerArrowDrawable(arrowDrawable);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    //NAV OPTIONS (UNG NASA GILID)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment())
                    .commit();
        } else if (id == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ShareFragment())
                    .commit();
        } else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AboutFragment())
                    .commit();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //LINK PARA SA ACTIVITIES
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"STARTED", Toast.LENGTH_SHORT).show();

        learnBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_Activity.class);
            startActivity(intent);
        });
        practiceBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Practice_Activity.class);
            startActivity(intent);
        });
        musicSheetBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MusicSheets_Activity.class);
            startActivity(intent);
        });

    }

    public void onClickProfile(View v){

    }

}