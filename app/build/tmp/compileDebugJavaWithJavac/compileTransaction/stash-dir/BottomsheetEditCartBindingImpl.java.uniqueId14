package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class BottomsheetEditCartBindingImpl extends BottomsheetEditCartBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.size_area, 6);
        sViewsWithIds.put(R.id.size_heading, 7);
        sViewsWithIds.put(R.id.size_describe, 8);
        sViewsWithIds.put(R.id.size_group, 9);
        sViewsWithIds.put(R.id.tv_small_price, 10);
        sViewsWithIds.put(R.id.tv_medium_price, 11);
        sViewsWithIds.put(R.id.tv_big_price, 12);
        sViewsWithIds.put(R.id.radioGroup, 13);
        sViewsWithIds.put(R.id.topping_area, 14);
        sViewsWithIds.put(R.id.topping_heading, 15);
        sViewsWithIds.put(R.id.topping_describe, 16);
        sViewsWithIds.put(R.id.topping_container, 17);
        sViewsWithIds.put(R.id.checkbox, 18);
        sViewsWithIds.put(R.id.tv_topping_price, 19);
        sViewsWithIds.put(R.id.note_heading, 20);
        sViewsWithIds.put(R.id.btn_confirm, 21);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public BottomsheetEditCartBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private BottomsheetEditCartBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[21]
            , (android.widget.CheckBox) bindings[18]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[1]
            , (android.widget.RadioGroup) bindings[13]
            , (android.widget.RadioButton) bindings[4]
            , (android.widget.RadioButton) bindings[3]
            , (android.widget.RadioButton) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[6]
            , (android.widget.TextView) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[9]
            , (android.widget.TextView) bindings[7]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[14]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[17]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[19]
            , (android.widget.EditText) bindings[5]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.productHeading.setTag(null);
        this.rbLarge.setTag(null);
        this.rbMedium.setTag(null);
        this.rbSmall.setTag(null);
        this.txtNote.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        else if (BR.product == variableId) {
            setProduct((com.example.foodorderingapp.data.model.entity.Product) variable);
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
    public void setProduct(@Nullable com.example.foodorderingapp.data.model.entity.Product Product) {
        this.mProduct = Product;
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
        boolean orderItemSizeEqualsRbSmallAndroidStringSmallSize = false;
        com.example.foodorderingapp.data.model.entity.OrderItem orderItem = mOrderItem;
        boolean orderItemSizeEqualsRbLargeAndroidStringLargeSize = false;
        java.lang.String orderItemNote = null;
        java.lang.String orderItemProductName = null;
        boolean orderItemSizeEqualsRbMediumAndroidStringMediumSize = false;
        java.lang.String orderItemSize = null;

        if ((dirtyFlags & 0x9L) != 0) {



                if (orderItem != null) {
                    // read orderItem.note
                    orderItemNote = orderItem.getNote();
                    // read orderItem.productName
                    orderItemProductName = orderItem.getProductName();
                    // read orderItem.size
                    orderItemSize = orderItem.getSize();
                }


                if (orderItemSize != null) {
                    // read orderItem.size.equals(@android:string/small_size)
                    orderItemSizeEqualsRbSmallAndroidStringSmallSize = orderItemSize.equals(rbSmall.getResources().getString(R.string.small_size));
                    // read orderItem.size.equals(@android:string/large_size)
                    orderItemSizeEqualsRbLargeAndroidStringLargeSize = orderItemSize.equals(rbLarge.getResources().getString(R.string.large_size));
                    // read orderItem.size.equals(@android:string/medium_size)
                    orderItemSizeEqualsRbMediumAndroidStringMediumSize = orderItemSize.equals(rbMedium.getResources().getString(R.string.medium_size));
                }
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.productHeading, orderItemProductName);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rbLarge, orderItemSizeEqualsRbLargeAndroidStringLargeSize);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rbMedium, orderItemSizeEqualsRbMediumAndroidStringMediumSize);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rbSmall, orderItemSizeEqualsRbSmallAndroidStringSmallSize);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtNote, orderItemNote);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): orderItem
        flag 1 (0x2L): product
        flag 2 (0x3L): cartVM
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}