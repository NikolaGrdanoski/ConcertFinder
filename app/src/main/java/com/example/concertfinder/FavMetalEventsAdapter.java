package com.example.concertfinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavMetalEventsAdapter extends RecyclerView.Adapter<FavMetalEventsAdapter.ViewHolder> {
    Context context;
    ArrayList<Concerts> concertsArrayList;
    String username;

    public FavMetalEventsAdapter(Context context, ArrayList<Concerts> concertsArrayList, String username) {
        this.context = context;
        this.concertsArrayList = concertsArrayList;
        this.username = username;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button ctxtname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ctxtname = (Button) itemView.findViewById(R.id.ctxtname);
            ctxtname.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();

            ctxtname = (Button) itemView.findViewById(R.id.ctxtname);

            Intent intent = new Intent(context, HeavyMetalEvent.class);
            intent.putExtra("cName", concertsArrayList.get(p).getName());
            intent.putExtra("Username", username);
            context.startActivity(intent);
        }
    }

    @NonNull
    public FavMetalEventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_metal_events_single_item, viewGroup, false);
        return new FavMetalEventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMetalEventsAdapter.ViewHolder holder, int p) {
        Concerts concerts = concertsArrayList.get(p);
        holder.ctxtname.setText(concerts.getName());
    }

    public int getItemCount() { return concertsArrayList.size(); }
}
