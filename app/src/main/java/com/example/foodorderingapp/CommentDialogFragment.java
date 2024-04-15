package com.example.foodorderingapp;


import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.adapter.CommentAdapter;
import com.example.foodorderingapp.adapter.NotificationAdapter;
import com.example.foodorderingapp.domain.CommentDomain;
import com.example.foodorderingapp.domain.Notification;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommentDialogFragment extends BottomSheetDialogFragment {

    private List<CommentDomain> listComment;
    public CommentDialogFragment(List<CommentDomain> listComment) {
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

        return bottomSheetDialog;
    }
}

