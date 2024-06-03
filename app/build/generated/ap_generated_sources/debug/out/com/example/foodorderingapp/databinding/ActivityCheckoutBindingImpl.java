package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCheckoutBindingImpl extends ActivityCheckoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.order_info_container, 13);
        sViewsWithIds.put(R.id.order_info_heading, 14);
        sViewsWithIds.put(R.id.btn_change_address, 15);
        sViewsWithIds.put(R.id.order_details_container, 16);
        sViewsWithIds.put(R.id.product_heading, 17);
        sViewsWithIds.put(R.id.recyclerViewOrderDetail, 18);
        sViewsWithIds.put(R.id.payment_coupon_container, 19);
        sViewsWithIds.put(R.id.payment_heading, 20);
        sViewsWithIds.put(R.id.btn_see_payment, 21);
        sViewsWithIds.put(R.id.coupon_heading, 22);
        sViewsWithIds.put(R.id.icon2, 23);
        sViewsWithIds.put(R.id.btn_choose_point, 24);
        sViewsWithIds.put(R.id.icon3, 25);
        sViewsWithIds.put(R.id.coupon, 26);
        sViewsWithIds.put(R.id.btn_see_coupons, 27);
        sViewsWithIds.put(R.id.price_overview_container, 28);
        sViewsWithIds.put(R.id.total, 29);
        sViewsWithIds.put(R.id.choosen_point, 30);
        sViewsWithIds.put(R.id.choosen_coupon, 31);
        sViewsWithIds.put(R.id.final_price, 32);
        sViewsWithIds.put(R.id.btn_buy, 33);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCheckoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }
    private ActivityCheckoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 9
            , (android.widget.Button) bindings[33]
            , (android.widget.ImageView) bindings[15]
            , (android.widget.Switch) bindings[24]
            , (android.widget.ImageView) bindings[27]
            , (android.widget.ImageView) bindings[21]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[32]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[23]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.LinearLayout) bindings[16]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[13]
            , (android.widget.TextView) bindings[14]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[6]
            , (android.widget.LinearLayout) bindings[28]
            , (android.widget.TextView) bindings[17]
            , (androidx.recyclerview.widget.RecyclerView) bindings[18]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[7]
            );
        this.icon1.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.paymentMethod.setTag(null);
        this.totalPrice.setTag(null);
        this.totalQuantity.setTag(null);
        this.txtCouponToPrice.setTag(null);
        this.txtFinalPrice.setTag(null);
        this.txtFullAddress.setTag(null);
        this.txtPointToPrice.setTag(null);
        this.txtRecipientName.setTag(null);
        this.txtRecipientPhone.setTag(null);
        this.userPoint.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x400L;
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
        if (BR.checkoutVM == variableId) {
            setCheckoutVM((com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCheckoutVM(@Nullable com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel CheckoutVM) {
        this.mCheckoutVM = CheckoutVM;
        synchronized(this) {
            mDirtyFlags |= 0x200L;
        }
        notifyPropertyChanged(BR.checkoutVM);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeCheckoutVMDiscountValueLiveData((androidx.lifecycle.MutableLiveData<java.lang.Double>) object, fieldId);
            case 1 :
                return onChangeCheckoutVMUserAddressLiveData((androidx.lifecycle.MutableLiveData<com.example.foodorderingapp.data.model.entity.UserAddress>) object, fieldId);
            case 2 :
                return onChangeCheckoutVMOrderPriceLiveData((androidx.lifecycle.MutableLiveData<java.lang.Double>) object, fieldId);
            case 3 :
                return onChangeCheckoutVMTotalProductLiveData((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 4 :
                return onChangeCheckoutVMPointUsedLiveData((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 5 :
                return onChangeCheckoutVMTotalPrice((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 6 :
                return onChangeCheckoutVMPointTotalLiveData((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 7 :
                return onChangeCheckoutVMIconPaymentLiveData((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 8 :
                return onChangeCheckoutVMPaymentMethodLiveData((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeCheckoutVMDiscountValueLiveData(androidx.lifecycle.MutableLiveData<java.lang.Double> CheckoutVMDiscountValueLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMUserAddressLiveData(androidx.lifecycle.MutableLiveData<com.example.foodorderingapp.data.model.entity.UserAddress> CheckoutVMUserAddressLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMOrderPriceLiveData(androidx.lifecycle.MutableLiveData<java.lang.Double> CheckoutVMOrderPriceLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMTotalProductLiveData(androidx.lifecycle.MutableLiveData<java.lang.Integer> CheckoutVMTotalProductLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMPointUsedLiveData(androidx.lifecycle.MutableLiveData<java.lang.Integer> CheckoutVMPointUsedLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMTotalPrice(androidx.lifecycle.MutableLiveData<java.lang.Integer> CheckoutVMTotalPrice, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMPointTotalLiveData(androidx.lifecycle.MutableLiveData<java.lang.Integer> CheckoutVMPointTotalLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMIconPaymentLiveData(androidx.lifecycle.MutableLiveData<java.lang.Integer> CheckoutVMIconPaymentLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckoutVMPaymentMethodLiveData(androidx.lifecycle.MutableLiveData<java.lang.String> CheckoutVMPaymentMethodLiveData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
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
        int androidxDatabindingViewDataBindingSafeUnboxCheckoutVMIconPaymentLiveDataGetValue = 0;
        com.example.foodorderingapp.data.model.entity.UserAddress checkoutVMUserAddressLiveDataGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.Double> checkoutVMDiscountValueLiveData = null;
        androidx.lifecycle.MutableLiveData<com.example.foodorderingapp.data.model.entity.UserAddress> checkoutVMUserAddressLiveData = null;
        androidx.lifecycle.MutableLiveData<java.lang.Double> checkoutVMOrderPriceLiveData = null;
        java.lang.Integer checkoutVMIconPaymentLiveDataGetValue = null;
        java.lang.Integer checkoutVMPointTotalLiveDataGetValue = null;
        com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel checkoutVM = mCheckoutVM;
        java.lang.String checkoutVMUserAddressLiveDataRecipientName = null;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> checkoutVMTotalProductLiveData = null;
        java.lang.String checkoutVMPaymentMethodLiveDataGetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckoutVMPointUsedLiveDataGetValue = 0;
        java.lang.String checkoutVMUserAddressLiveDataAllAddress = null;
        double androidxDatabindingViewDataBindingSafeUnboxCheckoutVMOrderPriceLiveDataGetValue = 0.0;
        java.lang.Integer checkoutVMPointUsedLiveDataGetValue = null;
        java.lang.Integer checkoutVMTotalProductLiveDataGetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckoutVMPointTotalLiveDataGetValue = 0;
        java.lang.Double checkoutVMDiscountValueLiveDataGetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckoutVMTotalProductLiveDataGetValue = 0;
        java.lang.Integer checkoutVMTotalPriceGetValue = null;
        double androidxDatabindingViewDataBindingSafeUnboxCheckoutVMDiscountValueLiveDataGetValue = 0.0;
        java.lang.String checkoutVMUserAddressLiveDataRecipientPhone = null;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> checkoutVMPointUsedLiveData = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckoutVMTotalPriceGetValue = 0;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> checkoutVMTotalPrice = null;
        java.lang.Double checkoutVMOrderPriceLiveDataGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> checkoutVMPointTotalLiveData = null;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> checkoutVMIconPaymentLiveData = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> checkoutVMPaymentMethodLiveData = null;

        if ((dirtyFlags & 0x7ffL) != 0) {


            if ((dirtyFlags & 0x601L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.discountValueLiveData
                        checkoutVMDiscountValueLiveData = checkoutVM.getDiscountValueLiveData();
                    }
                    updateLiveDataRegistration(0, checkoutVMDiscountValueLiveData);


                    if (checkoutVMDiscountValueLiveData != null) {
                        // read checkoutVM.discountValueLiveData.getValue()
                        checkoutVMDiscountValueLiveDataGetValue = checkoutVMDiscountValueLiveData.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.discountValueLiveData.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMDiscountValueLiveDataGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMDiscountValueLiveDataGetValue);
            }
            if ((dirtyFlags & 0x602L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.userAddressLiveData
                        checkoutVMUserAddressLiveData = checkoutVM.getUserAddressLiveData();
                    }
                    updateLiveDataRegistration(1, checkoutVMUserAddressLiveData);


                    if (checkoutVMUserAddressLiveData != null) {
                        // read checkoutVM.userAddressLiveData.getValue()
                        checkoutVMUserAddressLiveDataGetValue = checkoutVMUserAddressLiveData.getValue();
                    }


                    if (checkoutVMUserAddressLiveDataGetValue != null) {
                        // read checkoutVM.userAddressLiveData.getValue().recipientName
                        checkoutVMUserAddressLiveDataRecipientName = checkoutVMUserAddressLiveDataGetValue.getRecipientName();
                        // read checkoutVM.userAddressLiveData.getValue().allAddress
                        checkoutVMUserAddressLiveDataAllAddress = checkoutVMUserAddressLiveDataGetValue.getAllAddress();
                        // read checkoutVM.userAddressLiveData.getValue().recipientPhone
                        checkoutVMUserAddressLiveDataRecipientPhone = checkoutVMUserAddressLiveDataGetValue.getRecipientPhone();
                    }
            }
            if ((dirtyFlags & 0x604L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.orderPriceLiveData
                        checkoutVMOrderPriceLiveData = checkoutVM.getOrderPriceLiveData();
                    }
                    updateLiveDataRegistration(2, checkoutVMOrderPriceLiveData);


                    if (checkoutVMOrderPriceLiveData != null) {
                        // read checkoutVM.orderPriceLiveData.getValue()
                        checkoutVMOrderPriceLiveDataGetValue = checkoutVMOrderPriceLiveData.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.orderPriceLiveData.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMOrderPriceLiveDataGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMOrderPriceLiveDataGetValue);
            }
            if ((dirtyFlags & 0x608L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.totalProductLiveData
                        checkoutVMTotalProductLiveData = checkoutVM.getTotalProductLiveData();
                    }
                    updateLiveDataRegistration(3, checkoutVMTotalProductLiveData);


                    if (checkoutVMTotalProductLiveData != null) {
                        // read checkoutVM.totalProductLiveData.getValue()
                        checkoutVMTotalProductLiveDataGetValue = checkoutVMTotalProductLiveData.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.totalProductLiveData.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMTotalProductLiveDataGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMTotalProductLiveDataGetValue);
            }
            if ((dirtyFlags & 0x610L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.pointUsedLiveData
                        checkoutVMPointUsedLiveData = checkoutVM.getPointUsedLiveData();
                    }
                    updateLiveDataRegistration(4, checkoutVMPointUsedLiveData);


                    if (checkoutVMPointUsedLiveData != null) {
                        // read checkoutVM.pointUsedLiveData.getValue()
                        checkoutVMPointUsedLiveDataGetValue = checkoutVMPointUsedLiveData.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.pointUsedLiveData.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMPointUsedLiveDataGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMPointUsedLiveDataGetValue);
            }
            if ((dirtyFlags & 0x620L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.totalPrice
                        checkoutVMTotalPrice = checkoutVM.getTotalPrice();
                    }
                    updateLiveDataRegistration(5, checkoutVMTotalPrice);


                    if (checkoutVMTotalPrice != null) {
                        // read checkoutVM.totalPrice.getValue()
                        checkoutVMTotalPriceGetValue = checkoutVMTotalPrice.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.totalPrice.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMTotalPriceGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMTotalPriceGetValue);
            }
            if ((dirtyFlags & 0x640L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.pointTotalLiveData
                        checkoutVMPointTotalLiveData = checkoutVM.getPointTotalLiveData();
                    }
                    updateLiveDataRegistration(6, checkoutVMPointTotalLiveData);


                    if (checkoutVMPointTotalLiveData != null) {
                        // read checkoutVM.pointTotalLiveData.getValue()
                        checkoutVMPointTotalLiveDataGetValue = checkoutVMPointTotalLiveData.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.pointTotalLiveData.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMPointTotalLiveDataGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMPointTotalLiveDataGetValue);
            }
            if ((dirtyFlags & 0x680L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.iconPaymentLiveData
                        checkoutVMIconPaymentLiveData = checkoutVM.getIconPaymentLiveData();
                    }
                    updateLiveDataRegistration(7, checkoutVMIconPaymentLiveData);


                    if (checkoutVMIconPaymentLiveData != null) {
                        // read checkoutVM.iconPaymentLiveData.getValue()
                        checkoutVMIconPaymentLiveDataGetValue = checkoutVMIconPaymentLiveData.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkoutVM.iconPaymentLiveData.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxCheckoutVMIconPaymentLiveDataGetValue = androidx.databinding.ViewDataBinding.safeUnbox(checkoutVMIconPaymentLiveDataGetValue);
            }
            if ((dirtyFlags & 0x700L) != 0) {

                    if (checkoutVM != null) {
                        // read checkoutVM.paymentMethodLiveData
                        checkoutVMPaymentMethodLiveData = checkoutVM.getPaymentMethodLiveData();
                    }
                    updateLiveDataRegistration(8, checkoutVMPaymentMethodLiveData);


                    if (checkoutVMPaymentMethodLiveData != null) {
                        // read checkoutVM.paymentMethodLiveData.getValue()
                        checkoutVMPaymentMethodLiveDataGetValue = checkoutVMPaymentMethodLiveData.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x680L) != 0) {
            // api target 1

            this.icon1.setImageResource(androidxDatabindingViewDataBindingSafeUnboxCheckoutVMIconPaymentLiveDataGetValue);
        }
        if ((dirtyFlags & 0x700L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.paymentMethod, checkoutVMPaymentMethodLiveDataGetValue);
        }
        if ((dirtyFlags & 0x620L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.totalPrice, androidxDatabindingViewDataBindingSafeUnboxCheckoutVMTotalPriceGetValue);
        }
        if ((dirtyFlags & 0x608L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setProductNumbFormatted(this.totalQuantity, androidxDatabindingViewDataBindingSafeUnboxCheckoutVMTotalProductLiveDataGetValue);
        }
        if ((dirtyFlags & 0x601L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.txtCouponToPrice, androidxDatabindingViewDataBindingSafeUnboxCheckoutVMDiscountValueLiveDataGetValue);
        }
        if ((dirtyFlags & 0x604L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.txtFinalPrice, androidxDatabindingViewDataBindingSafeUnboxCheckoutVMOrderPriceLiveDataGetValue);
        }
        if ((dirtyFlags & 0x602L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtFullAddress, checkoutVMUserAddressLiveDataAllAddress);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtRecipientName, checkoutVMUserAddressLiveDataRecipientName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtRecipientPhone, checkoutVMUserAddressLiveDataRecipientPhone);
        }
        if ((dirtyFlags & 0x610L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.txtPointToPrice, androidxDatabindingViewDataBindingSafeUnboxCheckoutVMPointUsedLiveDataGetValue);
        }
        if ((dirtyFlags & 0x640L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPointFormatted(this.userPoint, androidxDatabindingViewDataBindingSafeUnboxCheckoutVMPointTotalLiveDataGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): checkoutVM.discountValueLiveData
        flag 1 (0x2L): checkoutVM.userAddressLiveData
        flag 2 (0x3L): checkoutVM.orderPriceLiveData
        flag 3 (0x4L): checkoutVM.totalProductLiveData
        flag 4 (0x5L): checkoutVM.pointUsedLiveData
        flag 5 (0x6L): checkoutVM.totalPrice
        flag 6 (0x7L): checkoutVM.pointTotalLiveData
        flag 7 (0x8L): checkoutVM.iconPaymentLiveData
        flag 8 (0x9L): checkoutVM.paymentMethodLiveData
        flag 9 (0xaL): checkoutVM
        flag 10 (0xbL): null
    flag mapping end*/
    //end
}