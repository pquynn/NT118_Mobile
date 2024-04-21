package com.example.foodorderingapp.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;

import java.text.DecimalFormat;

public class AdminHomeAdapter extends RecyclerView.Adapter<AdminHomeAdapter.ViewHolder> {
    private View.OnClickListener onClickListener;
    private long totalRevenue, totalOrder, totalRefund;

    public AdminHomeAdapter(long revenue, long order, long refund) {
        this.totalRevenue = revenue;
        this.totalOrder = order;
        this.totalRefund = refund;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_admin_home, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtRevenue.setText(formarNumber(totalRevenue) + "Đ");
        holder.txtTotalOrder.setText(formarNumber(totalOrder) + "đơn");
        holder.txtRefund.setText(formarNumber(totalRefund) + "Đ");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRevenue, txtTotalOrder, txtRefund;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRevenue = itemView.findViewById(R.id.txtRevenue);
            txtTotalOrder = itemView.findViewById(R.id.txtTotalOrder);
            txtRefund = itemView.findViewById(R.id.txtRefund);
        }
    }

    public String formarNumber(long revenue) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(revenue).replace(",", ".");
    }
}