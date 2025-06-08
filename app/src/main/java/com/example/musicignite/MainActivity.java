package com.example.musicignite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_PROFILE_PIC_URI = "profile_pic_uri";

    TextView username, displayName;
    Button learnBtn, practiceBtn, musicSheetBtn;

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




        // Show username in header
        username = findViewById(R.id.usernameShow);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usernameStr = prefs.getString("username", "Guest");
        username.setText("@" + usernameStr);

        // Setup Toolbar and Navigation Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set profile image in nav header
        View headerView = navigationView.getHeaderView(0);
        ImageView profilePic = headerView.findViewById(R.id.profilePic);

        // Load profile pic from shared prefs
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uriString = sharedPrefs.getString(KEY_PROFILE_PIC_URI, null);

        loadProfilePicIntoNavHeader();

        // Drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Customize hamburger icon
        DrawerArrowDrawable arrowDrawable = toggle.getDrawerArrowDrawable();
        arrowDrawable.setBarLength(80f);
        arrowDrawable.setBarThickness(9f);
        arrowDrawable.setGapSize(20f);
        arrowDrawable.setColor(ContextCompat.getColor(this, R.color.black));
        toggle.setDrawerArrowDrawable(arrowDrawable);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    private void loadProfilePicIntoNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView profilePic = findViewById(R.id.profilePic);

        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uriString = sharedPrefs.getString(KEY_PROFILE_PIC_URI, null);

        if (uriString != null) {
            try {
                Uri imageUri = Uri.parse(uriString);
                profilePic.setImageURI(null); // force refresh
                profilePic.setImageURI(imageUri);
            } catch (Exception e) {
                profilePic.setImageResource(R.drawable.profileicon);
            }
        } else {
            profilePic.setImageResource(R.drawable.profileicon);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        } else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        } else if (id == R.id.nav_logout) {

            finishAffinity();
            System.exit(0);
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

    @Override
    protected void onResume() {
        super.onResume();
        loadProfilePicIntoNavHeader();

    }

    @Override
    protected void onStart() {
        super.onStart();

        NavigationView navigationView = findViewById(R.id.nav_view);
        String frag = getIntent().getStringExtra("open_fragment");
        if ("my_fragment".equals(frag)) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_settings);
        }
        // Get the saved username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userUsername = prefs.getString("username", "");  // key used in login


        if (!userUsername.isEmpty()) {
            // Reference to user's node in Firebase
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            // Query the data for the username
            reference.child(userUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String nameFromDB = snapshot.child("name").getValue(String.class);
                        String emailFromDB = snapshot.child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child("username").getValue(String.class);

                        // Update SharedPreferences with latest data
                        SharedPreferences prefSettings = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editorz = prefSettings.edit();
                        editorz.putString("usernameSet", usernameFromDB);
                        editorz.putString("nameSet", nameFromDB);
                        editorz.putString("emailSet", emailFromDB);
                        TextView displayName = findViewById(R.id.displayNameHeader);
                        displayName.setText(nameFromDB);
                        editorz.apply();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors here (optional)
                }
            });
        }


    }
}