package com.example.foodorderingapp.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.databinding.BottomsheetRefundReasonBinding;
import com.example.foodorderingapp.databinding.ViewholderRefundRequestBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefundRequestAdapter extends RecyclerView.Adapter<RefundRequestAdapter.ViewHolder> {
    private Map<String, OrderItem> orderItemMap;
    private Context context;
    private String reason = "";
    private boolean isDialogOpen = false, isChecked = false;
    private Map<Integer, Uri> imageUris = new HashMap<>();
    private Map<Integer, Uri> videoUris = new HashMap<>();
    private List<String> selectedOrderItemId;
    private List<Integer> selectedQuantity;
    private int currentPosition = -1;
    private ActivityResultLauncher<Intent> selectImgLauncher;
    private ActivityResultLauncher<Intent> selectVideoLauncher;

    // using map to store reasons, edit text values to avoid missing when choose image and video because i don't know the state
    private Map<String, String> reasons = new HashMap<>();
    private Map<String, String> editTextValues = new HashMap<>();

    public RefundRequestAdapter(Map<String, OrderItem> orderItemMap,
                                List<String> selectedOrderItemId,
                                List<Integer> selectedQuantity,
                                Context context,
                                ActivityResultLauncher<Intent> selectImgLauncher,
                                ActivityResultLauncher<Intent> selectVideoLauncher) {
        this.orderItemMap = orderItemMap;
        this.selectedOrderItemId = selectedOrderItemId;
        this.selectedQuantity = selectedQuantity;
        this.context = context;
        this.selectImgLauncher = selectImgLauncher;
        this.selectVideoLauncher = selectVideoLauncher;
    }

    @NonNull
    @Override
    public RefundRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderRefundRequestBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_refund_request, parent, false);
        return new RefundRequestAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RefundRequestAdapter.ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(orderItemMap.keySet());
        String key = keys.get(position);
        OrderItem orderItem = orderItemMap.get(key);
        // get quantity
        int quantity = selectedQuantity.get(selectedOrderItemId.indexOf(key));
        holder.binding.txtQuantity.setText(String.valueOf(quantity));

        holder.bind(orderItem);

        // Restore reason text from the map
        String reason = reasons.get(key);
        if (reason != null) {
            holder.binding.txtReason.setText(reason);
        } else {
            holder.binding.txtReason.setText("Chọn lý do");
        }

        // Restore EditText value from the map
        String editTextValue = editTextValues.get(key);
        if (editTextValue != null) {
            holder.binding.txtDescription.setText(editTextValue);
        } else {
            holder.binding.txtDescription.setText("");
        }

        // Remove any existing TextWatcher to avoid multiple instances
        holder.binding.txtDescription.removeTextChangedListener(holder.textWatcher);

        // Create a new TextWatcher
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                editTextValues.put(key, s.toString());
            }
        };

        // Add the TextWatcher to the EditText
        holder.binding.txtDescription.addTextChangedListener(holder.textWatcher);

        // button select refund reason click event
        holder.binding.btnSelectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDialogOpen) {
                    showDialog(view.getContext(), holder.binding.txtReason, key);
                }
            }
        });

        // button select image click event
        holder.binding.btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = holder.getAdapterPosition();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                selectImgLauncher.launch(intent);
            }
        });

        // button select video click event
        holder.binding.btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = holder.getAdapterPosition();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                selectVideoLauncher.launch(intent);
            }
        });

        // set image and video if exist
        Uri imageUri = imageUris.get(position);
        if (imageUri != null) {
            holder.imageUri = imageUri;
            holder.binding.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(imageUri).into(holder.binding.imageView);
        } else {
            holder.binding.imageView.setVisibility(View.GONE);
        }

        holder.binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Uri videoUri = videoUris.get(position);
        if (videoUri != null) {
            holder.videoUri = videoUri;
            holder.binding.flVideo.setVisibility(View.VISIBLE);
            holder.binding.videoView.setVideoURI(videoUri);
            holder.binding.videoView.start();
        } else {
            holder.binding.flVideo.setVisibility(View.GONE);
        }
    }

    // To prevent memory leaks or unwanted behaviors, it’s good practice to clean up any listeners when a view is recycled.
    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.binding.txtDescription.setText("");
        holder.binding.txtDescription.removeTextChangedListener(holder.textWatcher);
    }

    // Function to show bottom dialog when button is clicked
    public void showDialog(Context context, TextView txtReason, String key) {
        isDialogOpen = true; // Update dialog state
        Dialog dialog = new Dialog(context); // Corrected line
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        BottomsheetRefundReasonBinding bindingBottomSheet = BottomsheetRefundReasonBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(bindingBottomSheet.getRoot());
        bindingBottomSheet.setLifecycleOwner(bindingBottomSheet.getLifecycleOwner());

        bindingBottomSheet.setReason(txtReason.getText().toString());
        reason = "";
        isChecked = false;
        // You can add logic for handling RadioButton clicks if needed
        bindingBottomSheet.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                reason = checkedRadioButton.getText().toString();
                isChecked = true;
            }

        });

        bindingBottomSheet.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    txtReason.setText(reason);
                    reasons.put(key, reason); // Save the reason to the map
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Bạn chưa chọn lý do!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isDialogOpen = false; // Update dialog state when dismissed
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void setImageUri(int position, Uri uri) {
        imageUris.put(position, uri);
        notifyItemChanged(position);
    }

    public void setVideoUri(int position, Uri uri) {
        videoUris.put(position, uri);
        notifyItemChanged(position);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getItemCount() {
        return orderItemMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderRefundRequestBinding binding;
        private Uri imageUri, videoUri;
        private TextWatcher textWatcher;

        public ViewHolder(@NonNull ViewholderRefundRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewholderRefundRequestBinding getBinding() {
            return binding;
        }

        public Uri getImageUri() {
            return imageUri;
        }

        public Uri getVideoUri() {
            return videoUri;
        }

        void bind(OrderItem orderItem) {
            binding.setOrderItem(orderItem);
            binding.executePendingBindings();
        }
    }
}
