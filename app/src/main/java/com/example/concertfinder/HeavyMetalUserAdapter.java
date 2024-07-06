package com.example.concertfinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HeavyMetalUserAdapter extends RecyclerView.Adapter<HeavyMetalUserAdapter.ViewHolder> {
    Context context;
    ArrayList<Concerts> concertsArrayList;
    String username;

    public HeavyMetalUserAdapter(Context context, ArrayList<Concerts> concertsArrayList, String username) {
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
    public HeavyMetalUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.heavy_metal_single_item, viewGroup, false);
        return new HeavyMetalUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeavyMetalUserAdapter.ViewHolder holder, int p) {
        Concerts concerts = concertsArrayList.get(p);
        holder.ctxtname.setText(concerts.getName());
    }

    public int getItemCount() { return concertsArrayList.size(); }
}
