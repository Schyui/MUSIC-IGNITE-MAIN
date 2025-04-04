package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class TimeRush extends AppCompatActivity {

    ImageView backBtn, speakerQuizBtn;
    Button[] choiceButtons = new Button[6];
    GridLayout choicesGrid;
    TextView timerText;

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private MediaPlayer mediaPlayer;
    private CountDownTimer timer;
    private boolean quizStarted = false;
    private boolean timerRunning = false; // New flag to track timer state

    // tunog ng mga quiz
    private final HashMap<String, Integer> soundMap = new HashMap<String, Integer>() {{
        put("A", R.raw.note_a);
        put("B", R.raw.note_b);
        put("C", R.raw.note_c);
        put("D", R.raw.note_d);
        put("E", R.raw.note_e);
        put("F", R.raw.note_f);
        put("G", R.raw.note_g);
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_rush);

        backBtn = findViewById(R.id.backBtn);
        speakerQuizBtn = findViewById(R.id.speakerQuizBtn);
        choicesGrid = findViewById(R.id.choicesGrid);
        timerText = findViewById(R.id.timerText);

        choiceButtons[0] = findViewById(R.id.choice1);
        choiceButtons[1] = findViewById(R.id.choice2);
        choiceButtons[2] = findViewById(R.id.choice3);
        choiceButtons[3] = findViewById(R.id.choice4);
        choiceButtons[4] = findViewById(R.id.choice5);
        choiceButtons[5] = findViewById(R.id.choice6);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfectPitch.class);
            startActivity(intent);
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
                playCurrentSound(); // Play sound without restarting the quiz
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

        playCurrentSound();

        if (!timerRunning) { // eto yung nag p-prevent ng muliple timer pag ni click uli yung speaker btn
            timerRunning = true; // eto yung nag p-prevent para di na ma reset yung timer everytime na
            // gusto ni user na pindutin uli yung speaker btn
            timer = new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerText.setText("Time Left: " + millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    revealAnswer(null);
                }
            }.start();
        }
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

        timer.cancel();
        timerRunning = false;
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
    private void showQuizEndDialog() {
        runOnUiThread(() -> { // Ensures yung UI update na mag run sa main thread
            AlertDialog.Builder builder = new AlertDialog.Builder(TimeRush.this);
            builder.setTitle("Quiz Finished!")
                    .setMessage("Your score: " + score + "/10\nDo you want to take the quiz again?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        quizStarted = false;
                        score = 0;
                        currentQuestionIndex = 0;
                        generateQuestions();
                        timerText.setText("Time Left: 10s");
                        quizStarted = true;
                        playQuestion(currentQuestionIndex);
                    })
                    .setNegativeButton("No", (dialog, which) -> finish())
                    .show();
        });
    }
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
