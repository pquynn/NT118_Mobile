package com.example.foodorderingapp.ui.viewmodel.customer.productdetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.data.repository.topping.IToppingRepository;
import com.example.foodorderingapp.data.repository.topping.ToppingRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ProductDetailViewModel extends ViewModel {
    private String productId = "", userId;
    private Context context;
    private Activity activity;
    private ProgressDialog progressDialog;
    private ProductRepository productRepository;
    private ToppingRepository toppingRepository;
    private OrderRepository orderRepository;
    private OrdersFeedbackRepository ordersFeedbackRepository;
    private MutableLiveData<Product> productLiveData;
    private MutableLiveData<List<Comment>> listCommentLiveData;
    private MutableLiveData<Order> cartLiveData;
    private MutableLiveData<OrderItem> productCartLiveData;
    private MutableLiveData<Double> averagePointLiveData;
    private List<Topping> toppings = new ArrayList<>();

    public ProductDetailViewModel(String productId, String userId, Context context, Activity activity) {
        this.productId = productId;
        this.userId = userId;
        this.context = context;
        this.activity = activity;
        productRepository = new ProductRepository();
        toppingRepository = new ToppingRepository();
        orderRepository = new OrderRepository();
        ordersFeedbackRepository = new OrdersFeedbackRepository();
        productLiveData = new MutableLiveData<>();
        listCommentLiveData = new MutableLiveData<>();
        cartLiveData = new MutableLiveData<>();
        productCartLiveData = new MutableLiveData<>();
        averagePointLiveData = new MutableLiveData<>();
        loadProductDetails();
        initProductCartLiveData();
        loadToppingList();
        loadAveragePoint();
    }

    public LiveData<Product> getProductLiveData() {
        loadProductDetails();
        return productLiveData;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public MutableLiveData<List<Comment>> getListCommentLiveData(String productID){
        loadListComment();
        return listCommentLiveData;
    }

    public MutableLiveData<List<Comment>> getListCommentLiveData() {
        return listCommentLiveData;
    }

    public MutableLiveData<Order> getCartLiveData() {
        return cartLiveData;
    }

    public MutableLiveData<OrderItem> getProductCartLiveData() {
        return productCartLiveData;
    }

    public OrdersFeedbackRepository getOrdersFeedbackRepository() {
        return ordersFeedbackRepository;
    }

    public MutableLiveData<Double> getAveragePointLiveData() {
        loadAveragePoint();
        return averagePointLiveData;
    }

    // method to get product cart current size
    public String getProductCartSize(){
        String size = "";
        if(productCartLiveData.getValue() != null){
            OrderItem orderItem = productCartLiveData.getValue();
            size = (orderItem.getSize() == null || orderItem.getSize().isEmpty()) ? "Mặc định" : orderItem.getSize();
        }
        return size;
    }

    public void loadAveragePoint(){
        ordersFeedbackRepository.calculateProductAveragePoint(productId, new OrdersFeedbackRepository.averagePointCallback() {
            @Override
            public void onLoad(double point) {
                averagePointLiveData.setValue(point);
            }

            @Override
            public void onError(Exception e) {
                Log.e("Error", "Error loading average point: " + e.getMessage());
            }
        });
    }

    private  void loadListComment(){
        ordersFeedbackRepository.getCommentsByProductID(productId, new OrdersFeedbackRepository.commentListCallback() {
            @Override
            public void loadListCommentSuccess(List<Comment> listComment) {
                listCommentLiveData.setValue(listComment);
            }

            @Override
            public void loadlistCommentError(Exception e) {
                Log.e("Error Product detail VM comment:", "Error load comment list in Product detail VM");
            }
        });
    }

    private void loadProductDetails() {
        productRepository.getProductDetails(productId, new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                productLiveData.setValue(product);
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {
                Log.e("Error", "Error loading product details: " + errorMessage);
            }
        });
    }

    // method to load topping list in firestore
    public void loadToppingList(){
        toppingRepository.getAllToppings(new IToppingRepository.ToppingListCallBack() {
            @Override
            public void onToppingListLoaded(List<Topping> toppingList) {
                toppings.clear();
                toppings.addAll(toppingList);
            }

            @Override
            public void onFailed(String errorMessage) {
                Log.e("Error", "Error loading toppings: " + errorMessage);
            }
        });
    }

    // method to init order item
    public void initProductCartLiveData(){
        productRepository.getProductDetails(productId, new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(product.getProductName());
                orderItem.setProductImage(product.getProductImage());
                orderItem.setIdProduct(product.getId());
                orderItem.setPrice(product.getProductPrice());
                orderItem.setQuantity(1);
                orderItem.setNote("");
                orderItem.setSize("");
                orderItem.setTopping(new ArrayList<>());
                productCartLiveData.setValue(orderItem);
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {
                Log.e("Error", "Error initializing product cart: " + errorMessage);
            }
        });
    }

    // method to solve add to cart
    public void addToCart(){
        showProgressDialog("Đang xử lý...");
        OrderItem orderItem = productCartLiveData.getValue();
        if (orderItem == null) {
            Log.e("Error", "OrderItem is null");
            dismissProgressDialog();
            return;
        }

        String size = getProductCartSize();
        Map<String, Map<String, Integer>> productSizeMap = productLiveData.getValue().getProductSize();
        if (productSizeMap == null) {
            Log.e("Error", "Product size map is null");
            dismissProgressDialog();
            return;
        }

        Object productQuantityObj = productSizeMap.get(size).get("QUANTITY");
        Object productPriceObj = productSizeMap.get(size).get("PRICE");
        int productQuantity = convertObject(productQuantityObj);
        int productSizePrice = convertObject(productPriceObj);

        if(productQuantity == 0){
            dismissProgressDialog();
            Toast.makeText(context, "Rất tiếc, sản phẩm hiện đã hết hàng.", Toast.LENGTH_SHORT).show();
            orderItem.setQuantity(1);
            orderItem.setPrice(orderItem.getPrice() - productSizePrice);
            orderItem.setSize("");
        }
        else if(productQuantity < orderItem.getQuantity()){
            dismissProgressDialog();
            Toast.makeText(context, "Rất tiếc, bạn chỉ có thể mua tối đa " + productQuantity + " sản phẩm!", Toast.LENGTH_SHORT).show();
        }
        else{
            orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
                @Override
                public void onOrderLoaded(Order order) {
                    processUpdateOrder(orderItem, order);
                }

                @Override
                public void onError(String errorMessage) {
                    if("Cart not found".equals(errorMessage)){
                        String id = UUID.randomUUID().toString();
                        Map<String, OrderItem> orderItemMap = new HashMap<>();
                        orderItemMap.put(id, orderItem);
                        orderRepository.createOrder(userId, orderItemMap, new IOrderRepository.OrderCallback() {
                            @Override
                            public void onOrderLoaded(Order order) {
                                dismissProgressDialog();
                                Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                activity.finish();
                            }

                            @Override
                            public void onError(String errorMessage) {
                                Log.e("Error", "Error creating order: " + errorMessage);
                            }
                        });
                    }
                }
            });
        }
    }

    public void processUpdateOrder(OrderItem productCart, Order order) {
        if (productCart == null || order == null || order.getOrderItem() == null) {
            Log.e("error", "Invalid input: productCart or order or order items is null.");
            return;
        }

        boolean isExistingOrderItem = false;

        // FIND IF THERE IS ANY SIMILAR ORDER ITEM
        // Iterate through the orderItemMap
        for (Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()) {
            String itemId = entry.getKey();
            OrderItem item = entry.getValue();

            // Check if product id are the same
            if (productCart.getIdProduct().equals(item.getIdProduct())) {

                // Check if size, topping is null and is exist
                if (item.getSize() != null && item.getTopping() != null) {
                    if (Objects.equals(item.getSize(), productCart.getSize())
                            && Objects.equals(item.getTopping(), productCart.getTopping())
                            && Objects.equals(item.getNote(), productCart.getNote())) {
                        isExistingOrderItem = true;
                    }
                } else if (item.getSize() != null && item.getTopping() == null) {
                    if (Objects.equals(item.getSize(), productCart.getSize())
                            && Objects.equals(item.getNote(), productCart.getNote())) {
                        isExistingOrderItem = true;
                    }
                } else if (item.getSize() == null && item.getTopping() != null) {
                    if (Objects.equals(item.getTopping(), productCart.getTopping())
                            && Objects.equals(item.getNote(), productCart.getNote())) {
                        isExistingOrderItem = true;
                    }
                } else {
                    if (Objects.equals(item.getNote(), productCart.getNote())) {
                        isExistingOrderItem = true;
                    }
                }

                // If exist order item that is the same product cart
                if (isExistingOrderItem) {
                    // Update quantity += 1 for another orderItem
                    item.setQuantity(item.getQuantity() + productCart.getQuantity());
                    orderRepository.addOrUpdateProductCart(order.getId(), itemId, item, new IOrderRepository.OrderChangedCallback() {
                        @Override
                        public void onOrderChanged() {
                            runOnUiThread(() -> {
                                dismissProgressDialog();
                                Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                activity.finish();
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("error", "onError: " + errorMessage);
                        }
                    });
                    break;
                }
            }
        }

        // CASE ORDER ITEM SIZE AND TOPPING IS NULLABLE:
        // CHANGE DATATYPE IN FIRESTORE TO STRING AND EMPTY ARRAY
        // IF THERE ISN'T EXIST DUPLICATE ORDER ITEM --> CREATE NEW ORDER ITEM
        if (!isExistingOrderItem) {
            //init random order item id
            String id = UUID.randomUUID().toString();
            //call repo to add product cart (order item)
            orderRepository.addOrUpdateProductCart(order.getId(), id, productCart, new IOrderRepository.OrderChangedCallback() {
                @Override
                public void onOrderChanged() {
                    runOnUiThread(() -> {
                        dismissProgressDialog();
                        Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                        activity.finish();

                    });
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("error", "onError: " + errorMessage);
                }
            });
        }
    }

    private void runOnUiThread(Runnable action) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else {
            new Handler(Looper.getMainLooper()).post(action);
        }
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public int convertObject(Object object){
        int intObj = 0;
        if (object instanceof Long) {
            intObj = ((Long) object).intValue();
        } else if (object instanceof Integer) {
            intObj = (Integer) object;
        } else {
            Log.d("productdetail", "PRICE is of unexpected type: " + (object != null ? object.getClass().getName() : "null"));
        }
        return intObj;
    }

    public void reloadData(){
        loadProductDetails();
        initProductCartLiveData();
        loadAveragePoint();
        Log.d("productdetail", "reloadData: product cart: " + productCartLiveData.getValue().toString());
    }
}
