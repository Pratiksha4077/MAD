package com.example.exp7_progressbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnProgress, btnCircle;
    RatingBar ratingBar;

    ProgressDialog progressDialog;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProgress = findViewById(R.id.btnProgress);
        btnCircle = findViewById(R.id.btnCircle);
        ratingBar = findViewById(R.id.ratingBar);

        btnProgress.setOnClickListener(v -> {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.show();

            progressStatus = 0;

            new Thread(() -> {
                while (progressStatus < 100) {
                    progressStatus += 10;

                    handler.post(() -> progressDialog.setProgress(progressStatus));

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,
                            "Download Complete",
                            Toast.LENGTH_SHORT).show();
                });
            }).start();
        });

        btnCircle.setOnClickListener(v -> {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true); // circular
            progressDialog.setCancelable(false);
            progressDialog.show();

            handler.postDelayed(() -> {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,
                        "Process Finished",
                        Toast.LENGTH_SHORT).show();
            }, 3000);
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            Toast.makeText(MainActivity.this,
                    "Rating: " + rating,
                    Toast.LENGTH_SHORT).show();
        });
    }
}