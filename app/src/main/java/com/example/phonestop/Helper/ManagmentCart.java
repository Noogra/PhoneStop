package com.example.phonestop.Helper;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.phonestop.Interface.ChangeNumberItemsListener;
import com.example.phonestop.Models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagmentCart {
    private static ManagmentCart instance;
    private static ArrayList<Product> product_list;
    private static Context context;

    //Firebase
    private DatabaseReference databaseReference;


    private ManagmentCart(Context other){
        product_list = new ArrayList<>();
        context = other.getApplicationContext();
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("cartProd");
        // In CartManager constructor or method:
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();  // Get the current user's ID
        databaseReference = FirebaseDatabase.getInstance().getReference("cartProd").child(userId);
    }
    public static synchronized ManagmentCart getInstance(Context context) {
        if (instance == null) {
            instance = new ManagmentCart(context);
        }
        return instance;
    }

    public void initManagmentCart(Context context){
        product_list = new ArrayList<>();
    }
    // Save the cart to Firebase
    public void saveCartToFirebase() {
        databaseReference.setValue(product_list).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Firebase", "Cart saved successfully.");
                } else {
                    Log.e("FirebaseError", "Error saving cart: " + task.getException().getMessage());
                }
            }
        });
    }

    // Method to load the cart from Firebase
    public void loadCartFromFirebase(final CartLoadCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ArrayList<Product> products = new ArrayList<>();
                product_list.clear();  // Clear the local cart to load the data fresh
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        product_list.add(product);
                    }
                }
                callback.onCartLoaded(product_list);
                //product_list = products;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCartLoadFailed(databaseError.toException());
            }
        });
    }

    // Interface for callbacks to notify when the cart is loaded
    public interface CartLoadCallback {
        void onCartLoaded(ArrayList<Product> products);
        void onCartLoadFailed(Exception e);
    }

    public void insertProduct(Product item) {
        boolean existAlready = false;
        int n = 0;
        for (int y = 0; y < product_list.size(); y++) {
            if (product_list.get(y).getName().equals(item.getName())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        if (existAlready) {
            product_list.get(n).setNumberinCart(item.getNumberinCart());
        } else {
            product_list.add(item);
        }
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Product> getListCart() {
        if(product_list == null){
            product_list = new ArrayList<>();
        }
        return product_list;
    }

    public void minusItem(ArrayList<Product> product_list, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (product_list.get(position).getNumberinCart() == 1) {
            product_list.remove(position);
        } else {
            product_list.get(position).setNumberinCart(product_list.get(position).getNumberinCart() - 1);
        }
        changeNumberItemsListener.changed();
    }

    public void plusItem(ArrayList<Product> product_list, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        product_list.get(position).setNumberinCart(product_list.get(position).getNumberinCart() + 1);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        if(product_list == null){
            return 0d;
        }
        double fee = 0;
        for (int i = 0; i < product_list.size(); i++) {
            fee = fee + (product_list.get(i).getPrice() * product_list.get(i).getNumberinCart());
        }
        return fee;
    }
}
