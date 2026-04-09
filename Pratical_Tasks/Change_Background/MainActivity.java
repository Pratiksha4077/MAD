package com.example.change_background;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    Button b1,b2,b3;
    ConstraintLayout cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        cl=findViewById(R.id.layout);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //cl.setBackgroundColor(Color.RED);
                cl.setBackgroundResource(R.drawable.img);
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //cl.setBackgroundColor(Color.GREEN);
                cl.setBackgroundResource(R.drawable.img_1);
            }
        });

        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //cl.setBackgroundColor(Color.YELLOW);
                cl.setBackgroundResource(R.drawable.img_2);
            }
        });
    }
}