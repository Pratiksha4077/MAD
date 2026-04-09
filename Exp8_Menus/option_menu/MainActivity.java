package com.example.option_menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_search) {
            Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.menu_Share) {
            Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.menu_profile) {
            Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.menu_setting) {
            Toast.makeText(this, "Setting Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}