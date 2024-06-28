package com.example.javajoyadmin.ui.activityfragment.admin.refund;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.javajoyadmin.R;
import com.example.javajoyadmin.databinding.ActivityMediaFullScreenBinding;

public class MediaFullScreenActivity extends AppCompatActivity {

    private TextView screenName;
    private FrameLayout btnBack;
    private boolean isImage = true;
    private String mediaUrl = "";
    private ActivityMediaFullScreenBinding binding;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_full_screen);
        binding.setLifecycleOwner(this);

        // Create AlertDialog with ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(true); // Prevents dialog from being dismissed
        progressDialog = builder.create();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // get data through intent
        if(getIntent().getExtras() != null){
            isImage = getIntent().getExtras().getBoolean("isImage");
            mediaUrl = getIntent().getExtras().getString("mediaUrl");
        }

        progressDialog.show();

        binding.fullScreenVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        // display image or video
        if(isImage){
            progressDialog.dismiss();
            binding.fullScreenImageView.setVisibility(View.VISIBLE);
            binding.fullScreenVideoView.setVisibility(View.GONE);
            if (mediaUrl != null) {
                Glide.with(binding.fullScreenImageView.getContext())
                        .load(mediaUrl)
                        .into(binding.fullScreenImageView);
            }
        }
        else{
            binding.fullScreenVideoView.setVisibility(View.VISIBLE);
            binding.fullScreenImageView.setVisibility(View.GONE);
            if (mediaUrl != null && !mediaUrl.isEmpty()) {
                Uri uri = Uri.parse(mediaUrl);
                binding.fullScreenVideoView.setVideoURI(uri);
                binding.fullScreenVideoView.setMediaController(new MediaController(this));
                binding.fullScreenVideoView.start();

                // Set VideoView listeners
                binding.fullScreenVideoView.setOnPreparedListener(mp -> {
                    // Dismiss progress dialog once video is ready to play
                    binding.fullScreenVideoView.start(); // Start the video
                    progressDialog.dismiss();
                });

                binding.fullScreenVideoView.setOnErrorListener((mp, what, extra) -> {
                    // Handle error and dismiss progress dialog
                    progressDialog.dismiss();
                    return false;
                });
            }
        }
    }
}
