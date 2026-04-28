package com.example.rolex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.Collections;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private LinearLayout emptyState;
    private NotificationAdapter adapter;
    private ArrayList<NotificationModel> notificationList;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize UI
        rvNotifications = findViewById(R.id.rvNotifications);
        emptyState = findViewById(R.id.emptyState);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        rvNotifications.setAdapter(adapter);

        // Firebase path: Notifications -> UserID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference("Notifications").child(uid);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    NotificationModel model = ds.getValue(NotificationModel.class);
                    if (model != null) {
                        notificationList.add(model);
                    }
                }

                // Show newest notifications at the top
                Collections.reverse(notificationList);

                // Toggle Empty State visibility
                if (notificationList.isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                    rvNotifications.setVisibility(View.GONE);
                } else {
                    emptyState.setVisibility(View.GONE);
                    rvNotifications.setVisibility(View.VISIBLE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // --- Modern RecyclerView Adapter ---
    class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        private ArrayList<NotificationModel> list;

        public NotificationAdapter(ArrayList<NotificationModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NotificationModel model = list.get(position);
            holder.tvMessage.setText(model.message);
            holder.tvTime.setText(model.timestamp);

            // Dynamic Color Coding: Green for Approved, Red for Rejected
            if (model.message.toLowerCase().contains("approved")) {
                holder.viewStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#4CAF50")));
            } else if (model.message.toLowerCase().contains("rejected")) {
                holder.viewStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FF5252")));
            } else {
                holder.viewStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#D4AF37")));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvMessage, tvTime;
            View viewStatus;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMessage = itemView.findViewById(R.id.tvNotifMessage);
                tvTime = itemView.findViewById(R.id.tvNotifTime);
                viewStatus = itemView.findViewById(R.id.viewStatusColor);
            }
        }
    }
}