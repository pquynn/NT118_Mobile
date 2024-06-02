package com.example.foodorderingapp.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.AM_AddAddressActivity;

import java.util.ArrayList;

public class AccountAddressAdapter extends RecyclerView.Adapter<AccountAddressAdapter.ViewHolder> {
    ArrayList<UserAddress> addresslist;
    UserInfoRepository repository_user = new UserInfoRepository();
    AlertDialog progressDialog;
    private static final int REQUEST_CHANGE_ADDRESS = 1;

    public AccountAddressAdapter(ArrayList<UserAddress> addresslist){
        this.addresslist = addresslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_address, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.address.setText(addresslist.get(position).getAllAddress());
        holder.recipientName.setText(addresslist.get(position).getRecipientName());
        holder.phone.setText(addresslist.get(position).getRecipientPhone());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent myIntent = new Intent(context, AM_AddAddressActivity.class);
//                myIntent.putExtra("user_id", addresslist.get(position).getIdUser());
                myIntent.putExtra("user_address_id", addresslist.get(position).getId());
                // Kiểm tra xem context có phải là một instance của Activity hay không
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.startActivityIfNeeded(myIntent, REQUEST_CHANGE_ADDRESS); // requestCode có thể là bất kỳ giá trị nào bạn muốn
                } else {
                    // Nếu context không phải là Activity, bạn có thể xử lý tình huống này, ví dụ:
                    context.startActivity(myIntent);
                }
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa địa chỉ này?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String addressId = addresslist.get(position).getId();
                                repository_user.deleteAdress(addressId, new UserInfoRepository.deleteAddressCallback() {
                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void deleteAddressSuccess() {
                                        Toast.makeText(context, "Xóa địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                                        addresslist.remove(position);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void deleteAddressError(Exception e) {
                                        Toast.makeText(context, "Xóa địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView recipientName;
        TextView phone;
        ImageView btn_edit;
        ImageView btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txt_full_address);
            recipientName = itemView.findViewById(R.id.txt_recipient_name);
            phone = itemView.findViewById(R.id.txt_recipient_phone);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
