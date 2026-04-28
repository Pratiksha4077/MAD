package com.example.rolex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Appointment> appointmentList;
    private AppointmentAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference("Appointments");
        listView = findViewById(R.id.listAppointments);
        appointmentList = new ArrayList<>();
        adapter = new AppointmentAdapter();
        listView.setAdapter(adapter);

        // Admin Logout Logic
        findViewById(R.id.btnAdminLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        });

        fetchAppointments();
    }

    private void fetchAppointments() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Appointment app = ds.getValue(Appointment.class);
                    if (app != null) appointmentList.add(app);
                }
                adapter.notifyDataSetChanged();
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    class AppointmentAdapter extends BaseAdapter {
        @Override public int getCount() { return appointmentList.size(); }
        @Override public Object getItem(int i) { return appointmentList.get(i); }
        @Override public long getItemId(int i) { return i; }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(AdminActivity.this).inflate(R.layout.item_appointment, viewGroup, false);
            }

            Appointment app = appointmentList.get(i);
            ((TextView)view.findViewById(R.id.tvAdminCustomerName)).setText("Customer: " + app.userName);
            ((TextView)view.findViewById(R.id.tvAdminServiceName)).setText(app.serviceName);
            ((TextView)view.findViewById(R.id.tvAdminDateTime)).setText(app.date + " | " + app.time);
            ((TextView)view.findViewById(R.id.tvAdminStatus)).setText("Status: " + app.status);

            // Admin Actions
            view.findViewById(R.id.btnApprove).setOnClickListener(v -> updateStatus(app, "Approved"));
            view.findViewById(R.id.btnReject).setOnClickListener(v -> updateStatus(app, "Rejected"));

            return view;
        }

        private void updateStatus(Appointment app, String newStatus) {
            // Update status
            mDatabase.child(app.appointmentId).child("status").setValue(newStatus);

            // Create notification for user
            String time = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
            NotificationModel notification = new NotificationModel("Booking for " + app.serviceName + " was " + newStatus, time, app.serviceName);

            FirebaseDatabase.getInstance().getReference("Notifications")
                    .child(app.userId).push().setValue(notification)
                    .addOnSuccessListener(aVoid -> Toast.makeText(AdminActivity.this, "User Notified: " + newStatus, Toast.LENGTH_SHORT).show());
        }
    }
}