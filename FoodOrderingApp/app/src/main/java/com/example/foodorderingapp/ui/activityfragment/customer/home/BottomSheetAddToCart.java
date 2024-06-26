package com.example.foodorderingapp.ui.activityfragment.customer.home;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.adapter.BuyNowToppingAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.home.BuyNowViewModel;
import com.example.foodorderingapp.ui.viewmodel.customer.home.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BottomSheetAddToCart extends BottomSheetDialogFragment {
    private BuyNowViewModel viewModel;
    private HomeViewModel homeViewModel;
    private RadioGroup radioGroup;
    private RadioButton rbSmall, rbMedium, rbLarge;
    private TextView tvBigPrice, tvMediumPrice, tvSmallPrice, tvProductHeading, tvProductPrice;
    private EditText txtNote;
    private ConstraintLayout toppingArea, sizeArea;
    private Button btnAddToCart;
    private LinearLayout outOfStockWarning;

    private  static final String ARG_PRODUCT_ID = "productId";
    private String productId;
    private Context context;
    private BuyNowToppingAdapter adapter;
    private List<Topping> toppings;

    private int oldSizePrice = 0, newSizePrice = 0;
    private List<String> toppingNames, toppingSelected, tempCheckedTopping;

    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    private static final int LOGIN_REQUEST_CODE = 2;

    public static BottomSheetAddToCart newInstance (String productId){
        BottomSheetAddToCart fragment = new BottomSheetAddToCart();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString(ARG_PRODUCT_ID);
        }
        viewModel = new ViewModelProvider(this).get(BuyNowViewModel.class);
        viewModel.init(productId, requireContext(), requireActivity());
        viewModel.loadProduct(productId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_add_to_cart, container, false);

        // get user id from shared preferences
        sharedPreferences = requireContext().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);

        tvProductHeading = view.findViewById(R.id.product_heading);
        tvProductPrice = view.findViewById(R.id.txt_product_cost);
        radioGroup = view.findViewById(R.id.radioGroup);
        rbSmall = view.findViewById(R.id.rb_small);
        rbMedium = view.findViewById(R.id.rb_medium);
        rbLarge = view.findViewById(R.id.rb_large);
        tvBigPrice = view.findViewById(R.id.tv_big_price);
        tvMediumPrice = view.findViewById(R.id.tv_medium_price);
        tvSmallPrice = view.findViewById(R.id.tv_small_price);
        txtNote = view.findViewById(R.id.txt_note);
        toppingArea = view.findViewById(R.id.topping_area);
        sizeArea = view.findViewById(R.id.size_area);
        btnAddToCart = view.findViewById(R.id.add_to_cart);
        outOfStockWarning = view.findViewById(R.id.out_of_stock_warning);

        toppings = viewModel.getToppings();
        toppingSelected = new ArrayList<>();
        toppingNames = new ArrayList<>();
        adapter = new BuyNowToppingAdapter(requireContext(), toppings, toppingNames, toppingSelected, viewModel, this::onItemCheckedChanged);

        RecyclerView recyclerViewTopping = view.findViewById(R.id.recyclerViewTopping);
        recyclerViewTopping.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewTopping.setAdapter(adapter);

        viewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    if (viewModel.getProductCartLiveData().getValue() != null) {
                        updateAddToCartButton(viewModel.getProductCartLiveData().getValue());
                    }

                    tvProductHeading.setText(product.getProductName());
                    tvProductPrice.setText(setPriceFormatted(product.getProductPrice()));

                    // Show or hide topping area
                    if (product.getTopping() != null) {
                        toppingArea.setVisibility(View.VISIBLE);
                        toppingNames.clear();
                        toppingNames.addAll(product.getTopping());
                        adapter.notifyDataSetChanged();
                    } else {
                        toppingArea.setVisibility(View.GONE);
                    }

                    // Show or hide size area
                    if (product.getProductSize() != null) {
                        sizeArea.setVisibility(View.VISIBLE);
                        rbLarge.setVisibility(View.GONE);
                        rbMedium.setVisibility(View.GONE);
                        rbSmall.setVisibility(View.GONE);
                        tvBigPrice.setVisibility(View.GONE);
                        tvMediumPrice.setVisibility(View.GONE);
                        tvSmallPrice.setVisibility(View.GONE);

                        int numbOutOfStockProduct = 0;
                        int numbSize = product.getProductSize().size();

                        for (Map.Entry<String, Map<String, Integer>> entry : product.getProductSize().entrySet()) {
                            String size = entry.getKey();
                            Map<String, Integer> sizeInfo = entry.getValue();
                            int quantity = sizeInfo.getOrDefault("QUANTITY", 0);
                            int price = sizeInfo.getOrDefault("PRICE", 0);

                            if (quantity == 0) {
                                numbOutOfStockProduct++;
                            }

                            switch (size) {
                                case "Lớn":
                                    setRadioButtonUI(size, price, quantity, tvBigPrice, rbLarge);
                                    break;
                                case "Vừa":
                                    setRadioButtonUI(size, price, quantity, tvMediumPrice, rbMedium);
                                    break;
                                case "Nhỏ":
                                    setRadioButtonUI(size, price, quantity, tvSmallPrice, rbSmall);
                                    break;
                                case "Mặc định":
                                    sizeArea.setVisibility(View.GONE);
                                    break;
                            }
                        }

                        if (numbOutOfStockProduct != numbSize) {
                            if (viewModel.getProductCartLiveData().getValue() != null) {
                                OrderItem orderItem = viewModel.getProductCartLiveData().getValue();
                                if (orderItem.getSize().isEmpty() && sizeArea.getVisibility() == View.VISIBLE) {
                                    validateAddToCart(false);
                                } else {
                                    validateAddToCart(true);
                                }
                                outOfStockWarning.setVisibility(View.GONE);
                            }
                        } else {
                            validateAddToCart(false);
                            outOfStockWarning.setVisibility(View.VISIBLE);
                        }
                    } else {
                        sizeArea.setVisibility(View.GONE);
                    }
                }
            }
        });

        viewModel.getProductCartLiveData().observe(getViewLifecycleOwner(), new Observer<OrderItem>() {
            @Override
            public void onChanged(OrderItem orderItem) {
                sizeArea.setVisibility(orderItem.getSize() != null ? View.VISIBLE : View.GONE);

                if (sizeArea.getVisibility() == View.VISIBLE) {
                    if (orderItem.getSize() != null && orderItem.getSize().isEmpty()) {
                        uncheckAllRadioButtons();
                        validateAddToCart(false);
                    }
                } else {
                    validateAddToCart(true);
                }

                if (orderItem.getNote().isEmpty()) {
                    txtNote.setText("");
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                                    updateAddToCartButton(orderItem);
                                    validateAddToCart(true);
                                }
                            }
                        }
                    }
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId == null) {
                    // User ID not found, handle this case
                    Intent intent = new Intent(requireContext(), activity_login.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
                    // Add to cart
                    String note = txtNote.getText().toString();
                    viewModel.getProductCartLiveData().getValue().setNote(note);
                    viewModel.addToCart(userId);
                    dismiss();  // Close the BottomSheet
                }
            }
        });

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Adjust height based on content
                View bottomSheet = getView();
                if (bottomSheet != null) {
                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        bottomSheet.setLayoutParams(layoutParams);
                    }
                }
            }
        });

        return view;
    }

    public int convertObject(Object object){
        int intObj = 0;
        if (object instanceof Long) {
            intObj = ((Long) object).intValue();
        } else if (object instanceof Integer) {
            intObj = (Integer) object;
        }
        return intObj;
    }
    private void uncheckAllRadioButtons() {
        // Iterate through all radio buttons in the RadioGroup and uncheck them
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.clearCheck();
        }
    }

    // method to enable or disable add to cart feature
    public void validateAddToCart(boolean isValid){
        if(isValid){
            btnAddToCart.setEnabled(true);
        }
        else{
            btnAddToCart.setEnabled(false);
        }
    }

    private void onItemCheckedChanged(boolean isChecked, Topping topping) {
        OrderItem orderItem = viewModel.getProductCartLiveData().getValue();
        ArrayList<String> toppings = orderItem.getTopping();
        if (isChecked) {
            orderItem.setPrice(orderItem.getPrice() + topping.getPriceTopping());
            toppings.add(topping.getNameTopping());
        } else {
            orderItem.setPrice(orderItem.getPrice() - topping.getPriceTopping());
            toppings.remove(topping.getNameTopping());
        }
        updateAddToCartButton(orderItem);
    }

    //Cập nhật giá của sp
    private void updateAddToCartButton(OrderItem orderItem) {
        int totalPrice = orderItem.getPrice();

        // Format the price and update the button text
        String formattedPrice = setPriceFormatted(totalPrice);
        btnAddToCart.setText("Thêm vào giỏ hàng - " + formattedPrice);
    }

    public void setRadioButtonUI(String size, int price, int quantity, TextView textView, RadioButton radioButton){
        int grey = ContextCompat.getColor(requireContext(), R.color.transparent50_gray);
        int dark = ContextCompat.getColor(requireContext(), R.color.dark_text);
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
            textView.setTextColor(dark);
            radioButton.setTextColor(dark);
            radioButton.setEnabled(true);
        }
    }

    //method to set price formatted
    public static String setPriceFormatted(int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        return formattedPrice;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null && data.hasExtra("isLogin")){
                if(data.getBooleanExtra("isLogin", false)){
                    //get user id after login
                    userId = sharedPreferences.getString(KEY_USER_ID, null);
                    //todo: add to cart (continue proccess after user login)
                    String note = txtNote.getText().toString();
                    viewModel.getProductCartLiveData().getValue().setNote(note);
                    viewModel.addToCart(userId);
                    //quan sát thay đổi đăng nhập -> true
                    viewModel.setLoginSuccess(true);

                    dismiss();
                }
            }
        }
    }
}
