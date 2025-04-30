package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GtnHard extends AppCompatActivity {
    ImageView backBtn, ImageQuizBtn;
    Button[] choiceButtons = new Button[6];
    GridLayout choicesGrid;

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean quizStarted = false;

    private final HashMap<String, Integer> soundMap = new HashMap<String, Integer>() {{
        put("A", R.drawable.note_a);
        put("B", R.drawable.note_b);
        put("C", R.drawable.note_c);
        put("D", R.drawable.note_d);
        put("E", R.drawable.note_e);
        put("F", R.drawable.note_f);
        put("G", R.drawable.note_g);
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtn_easy);

        backBtn = findViewById(R.id.backBtn);
        ImageQuizBtn = findViewById(R.id.ImageQuizNote_btn);
        choicesGrid = findViewById(R.id.choicesGrid);

        choiceButtons[0] = findViewById(R.id.choice1);
        choiceButtons[1] = findViewById(R.id.choice2);
        choiceButtons[2] = findViewById(R.id.choice3);
        choiceButtons[3] = findViewById(R.id.choice4);
        choiceButtons[4] = findViewById(R.id.choice5);
        choiceButtons[5] = findViewById(R.id.choice6);

        backBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GtnHard.this);
            builder.setTitle("Warning!")
                    .setMessage("Are you sure you want to go back? Your progress will be lost.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent backIntent = new Intent(this, PerfectPitch.class);
                        startActivity(backIntent);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        ImageQuizBtn.setOnClickListener(v -> {
            if (!quizStarted) {
                generateQuestions();
                currentQuestionIndex = 0;
                score = 0;
                quizStarted = true;
                playQuestion(currentQuestionIndex);
            }
        });

        for (Button button : choiceButtons) {
            button.setOnClickListener(this::handleAnswer);
        }
    }

    private void generateQuestions() {
        List<String> notes = new ArrayList<>(soundMap.keySet());
        Collections.shuffle(notes);
        questions.clear();

        for (int i = 0; i < 10; i++) {
            String correct = notes.get(i % notes.size());
            List<String> choices = new ArrayList<>(notes);
            choices.remove(correct);
            Collections.shuffle(choices);
            while (choices.size() > 5) {
                choices.remove(choices.size() - 1);
            }
            choices.add(correct);
            Collections.shuffle(choices);
            questions.add(new Question(correct, choices));
        }
    }

    private void playQuestion(int index) {
        if (index >= questions.size()) {
            showQuizEndDialog();
            return;
        }

        Question q = questions.get(index);
        ImageQuizBtn.setImageResource(soundMap.get(q.correctAnswer));

        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i].setText(q.choices.get(i));
            choiceButtons[i].setEnabled(true);
            choiceButtons[i].setBackgroundResource(R.drawable.btn_border);
        }
    }

    private void handleAnswer(View view) {
        if (!quizStarted) {
            Toast.makeText(this, "Click the image to start the quiz!", Toast.LENGTH_SHORT).show();
            return;
        }

        Button selected = (Button) view;
        String answer = selected.getText().toString();
        Question q = questions.get(currentQuestionIndex);

        for (Button btn : choiceButtons) {
            btn.setEnabled(false);
            if (btn.getText().toString().equals(q.correctAnswer)) {
                btn.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            } else if (btn == selected) {
                btn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }
        }

        if (answer.equals(q.correctAnswer)) {
            score++;
        }

        new android.os.Handler().postDelayed(() -> {
            currentQuestionIndex++;
            playQuestion(currentQuestionIndex);
        }, 1000);
    }

    private void showQuizEndDialog() {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GtnHard.this);
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

    private static class Question {
        String correctAnswer;
        List<String> choices;

        Question(String correctAnswer, List<String> choices) {
            this.correctAnswer = correctAnswer;
            this.choices = choices;
        }
    }
}
