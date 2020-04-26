package com.example.proiectfinal.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectfinal.R;

public class UserAdapter extends
        RecyclerView.Adapter<UserAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

//            titleTextView = (TextView) itemView.findViewById(R.id.event_title);
//            descriptionTextView = (TextView) itemView.findViewById(R.id.event_description);
//            dateTextView = (TextView) itemView.findViewById(R.id.event_date);
//            detailsButton = (Button) itemView.findViewById(R.id.details_button);
        }
    }
}
