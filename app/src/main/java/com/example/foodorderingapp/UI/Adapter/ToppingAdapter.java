package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Topping;

import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ViewHolder> {

    private Context context;
    private List<Topping> nList;

    public ToppingAdapter(Context mcontext,List<Topping> list){
        this.context = mcontext;
        this.nList = list;
    }

    @NonNull
    @Override
    public ToppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_topping_productdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingAdapter.ViewHolder holder, int position) {
        Topping toppingSizeDomain = nList.get(position);
        if (toppingSizeDomain == null)
            return;

        holder.txtName.setText(toppingSizeDomain.getNameTopping());
        holder.txtPrice.setText(toppingSizeDomain.getPriceTopping());
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_nameTopping);
            txtPrice = itemView.findViewById(R.id.txt_priceTopping);
        }
    }
}
