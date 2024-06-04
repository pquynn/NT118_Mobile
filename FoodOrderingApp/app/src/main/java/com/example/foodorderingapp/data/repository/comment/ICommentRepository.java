package com.example.foodorderingapp.data.repository.comment;

import com.example.foodorderingapp.data.model.entity.Comment;
import java.util.List;

public interface ICommentRepository {
    interface CommentListListener{
        void onCommentList(List<Comment> comments);
        void onError(String errorMessage);
    }
}
