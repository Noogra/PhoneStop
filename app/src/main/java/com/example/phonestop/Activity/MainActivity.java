package com.example.phonestop.Activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.phonestop.Data.DataManager;
import com.example.phonestop.Fragments.CategoryFragment;

import com.example.phonestop.Fragments.ProductFragment;
import com.example.phonestop.Fragments.ProductInfoFragment;
import com.example.phonestop.Fragments.UserFragment;
import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Models.Category;
import com.example.phonestop.Models.Product;
import com.example.phonestop.R;
import com.example.phonestop.Fragments.CartFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements CategoryFragment.CategoryClickHandler, ProductFragment.ProductClickHandler{

    public static String FRAG_CAT_TAG = "catTag";
    public static String FRAG_SUBCAT_TAG = "subcCatTag";
    public static String FRAG_PROD_TAG = "prodTag";
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private ManagmentCart managmentCart;

    private TabLayout.OnTabSelectedListener tabSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        managmentCart = ManagmentCart.getInstance(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            currentFragment = CategoryFragment.newInstance(DataManager.getDomainCategories());
            transaction.add(R.id.fragment, currentFragment, FRAG_CAT_TAG);
            transaction.commit();
        }
        findViews();
        initViews();

        loadCart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        managmentCart.saveCartToFirebase();
    }


    private void loadCart() {
        managmentCart.loadCartFromFirebase(new ManagmentCart.CartLoadCallback() {
            @Override
            public void onCartLoaded(ArrayList<Product> products) {
                CartFragment cartFragment = (CartFragment) getSupportFragmentManager().findFragmentById(R.id.cartFragment);
                if (cartFragment != null) {
                    cartFragment.updateCartUI(products); // Call the method in CartFragment
                }
            }

            @Override
            public void onCartLoadFailed(Exception e) {
                Toast.makeText(MainActivity.this, "Failed to load cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public TabLayout.OnTabSelectedListener getTabSelectedListener() {
        return tabSelectedListener;
    }

    private void initViews() {
        tabSelectedListener = new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch(tab.getPosition()){
                    case 0: //Home tab
                        fragment = CategoryFragment.newInstance(DataManager.getDomainCategories());
                        break;
                    case 1: //Cart tab
                        fragment = new CartFragment();
                        break;

                    case 2: //User tab
                        fragment = new UserFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch(tab.getPosition()){
                    case 0: //Home tab
                        fragment = CategoryFragment.newInstance(DataManager.getDomainCategories());
                        break;
                    case 1: //Cart tab
                        fragment = new CartFragment();
                        break;

                    case 2: //User tab
                            fragment = new UserFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        };
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

    }

    private void findViews() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
    }

    @Override
    public void onCategoryClick(Category category) {
        FragmentManager fm = getSupportFragmentManager();
        if(!category.isSubCategory()){
            CategoryFragment catFrag = CategoryFragment.newInstance(DataManager.getSubCategoriesFromCategory(category));
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment, catFrag,FRAG_CAT_TAG);
            ft.addToBackStack(null);
            ft.commit();
        }
        else {
            ProductFragment productFrag = ProductFragment.newInstance(DataManager.getProductsFromCategory(category));
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment, productFrag,FRAG_SUBCAT_TAG);
            ft.addToBackStack(null);
            ft.commit();
        }


    }

    @Override
    public void onProductClick(Product product) {
        FragmentManager fm = getSupportFragmentManager();
        ProductInfoFragment productInfoFragment = ProductInfoFragment.newInstance(product);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, productInfoFragment, FRAG_PROD_TAG);
        ft.addToBackStack(null);
        ft.commit();
    }
}