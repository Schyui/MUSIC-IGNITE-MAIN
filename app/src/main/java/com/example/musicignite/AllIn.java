package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AllIn extends AppCompatActivity {

    ImageView backBtn, speakerQuizBtn;
    Button[] choiceButtons = new Button[6];
    GridLayout choicesGrid;


    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private MediaPlayer mediaPlayer;
    private boolean quizStarted = false;

    // tunog ng mga quiz and answer, yung may"" yung ans na nandun sa loob ng button yung raw naman ano sha location nung tunog
    private final HashMap<String, Integer> soundMap = new HashMap<String, Integer>() {{
        put("A", R.raw.bass_a1);
        put("B", R.raw.bass_b1);
        put("C", R.raw.bass_c1);
        put("D", R.raw.bass_d1);
        put("E", R.raw.bass_e1);
        put("F", R.raw.bass_f1);
        put("G", R.raw.bass_g1);

        put("A", R.raw.gcleftrble_a);
        put("B", R.raw.gcleftreble_b);
        put("C", R.raw.gcleftreble_c);
        put("D", R.raw.gcleftreble_d);
        put("E", R.raw.gcleftreble_e);
        put("F", R.raw.gcleftreble_f);
        put("G", R.raw.gcleftreble_g);

        put("A♭", R.raw.flata_gsharp);
        put("B♭", R.raw.flatb_asharp);
        put("D♭", R.raw.flatd_csharp);
        put("E♭", R.raw.flate_dsharp);
        put("G♭", R.raw.flatg_fsharp);

        put("G♯", R.raw.flata_gsharp);
        put("A♯", R.raw.flatb_asharp);
        put("C♯", R.raw.flatd_csharp);
        put("D♯", R.raw.flate_dsharp);
        put("F♯", R.raw.flatg_fsharp);
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_rush);

        backBtn = findViewById(R.id.backBtn);
        speakerQuizBtn = findViewById(R.id.speakerQuizBtn);
        choicesGrid = findViewById(R.id.choicesGrid);


        choiceButtons[0] = findViewById(R.id.choice1);
        choiceButtons[1] = findViewById(R.id.choice2);
        choiceButtons[2] = findViewById(R.id.choice3);
        choiceButtons[3] = findViewById(R.id.choice4);
        choiceButtons[4] = findViewById(R.id.choice5);
        choiceButtons[5] = findViewById(R.id.choice6);

        backBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AllIn.this);
            builder.setTitle("Warning!")
                    .setMessage("You will lose your progress")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent backIntent = new Intent(this, PerfectPitch.class);
                        startActivity(backIntent);
                    })
                    .setNegativeButton("No", (dialog, which) -> {})
                    .show();
        });

        // if napindot na yung speaker na image tsaka lang lalabas yung mga answers na pagpipilian sa buttons
        speakerQuizBtn.setOnClickListener(v -> {
            if (!quizStarted) {
                generateQuestions();
                currentQuestionIndex = 0;
                score = 0;
                quizStarted = true;
                playQuestion(currentQuestionIndex);
            } else {
                playCurrentSound(); // play lang ng tunog kung pinindot ulit
            }
        });

        for (Button button : choiceButtons) {
            button.setOnClickListener(this::handleAnswer);
        }
    }

    // eto yung nag s-shuffle ng mga tanong everytime na pininpindot yung time rush
    private void generateQuestions() {
        List<String> notes = new ArrayList<>(soundMap.keySet());
        Collections.shuffle(notes);
        questions.clear(); // Clear ng previous questions

        for (int i = 0; i < 10; i++) {
            String correct = notes.get(i % notes.size()); // Avoiding index errors
            List<String> choices = new ArrayList<>(notes);
            choices.remove(correct); // Remove correct answer to prevent duplicates
            Collections.shuffle(choices);
            while (choices.size() > 5) { // Keep only 5 wrong choices
                choices.remove(choices.size() - 1);
            }
            choices.add(correct); // Add the correct answer back
            Collections.shuffle(choices); // Shuffle again to mix the correct answer
            questions.add(new Question(correct, choices)); // 6 na choices
        }
    }

    private void playQuestion(int index) {
        resetButtons();
        Question q = questions.get(index);

        for (int i = 0; i < 6; i++) {
            choiceButtons[i].setText(q.choices.get(i));
            choiceButtons[i].setEnabled(true);
        }

        playCurrentSound(); // play ng tunog para sa current na tanong
    }

    private void playCurrentSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundMap.get(questions.get(currentQuestionIndex).correctAnswer));
        mediaPlayer.start();
    }

    private void handleAnswer(View view) {
        if (!quizStarted) {
            Toast.makeText(this, "Click the speaker button to start the quiz!", Toast.LENGTH_SHORT).show();
            return;
        }

        Button selectedButton = (Button) view;
        String selectedText = selectedButton.getText().toString();
        revealAnswer(selectedText);
    }

    private void revealAnswer(String selectedAnswer) {
        Question current = questions.get(currentQuestionIndex);

        for (Button b : choiceButtons) {
            b.setEnabled(false);
            String btnText = b.getText().toString();

            if (btnText.equals(current.correctAnswer)) {
                b.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            } else if (btnText.equals(selectedAnswer)) {
                b.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            } else {
                b.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }

        if (selectedAnswer != null && selectedAnswer.equals(current.correctAnswer)) {
            score++;
        }

        choicesGrid.postDelayed(() -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                playQuestion(currentQuestionIndex);
            } else {
                showQuizEndDialog();
            }
        }, 1500);
    }

    // UI dialog kapag tapos na yung quiz
    private void showQuizEndDialog() {
        runOnUiThread(() -> { // Ensures yung UI update na mag run sa main thread
            AlertDialog.Builder builder = new AlertDialog.Builder(AllIn.this);
            builder.setTitle("Quiz Finished!")
                    .setMessage("Your score: " + score + "/10\nDo you want to take the quiz again?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        quizStarted = false;
                        score = 0;
                        currentQuestionIndex = 0;
                        generateQuestions();

                        quizStarted = true;
                        playQuestion(currentQuestionIndex);
                    })
                    .setNegativeButton("No", (dialog, which) -> finish())
                    .show();
        });
    }

    // reset ng mga button na gray at disabled
    private void resetButtons() {
        for (Button b : choiceButtons) {
            b.setBackgroundResource(R.drawable.btn_border);
            b.setEnabled(false);
        }
    }

    private class Question {
        String correctAnswer;
        List<String> choices;

        Question(String correctAnswer, List<String> choices) {
            this.correctAnswer = correctAnswer;
            this.choices = new ArrayList<>(choices);
            Collections.shuffle(this.choices);
        }
    }
}
