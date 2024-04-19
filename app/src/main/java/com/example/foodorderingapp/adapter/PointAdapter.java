package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.PointDomain;

import java.util.ArrayList;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {
    ArrayList<PointDomain> pointList;

    public PointAdapter (ArrayList<PointDomain> pointList){
        this.pointList = pointList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pointView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_point, parent, false);
        return new ViewHolder(pointView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.point.setText(String.valueOf(pointList.get(position).getPoint())); //int->string
        holder.point_date.setText(pointList.get(position).getPoint_date());
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView point, point_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            point = itemView.findViewById(R.id.txt_point);
            point_date = itemView.findViewById((R.id.txt_point_date));
        }
    }
}
