package com.example.exp10_file_handling;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;   // NEW TextView
    Button saveBtn, loadBtn;

    String fileName = "notes.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.e1);
        textView = findViewById(R.id.t1);   // link TextView
        saveBtn = findViewById(R.id.b1);
        loadBtn = findViewById(R.id.b2);

        // SAVE DATA
        saveBtn.setOnClickListener(v -> {
            try {
                String data = editText.getText().toString();

                FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();

                Toast.makeText(this, "File Saved Successfully", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Error Saving File", Toast.LENGTH_SHORT).show();
            }
        });

        // LOAD DATA INTO TEXTVIEW
        loadBtn.setOnClickListener(v -> {
            try {
                FileInputStream fis = openFileInput(fileName);

                int c;
                StringBuilder temp = new StringBuilder();

                while ((c = fis.read()) != -1) {
                    temp.append((char) c);
                }

                fis.close();

                // Set data in TextView instead of EditText
                textView.setText(temp.toString());

                Toast.makeText(this, "File Loaded", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Error Loading File", Toast.LENGTH_SHORT).show();
            }
        });
    }
}