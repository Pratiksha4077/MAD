package com.example.rolex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private ImageButton btnLogout, btnNotification;
    private TextView tvWelcome;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnLogout = findViewById(R.id.btnLogout);
        btnNotification = findViewById(R.id.btnNotification);
        tvWelcome = findViewById(R.id.tvWelcome);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Inside onCreate
        Button btnClaim = findViewById(R.id.btnClaimOffer);
        btnClaim.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ServiceListActivity.class);
            intent.putExtra("SERVICE_NAME", "Facial");
            startActivity(intent);
        });

        // Dynamic Name Logic
        String name = getIntent().getStringExtra("USER_NAME");
        if (name != null && !name.isEmpty()) {
            tvWelcome.setText(name);
        } else {
            fetchUserNameFromDB();
        }

        listenForBookingUpdates();

        // Default Fragment
        loadFragment(new WomenFragment());

        // Navigation Menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_women) {
                selectedFragment = new WomenFragment();
            } else if (id == R.id.nav_men) {
                selectedFragment = new MenFragment();
            } else if (id == R.id.nav_products) {
                selectedFragment = new ProductFragment();
            }

            return loadFragment(selectedFragment);
        });

        // Open Notification History
        btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
        });

        // Logout Logic
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void fetchUserNameFromDB() {
        if (mAuth.getCurrentUser() == null) return;
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(uid).child("name").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                tvWelcome.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }

    private void listenForBookingUpdates() {
        if (mAuth.getCurrentUser() == null) return;
        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabase.child("Appointments").orderByChild("userId").equalTo(currentUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                            String status = appSnapshot.child("status").getValue(String.class);
                            String service = appSnapshot.child("serviceName").getValue(String.class);

                            if ("Approved".equals(status) || "Rejected".equals(status)) {
                                triggerSystemNotification(service, status);
                            }
                        }
                    }
                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private void triggerSystemNotification(String service, String status) {
        String channelId = "rolex_updates";
        android.app.NotificationManager manager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(channelId, "Rolex Salon", android.app.NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        androidx.core.app.NotificationCompat.Builder builder = new androidx.core.app.NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Appointment Update")
                .setContentText("Your " + service + " booking was " + status)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}