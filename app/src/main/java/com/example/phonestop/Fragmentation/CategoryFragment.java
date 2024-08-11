package com.example.phonestop.Fragmentation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestop.Adapters.CategoryAdapter;
import com.example.phonestop.Data.DataManager;
import com.example.phonestop.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private RecyclerView category_list_items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.category_card_list_fragment, container, false);
        findViews(v);
        initViews();

        return v;
    }

    private void findViews(View v) {
        category_list_items = v.findViewById(R.id.category_list_items);
        CategoryAdapter categoryAdapter = new CategoryAdapter(DataManager.getCategories());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        category_list_items.setLayoutManager(linearLayoutManager);
        category_list_items.setNestedScrollingEnabled(false);
        category_list_items.setAdapter(categoryAdapter);

    }

    private void initViews() {
    }

}

