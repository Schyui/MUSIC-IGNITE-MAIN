package com.example.musicignite;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicSheets_Activity extends AppCompatActivity {
    HashMap<String, Float> ratingMap = new HashMap<>();
    ImageView backBtn, plusIconBtn;
    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> itemList;
    HashMap<String, Uri> fileMap = new HashMap<>();
    HashMap<String, ArrayList<String>> commentMap = new HashMap<>();
    Uri selectedFileUri;

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedFileUri = result.getData().getData();
                    Toast.makeText(this, "File selected!", Toast.LENGTH_SHORT).show();
                }
            });

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences("music_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        editor.putString("itemList", gson.toJson(itemList));

        HashMap<String, String> uriMap = new HashMap<>();
        for (String key : fileMap.keySet()) {
            uriMap.put(key, fileMap.get(key).toString());
        }

        JSONArray ratingsArray = new JSONArray();
        for (String title : ratingMap.keySet()) {
            JSONObject ratingObj = new JSONObject();
            try {
                ratingObj.put("title", title);
                ratingObj.put("rating", ratingMap.get(title));
                ratingsArray.put(ratingObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString("ratings_json", ratingsArray.toString());
        editor.putString("fileMap", gson.toJson(uriMap));
        editor.putString("commentMap", gson.toJson(commentMap));
        editor.apply();
    }

    private void loadData() {
        SharedPreferences prefs = getSharedPreferences("music_data", MODE_PRIVATE);
        Gson gson = new Gson();

        String listJson = prefs.getString("itemList", null);
        String fileJson = prefs.getString("fileMap", null);
        String commentJson = prefs.getString("commentMap", null);

        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        Type mapType = new TypeToken<HashMap<String, String>>(){}.getType();
        Type commentType = new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType();

        itemList = listJson != null ? gson.fromJson(listJson, listType) : new ArrayList<>();

        if (fileJson != null) {
            HashMap<String, String> uriStrings = gson.fromJson(fileJson, mapType);
            fileMap = new HashMap<>();
            for (String key : uriStrings.keySet()) {
                fileMap.put(key, Uri.parse(uriStrings.get(key)));
            }
        } else {
            fileMap = new HashMap<>();
        }

        commentMap = commentJson != null ? gson.fromJson(commentJson, commentType) : new HashMap<>();
        String ratingsJson = prefs.getString("ratings_json", null);
        if (ratingsJson != null) {
            try {
                JSONArray ratingsArray = new JSONArray(ratingsJson);
                for (int i = 0; i < ratingsArray.length(); i++) {
                    JSONObject obj = ratingsArray.getJSONObject(i);
                    String title = obj.getString("title");
                    float rating = (float) obj.getDouble("rating");
                    ratingMap.put(title, rating);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_sheets);

        backBtn = findViewById(R.id.backBtn);
        plusIconBtn = findViewById(R.id.plusIconImg);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listView);
        requestWritePermission();
        loadData();
        if (itemList == null) itemList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) { return false; }
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        plusIconBtn.setOnClickListener(v -> showAddMusicSheetDialog());
        backBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String songTitle = itemList.get(position);
            showMusicSheetDetailsDialog(songTitle);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String songTitle = itemList.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete Music Sheet")
                    .setMessage("Are you sure you want to delete \"" + songTitle + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        itemList.remove(songTitle);
                        fileMap.remove(songTitle);
                        ratingMap.remove(songTitle);
                        commentMap.remove(songTitle);
                        adapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(this, "Music sheet deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });
    }

    private void showAddMusicSheetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_music_sheet, null);
        builder.setView(dialogView);

        EditText songNameInput = dialogView.findViewById(R.id.editSongName);
        Button uploadButton = dialogView.findViewById(R.id.btnUpload);

        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            filePickerLauncher.launch(intent);
        });

        builder.setPositiveButton("Add", (dialog, which) -> {
            String songName = songNameInput.getText().toString().trim();
            if (!songName.isEmpty() && selectedFileUri != null) {
                itemList.add(songName);
                fileMap.put(songName, selectedFileUri);
                commentMap.put(songName, new ArrayList<>());
                adapter.notifyDataSetChanged();
                saveData();
                Toast.makeText(this, "Music sheet added!", Toast.LENGTH_SHORT).show();
                selectedFileUri = null;
            } else {
                Toast.makeText(this, "Please enter a song name and select a file!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void showMusicSheetDetailsDialog(String songTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_music_sheet_detail, null);
        builder.setView(dialogView);

        TextView titleText = dialogView.findViewById(R.id.songTitle);
        TextView uploaderText = dialogView.findViewById(R.id.uploaderName);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        EditText commentInput = dialogView.findViewById(R.id.commentInput);
        Button sendCommentBtn = dialogView.findViewById(R.id.sendCommentBtn);
        Button downloadBtn = dialogView.findViewById(R.id.downloadButton);
        ListView commentListView = dialogView.findViewById(R.id.commentListView);

        titleText.setText(songTitle);
        uploaderText.setText("Uploaded by: John Doe");

        ArrayList<String> comments = commentMap.getOrDefault(songTitle, new ArrayList<>());
        ArrayAdapter<String> commentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);
        commentListView.setAdapter(commentAdapter);
        ratingBar.setRating(ratingMap.getOrDefault(songTitle, 0f));

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                ratingMap.put(songTitle, rating);
                saveData();
            }
        });

        sendCommentBtn.setOnClickListener(v -> {
            String userComment = commentInput.getText().toString().trim();
            if (!userComment.isEmpty()) {
                comments.add("You: " + userComment);
                commentAdapter.notifyDataSetChanged();
                commentInput.setText("");
            }
        });

        downloadBtn.setOnClickListener(v -> {
            Uri fileUri = fileMap.get(songTitle);
            if (fileUri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(fileUri);
                    if (inputStream == null) throw new Exception("Unable to open input stream");

                    String fileName = songTitle.replaceAll("[^a-zA-Z0-9]", "_") + ".pdf";

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                        values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
                        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                        Uri externalUri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                        Uri fileOutUri = getContentResolver().insert(externalUri, values);

                        if (fileOutUri == null) throw new Exception("Failed to create new file");

                        OutputStream outputStream = getContentResolver().openOutputStream(fileOutUri);
                        if (outputStream == null) throw new Exception("Unable to open output stream");

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }

                        outputStream.close();
                        inputStream.close();

                    } else {
                        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        if (!downloadsDir.exists()) downloadsDir.mkdirs();

                        File outFile = new File(downloadsDir, fileName);
                        OutputStream outputStream = new FileOutputStream(outFile);

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }

                        outputStream.close();
                        inputStream.close();
                    }

                    Toast.makeText(this, "File saved to Downloads.", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to download file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private static final int REQUEST_WRITE_PERMISSION = 100;

    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            }
        }
    }
}
