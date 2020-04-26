package com.example.proiectfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectfinal.R;
import com.example.proiectfinal.model.Event;

import java.util.List;

public class EventAdapter extends
        RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> mEvents;

    public EventAdapter(List<Event> events) {
        mEvents = events;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

//         Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_event, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Event event = mEvents.get(position);

        // Set item views based on your views and data model
        TextView titleView = viewHolder.titleTextView;
        titleView.setText(event.getEventTitle());

        TextView descriptionView = viewHolder.descriptionTextView;
        descriptionView.setText(event.getEventTitle());

        TextView dateView = viewHolder.dateTextView;
        dateView.setText(event.getEventTitle());
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView dateTextView;
        public Button detailsButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.event_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.event_description);
            dateTextView = (TextView) itemView.findViewById(R.id.event_date);
            detailsButton = (Button) itemView.findViewById(R.id.details_button);
        }
    }
}