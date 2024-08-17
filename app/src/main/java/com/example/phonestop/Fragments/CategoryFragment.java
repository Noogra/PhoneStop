package com.example.phonestop.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestop.Adapters.CategoryAdapter;
import com.example.phonestop.Data.DataManager;
import com.example.phonestop.Models.Category;
import com.example.phonestop.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    private RecyclerView category_list_items;
    private View v;
    private CategoryClickHandler mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.category_card_list_fragment, container, false);
        findViews();

        return v;
    }

    private ArrayList<Category> retList;
    public static boolean first = true;

    private void findViews() {
        if(first)
        {
            retList = DataManager.getDomainCategories();
            first = false;
        }
        else
            retList = getArguments().getParcelableArrayList(ARG_CATEGORY_LIST);

        category_list_items = v.findViewById(R.id.category_list_items);
        CategoryAdapter categoryAdapter = new CategoryAdapter(retList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        category_list_items.setLayoutManager(linearLayoutManager);
        category_list_items.setNestedScrollingEnabled(false);
        category_list_items.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                if(mListener != null)
                {
                    mListener.onCategoryClick(retList.get(position));
                }
            }
        });
    }

    private static final String ARG_CATEGORY_LIST = "category_list";

    public static CategoryFragment newInstance(List<Category> categoryList) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CATEGORY_LIST, new ArrayList<>(categoryList));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryClickHandler) {
            mListener = (CategoryClickHandler) context;
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

    public interface CategoryClickHandler {
        void onCategoryClick(Category category);
    }




}

