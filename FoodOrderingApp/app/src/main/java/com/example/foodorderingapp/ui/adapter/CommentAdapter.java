package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> listComment;

    public CommentAdapter(List<Comment> commentList) {
        this.listComment = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = listComment.get(position);

        holder.txtName.setText(comment.getNameUser());
        holder.txtContent.setText(comment.getContent());

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault());
        Date date = comment.getDate();
        String formattedDate = dateFormat.format(date);
        holder.txtDate.setText(formattedDate);


        holder.ratingBar.setRating(comment.getRatingBar());
        holder.ratingBar.setIsIndicator(true);// hiển thị chế độ xem
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
