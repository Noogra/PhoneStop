package com.example.phonestop.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestop.Models.Category;
import com.example.phonestop.R;
import com.example.phonestop.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);
        ImageLoader.getInstance().load(category.getImg(), holder.category_img_id);
        holder.category_name_id.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories == null? 0 : categories.size();
    }

    private Category getItem(int position){
        return categories.get(position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private CardView category_card_id;
        private final TextView category_name_id;
        private final ShapeableImageView category_img_id;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_card_id = itemView.findViewById(R.id.category_card_id);
            category_name_id = itemView.findViewById(R.id.category_name_id);
            category_img_id = itemView.findViewById(R.id.category_img_id);
        }
    }
}
