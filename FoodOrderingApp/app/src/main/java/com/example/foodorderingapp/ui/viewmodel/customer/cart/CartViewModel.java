package com.example.foodorderingapp.ui.viewmodel.customer.cart;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.data.repository.topping.IToppingRepository;
import com.example.foodorderingapp.data.repository.topping.ToppingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartViewModel extends ViewModel {
    private String userId;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context; // Context variable

    //Live data
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> totalPrice = new MutableLiveData<>();
    private MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();
    private List<Topping> toppings = new ArrayList<>();
    private MutableLiveData<Boolean> isValidCheckout = new MutableLiveData<>();

    // Repository
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private ToppingRepository toppingRepository;

    public CartViewModel(String userId, Context context){
        this.userId = userId;
        this.context = context;
        orderRepository = new OrderRepository();
        productRepository = new ProductRepository();
        toppingRepository = new ToppingRepository();
        reloadData();
    }

    // Getter
    public MutableLiveData<Integer> getTotalPrice() {
        if(orderLiveData.getValue()!= null)
            totalPrice.setValue(calculateTotalPrice(orderLiveData.getValue().getOrderItem()));
        else{
            totalPrice.setValue(0);
        }
        return totalPrice;
    }

    public MutableLiveData<Order> getOrderLiveData() {
        loadCart(userId);
        return orderLiveData;
    }

    public MutableLiveData<Product> getProductLiveData(String productId) {
        loadProduct(productId);
        return productLiveData;
    }

    public MutableLiveData<List<Product>> getProductListLiveData() {
        loadProductList();
        return productListLiveData;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public MutableLiveData<Boolean> getIsValidCheckout() {
        return isValidCheckout;
    }

    public void setIsValidCheckout(boolean isValidCheckout) {
        this.isValidCheckout.setValue(isValidCheckout);
    }

    public MutableLiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    // load method
    public void loadCart(String userId){
        orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
                totalPrice.setValue(calculateTotalPrice(order.getOrderItem()));
            }

            @Override
            public void onError(String errorMessage) {
                if(errorMessage.equals("Cart not found")){
                    orderLiveData.setValue(null);
                }
            }
        });
    }

    // load product
    public void loadProduct(String productId){
        productRepository.getProductById(productId, new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                productLiveData.setValue(product);
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {

            }
        });
    }

    // method to update order item to firestore
    public void updateOrderItemByOrderId(String orderItemId){
        if (orderLiveData.getValue() == null) {
            // Handle the case where orderLiveData is null
            return;
        }

        String orderId = orderLiveData.getValue().getId();
        OrderItem orderItem = orderLiveData.getValue().getOrderItemElementById(orderItemId);

        if (orderId == null || orderItem == null) {
            // Handle the case where orderId or orderItem is null
            return;
        }
            orderRepository.addOrUpdateProductCart(
                    orderId,
                    orderItemId,
                    orderItem,
                    new IOrderRepository.OrderChangedCallback() {
                        @Override
                        public void onOrderChanged() {
//                        dismissProgressDialog();
                            Toast.makeText(context, "Rất tiếc, bạn chỉ có thể mua tối đa " + orderItem.getQuantity() + " sản phẩm " + orderItem.getProductName(), Toast.LENGTH_SHORT).show();
                            reloadData();
                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    }
            );

    }

    // method to load product list live data base on order item
    public void loadProductList() {
        List<String> ids = new ArrayList<>();
        if(orderLiveData.getValue() != null){
            for (Map.Entry<String, OrderItem> entry : orderLiveData.getValue().getOrderItem().entrySet()) {
                ids.add(entry.getValue().getIdProduct());
            }
            productRepository.getProductListByIds(ids, new IProductRepository.ProductListCallback() {
                @Override
                public void onProductListLoaded(List<Product> productList) {
                    productListLiveData.setValue(productList);
                }

                @Override
                public void onProductListLoadFailed(String errorMessage) {

                }
            });
        }

    }

    // method to load topping list in firestore
    public void loadToppingList(){
        toppingRepository.getAllToppings(new IToppingRepository.ToppingListCallBack() {
            @Override
            public void onToppingListLoaded(List<Topping> toppingList) {
                toppings.addAll(toppingList);
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }

    // calculate total price
    public int calculateTotalPrice(Map<String, OrderItem> buyingProductHashMap){
        int totalPrice = 0;
        for(Map.Entry<String, OrderItem> entry : buyingProductHashMap.entrySet()){
            totalPrice += entry.getValue().getPrice() * entry.getValue().getQuantity();
        }
        return totalPrice;
    }

    public void updateTotalPrice(int deltaPrice){
        int newPrice = totalPrice.getValue() - deltaPrice;
        totalPrice.setValue(newPrice);
    }

    // Delete orderItem
    public void deleteProductCart(String orderItemId){
        showProgressDialog("Đang xử lý...");

        orderRepository.deleteProductCart(
                orderLiveData.getValue().getId(),
                orderItemId,
                new IOrderRepository.OrderItemRemovedCallback() {
                    @Override
                    public void onOrderItemRemoved(String id) {
                        // Load new cart product after delete from db
                        reloadData();
                        // Dismiss progress dialog
                        dismissProgressDialog();

                    }
                    @Override
                    public void onError(String errorMessage) {
                        dismissProgressDialog();
                    }
                }
        );
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    // method to reload activity
    public void reloadData(){
        loadCart(userId);
        loadToppingList();
        isValidCheckout.setValue(true);
        loadProductList();
//        loadCartItemCount();
    }

    
    // EVENT LISTENER
    // Click listeners for increase and decrease buttons
    public void onChangeQuantityButtonClick(String orderItemId, int deltaPrice) {
        // update total price
        totalPrice.setValue(totalPrice.getValue() + deltaPrice);

        // update product cart in firestore
        orderRepository.addOrUpdateProductCart(
                orderLiveData.getValue().getId(),
                orderItemId,
                orderLiveData.getValue().getOrderItemElementById(orderItemId),
                new IOrderRepository.OrderChangedCallback() {
                    @Override
                    public void onOrderChanged() {
//                        loadCartItemCount();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                }
        );
    }

    // Check listenter for button confirm to edit product cart
    public void onConfrimButtonClick(String orderItemId, String productId, String newSize, List<String> newToppings, String newNote, int newPrice) {
        showProgressDialog("Đang xử lý...");
        OrderItem newOrderItem = orderLiveData.getValue().getOrderItemElementById(orderItemId);
        boolean isExistingOrderItem = false;
        String orderId = orderLiveData.getValue().getId();

        // FIND IF THERE IS ANY SIMILAR ORDER ITEM
        // Iterate through the orderItemMap
        for (Map.Entry<String, OrderItem> entry : orderLiveData.getValue().getOrderItem().entrySet()) {
            String itemId = entry.getKey();
            OrderItem item = entry.getValue();

            // Check if the itemId is different from the current orderItemId
            if (!itemId.equals(orderItemId)) {

                // Check if product id are the same
                if(productId.equals(item.getIdProduct())){

                    if(item.getSize() != null && item.getTopping() != null){
                        if (Objects.equals(item.getSize(), newSize) && Objects.equals(item.getTopping(), newToppings) && Objects.equals(item.getNote(), newNote))
                            isExistingOrderItem = true;
                    }
                    else if(item.getSize() != null && item.getTopping() == null){
                        if (Objects.equals(item.getSize(), newSize) && Objects.equals(item.getNote(), newNote))
                            isExistingOrderItem = true;
                    } else if (item.getSize() == null && item.getTopping() != null) {
                        if (Objects.equals(item.getTopping(), newToppings) && Objects.equals(item.getNote(), newNote))
                            isExistingOrderItem = true;
                    }
                    else{
                        if (Objects.equals(item.getNote(), newNote))
                            isExistingOrderItem = true;
                    }

                    // Check if the size, toppings, and note are the same
                    if (isExistingOrderItem) {
                        // Update quantity += 1 for another orderItem
                        item.setQuantity(item.getQuantity() + newOrderItem.getQuantity());

                        orderRepository.addOrUpdateProductCart(orderId, itemId, item, new IOrderRepository.OrderChangedCallback() {
                            @Override
                            public void onOrderChanged() {
                                // Remove the current orderItem
                                orderRepository.deleteProductCart(orderId, orderItemId, new IOrderRepository.OrderItemRemovedCallback() {
                                    @Override
                                    public void onOrderItemRemoved(String id) {
                                        reloadData();
//                                        loadCartItemCount();
                                        dismissProgressDialog();
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Log.e("error", "onError: " + errorMessage);
                                    }
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
        }

        /*
        CASE ORDER ITEM SIZE AND TOPPING IS NULLABLE:
        - CHANGE DATATYPE IN FIRESTORE TO STRING AND EMPTY ARRAY
         */
        // Update order item if there isn't exist any same order item?
        if (!isExistingOrderItem) {
            if(newOrderItem.getSize() != null){
                newOrderItem.setSize(newSize);
            }
            else
                newOrderItem.setSize("");

            if (newOrderItem.getTopping() != null) {
                newOrderItem.setTopping(newToppings != null ? new ArrayList<>(newToppings) : null);
            }
            else{
                ArrayList<String> emptyList = new ArrayList<>();
                newOrderItem.setTopping(emptyList);
            }

            newOrderItem.setNote(newNote);
            newOrderItem.setPrice(newPrice);

            orderRepository.addOrUpdateProductCart(orderId, orderItemId, newOrderItem, new IOrderRepository.OrderChangedCallback() {
                @Override
                public void onOrderChanged() {
                    reloadData();
//                    loadCartItemCount();
                    dismissProgressDialog();
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("error", "onError: " + errorMessage);
                }
            });
        }
    }
}
