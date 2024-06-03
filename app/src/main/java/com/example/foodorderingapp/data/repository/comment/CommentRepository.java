package com.example.foodorderingapp.data.repository.comment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodorderingapp.data.model.entity.Comment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class CommentRepository implements ICommentRepository{
    private final FirebaseFirestore db;

    public CommentRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getAllComment(CommentListListener callBack) {
        db.collection("COMMENT")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Comment> commentList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment comment = document.toObject(Comment.class);
                                commentList.add(comment);
                                callBack.onCommentList(commentList);
                            }
                        } else {
                            callBack.onError(String.valueOf(Log.d("Firestore", "Error getting documents: ", task.getException())));
                        }
                    }
                });
    }
}
