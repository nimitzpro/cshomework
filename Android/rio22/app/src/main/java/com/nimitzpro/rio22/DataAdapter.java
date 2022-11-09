package com.nimitzpro.rio22;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{

    private Context context;
    private int rowID;
    private ArrayList<Team> teams;


    public DataAdapter(Context context, int rowID, ArrayList<Team> teams) {
        this.context = context;
        this.rowID = rowID;
        this.teams = teams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout and return the tree view
        View v = LayoutInflater.from(this.context).inflate(this.rowID, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(teams.get(position).getName());
        holder.region.setText(teams.get(position).getRegion());
        String team_image = teams.get(position).getImage();
        Integer id = context.getResources().getIdentifier(team_image, "drawable", context.getPackageName());
        holder.icon.setImageResource(id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to another activity
                Intent intent = new Intent(context, TeamPage.class);
                Team team = teams.get(holder.getAdapterPosition());
                intent.putExtra("team_name", team);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView region;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.region = itemView.findViewById(R.id.region);
            this.icon = itemView.findViewById(R.id.imageView);
        }
    }
}
