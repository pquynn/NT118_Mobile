package com.example.foodorderingapp.ui.adapter;//package com.example.foodorderingapp.UI.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.databinding.BottomsheetEditCartBinding;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;
//import com.example.foodorderingapp.databinding.ViewholderCartBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private CartViewModel cartViewModel;
    private Map<String, OrderItem> orderItemMap;
    private List<Product> productList;
//    private OnItemClickListener listener;
    private Context context;
    private MainActivity mainActivity;
    private boolean isDialogOpen = false;

    private Product tempProduct;
    private int tempOrderItemPrice = 0, oldSizePrice = 0, newSizePrice = 0;
    private String tempSize = "";
    private List<Topping> toppings;
    private List<String> toppingNames, toppingSelected, tempCheckedTopping;
    private int productQuantity;
    public CartAdapter(Map<String, OrderItem> orderItemMap,
                       List<Product> productList,
                       CartViewModel cartViewModel,
                       Context context,
                       MainActivity mainActivity){
        this.orderItemMap = orderItemMap;
        this.cartViewModel = cartViewModel;
        this.productList = productList;
//        this.listener = listener;
        this.context = context;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCartBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_cart, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Map<String, OrderItem> orderItemMap = cartViewModel.getOrderMutableLiveData().getValue().getOrderItem();
        List<String> keys = new ArrayList<>(orderItemMap.keySet());
        String key = keys.get(position);
        OrderItem orderItem = orderItemMap.get(key);
        // check if product is available
        //1. get size of order item
        String size = (orderItem.getSize() == null || orderItem.getSize().isEmpty()) ? "Mặc định" : orderItem.getSize();
        String productId = orderItem.getIdProduct();

        //2. get product
        Product product = null;
        for (Product p : productList) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        holder.bind(orderItem, product);

        //3. check --> if unavailable --> disable button checkout
        int grey = ContextCompat.getColor(context, R.color.transparent50_gray);
        int dark = ContextCompat.getColor(context, R.color.dark_text);

        holder.binding.btnEdit.setVisibility(View.VISIBLE);
        holder.binding.clEditQuantity.setVisibility(View.VISIBLE);
        holder.binding.clOutOfStock.setVisibility(View.INVISIBLE);
        holder.binding.btnDelete.setVisibility(View.INVISIBLE);
        holder.binding.txtProductCost.setTextColor(dark);
        holder.binding.txtProductName.setTextColor(dark);
        holder.binding.txtProductSize.setTextColor(dark);

        if (product != null) {
            //enable confirm button in dialog
            holder.binding.clSizeOutOfStock.setVisibility(View.INVISIBLE);
            holder.bindingBottomSheet.btnConfirm.setEnabled(true);
            holder.bindingBottomSheet.txtOutOfStock.setVisibility(View.GONE);

            productQuantity = product.getProductSize().get(size).getOrDefault("QUANTITY", 0);
            if (product == null || productQuantity == 0) {
                boolean allSizesOutOfStock = true;

                if(!size.equals("Mặc định")) {
                    // check other sizes' quantities
                    for (Map.Entry<String, Map<String, Integer>> entry : product.getProductSize().entrySet()) {
                        if (!entry.getKey().equals(size)) { // Exclude the current order item's size
                            int otherSizeQuantity = entry.getValue().getOrDefault("QUANTITY", 0);
                            if (otherSizeQuantity > 0) {
                                allSizesOutOfStock = false;
                                break;
                            }
                        }
                    }
                }

                //update live data
                cartViewModel.updateTotalPrice(orderItem.getQuantity() * orderItem.getPrice());
                orderItem.setQuantity(0);
                cartViewModel.setIsValidCheckout(false);

                // update ui when product cart is unavailable--> change viewholder UI
                if(allSizesOutOfStock) {
                    // if all product sizes are out of stock
                    holder.binding.btnEdit.setVisibility(View.INVISIBLE);
                    holder.binding.clEditQuantity.setVisibility(View.INVISIBLE);
                    holder.binding.clSizeOutOfStock.setVisibility(View.INVISIBLE);
                    holder.binding.clOutOfStock.setVisibility(View.VISIBLE);
                    holder.binding.btnDelete.setVisibility(View.VISIBLE);
                    holder.binding.txtProductCost.setTextColor(grey);
                    holder.binding.txtProductName.setTextColor(grey);
                    holder.binding.txtProductSize.setTextColor(grey);

                    //disable confirm button in dialog
                    holder.bindingBottomSheet.btnConfirm.setEnabled(false);
                    holder.bindingBottomSheet.txtOutOfStock.setVisibility(View.VISIBLE);
                }
                else{ // if just product cart size is out of stock
                    holder.binding.btnEdit.setVisibility(View.VISIBLE);
                    holder.binding.clEditQuantity.setVisibility(View.INVISIBLE);
                    holder.binding.clSizeOutOfStock.setVisibility(View.VISIBLE);
                }
            } else if (productQuantity < orderItem.getQuantity() && productQuantity > 0) {
                // if product quantity is less than order item quantity --> update order item
                holder.binding.clSizeOutOfStock.setVisibility(View.INVISIBLE);
                cartViewModel.setIsValidCheckout(true);
                orderItem.setQuantity(productQuantity);
                cartViewModel.updateOrderItemByOrderId(key);
            } else if (orderItem.getQuantity() == 0 && productQuantity > 0){
                holder.binding.clSizeOutOfStock.setVisibility(View.INVISIBLE);
                cartViewModel.setIsValidCheckout(true);
                cartViewModel.reloadData();
            }

        }
        //button delete click
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.deleteProductCart(key);
                cartViewModel.setIsValidCheckout(true);
                mainActivity.reloadBadge();
            }
        });


        //button decrease quanity
        holder.binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reload data to get realtime product quantity
                cartViewModel.reloadData();

                int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
                quantity -= 1;
                if(quantity > 0){
                    orderItem.setQuantity(quantity);
                    notifyDataSetChanged();
                    mainActivity.reloadBadge();
                    cartViewModel.onChangeQuantityButtonClick(key, -1 * orderItem.getPrice());
                }
                // show message to confirm delete product or not
                else {
                    showAlertDialog(v.getContext(), key);
                }
            }
        });


        //button increase quanity
        holder.binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.reloadData();
                if(productQuantity > 0){
                    int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
                    quantity += 1;
                    orderItem.setQuantity(quantity);
                    notifyDataSetChanged();
                    mainActivity.reloadBadge();
                    cartViewModel.onChangeQuantityButtonClick(key, 1 * orderItem.getPrice());
                }

            }
        });


        //button edit
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDialogOpen) {
                    //use reload data async to make bottom dialog wait for all data is uptodate
                    cartViewModel.reloadDataAsync(() -> {
                        // This code runs after reloadData completes
                        holder.showBottomSheetDialog(context, key, orderItem);
                    });
                }
            }
        });

    }

    // Function to show alert dialog when quantity == 0
    public void showAlertDialog(Context context, String orderItemId){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Xóa sản phẩm");
        alert.setMessage("Bạn muốn xóa sản phẩm này?");
        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cartViewModel.deleteProductCart(orderItemId);
            }
        });

        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public int getItemCount() {
        return orderItemMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderCartBinding binding;
        private Dialog dialog = new Dialog(context);
        private BottomsheetEditCartBinding bindingBottomSheet;
        private CartToppingAdapter cartToppingAdapter;
        private Product product;
        private OrderItem orderItem;
        public ViewHolder(@NonNull ViewholderCartBinding binding){
            super(binding.getRoot());
            //init cart view holder binding
            this.binding = binding;
            //init bottom sheet binding
            bindingBottomSheet = BottomsheetEditCartBinding.inflate(LayoutInflater.from(context));
            dialog.setContentView(bindingBottomSheet.getRoot());
            bindingBottomSheet.setLifecycleOwner(binding.getLifecycleOwner());
        }

        public BottomsheetEditCartBinding getBindingBottomSheet(){
            return bindingBottomSheet;
        }


        void bind(OrderItem orderItem, Product product){
            this.orderItem = orderItem;
            this.product = product;
            binding.setOrderItem(orderItem);
            bindingBottomSheet.setProduct(product);
            bindingBottomSheet.setCartVM(cartViewModel); // Set the ViewModel if needed
            bindingBottomSheet.setOrderItem(orderItem);
            binding.executePendingBindings();
        }

        public void showBottomSheetDialog(Context context, String key, OrderItem orderItem) {
            isDialogOpen = true; // Update dialog state

            this.orderItem = orderItem;

            // Initialize tempCheckedTopping here
            tempCheckedTopping = new ArrayList<>();

            // load UI
            loadBottomSheetUI(dialog);


            // Size Radio button check event listener
            if(orderItem.getSize() != null)
                tempSize = orderItem.getSize();
            tempOrderItemPrice = orderItem.getPrice();
            // Assuming bindingBottomSheet.radioGroup is your RadioGroup
            if(tempSize != null) {
                bindingBottomSheet.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // get old size price
                        if (tempProduct != null && tempProduct.getProductSize() != null) {
                            Map<String, Map<String, Integer>> size = tempProduct.getProductSize();
                            oldSizePrice = size.get(tempSize).get("PRICE");
                            // Find which radio button is checked
                            RadioButton checkedRadioButton = group.findViewById(checkedId);
                            tempSize = checkedRadioButton.getText().toString();
                            newSizePrice = tempProduct.getProductSize().get(tempSize).get("PRICE");
                            tempOrderItemPrice = tempOrderItemPrice + newSizePrice - oldSizePrice;
                            bindingBottomSheet.btnConfirm.setText(setPriceFormatted(tempOrderItemPrice));
                        }
                    }

                });
            }

            // Button confirm edit cart click event listenr
            bindingBottomSheet.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String note = bindingBottomSheet.txtNote.getText().toString();
                    cartViewModel.onConfrimButtonClick(key, tempProduct.getId(), tempSize, tempCheckedTopping, note, tempOrderItemPrice);
                }
            });

            // dismiss dialog
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    isDialogOpen = false; // Update dialog state when dismissed
                    tempSize = "";
                    toppingNames.clear();
                    tempCheckedTopping.clear();
                    tempOrderItemPrice = 0;
                    cartViewModel.reloadData();
                }
            });

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }

        // METHOD TO LOAD BOTTOM SHEET
        public void loadBottomSheetUI(Dialog dialog){
            // init collection
            toppings = cartViewModel.getToppings();
            toppingSelected = new ArrayList<>();
            toppingNames = new ArrayList<>();

            // init adapter
            cartToppingAdapter = new CartToppingAdapter(toppings, toppingNames, toppingSelected, cartViewModel, new CartToppingAdapter.OnCheckedChangeListener() {
                @Override
                public void onItemCheckedChanged(boolean isChecked, Topping topping) {
//                    Log.d("fixcart", "old tempCheckedTopping: " + tempCheckedTopping.toString());
                    if (isChecked) {
                        tempOrderItemPrice += topping.getPriceTopping();
                        tempCheckedTopping.add(topping.getNameTopping());
                    } else {
                        tempOrderItemPrice -= topping.getPriceTopping();
                        if (tempCheckedTopping != null)
                            tempCheckedTopping.remove(topping.getNameTopping());
                    }
//                    Log.d("fixcart", "new tempCheckedTopping: " + tempCheckedTopping.toString());
                    bindingBottomSheet.btnConfirm.setText(setPriceFormatted(tempOrderItemPrice));
                }
            });
            bindingBottomSheet.recyclerViewTopping.setLayoutManager(new LinearLayoutManager(dialog.getOwnerActivity()));
            bindingBottomSheet.recyclerViewTopping.setAdapter(cartToppingAdapter);

            //assign product to temp product for size and topping event listener
            if(product != null)
                tempProduct = product;

            // show topping area if not null
            if (product != null && product.getTopping() != null) {
                bindingBottomSheet.toppingArea.setVisibility(View.VISIBLE);
                toppingNames.clear();
                toppingNames.addAll(product.getTopping());
                toppingSelected.clear();
                toppingSelected.addAll(orderItem.getTopping());
                tempCheckedTopping.clear();
                tempCheckedTopping.addAll(orderItem.getTopping());
                cartToppingAdapter.notifyDataSetChanged();
            } else {

                bindingBottomSheet.toppingArea.setVisibility(View.GONE);
            }
            // show size area if not null
            if (product != null && product.getProductSize() != null) {
                // hide all radio button and just show if one is exist
                bindingBottomSheet.sizeArea.setVisibility(View.VISIBLE);
                bindingBottomSheet.rbLarge.setVisibility(View.GONE);
                bindingBottomSheet.rbMedium.setVisibility(View.GONE);
                bindingBottomSheet.rbSmall.setVisibility(View.GONE);
                bindingBottomSheet.tvBigPrice.setVisibility(View.GONE);
                bindingBottomSheet.tvMediumPrice.setVisibility(View.GONE);
                bindingBottomSheet.tvSmallPrice.setVisibility(View.GONE);
                for (Map.Entry<String, Map<String, Integer>> entry : product.getProductSize().entrySet()) {
                    String size = entry.getKey();
                    Map<String, Integer> details = entry.getValue();
                    Object priceObj = entry.getValue().get("PRICE");
                    Object quantityObj = entry.getValue().get("QUANTITY");
                    int quantity =convertObject(quantityObj);
                    int price = convertObject(priceObj);

                    switch (size) {
                        case "Lớn": {
                            setRadioButtonUI(size, price, quantity, bindingBottomSheet.tvBigPrice, bindingBottomSheet.rbLarge);
                            break;
                        }
                        case "Vừa": {
                            setRadioButtonUI(size, price, quantity, bindingBottomSheet.tvMediumPrice, bindingBottomSheet.rbMedium);
                            break;
                        }
                        case "Nhỏ": {
                            setRadioButtonUI(size, price, quantity, bindingBottomSheet.tvSmallPrice, bindingBottomSheet.rbSmall);
                            break;
                        }
                        case "Mặc định":{
                            bindingBottomSheet.sizeArea.setVisibility(View.GONE);
                        }
                    }
                }
            } else {
                bindingBottomSheet.sizeArea.setVisibility(View.GONE);
            }
        }
    }


    //method ot set price formatted
    public static String setPriceFormatted(int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        return formattedPrice;
    }

    //method to set ui for radio button by quantity
    public void setRadioButtonUI(String size, int price, int quantity, TextView textView, RadioButton radioButton){
        int grey = ContextCompat.getColor(context, R.color.transparent50_gray);
        int dark = ContextCompat.getColor(context, R.color.dark_text);
        radioButton.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(setPriceFormatted(price));
        if(quantity == 0){
//            // reset product cart if product is unavailable
//            if(viewModel.getProductCartSize() != null && viewModel.getProductCartSize().equals(size)){
//                OrderItem orderItem = viewModel.getProductCartLiveData().getValue();
//                orderItem.setQuantity(1);
//                orderItem.setPrice(orderItem.getPrice() - price);
//                orderItem.setSize("");
//            }
            textView.setTextColor(grey);
            radioButton.setTextColor(grey);
            radioButton.setEnabled(false);
        }
        else{
            textView.setTextColor(dark);
            radioButton.setTextColor(dark);
            radioButton.setEnabled(true);
        }
    }

    //method to conver object has class name Long into int
    public int convertObject(Object object){
        int intObj = 0;
        if (object instanceof Long) {
            intObj = ((Long) object).intValue();
        } else if (object instanceof Integer) {
            intObj = (Integer) object;
        }
        return intObj;
    }
}

