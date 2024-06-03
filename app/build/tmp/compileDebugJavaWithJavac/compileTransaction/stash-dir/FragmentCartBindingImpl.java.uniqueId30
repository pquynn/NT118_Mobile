package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCartBindingImpl extends FragmentCartBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearLayout5, 3);
        sViewsWithIds.put(R.id.txt_total, 4);
        sViewsWithIds.put(R.id.btn_checkout, 5);
        sViewsWithIds.put(R.id.scrollview, 6);
        sViewsWithIds.put(R.id.recyclerViewCart, 7);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCartBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private FragmentCartBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.Button) bindings[5]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (android.widget.ScrollView) bindings[6]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[4]
            );
        this.linearLayout4.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.txtPrice.setTag(null);
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
        if (BR.cartVM == variableId) {
            setCartVM((com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCartVM(@Nullable com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel CartVM) {
        this.mCartVM = CartVM;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.cartVM);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeCartVMTotalPrice((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeCartVMTotalPrice(androidx.lifecycle.MutableLiveData<java.lang.Integer> CartVMTotalPrice, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
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
        androidx.lifecycle.MutableLiveData<java.lang.Integer> cartVMTotalPrice = null;
        com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel cartVM = mCartVM;
        java.lang.Integer cartVMTotalPriceGetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxCartVMTotalPriceGetValue = 0;

        if ((dirtyFlags & 0x7L) != 0) {



                if (cartVM != null) {
                    // read cartVM.totalPrice
                    cartVMTotalPrice = cartVM.getTotalPrice();
                }
                updateLiveDataRegistration(0, cartVMTotalPrice);


                if (cartVMTotalPrice != null) {
                    // read cartVM.totalPrice.getValue()
                    cartVMTotalPriceGetValue = cartVMTotalPrice.getValue();
                }


                // read androidx.databinding.ViewDataBinding.safeUnbox(cartVM.totalPrice.getValue())
                androidxDatabindingViewDataBindingSafeUnboxCartVMTotalPriceGetValue = androidx.databinding.ViewDataBinding.safeUnbox(cartVMTotalPriceGetValue);
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted(this.txtPrice, androidxDatabindingViewDataBindingSafeUnboxCartVMTotalPriceGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): cartVM.totalPrice
        flag 1 (0x2L): cartVM
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}