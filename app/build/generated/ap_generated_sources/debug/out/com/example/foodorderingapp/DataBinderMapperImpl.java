package com.example.foodorderingapp;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.foodorderingapp.databinding.ActivityCheckoutAddressBindingImpl;
import com.example.foodorderingapp.databinding.ActivityCheckoutBindingImpl;
import com.example.foodorderingapp.databinding.ActivityCouponBindingImpl;
import com.example.foodorderingapp.databinding.BottomsheetEditCartBindingImpl;
import com.example.foodorderingapp.databinding.FragmentCartBindingImpl;
import com.example.foodorderingapp.databinding.FragmentNotificationBindingImpl;
import com.example.foodorderingapp.databinding.ViewholderAddressBindingImpl;
import com.example.foodorderingapp.databinding.ViewholderCartBindingImpl;
import com.example.foodorderingapp.databinding.ViewholderCouponBindingImpl;
import com.example.foodorderingapp.databinding.ViewholderNotificationBindingImpl;
import com.example.foodorderingapp.databinding.ViewholderOrderDetailBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCHECKOUT = 1;

  private static final int LAYOUT_ACTIVITYCHECKOUTADDRESS = 2;

  private static final int LAYOUT_ACTIVITYCOUPON = 3;

  private static final int LAYOUT_BOTTOMSHEETEDITCART = 4;

  private static final int LAYOUT_FRAGMENTCART = 5;

  private static final int LAYOUT_FRAGMENTNOTIFICATION = 6;

  private static final int LAYOUT_VIEWHOLDERADDRESS = 7;

  private static final int LAYOUT_VIEWHOLDERCART = 8;

  private static final int LAYOUT_VIEWHOLDERCOUPON = 9;

  private static final int LAYOUT_VIEWHOLDERNOTIFICATION = 10;

  private static final int LAYOUT_VIEWHOLDERORDERDETAIL = 11;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(11);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.activity_checkout, LAYOUT_ACTIVITYCHECKOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.activity_checkout_address, LAYOUT_ACTIVITYCHECKOUTADDRESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.activity_coupon, LAYOUT_ACTIVITYCOUPON);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.bottomsheet_edit_cart, LAYOUT_BOTTOMSHEETEDITCART);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.fragment_cart, LAYOUT_FRAGMENTCART);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.fragment_notification, LAYOUT_FRAGMENTNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.viewholder_address, LAYOUT_VIEWHOLDERADDRESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.viewholder_cart, LAYOUT_VIEWHOLDERCART);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.viewholder_coupon, LAYOUT_VIEWHOLDERCOUPON);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.viewholder_notification, LAYOUT_VIEWHOLDERNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.foodorderingapp.R.layout.viewholder_order_detail, LAYOUT_VIEWHOLDERORDERDETAIL);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCHECKOUT: {
          if ("layout/activity_checkout_0".equals(tag)) {
            return new ActivityCheckoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_checkout is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCHECKOUTADDRESS: {
          if ("layout/activity_checkout_address_0".equals(tag)) {
            return new ActivityCheckoutAddressBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_checkout_address is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCOUPON: {
          if ("layout/activity_coupon_0".equals(tag)) {
            return new ActivityCouponBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_coupon is invalid. Received: " + tag);
        }
        case  LAYOUT_BOTTOMSHEETEDITCART: {
          if ("layout/bottomsheet_edit_cart_0".equals(tag)) {
            return new BottomsheetEditCartBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for bottomsheet_edit_cart is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCART: {
          if ("layout/fragment_cart_0".equals(tag)) {
            return new FragmentCartBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_cart is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTNOTIFICATION: {
          if ("layout/fragment_notification_0".equals(tag)) {
            return new FragmentNotificationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_notification is invalid. Received: " + tag);
        }
        case  LAYOUT_VIEWHOLDERADDRESS: {
          if ("layout/viewholder_address_0".equals(tag)) {
            return new ViewholderAddressBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for viewholder_address is invalid. Received: " + tag);
        }
        case  LAYOUT_VIEWHOLDERCART: {
          if ("layout/viewholder_cart_0".equals(tag)) {
            return new ViewholderCartBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for viewholder_cart is invalid. Received: " + tag);
        }
        case  LAYOUT_VIEWHOLDERCOUPON: {
          if ("layout/viewholder_coupon_0".equals(tag)) {
            return new ViewholderCouponBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for viewholder_coupon is invalid. Received: " + tag);
        }
        case  LAYOUT_VIEWHOLDERNOTIFICATION: {
          if ("layout/viewholder_notification_0".equals(tag)) {
            return new ViewholderNotificationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for viewholder_notification is invalid. Received: " + tag);
        }
        case  LAYOUT_VIEWHOLDERORDERDETAIL: {
          if ("layout/viewholder_order_detail_0".equals(tag)) {
            return new ViewholderOrderDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for viewholder_order_detail is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(10);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "cartVM");
      sKeys.put(2, "checkoutVM");
      sKeys.put(3, "coupon");
      sKeys.put(4, "couponVM");
      sKeys.put(5, "notification");
      sKeys.put(6, "notificationVM");
      sKeys.put(7, "orderItem");
      sKeys.put(8, "product");
      sKeys.put(9, "userAddress");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(11);

    static {
      sKeys.put("layout/activity_checkout_0", com.example.foodorderingapp.R.layout.activity_checkout);
      sKeys.put("layout/activity_checkout_address_0", com.example.foodorderingapp.R.layout.activity_checkout_address);
      sKeys.put("layout/activity_coupon_0", com.example.foodorderingapp.R.layout.activity_coupon);
      sKeys.put("layout/bottomsheet_edit_cart_0", com.example.foodorderingapp.R.layout.bottomsheet_edit_cart);
      sKeys.put("layout/fragment_cart_0", com.example.foodorderingapp.R.layout.fragment_cart);
      sKeys.put("layout/fragment_notification_0", com.example.foodorderingapp.R.layout.fragment_notification);
      sKeys.put("layout/viewholder_address_0", com.example.foodorderingapp.R.layout.viewholder_address);
      sKeys.put("layout/viewholder_cart_0", com.example.foodorderingapp.R.layout.viewholder_cart);
      sKeys.put("layout/viewholder_coupon_0", com.example.foodorderingapp.R.layout.viewholder_coupon);
      sKeys.put("layout/viewholder_notification_0", com.example.foodorderingapp.R.layout.viewholder_notification);
      sKeys.put("layout/viewholder_order_detail_0", com.example.foodorderingapp.R.layout.viewholder_order_detail);
    }
  }
}
