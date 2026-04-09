package com.example.popup_menu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnMenu);

                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.item1) {
                            Toast.makeText(MainActivity.this, "Edit Clicked", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if (id == R.id.item2) {
                            Toast.makeText(MainActivity.this, "Delete Clicked", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if (id == R.id.item3) {
                            Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }
}