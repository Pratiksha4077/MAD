package com.example.poe_exam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class second_activity extends AppCompatActivity {

    ImageButton i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);


        i1=findViewById(R.id.i1);
        i2=findViewById(R.id.i2);
        i3=findViewById(R.id.i3);
        i4=findViewById(R.id.i4);
        i5=findViewById(R.id.i5);
        i6=findViewById(R.id.i6);
        i7=findViewById(R.id.i7);
        i8=findViewById(R.id.i8);
        i9=findViewById(R.id.i9);
        i10=findViewById(R.id.i10);
        i11=findViewById(R.id.i11);
        i12=findViewById(R.id.i12);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab1.class);
                startActivity(i);
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab2.class);
                startActivity(i);
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab3.class);
                startActivity(i);
            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab4.class);
                startActivity(i);
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab5.class);
                startActivity(i);
            }
        });

        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab6.class);
                startActivity(i);
            }
        });

        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab7.class);
                startActivity(i);
            }
        });

        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab8.class);
                startActivity(i);
            }
        });

        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab9.class);
                startActivity(i);
            }
        });

        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab10.class);
                startActivity(i);
            }
        });

        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab11.class);
                startActivity(i);
            }
        });

        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(second_activity.this, lab12.class);
                startActivity(i);
            }
        });


    }
}