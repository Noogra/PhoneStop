package com.example.phonestop.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phonestop.Adapters.ProductAdapter;
import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Interface.ChangeNumberItemsListener;
import com.example.phonestop.Models.Product;
import com.example.phonestop.R;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    private RecyclerView product_list_items;
    private ProductClickHandler mListener;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.product_list_fragment, container, false);
        findViews(v);
        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save the cart to Firebase when the fragment's view is destroyed
        ManagmentCart.getInstance(getContext().getApplicationContext()).saveCartToFirebase();
    }

    private void findViews(View v) {
        product_list_items = v.findViewById(R.id.product_list_items);
        ArrayList<Product> products = getArguments().getParcelableArrayList(ARG_PRODUCT_LIST);
        ProductAdapter productAdapter = new ProductAdapter(products, getActivity(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        product_list_items.setLayoutManager(linearLayoutManager);
        product_list_items.setNestedScrollingEnabled(false);
        product_list_items.setAdapter(productAdapter);

        productAdapter.setProductClickListener(new ProductAdapter.ProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                if(mListener != null){
                    mListener.onProductClick(product);
                }
            }
        });
    }

    private static final String ARG_PRODUCT_LIST = "product_list";

    public static ProductFragment newInstance(List<Product> productList) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRODUCT_LIST, new ArrayList<>(productList));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryFragment.CategoryClickHandler) {
            mListener = (ProductClickHandler) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OperandHandler");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ProductClickHandler{
        void onProductClick(Product product);
    }
}
