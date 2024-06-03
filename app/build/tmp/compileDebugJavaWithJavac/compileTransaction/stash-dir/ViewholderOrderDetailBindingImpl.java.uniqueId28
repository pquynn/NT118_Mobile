package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ViewholderOrderDetailBindingImpl extends ViewholderOrderDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lbl_quantity, 8);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ViewholderOrderDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private ViewholderOrderDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[4]
            );
        this.imgProduct.setTag(null);
        this.orderDetailContainer.setTag(null);
        this.txtNote.setTag(null);
        this.txtProductCost.setTag(null);
        this.txtProductName.setTag(null);
        this.txtProductSize.setTag(null);
        this.txtQuantity.setTag(null);
        this.txtTopping.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        java.lang.String orderItemToppingString = null;
        java.lang.String orderItemNote = null;
        int orderItemQuantity = 0;
        java.lang.String orderItemProductName = null;
        int orderItemPrice = 0;
        java.lang.String orderItemSize = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (orderItem != null) {
                    // read orderItem.productImage
                    orderItemProductImage = orderItem.getProductImage();
                    // read orderItem.toppingString
                    orderItemToppingString = orderItem.getToppingString();
                    // read orderItem.note
                    orderItemNote = orderItem.getNote();
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
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.loadImage(this.imgProduct, orderItemProductImage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtNote, orderItemNote);
            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.txtProductCost, orderItemPrice);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtProductName, orderItemProductName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtProductSize, orderItemSize);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtQuantity, stringValueOfOrderItemQuantity);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtTopping, orderItemToppingString);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): orderItem
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}