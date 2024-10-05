package com.example.morgue_module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PendingOrdersFragment extends Fragment {
    RecyclerView rvPendings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pending_orders, container, false);
        rvPendings = view.findViewById(R.id.rvPendings);
        List<String> pendingOrders = new ArrayList<>();
        pendingOrders.add("Order 1");
        pendingOrders.add("Order 2");
        pendingOrders.add("Order 3");
        rvPendings.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PendingOrdersAdapter adapter = new PendingOrdersAdapter(pendingOrders,view.getContext());
        rvPendings.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Prevent full swipe, reveal the buttons without removing the item
                adapter.showButtons(viewHolder.getAdapterPosition());
                adapter.notifyItemChanged(viewHolder.getAdapterPosition()); // Reset the swipe state
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //adapter.hideButtons(viewHolder.getAdapterPosition());
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                // Only allow partial swipe to reveal buttons (e.g., 0.3f means 30% of the item width)
                return 0.3f;
            }
            /*
            @Override
            public float getSwipeEscapeVelocity(float defaultValue) {
                return super.getSwipeEscapeVelocity(defaultValue) * 2; // Slow down the swipe speed
            }

            @Override
            public float getSwipeVelocityThreshold(float defaultValue) {
                return super.getSwipeVelocityThreshold(defaultValue) * 2; // Slow down the swipe release velocity
            }*/
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvPendings);
        // Attach swipe actions
        return  view;

    }
}