package com.example.musicignite;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class BookIcon extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private HashMap<String, Integer> soundMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_icon);

        // Initialize sound map with your raw sound resource IDs
        soundMap = new HashMap<>();

        // G Clef & Treble Notes raw sounds
        soundMap.put("A", R.raw.gcleftrble_a);
        soundMap.put("B", R.raw.gcleftreble_b);
        soundMap.put("C", R.raw.gcleftreble_c);
        soundMap.put("D", R.raw.gcleftreble_d);
        soundMap.put("E", R.raw.gcleftreble_e);
        soundMap.put("F", R.raw.gcleftreble_f);
        soundMap.put("G", R.raw.gcleftreble_g);

        // Bass Clef raw sounds
        soundMap.put("Bass_A", R.raw.bass_a1);
        soundMap.put("Bass_B", R.raw.bass_b1);
        soundMap.put("Bass_C", R.raw.bass_c1);
        soundMap.put("Bass_D", R.raw.bass_d1);
        soundMap.put("Bass_E", R.raw.bass_e1);
        soundMap.put("Bass_F", R.raw.bass_f1);
        soundMap.put("Bass_G", R.raw.bass_g1);

        // Flats and sharps (same sounds for equivalent flats/sharps)
        soundMap.put("A♭", R.raw.flata_gsharp);
        soundMap.put("B♭", R.raw.flatb_asharp);
        soundMap.put("D♭", R.raw.flatd_csharp);
        soundMap.put("E♭", R.raw.flate_dsharp);
        soundMap.put("G♭", R.raw.flatg_fsharp);

        soundMap.put("G♯", R.raw.flata_gsharp);
        soundMap.put("A♯", R.raw.flatb_asharp);
        soundMap.put("C♯", R.raw.flatd_csharp);
        soundMap.put("D♯", R.raw.flate_dsharp);
        soundMap.put("F♯", R.raw.flatg_fsharp);

        // G Clef Buttons
        setupButton(R.id.play_gclef_a, "A");
        setupButton(R.id.play_gclef_b, "B");
        setupButton(R.id.play_gclef_c, "C");
        setupButton(R.id.play_gclef_d, "D");
        setupButton(R.id.play_gclef_e, "E");
        setupButton(R.id.play_gclef_ee, "E");
        setupButton(R.id.play_gclef_f, "F");
        setupButton(R.id.play_gclef_ff, "F");
        setupButton(R.id.play_gclef_g, "G");

        // Bass Clef Buttons
        setupButton(R.id.play_bass_a, "Bass_A");
        setupButton(R.id.play_bass_aa, "Bass_A");
        setupButton(R.id.play_bass_b, "Bass_B");
        setupButton(R.id.play_bass_bb, "Bass_B");
        setupButton(R.id.play_bass_c, "Bass_C");
        setupButton(R.id.play_bass_cc, "Bass_C");
        setupButton(R.id.play_bass_d, "Bass_D");
        setupButton(R.id.play_bass_e, "Bass_E");
        setupButton(R.id.play_bass_f, "Bass_F");
        setupButton(R.id.play_bass_ff, "Bass_F");
        setupButton(R.id.play_bass_g, "Bass_G");
        setupButton(R.id.play_bass_gg, "Bass_G");

        // G Clef Flats Buttons
        setupButton(R.id.play_gclef_ab, "A♭");
        setupButton(R.id.play_gclef_bb, "B♭");
        setupButton(R.id.play_gclef_db, "D♭");
        setupButton(R.id.play_gclef_eb, "E♭");
        setupButton(R.id.play_gclef_eeb, "E♭");
        setupButton(R.id.play_gclef_gb, "G♭");

        // G Clef Sharps Buttons
        setupButton(R.id.play_gclef_as, "A♯");
        setupButton(R.id.play_gclef_cs, "C♯");
        setupButton(R.id.play_gclef_ds, "D♯");
        setupButton(R.id.play_gclef_fs, "F♯");
        setupButton(R.id.play_gclef_ffs, "F♯");
        setupButton(R.id.play_gclef_gs, "G♯");

        // Treble Clef Buttons (same as G Clef, but separate IDs)
        setupButton(R.id.play_treble_a, "A");
        setupButton(R.id.play_treble_b, "B");
        setupButton(R.id.play_treble_c, "C");
        setupButton(R.id.play_treble_cc, "C");
        setupButton(R.id.play_treble_d, "D");
        setupButton(R.id.play_treble_dd, "D");
        setupButton(R.id.play_treble_e, "E");
        setupButton(R.id.play_treble_ee, "E");
        setupButton(R.id.play_treble_f, "F");
        setupButton(R.id.play_treble_ff, "F");
        setupButton(R.id.play_treble_g, "G");
        setupButton(R.id.play_treble_gg, "G");
    }

    private void setupButton(int buttonId, String noteKey) {
        Button btn = findViewById(buttonId);
        if (btn != null) {
            btn.setOnClickListener(v -> playSound(noteKey));
        }
    }

    private void playSound(String note) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        Integer soundResId = soundMap.get(note);
        if (soundResId != null) {
            mediaPlayer = MediaPlayer.create(this, soundResId);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
