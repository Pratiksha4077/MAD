package com.example.rolex;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {

    // UI Elements
    private ImageView ivServiceDetail, btnBack;
    private TextView tvSummary, tvSelectedDate;
    private CardView cvDatePicker;
    private Button btnConfirmAppointment;
    private Button[] slotButtons = new Button[9];

    // Data Variables
    private String selectedDate = "";
    private String selectedSlot = "";
    private String serviceName;
    private int imageRes;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 1. Get Data passed from ServiceListActivity
        serviceName = getIntent().getStringExtra("SERVICE_NAME");
        imageRes = getIntent().getIntExtra("IMAGE", 0);

        // 2. Initialize Views
        initViews();

        // 3. Setup Initial UI State
        tvSummary.setText(serviceName != null ? serviceName : "Service");
        if (imageRes != 0) {
            ivServiceDetail.setImageResource(imageRes);
        }

        // 4. Back Navigation
        btnBack.setOnClickListener(v -> finish());

        // 5. Date Selection
        cvDatePicker.setOnClickListener(v -> openDatePicker());

        // 6. Time Slot Selection Logic
        setupSlotSelection();

        // 7. Confirm Booking with Validation
        btnConfirmAppointment.setOnClickListener(v -> validateAndBook());
    }

    private void initViews() {
        ivServiceDetail = findViewById(R.id.ivServiceDetail);
        tvSummary = findViewById(R.id.tvSummary);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        cvDatePicker = findViewById(R.id.cvDatePicker);
        btnConfirmAppointment = findViewById(R.id.btnConfirmAppointment);
        btnBack = findViewById(R.id.btnBack);

        // Binding the 9 slots defined in XML
        slotButtons[0] = findViewById(R.id.slot1);
        slotButtons[1] = findViewById(R.id.slot2);
        slotButtons[2] = findViewById(R.id.slot3);
        slotButtons[3] = findViewById(R.id.slot4);
        slotButtons[4] = findViewById(R.id.slot5);
        slotButtons[5] = findViewById(R.id.slot6);
        slotButtons[6] = findViewById(R.id.slot7);
        slotButtons[7] = findViewById(R.id.slot8);
        slotButtons[8] = findViewById(R.id.slot9);
    }

    private void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    tvSelectedDate.setText(selectedDate);
                    tvSelectedDate.setTextColor(Color.BLACK);
                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setupSlotSelection() {
        for (Button btn : slotButtons) {
            if (btn == null) continue;
            btn.setOnClickListener(v -> {
                // Reset visual state
                for (Button b : slotButtons) {
                    if (b != null) {
                        b.setBackgroundColor(Color.WHITE);
                        b.setTextColor(Color.parseColor("#4A4A4A"));
                    }
                }
                // Highlight selection
                btn.setBackgroundColor(Color.parseColor("#E91E63"));
                btn.setTextColor(Color.WHITE);
                selectedSlot = btn.getText().toString();
            });
        }
    }

    private void validateAndBook() {
        // Basic Input Validation
        if (selectedDate.isEmpty()) {
            showErrorPopup("Date Missing", "Please select a schedule date.");
            return;
        }
        if (selectedSlot.isEmpty()) {
            showErrorPopup("Time Missing", "Please select a time slot.");
            return;
        }
        if (mAuth.getCurrentUser() == null) {
            showErrorPopup("Error", "User not authenticated.");
            return;
        }

        btnConfirmAppointment.setEnabled(false);
        DatabaseReference appointmentsRef = mDatabase.child("Appointments");

        // Check database for slot collision
        appointmentsRef.orderByChild("date").equalTo(selectedDate)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isTaken = false;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Appointment existingApp = ds.getValue(Appointment.class);
                            if (existingApp != null && existingApp.time.equals(selectedSlot)) {
                                // Block if it is Approved or still Pending
                                if (!"Rejected".equals(existingApp.status)) {
                                    isTaken = true;
                                    break;
                                }
                            }
                        }

                        if (isTaken) {
                            btnConfirmAppointment.setEnabled(true);
                            showErrorPopup("Slot Already Booked",
                                    "The time " + selectedSlot + " on " + selectedDate + " is already taken. Please choose another slot.");
                        } else {
                            proceedWithBooking();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        btnConfirmAppointment.setEnabled(true);
                    }
                });
    }

    private void proceedWithBooking() {
        String uid = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child(uid).child("name").get().addOnSuccessListener(dataSnapshot -> {
            String realName = dataSnapshot.exists() ? String.valueOf(dataSnapshot.getValue()) : "Customer";

            DatabaseReference appointmentsRef = mDatabase.child("Appointments");
            String appointmentId = appointmentsRef.push().getKey();

            Appointment appointment = new Appointment(
                    appointmentId, realName, serviceName, selectedDate, selectedSlot, "Pending", uid
            );

            if (appointmentId != null) {
                appointmentsRef.child(appointmentId).setValue(appointment)
                        .addOnSuccessListener(aVoid -> {
                            // Final Success Popup
                            new AlertDialog.Builder(this)
                                    .setTitle("Booking Confirmed!")
                                    .setMessage("Your request for " + serviceName + " has been submitted. Check notifications for admin approval.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        Intent intent = new Intent(this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .show();
                        })
                        .addOnFailureListener(e -> {
                            btnConfirmAppointment.setEnabled(true);
                            showErrorPopup("Database Error", e.getMessage());
                        });
            }
        });
    }

    private void showErrorPopup(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}