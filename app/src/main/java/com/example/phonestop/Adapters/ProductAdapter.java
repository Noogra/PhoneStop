package com.example.phonestop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private final ArrayList<Product> products;
    private ProductClickListener productClickListener;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public ProductAdapter(ArrayList<Product> products, Context context, ChangeNumberItemsListener changeNumberItemsListener){
        this.products = products;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_product_item, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = getItem(position);
        ImageLoader.getInstance().load(product.getImg_url(), holder.product_img_id);
        holder.product_name_text_id.setText(product.getName());
        holder.product_description_text_id.setText(product.getDescription());
        holder.product_price_text_id.setText("$" + product.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }
    private Product getItem(int position) {
        return products.get(position);
    }

    public ProductAdapter setProductClickListener(ProductClickListener productClickListener) {
        this.productClickListener = productClickListener;
        return this;
    }

    public interface ProductClickListener {
        void onProductClick(Product product);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView product_img_id;
        private final Button cart_BTN;
        private final TextView product_name_text_id;
        private final TextView product_description_text_id;
        private final TextView product_price_text_id;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img_id = itemView.findViewById(R.id.product_img_id);
            cart_BTN = itemView.findViewById(R.id.cart_BTN1);
            product_description_text_id = itemView.findViewById(R.id.product_description_text_id);
            product_price_text_id = itemView.findViewById(R.id.product_price_text_id);
            product_name_text_id = itemView.findViewById(R.id.product_name_text_id);

            cart_BTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product =  products.get(getAdapterPosition());
                    ManagmentCart managmentCart = ManagmentCart.getInstance(v.getContext().getApplicationContext());
                    int currNumber = product.getNumberinCart();
                    product.setNumberinCart(currNumber + 1);
                    managmentCart.insertProduct(products.get(getAdapterPosition()));

                }
            });

            itemView.setOnClickListener(v -> {
                if (productClickListener != null) {
                    productClickListener.onProductClick(products.get(getAdapterPosition()));
                }
            });

        }
    }
}

