package com.example.listview_layout;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ListView l1;
    String[] items = {
            "Apple",
            "Banana",
            "Mango",
            "Orange",
            "Grapes",
            "Pineapple",
            "Strawberry",
            "Watermelon",
            "Papaya",
            "Guava",
            "Kiwi",
            "Pomegranate",
            "Cherry",
            "Blueberry",
            "Raspberry",
            "Blackberry",
            "Peach",
            "Pear",
            "Plum",
            "Apricot",
            "Lychee",
            "Dragon Fruit",
            "Jackfruit",
            "Custard Apple",
            "Fig",
            "Date",
            "Coconut",
            "Lemon",
            "Sweet Lime",
            "Tangerine",
            "Passion Fruit",
            "Star Fruit",
            "Mulberry",
            "Avocado",
            "Cranberry",
            "Gooseberry",
            "Persimmon",
            "Nectarine",
            "Mandarin",
            "Pomelo",
            "Rambutan",
            "Sapota",
            "Soursop",
            "Breadfruit",
            "Longan",
            "Mangosteen",
            "Olive",
            "Quince",
            "Ugli Fruit",
            "Boysenberry"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        l1 = findViewById(R.id.l1);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        items);

        l1.setAdapter(adapter);
    }

}