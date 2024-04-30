package com.example.foodorderingapp.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.Data.Model.Entity.OrderItem;
import com.example.foodorderingapp.Data.Model.Entity.Refund;
import com.example.foodorderingapp.Data.Model.Entity.RefundItem;
import com.example.foodorderingapp.Data.Repository.Refund.IRefundRepository;
import com.example.foodorderingapp.Data.Repository.Refund.RefundRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Activity_Fragment.Customer.MainActivity;
import com.example.foodorderingapp.UI.Adapter.RefundRequestAdapter;
import com.example.foodorderingapp.Data.Model.OrderDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class uploadImageActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private Button btnSelectVideo;
    private TextView screenName;
    private Button btnUpload;
    private Button btnSelectImg;
    ImageView imageView;
    VideoView videoView;
    StorageReference storageReference;
    LinearProgressIndicator progressIndicator;
    Uri image;
    Uri video;

    RefundRepository refundRepository;

    private final ActivityResultLauncher<Intent> selectImgLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    btnUpload.setEnabled(true);
                    image = result.getData().getData();
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(image).into(imageView);
                }
            } else {
                Toast.makeText(uploadImageActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private final ActivityResultLauncher<Intent> selectVideoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    btnUpload.setEnabled(true);
                    video = result.getData().getData();
//                    Glide.with(getApplicationContext()).load(video).into(videoView);
                    if (video != null) {
                        btnUpload.setEnabled(true);
                        videoView.setVideoURI(video);
                        videoView.setVisibility(View.VISIBLE);
                        videoView.start(); // Start playing the video
                }
            } else {
                Toast.makeText(uploadImageActivity.this, "Please select an video", Toast.LENGTH_SHORT).show();
            }
        }
    }}
    );

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Yêu cầu hoàn tiền");

        imageView = findViewById(R.id.imageView1);
        videoView = findViewById(R.id.videoView);
        btnUpload = findViewById(R.id.btn_send);

          videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        FirebaseApp.initializeApp(uploadImageActivity.this);
        storageReference = FirebaseStorage.getInstance().getReference();


        progressIndicator = findViewById(R.id.progress);

        // set button back click event
        btnSelectImg = findViewById(R.id.btn_select_img);
        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                selectImgLauncher.launch(intent);
            }
        });

        btnSelectVideo = findViewById(R.id.btn_select_video);
        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                selectVideoLauncher.launch(intent);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null && video != null) {
                    uploadMedia(image);
                    uploadMedia(video);
                } else {
                    Toast.makeText(uploadImageActivity.this, "Please select both an image and a video", Toast.LENGTH_SHORT).show();
                }
            }
        });





        refundRepository = new RefundRepository();

        refundRepository.getRefundByOrderId("3", new IRefundRepository.RefundCallback() {
            @Override
            public void onRefundLoaded(Refund refund) {

//                Glide.with(uploadImageActivity.this)
//                        .load(refund.getRefundItemMap().get("5").getProofImage())
//                        .into(imageView);
                Uri uriVideo = Uri.parse(refund.getRefundItemMap().get("5").getProofVideo());

                videoView.setVideoURI(uriVideo);
                videoView.start();

            }

            @Override
            public void onError(String errorMessage) {

            }
        });


    }

//    private void displayImage(String imageUrl) {
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//            imageView.setVisibility(View.VISIBLE);
//            Glide.with(uploadImageActivity.this)
//                    .load(refundItemMap.get("5").getProofImage())
//                    .into(imageView);
//        }
//    }

//    private void displayVideo(String videoUrl) {
//        if (videoUrl != null && !videoUrl.isEmpty()) {
//            videoView.setVisibility(View.VISIBLE);
//            videoView.setVideoURI(Uri.parse(videoUrl));
//            videoView.start();
//        }
//    }




    private void uploadMedia(Uri file) {
        String folderName;
        if (file.getPath().contains("video")) {
            folderName = "refund_video";
        } else {
            folderName = "refund_image";
        }

        StorageReference ref = storageReference.child(folderName + "/" + UUID.randomUUID().toString());
        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadImageActivity.this, "Media Uploaded!!", Toast.LENGTH_SHORT).show();
                // Get the download URL of the uploaded image
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();
                        Log.d("FirestoreOrderRepository", "Media download URL: " + downloadUrl);
                        // Now you can use the downloadUrl as needed (e.g., save it to a database, display it in an ImageView, etc.)
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadImageActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                progressIndicator.setMax(Math.toIntExact(taskSnapshot.getTotalByteCount()));
//                progressIndicator.setProgress(Math.toIntExact(taskSnapshot.getBytesTransferred()));
            }
        });
    }
    }


