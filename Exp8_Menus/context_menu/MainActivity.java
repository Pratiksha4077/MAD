package com.example.contextmenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.t1);

        registerForContextMenu(textView);
    }

    // Create Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Option");

        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Handle Menu Click
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_edit) {
            Toast.makeText(this, "Edit Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.menu_delete) {
            Toast.makeText(this, "Delete Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.menu_share) {
            Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}