package com.example.phonestop.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.phonestop.Activity.MainActivity;
import com.example.phonestop.Adapters.CartAdapter;
import com.example.phonestop.AfterPurchaseFragment;
import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Interface.ChangeNumberItemsListener;
import com.example.phonestop.Models.Product;
import com.example.phonestop.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private TabLayout tabLayout;
    private TabLayout.OnTabSelectedListener listener;
    private TextView emptyTxt;
    private RecyclerView cartView;
    private TextView totalFeeTxt;
    private TextView deliveryTxt;
    private TextView totalTxt;
    private AppCompatButton checkOutBTN;
    private ScrollView scrollViewCart;
    private ManagmentCart managmentCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        findViews(view);
        managmentCart = ManagmentCart.getInstance(getContext().getApplicationContext());
        calculatorCart();
        initCartList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save the cart to Firebase when the fragment's view is destroyed
        managmentCart.saveCartToFirebase();
    }

    private void calculatorCart() {
        double delivery = 49;
        double total = Math.round((managmentCart.getTotalFee()+delivery)*100)/100;
        double productTotal = Math.round(managmentCart.getTotalFee()*100)/100;
        totalFeeTxt.setText(productTotal + " nis");
        deliveryTxt.setText(delivery + "nis");
        totalTxt.setText(total + "nis");
    }

    public void updateCartUI(ArrayList<Product> products){
        CartAdapter adapter = (CartAdapter) cartView.getAdapter();
        if(adapter != null){
            adapter.updateProducts(products);
            adapter.notifyDataSetChanged();
        }

    }
    private void initCartList(){
        if(managmentCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollViewCart.setVisibility(View.GONE);
        }else {
            emptyTxt.setVisibility(View.GONE);
            scrollViewCart.setVisibility(View.VISIBLE);
        }

        cartView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculatorCart();
            }
        }));
}

    private void findViews(View view) {
        ImageView backButton = view.findViewById(R.id.button_back);
        emptyTxt = view.findViewById(R.id.emptyTxt);
        cartView = view.findViewById(R.id.cartView);
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        deliveryTxt = view.findViewById(R.id.deliveryTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        scrollViewCart = view.findViewById(R.id.scrollViewCart);
        checkOutBTN = view.findViewById(R.id.checkOutBTN);
        // Get the TabLayout from the parent activity
        tabLayout = getActivity().findViewById(R.id.tablayout);
        // Store the existing listener to reattach it later
        listener = ((MainActivity) getActivity()).getTabSelectedListener();
        backButton.setOnClickListener(v -> {
            // Simulate the back press
            handleBackPress();
        });

        checkOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                managmentCart.savePurchase();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment, new AfterPurchaseFragment());
                ft.addToBackStack(null);
                ft.commit();
                ManagmentCart.getInstance(getActivity()).initManagmentCart(getActivity());
            }
        });
    }

    private void handleBackPress() {
        // Remove the current fragment (CartFragment)
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }

        // Temporarily remove the OnTabSelectedListener
        if (tabLayout != null && listener != null) {
            tabLayout.removeOnTabSelectedListener(listener);
        }

        // Programmatically select the Home tab
        if (tabLayout != null) {
            TabLayout.Tab homeTab = tabLayout.getTabAt(0); // Assuming the Home tab is at position 0
            if (homeTab != null) {
                homeTab.select(); // This will now only highlight the tab without triggering the listener
            }
        }

        // Reattach the listener after selecting the tab
        if (tabLayout != null && listener != null) {
            tabLayout.addOnTabSelectedListener(listener);
        }
    }
}