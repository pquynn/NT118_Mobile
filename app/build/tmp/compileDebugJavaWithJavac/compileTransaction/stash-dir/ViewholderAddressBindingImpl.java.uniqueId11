package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ViewholderAddressBindingImpl extends ViewholderAddressBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.white_circle, 4);
        sViewsWithIds.put(R.id.img_location, 5);
        sViewsWithIds.put(R.id.btn_edit, 6);
        sViewsWithIds.put(R.id.btn_delete, 7);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ViewholderAddressBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private ViewholderAddressBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.FrameLayout) bindings[4]
            );
        this.txtFullAddress.setTag(null);
        this.txtRecipientName.setTag(null);
        this.txtRecipientPhone.setTag(null);
        this.viewholderAddress.setTag(null);
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
        if (BR.userAddress == variableId) {
            setUserAddress((com.example.foodorderingapp.data.model.entity.UserAddress) variable);
        }
        else if (BR.checkoutVM == variableId) {
            setCheckoutVM((com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setUserAddress(@Nullable com.example.foodorderingapp.data.model.entity.UserAddress UserAddress) {
        this.mUserAddress = UserAddress;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.userAddress);
        super.requestRebind();
    }
    public void setCheckoutVM(@Nullable com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel CheckoutVM) {
        this.mCheckoutVM = CheckoutVM;
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
        java.lang.String userAddressRecipientName = null;
        java.lang.String userAddressAllAddress = null;
        java.lang.String userAddressRecipientPhone = null;
        com.example.foodorderingapp.data.model.entity.UserAddress userAddress = mUserAddress;

        if ((dirtyFlags & 0x5L) != 0) {



                if (userAddress != null) {
                    // read userAddress.recipientName
                    userAddressRecipientName = userAddress.getRecipientName();
                    // read userAddress.allAddress
                    userAddressAllAddress = userAddress.getAllAddress();
                    // read userAddress.recipientPhone
                    userAddressRecipientPhone = userAddress.getRecipientPhone();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtFullAddress, userAddressAllAddress);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtRecipientName, userAddressRecipientName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtRecipientPhone, userAddressRecipientPhone);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): userAddress
        flag 1 (0x2L): checkoutVM
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}