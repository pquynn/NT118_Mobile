package com.example.foodorderingapp.ui.viewmodel.customer.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.CategoryList;
import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.category.CategoryRepository;
import com.example.foodorderingapp.data.repository.category.ICategoryRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CategoryViewModel extends ViewModel {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private MutableLiveData<List<CategoryList>> categoryListProductLiveData;
    private MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
        productRepository = new ProductRepository();
        categoryListProductLiveData = new MutableLiveData<>();
        loadCategories();
        loadProductList();
    }

    //khai báo cho danh mục
    public LiveData<List<Category>> getCategoryList(){
        return categoryListLiveData;
    }
    //khai báo cho sản phẩm từng danh mục
    public LiveData<List<CategoryList>> getProductListLiveData() {
        return categoryListProductLiveData;
    }

    //load sữ liệu danh sách danh mục
    private void loadCategories() {
        categoryRepository = new CategoryRepository();
        categoryRepository.getListCategory(new ICategoryRepository.CategoryListCallBack() {
            @Override
            public void onCategoryListLoaded(List<Category> categoryList) {
                categoryListLiveData.setValue(categoryList);
            }

            @Override
            public void onCategoryListLoadFailed(Exception exception) {

            }
        });
    }

    //load danh sách sản phẩm theo từng danh mục
    public void loadProductList() {
        // Sử dụng repository để lấy danh sách sản phẩm theo categoryId
        categoryRepository.getListCategory(new ICategoryRepository.CategoryListCallBack() {
            @Override
            public void onCategoryListLoaded(List<Category> categoryList) {
                List<CategoryList> categoryLists = new ArrayList<>();
                for (Category category: categoryList){
                    productRepository.getProductsByCategory(category.getId(), new IProductRepository.ProductListCallback() {
                        @Override
                        public void onProductListLoaded(List<Product> productList) {
                            CategoryList categoryListItem = new CategoryList(category, productList);
                            categoryLists.add(categoryListItem);
                            categoryListProductLiveData.setValue(categoryLists);
                        }

                        @Override
                        public void onProductListLoadFailed(String errorMessage) {

                        }
                    });
                }
            }

            @Override
            public void onCategoryListLoadFailed(Exception exception) {

            }
        });
    }
}
