package com.example.foodorderingapp.ui.activityfragment.customer.productdetail;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.CommentAdapter;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;


public class CommentDialogFragment extends BottomSheetDialogFragment {

    private List<Comment> listComment;
    public CommentDialogFragment(List<Comment> listComment) {
        this.listComment = listComment;
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        // Reset the dialog state here if needed
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true); // Allows the dialog to be dismissed by clicking outside
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_comment, null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rcvComment = view.findViewById(R.id.rcv_comment);

        if (listComment != null && !listComment.isEmpty()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvComment.setLayoutManager(linearLayoutManager);

            CommentAdapter adapter = new CommentAdapter(listComment);
            rcvComment.setAdapter(adapter);

            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            rcvComment.addItemDecoration(itemDecoration);
        } else {
            Log.d("get in dialog", "get in");
            // If listComment is null or empty, hide the RecyclerView
            rcvComment.setVisibility(View.GONE);

            // Optionally, show a message indicating there are no comments
            TextView noCommentsTextView = view.findViewById(R.id.no_comments_text_view);
            noCommentsTextView.setVisibility(View.VISIBLE);
        }

        bottomSheetDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        return bottomSheetDialog;
    }

}

