package com.example.foodorderingapp.ui.activityfragment.customer.productdetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Refund;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.databinding.ActivityProductDetailBinding;
import com.example.foodorderingapp.databinding.ActivityRefundViewBinding;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.adapter.CartToppingAdapter;
import com.example.foodorderingapp.ui.adapter.RefundItemAdapter;
import com.example.foodorderingapp.ui.adapter.ToppingAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.productdetail.ProductDetailViewModel;
import com.example.foodorderingapp.ui.viewmodel.customer.refund.RefundViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity implements ToppingAdapter.OnCheckedChangeListener {
    private ActivityProductDetailBinding binding;
    private CharSequence originalText;
    private int originalMaxLines;
    private ProductDetailViewModel viewModel;
    private String productId = "";
    private Context context;
    private ToppingAdapter adapter;
    private List<Topping> toppings;
    private int oldSizePrice = 0, newSizePrice = 0;
    private List<String> toppingNames, toppingSelected, tempCheckedTopping;
    private CommentDialogFragment commentDialogFragment = null;
    private MainActivity mainActivity;
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        binding.setLifecycleOwner(this);
        context = this;

        // get product_id through intent
        if (getIntent() != null && getIntent().hasExtra("productID")) {
            productId = getIntent().getStringExtra("productID");
        }

        // Lưu trạng thái ban đầu của nội dung
        originalMaxLines = binding.contentTextView.getMaxLines();
        originalText = binding.contentTextView.getText();

        binding.showMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị toàn bộ nội dung khi nhấn vào "Xem thêm"
                binding.contentTextView.setMaxLines(Integer.MAX_VALUE);
                binding.showMoreTextView.setVisibility(View.GONE);
                binding.showLessTextView.setVisibility(View.VISIBLE);
            }
        });

        binding.showLessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rút gọn nội dung lại khi nhấn vào "Rút gọn"
                binding.contentTextView.setMaxLines(originalMaxLines);
                binding.contentTextView.setText(originalText);
                binding.showMoreTextView.setVisibility(View.VISIBLE);
                binding.showLessTextView.setVisibility(View.GONE);
            }
        });

        // set button back click event
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // init viewmodel
        viewModel = new ProductDetailViewModel(productId, this, this);

        // init collection
        toppings = viewModel.getToppings();
        toppingSelected = new ArrayList<>();
        toppingNames = new ArrayList<>();

        // init adapter for topping
        adapter = new ToppingAdapter(toppings, toppingNames, toppingSelected, viewModel, this::onItemCheckedChanged);
        binding.recyclerViewTopping.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewTopping.setAdapter(adapter);

        // observe change in product live data
        viewModel.getProductLiveData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                binding.setProductVM(viewModel);

                if(product != null)
                // show topping area if not null
                if (product != null && product.getTopping() != null) {
                    binding.toppingArea.setVisibility(View.VISIBLE);
                    toppingNames.clear();
                    toppingNames.addAll(product.getTopping());
                    adapter.notifyDataSetChanged();
                } else {

                    binding.toppingArea.setVisibility(View.GONE);
                }
                // show size area if not null
                if (product != null && product.getProductSize() != null) {
                    // hide all radio button and just show if one is exist
                    binding.sizeArea.setVisibility(View.VISIBLE);
                    binding.rbLarge.setVisibility(View.GONE);
                    binding.rbMedium.setVisibility(View.GONE);
                    binding.rbSmall.setVisibility(View.GONE);
                    binding.tvBigPrice.setVisibility(View.GONE);
                    binding.tvMediumPrice.setVisibility(View.GONE);
                    binding.tvSmallPrice.setVisibility(View.GONE);

                    // init flag variable to check if product is out of stock or not
                    int numbOutOfStockProduct = 0, numbSize = product.getProductSize().size();
                    for (Map.Entry<String, Map<String, Integer>> entry : product.getProductSize().entrySet()) {
                        String size = entry.getKey();
                        Object priceObj = entry.getValue().get("PRICE");
                        Object quantityObj = entry.getValue().get("QUANTITY");
                        int quantity =convertObject(quantityObj);
                        int price = convertObject(priceObj);

                        //check quantity == 0
                        if(quantity == 0)
                            numbOutOfStockProduct++;

                        // set ui for each size radio button
                        switch (size) {
                            case "Lớn": {
                                setRadioButtonUI(size, price, quantity, binding.tvBigPrice, binding.rbLarge);
                                break;
                            }
                            case "Vừa": {
                                setRadioButtonUI(size, price, quantity, binding.tvMediumPrice, binding.rbMedium);
                                break;
                            }
                            case "Nhỏ": {
                                setRadioButtonUI(size, price, quantity, binding.tvSmallPrice, binding.rbSmall);
                                break;
                            }
                            case "Mặc định":{
                                binding.sizeArea.setVisibility(View.GONE);
                            }
                        }
                    }

                    if(numbOutOfStockProduct != numbSize){
                        if(viewModel.getProductCartLiveData().getValue() != null){
                            OrderItem orderItem = viewModel.getProductCartLiveData().getValue();
                            if(orderItem.getSize().isEmpty() && binding.sizeArea.getVisibility() == View.VISIBLE)
                                validateAddToCart(false);
                            else
                                validateAddToCart(true);
                            binding.outOfStockWarning.setVisibility(View.GONE);
                        }
                    }
                    else {
                        validateAddToCart(false);
                        binding.outOfStockWarning.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.sizeArea.setVisibility(View.GONE);
                }

                binding.swipeRefreshLayout.setRefreshing(false); // Stop the refreshing animation
            }

        });

        // observe change in product cart live data
        viewModel.getProductCartLiveData().observe(this, new Observer<OrderItem>() {
            @Override
            public void onChanged(OrderItem orderItem) {
                binding.setProductVM(viewModel);
                if(binding.sizeArea.getVisibility() == View.VISIBLE){
                    if(orderItem.getSize()!= null){
                        if(orderItem.getSize().isEmpty()){
                            uncheckAllRadioButtons();
                            validateAddToCart(false);
                        }
                    }
                }
                else{
                    validateAddToCart(true);
                }
                if(orderItem.getNote().isEmpty())
                    binding.editTextDifferent.setText("");
            }
        });

        // observe change in average point
        viewModel.getAveragePointLiveData().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (aDouble!= null){
                    binding.textIcStar.setText(String.valueOf(aDouble));
                }
                else {
                    binding.textIcStar.setText("0.0");
                }
            }
        });

        // button quantity todo: điều chỉnh quantity lại
        binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    viewModel.getProductCartLiveData().getValue().setQuantity(quantity);
                }
            }
        });

        // button increase click
        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object productQuantityObj = viewModel.getProductLiveData().getValue().getProductSize()
                        .get(viewModel.getProductCartSize()).getOrDefault("QUANTITY", 0);
                int productQuantity = convertObject(productQuantityObj);
                int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
                if(quantity < productQuantity){
                    quantity++;
                    binding.txtQuantity.setText(String.valueOf(quantity));
                    viewModel.getProductCartLiveData().getValue().setQuantity(quantity);
                }
                else{
                    Toast.makeText(context, "Bạn chỉ được mua tối đa " + productQuantity + " sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // radio group check event
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int previousCheckedId = -1;
                // Check if any radio button is checked
                if (checkedId != -1) {
                    if (viewModel.getProductLiveData() != null && viewModel.getProductLiveData().getValue() != null) {
                        Product product = viewModel.getProductLiveData().getValue();
                        if (viewModel.getProductCartLiveData() != null && viewModel.getProductCartLiveData().getValue() != null) {
                            OrderItem orderItem = viewModel.getProductCartLiveData().getValue();

                            if (product.getProductSize() != null) {
                                Map<String, Map<String, Integer>> size = product.getProductSize();
                                // get old size, price if order item size is not empty
                                if (orderItem.getSize() != null && !orderItem.getSize().isEmpty() && size.containsKey(orderItem.getSize())) {
                                    // convert price from Long to int
                                    Object priceObj = size.get(orderItem.getSize()).get("PRICE");

                                    oldSizePrice = convertObject(priceObj);
                                } else if (orderItem.getSize() != null)
                                    oldSizePrice = 0;

                                RadioButton checkedRadioButton = group.findViewById(checkedId);
                                if (checkedRadioButton != null) {
                                    String checkedSize = checkedRadioButton.getText().toString();

                                    // convert price from Long to int
                                    Object priceObj = size.get(checkedSize).getOrDefault("PRICE", 0);
                                    newSizePrice = convertObject(priceObj);

                                    orderItem.setSize(checkedSize);
                                    orderItem.setPrice(orderItem.getPrice() - oldSizePrice + newSizePrice);
                                    // If needed, notify view model or update live data
                                    viewModel.getProductCartLiveData().setValue(orderItem);
                                }
                            }
                        }
                    }
                }
            }
        });
        // end: size

        // start: comment
        // method to display comment (Ánh)
        viewModel.getOrdersFeedbackRepository().checkExistComments(productId, new OrdersFeedbackRepository.checkCommentsCallback() {
            @Override
            public void loadCommentsSuccess(int isExist) {
                binding.txtComment.setText(isExist+"");
                if(isExist>0){
                    binding.icComment.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            commentDialogFragment = null;
                            viewModel.getListCommentLiveData(productId).observe((LifecycleOwner) context , new Observer<List<Comment>>() {
                                @Override
                                public void onChanged(List<Comment> list) {
                                    // Remove the observer after receiving the data to prevent multiple dialogs
                                    viewModel.getListCommentLiveData(productId).removeObserver(this);

                                    commentDialogFragment = new CommentDialogFragment(list);
                                    commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
                                }
                            });
                        }
                    });

                }else{
                    binding.icComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Load comment in activity: ", "No comments");

                            commentDialogFragment = new CommentDialogFragment(null);
                            commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
                        }
                    });

                }
            }

            @Override
            public void loadCommentError(Exception e) {

            }
        });
        //end: comment

        //start: button add to cart event
        binding.btnAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId == null) {
                    // User ID not found, handle this case
                    finish();
                    Intent intent = new Intent(getApplicationContext(), activity_login.class);
                    startActivity(intent);
                }
                else{
                    //todo: add to cart
                    String note = binding.editTextDifferent.getText().toString();
                    viewModel.getProductCartLiveData().getValue().setNote(note);
                    viewModel.addToCart(userId);
                }
            }
        });
        //end: button add to cart event

        // Set the color scheme for the spinner
        binding.swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.secondary),
                getResources().getColor(R.color.primary)
        );
        // Set up the swipe refresh listener

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Trigger the reload in the ViewModel
                viewModel.reloadData();
            }
        });
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

    //method to set price formatted
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
            // reset product cart if product is unavailable
            if(viewModel.getProductCartSize() != null && viewModel.getProductCartSize().equals(size)){
                OrderItem orderItem = viewModel.getProductCartLiveData().getValue();
                orderItem.setQuantity(1);
                orderItem.setPrice(orderItem.getPrice() - price);
                orderItem.setSize("");
            }
            textView.setTextColor(grey);
            radioButton.setTextColor(grey);
            radioButton.setEnabled(false);
            radioButton.setChecked(false);
        }
        else{
//            if(viewModel.getProductCartSize() != null && !viewModel.getProductCartSize().equals(size))
//                radioButton.setChecked(false);
            textView.setTextColor(dark);
            radioButton.setTextColor(dark);
            radioButton.setEnabled(true);
        }
    }
    private void uncheckAllRadioButtons() {
        // Iterate through all radio buttons in the RadioGroup and uncheck them
        for (int i = 0; i < binding.radioGroup.getChildCount(); i++) {
            binding.radioGroup.clearCheck();
        }
    }

    // method to enable or disable add to cart feature
    public void validateAddToCart(boolean isValid){
        if(isValid){
            binding.btnAddtocart.setEnabled(true);
            binding.btnDecrease.setEnabled(true);
            binding.btnIncrease.setEnabled(true);
        }
        else{
            binding.btnAddtocart.setEnabled(false);
            binding.btnDecrease.setEnabled(false);
            binding.btnIncrease.setEnabled(false);
        }
    }

    @Override
    public void onItemCheckedChanged(boolean isChecked, Topping topping) {
            OrderItem orderItem = viewModel.getProductCartLiveData().getValue();
            ArrayList<String> toppings = orderItem.getTopping();
            if (isChecked) {
                orderItem.setPrice(orderItem.getPrice() + topping.getPriceTopping());
                toppings.add(topping.getNameTopping());
            } else {
                orderItem.setPrice(orderItem.getPrice() - topping.getPriceTopping());
                toppings.remove(topping.getNameTopping());
            }
            viewModel.getProductCartLiveData().setValue(orderItem);
    }

}


