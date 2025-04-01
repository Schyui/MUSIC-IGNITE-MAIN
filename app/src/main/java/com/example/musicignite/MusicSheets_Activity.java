package com.example.musicignite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import java.util.Arrays;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MusicSheets_Activity extends AppCompatActivity {

    ImageView backBtn;
    ImageView plusIconBtn;
    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> itemList;
    EditText songNameInput;
    Button uploadButton;
    Uri selectedFileUri;
    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedFileUri = result.getData().getData();
                    Toast.makeText(this, "File selected: " + selectedFileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_sheets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        plusIconBtn = findViewById(R.id.plusIconImg);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listView);
        // try lang
        String[] items = {"Fur Elise", "Canon in D", "Moonlight Sonata", "Nocturne Op.9", "Clair de Lune"};
        itemList = new ArrayList<>(Arrays.asList(items));

        // dito nakikita yung data
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        // filter ng search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        plusIconBtn.setOnClickListener(v -> showAddMusicSheetDialog());
    }

    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    private void showAddMusicSheetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_music_sheet, null);
        builder.setView(dialogView);

        songNameInput = dialogView.findViewById(R.id.editSongName);
        uploadButton = dialogView.findViewById(R.id.btnUpload);

        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf|image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            filePickerLauncher.launch(intent);
        });

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newSong = songNameInput.getText().toString().trim();
            if (!newSong.isEmpty() && selectedFileUri != null) {
                itemList.add(newSong + " (Uploaded)");
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Music sheet added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a song name and upload a file!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}