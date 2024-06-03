package com.example.foodorderingapp.ui.activityfragment.customer.refund;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ActivityMediaFullScreenBinding;

public class MediaFullScreenActivity extends AppCompatActivity {

    private TextView screenName;
    private FrameLayout btnBack;
    private boolean isImage = true;
    private String mediaUrl = "";
    private ActivityMediaFullScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_full_screen);
        binding.setLifecycleOwner(this);

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

        binding.fullScreenVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        // display image or video
        if(isImage){
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
                binding.fullScreenVideoView.start();
            }
        }
    }
}
