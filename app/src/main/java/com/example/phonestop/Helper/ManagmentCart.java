package com.example.phonestop.Helper;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.phonestop.Interface.ChangeNumberItemsListener;
import com.example.phonestop.Models.Order;
import com.example.phonestop.Models.Product;
import com.example.phonestop.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagmentCart {
    private static ManagmentCart instance;
    private static ArrayList<Product> product_list;
    private static Context context;
    private User currentUser;
    private final double delivery = 49;
    private DatabaseReference cartRef;
    private String userId;

    private ManagmentCart(Context other){
        product_list = new ArrayList<>();
        context = other.getApplicationContext();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();  // Get the current user's ID
        cartRef = FirebaseDatabase.getInstance().getReference("cartProd").child(userId);
        loadUserData(userId);
    }
    public static synchronized ManagmentCart getInstance(Context context) {
        if (instance == null) {
            instance = new ManagmentCart(context);
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private User createUserObject(FirebaseUser firebaseUser){
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        String displayName = firebaseUser.getDisplayName();

        return new User()
                .setDisplayName(displayName)
                .setEmail(email)
                .setUid(uid);
    }

    public void saveUserToDataBase(){
        if (currentUser != null) {
            // Get a reference to the Firebase Realtime Database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

            // Save the user under their authId
            userRef.child(currentUser.getUid()).setValue(currentUser);
        }
    }

    public Order saveOrderToDatabase(){
        // Initialize Firebase Database reference
        String orderId = FirebaseDatabase.getInstance().getReference("Orders").push().getKey();
        Order order = new Order(currentUser, product_list);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        order.setId(orderId);
        double total = Math.round((getTotalFee()+delivery)*100)/100;
        order.setTotal_price(total);
        // Save the order under the Orders node with the generated orderId
        databaseReference.child(orderId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Firebase", "Order saved successfully with ID: " + orderId);
                } else {
                    Log.e("FirebaseError", "Failed to save order: " + task.getException().getMessage());
                }
            }
        });

        product_list = new ArrayList<>();
        return order;
    }

    public void saveCartToFirebase() {
        if(product_list != null){
            cartRef.setValue(product_list).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    }
    private void loadUserData(String authId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(authId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    currentUser = user;
                } else {
                    currentUser = createUserObject(FirebaseAuth.getInstance().getCurrentUser());
                    Log.d("LoadUser", "User data not found in the database.");
                }
                saveUserToDataBase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Log.e("LoadUserError", "Error loading user data: " + databaseError.getMessage());
            }
        });
    }

    public void loadUserFromDataBase(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            // Get a reference to the user's data in Firebase
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

            // Attach a listener to read the data
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        currentUser = user;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });
        }
    }

    // Method to load the cart from Firebase
    public void loadCartFromFirebase(final CartLoadCallback callback) {
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                product_list.clear();  // Clear the local cart to load the data fresh
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        product_list.add(product);
                    }
                }
                callback.onCartLoaded(product_list);
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

    public void clearProducts(){
        if (product_list != null) {
            product_list.clear();
        }
    }

    public void clear(){
        if (product_list != null) {
            product_list.clear();
        }

        instance = null;
        product_list = null;
        context = null;
        userId = null;
        currentUser = null;
        cartRef = null;

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
