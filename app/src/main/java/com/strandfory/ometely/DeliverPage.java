package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class DeliverPage extends Activity{

    private static AdapterDeliverPage adapterDeliverPage;
    private static ArrayList<String> name;
    private static ArrayList<String> phone;
    private static ArrayList<String> address;
    private static ArrayList<Integer> price;
    private static boolean b;
    private DatabaseReference reference;
    private String KEY_ORDERS = "orders";
    private ArrayList<String> key;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_page);
        b = true;
        name = new ArrayList<>();
        phone = new ArrayList<>();
        address = new ArrayList<>();
        price = new ArrayList<>();
        getList();
        adapterDeliverPage = new AdapterDeliverPage(name,phone,address,price);
    }


    private void getList(){
        reference = FirebaseDatabase.getInstance().getReference(KEY_ORDERS);
        ValueEventListener valueEventListener = new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = new ArrayList<>();
                phone = new ArrayList<>();
                address = new ArrayList<>();
                price = new ArrayList<>();
                key = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    System.out.println("hey");
                    if((order.dateC != null) && (order.dateD == null)) {
                        System.out.println("heya");
                        key.add(ds.getKey());
                        System.out.println(key);
                        name.add(order.name);
                        phone.add(order.phone);
                        address.add(order.address);
                        price.add(order.price);
                    }
                }
                if(b){
                    System.out.println("b = true");
                    b = false;
                    setAdapterDeliverPage();
                }
                else{
                    adapterDeliverPage.refreshData(name,phone,address,price);
                }

                if(name.size() == 0){
                    TextView t;
                    t = findViewById(R.id.NoOrdersD);
                    t.setText("ПОКА \n НЕТ \n ЗАКАЗОВ \n :/ \n \n \n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    private void setAdapterDeliverPage(){
        RecyclerView recyclerView = findViewById(R.id.DeliverRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterDeliverPage = new AdapterDeliverPage(name,phone,address,price);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        recyclerView.addItemDecoration(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterDeliverPage);
    }


    private final ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.END);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int idS = viewHolder.getAdapterPosition();
            Log.i(TAG, "It`s onSwipe" + idS);
            Date date = new Date();
            Map<String, Object> data= new HashMap<>();
            data.put("dateD", date);
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("orders")
                    .child(key.get(idS));
            ref.updateChildren(data);
            getList();
        }
    };

    public void logout(View v){
        WelcomePage.k = true;
        Intent intent = new Intent(DeliverPage.this, WelcomePage.class);
        startActivity(intent);
    }
}