package com.example.proiectfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proiectfinal.R;
import com.example.proiectfinal.activity.EventDetailActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> nImageNames = new ArrayList<>();
    private ArrayList<String> nImages = new ArrayList<>();
    private Context mcontext;
    private String facebookName, facebookProfileImage;

    public RecyclerViewAdapter(Context mcontext, ArrayList<String> nImageNames, ArrayList<String> nImages, String facebookName, String facebookProfileImage) {
        this.nImageNames = nImageNames;
        this.nImages = nImages;
        this.mcontext = mcontext;
        this.facebookName = facebookName;
        this.facebookProfileImage = facebookProfileImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(mcontext)
                .asBitmap()
                .load(nImages.get(position))
                .into(holder.image);

        holder.imageName.setText(nImageNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(mcontext, EventDetailActivity.class);
                intent.putExtra("detailTitle", nImageNames.get(position));
                intent.putExtra("detailImage", nImages.get(position));

                intent.putExtra("name", facebookName);
                intent.putExtra("image", facebookProfileImage);

                mcontext.startActivity(intent);

                Toast.makeText(mcontext, nImageNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            imageName = itemView.findViewById(R.id.event_title);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
