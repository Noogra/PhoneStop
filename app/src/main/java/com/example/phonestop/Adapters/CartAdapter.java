package com.example.phonestop.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Interface.ChangeNumberItemsListener;
import com.example.phonestop.Models.Product;
import com.example.phonestop.R;
import com.example.phonestop.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<Product> products;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Product> product_selected_list, ChangeNumberItemsListener changeNumberItemsListener) {
        this.products = product_selected_list;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    // Method to update the products list
    public void updateProducts(ArrayList<Product> products) {
        this.products.clear(); // Clear the existing data
        this.products.addAll(products); // Add the new data
        notifyDataSetChanged(); // Notify the adapter to refresh the UI
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Product product = getItem(position);
        ImageLoader.getInstance().load(product.getImg_url(), holder.product_img_id);
        holder.cart_title_text.setText(product.getName());
        holder.cart_item_price_text.setText(product.getPrice().toString() + " nis");
        holder.cart_feeEachItem_text.setText(Math.round((products.get(position).getNumberinCart() * products.get(position).getPrice())) + " nis");
        holder.cart_item_num_text.setText(String.valueOf(products.get(position).getNumberinCart()));

        holder.plusCartBtn.setOnClickListener(v -> ManagmentCart.getInstance(v.getContext().getApplicationContext()).plusItem(products, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagmentCart.getInstance(v.getContext().getApplicationContext()).minusItem(products, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }



    private Product getItem(int position) {
        return products.get(position);
    }


    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }



    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView product_img_id;
        private TextView cart_title_text;
        private TextView cart_feeEachItem_text;
        private TextView cart_item_price_text;
        private TextView plusCartBtn;
        private TextView cart_item_num_text;
        private TextView minusCartBtn;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img_id = itemView.findViewById(R.id.product_img_id);
            cart_title_text = itemView.findViewById(R.id.cart_title_text);
            cart_feeEachItem_text = itemView.findViewById(R.id.cart_feeEachItem_text);
            cart_item_price_text = itemView.findViewById(R.id.cart_item_price_text);
            plusCartBtn = itemView.findViewById(R.id.plusCartBtn);
            cart_item_num_text = itemView.findViewById(R.id.cart_item_num_text);
            minusCartBtn = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
