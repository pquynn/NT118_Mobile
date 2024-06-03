package com.example.foodorderingapp.databinding;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ViewholderNotificationBindingImpl extends ViewholderNotificationBinding  {

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

    public ViewholderNotificationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ViewholderNotificationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[4]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            );
        this.txtDate.setTag(null);
        this.txtNotiMessage.setTag(null);
        this.txtNotiName.setTag(null);
        this.viewholderNotification.setTag(null);
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
        if (BR.notification == variableId) {
            setNotification((com.example.foodorderingapp.data.model.entity.Notification) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setNotification(@Nullable com.example.foodorderingapp.data.model.entity.Notification Notification) {
        this.mNotification = Notification;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.notification);
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
        com.example.foodorderingapp.data.model.entity.Notification notification = mNotification;
        java.lang.String notificationTitle = null;
        java.lang.String notificationContent = null;
        java.util.Date notificationDate = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (notification != null) {
                    // read notification.title
                    notificationTitle = notification.getTitle();
                    // read notification.content
                    notificationContent = notification.getContent();
                    // read notification.date
                    notificationDate = notification.getDate();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setDateFormatted(this.txtDate, notificationDate);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtNotiMessage, notificationContent);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtNotiName, notificationTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): notification
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}