package com.example.rolex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceListActivity extends AppCompatActivity {

    ImageView serviceImage;
    TextView serviceNameText, serviceDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        // 🔹 Get data from Intent
        String serviceName = getIntent().getStringExtra("SERVICE_NAME");
        int imageRes = getIntent().getIntExtra("IMAGE", 0);

        if (serviceName == null) serviceName = "Service";

        // 🔹 Initialize UI
        TextView headerTitle = findViewById(R.id.tvHeaderTitle);
        serviceImage = findViewById(R.id.serviceImage);
        serviceNameText = findViewById(R.id.serviceName);
        serviceDesc = findViewById(R.id.serviceDesc);
        Button btnProceed = findViewById(R.id.btnProceedBooking);

        // 🔹 Set Header + Name
        headerTitle.setText(serviceName);
        serviceNameText.setText(serviceName);

        // 🔹 Set Image
        if (imageRes != 0) {
            serviceImage.setImageResource(imageRes);
        } else {
            serviceImage.setImageResource(R.drawable.w_facial); // fallback
        }

        // 🔹 Dynamic Description
        setServiceDescription(serviceName);

        // 🔹 Image Click Animation (Interactive)
        serviceImage.setOnClickListener(v -> {
            v.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(() ->
                            v.animate().scaleX(1f).scaleY(1f).setDuration(100)
                    );
        });

        // 🔹 Proceed Button
        String finalServiceName = serviceName;
        btnProceed.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceListActivity.this, AppointmentActivity.class);
            intent.putExtra("SERVICE_NAME", finalServiceName);
            intent.putExtra("IMAGE", imageRes);
            startActivity(intent);
        });

        // 🔹 Back Button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    // 🔹 Description Logic
    private void setServiceDescription(String serviceName) {

        switch (serviceName) {

            // Women Services
            case "Facial":
                serviceDesc.setText("Glow enhancing facial for radiant and healthy skin.");
                break;

            case "Hair Spa":
                serviceDesc.setText("Deep conditioning treatment for smooth and shiny hair.");
                break;

            case "Pedicure":
                serviceDesc.setText("Relaxing foot care with cleaning and massage.");
                break;

            case "Manicure":
                serviceDesc.setText("Complete nail care for beautiful hands.");
                break;

            case "Head Massage":
                serviceDesc.setText("Stress relief massage for relaxation and hair health.");
                break;

            case "Wax":
                serviceDesc.setText("Smooth and clean skin with professional waxing.");
                break;

            // Men Services
            case "Haircut":
                serviceDesc.setText("Stylish haircut tailored to your personality.");
                break;

            case "Beard Shaving":
                serviceDesc.setText("Clean and sharp beard shaving service.");
                break;

            case "Hair Styling":
                serviceDesc.setText("Trendy hair styling for a modern look.");
                break;

            case "Hair Coloring":
                serviceDesc.setText("Professional hair coloring with premium products.");
                break;

            default:
                serviceDesc.setText("Experience premium salon service with expert professionals.");
        }
    }
}