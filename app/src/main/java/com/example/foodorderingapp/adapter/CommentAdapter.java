package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.CommentDialogFragment;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.CommentDomain;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<CommentDomain> listComment;

    public CommentAdapter(List<CommentDomain> commentList) {
        this.listComment = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottomsheet_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        CommentDomain comment = listComment.get(position);

        holder.txtName.setText(comment.getName());
        holder.txtContent.setText(comment.getContent());
        holder.txtDate.setText(comment.getDate().toString());
        holder.ratingBar.setRating(comment.getRatingBar());
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtContent, txtDate;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            txtContent = itemView.findViewById(R.id.txt_comment);
            txtDate = itemView.findViewById(R.id.txt_date);

        }
    }
}
