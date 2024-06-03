package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ViewholderCouponBindingImpl extends ViewholderCouponBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.green_rec, 4);
        sViewsWithIds.put(R.id.img_coupon, 5);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ViewholderCouponBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ViewholderCouponBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[4]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            );
        this.txtCouponName.setTag(null);
        this.txtDescription.setTag(null);
        this.txtValidDate.setTag(null);
        this.viewholderCoupon.setTag(null);
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
        if (BR.couponVM == variableId) {
            setCouponVM((com.example.foodorderingapp.ui.viewmodel.customer.coupon.CouponViewModel) variable);
        }
        else if (BR.coupon == variableId) {
            setCoupon((com.example.foodorderingapp.data.model.entity.Coupon) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCouponVM(@Nullable com.example.foodorderingapp.ui.viewmodel.customer.coupon.CouponViewModel CouponVM) {
        this.mCouponVM = CouponVM;
    }
    public void setCoupon(@Nullable com.example.foodorderingapp.data.model.entity.Coupon Coupon) {
        this.mCoupon = Coupon;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.coupon);
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
        java.util.Date couponValidTo = null;
        com.example.foodorderingapp.data.model.entity.Coupon coupon = mCoupon;
        java.lang.String couponDescription = null;
        java.lang.String couponCouponName = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (coupon != null) {
                    // read coupon.validTo
                    couponValidTo = coupon.getValidTo();
                    // read coupon.description
                    couponDescription = coupon.getDescription();
                    // read coupon.couponName
                    couponCouponName = coupon.getCouponName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtCouponName, couponCouponName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtDescription, couponDescription);
            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setDateFormatted(this.txtValidDate, couponValidTo);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): couponVM
        flag 1 (0x2L): coupon
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}