package com.example.exp6_input_control;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText name, email, phone;
    RadioGroup genderGroup;
    RadioButton selectedGender;
    CheckBox cbReading, cbSports, cbMusic;
    ToggleButton toggleAccommodation;
    Button submit, reset;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        genderGroup = findViewById(R.id.rg);
        cbReading = findViewById(R.id.cbReading);
        cbSports = findViewById(R.id.cbSports);
        cbMusic = findViewById(R.id.cbMusic);

        toggleAccommodation = findViewById(R.id.toggleAccommodation);

        submit = findViewById(R.id.submit);
        reset = findViewById(R.id.reset);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentName = name.getText().toString();
                String studentEmail = email.getText().toString();
                String studentPhone = phone.getText().toString();

                int selectedId = genderGroup.getCheckedRadioButtonId();
                String gender = "";

                if (selectedId != -1) {
                    selectedGender = findViewById(selectedId);
                    gender = selectedGender.getText().toString();
                }

                String hobbies = "";
                if (cbReading.isChecked()) hobbies += "Reading ";
                if (cbSports.isChecked()) hobbies += "Sports ";
                if (cbMusic.isChecked()) hobbies += "Music ";

                String active;
                if (toggleAccommodation.isChecked()) {
                    active = "Hosteller";
                } else {
                    active = "Day Scholar";
                }

                String result = "Name: " + studentName +
                        "Email: " + studentEmail +
                        "Phone: " + studentPhone +
                        "Gender: " + gender +
                        "Hobbies: " + hobbies +
                        "Account Active: " + active;

                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

        // RESET BUTTON EVENT
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setText("");
                email.setText("");
                phone.setText("");

                genderGroup.clearCheck();

                cbReading.setChecked(false);
                cbSports.setChecked(false);
                cbMusic.setChecked(false);

                toggleAccommodation.setChecked(false);
            }
        });

        // ToggleButton Event
        toggleAccommodation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Hosteller Selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Day Scholar Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}