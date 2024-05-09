package com.example.foodorderingapp.UI.Activity_Fragment.Customer.ProductDetail;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.Entity.Comment;
import com.example.foodorderingapp.UI.Adapter.CommentAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;


public class CommentDialogFragment extends BottomSheetDialogFragment {

    private List<Comment> listComment;
    public CommentDialogFragment(List<Comment> listComment) {
        this.listComment = listComment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_comment, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView rcvComment = view.findViewById(R.id.rcv_comment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvComment.setLayoutManager(linearLayoutManager);

        CommentAdapter adapter = new CommentAdapter(listComment);
        rcvComment.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvComment.addItemDecoration(itemDecoration);

        bottomSheetDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        return bottomSheetDialog;
    }
}

