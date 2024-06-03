package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ViewholderCartBindingImpl extends ViewholderCartBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btn_edit, 6);
        sViewsWithIds.put(R.id.constraintLayout, 7);
        sViewsWithIds.put(R.id.btn_decrease, 8);
        sViewsWithIds.put(R.id.img_minus, 9);
        sViewsWithIds.put(R.id.btn_increase, 10);
        sViewsWithIds.put(R.id.img_plus, 11);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ViewholderCartBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ViewholderCartBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[8]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.FrameLayout) bindings[10]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            );
        this.imgProduct.setTag(null);
        this.productCartContainer.setTag(null);
        this.txtProductCost.setTag(null);
        this.txtProductName.setTag(null);
        this.txtProductSize.setTag(null);
        this.txtQuantity.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.orderItem == variableId) {
            setOrderItem((com.example.foodorderingapp.data.model.entity.OrderItem) variable);
        }
        else if (BR.cartVM == variableId) {
            setCartVM((com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setOrderItem(@Nullable com.example.foodorderingapp.data.model.entity.OrderItem OrderItem) {
        this.mOrderItem = OrderItem;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.orderItem);
        super.requestRebind();
    }
    public void setCartVM(@Nullable com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel CartVM) {
        this.mCartVM = CartVM;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String orderItemProductImage = null;
        com.example.foodorderingapp.data.model.entity.OrderItem orderItem = mOrderItem;
        java.lang.String stringValueOfOrderItemQuantity = null;
        int orderItemQuantity = 0;
        java.lang.String orderItemProductName = null;
        int orderItemPrice = 0;
        java.lang.String orderItemSize = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (orderItem != null) {
                    // read orderItem.productImage
                    orderItemProductImage = orderItem.getProductImage();
                    // read orderItem.quantity
                    orderItemQuantity = orderItem.getQuantity();
                    // read orderItem.productName
                    orderItemProductName = orderItem.getProductName();
                    // read orderItem.price
                    orderItemPrice = orderItem.getPrice();
                    // read orderItem.size
                    orderItemSize = orderItem.getSize();
                }


                // read String.valueOf(orderItem.quantity)
                stringValueOfOrderItemQuantity = java.lang.String.valueOf(orderItemQuantity);
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.loadImage(this.imgProduct, orderItemProductImage);
            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.txtProductCost, orderItemPrice);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtProductName, orderItemProductName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtProductSize, orderItemSize);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtQuantity, stringValueOfOrderItemQuantity);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): orderItem
        flag 1 (0x2L): cartVM
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}