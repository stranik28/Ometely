package com.strandfory.ometely;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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


public class CookPage extends Activity {

    private static AdapterCookPage adapterCookPage;
    private static ArrayList<String> name;
    private static ArrayList<String> phone;
    private static ArrayList<String> pizzas;
    WorkBD workBD;
    private static boolean p;
    private DatabaseReference reference;
    private String KEY_ORDERS = "orders";
    private ArrayList<String> key;


    private void getList(){
        reference = FirebaseDatabase.getInstance().getReference(KEY_ORDERS);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = new ArrayList<>();
                phone = new ArrayList<>();
                pizzas = new ArrayList<>();
                key = new ArrayList<>();
                int i = 1;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    if(order.dateC == null) {
                        i++;
                        key.add(ds.getKey());
                        name.add(order.name);
                        phone.add(order.phone);
                        String pizza1 = "Пепперони: " + order.countPepperonni + "\n";
                        String pizza2 = "Кальцоне: " + order.countCalzone + "\n";
                        String pizza3 = "Четыре сезона: " + order.countSeason + "\n";
                        String pizza4 = "Четыре сыра: " + order.countCheese + "\n";
                        String pizza5 = "Мексиканская: " + order.countMexican + "\n";
                        String pizaaas = pizza1 + pizza2 + pizza3 + pizza4 + pizza5;
                        pizzas.add(pizaaas);
                    }
                }
                if(p) {
                    setAdapterCookPage();
                    p = false;
                }
                else
                    adapterCookPage.refreshData(name,phone,pizzas);

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_page);
        workBD = new WorkBD(this);
        name = new ArrayList<>();
        phone = new ArrayList<>();
        pizzas = new ArrayList<>();
        key = new ArrayList<>();
        p = true;
        getList();
        adapterCookPage = new AdapterCookPage(name,phone,pizzas);
    }


    private void setAdapterCookPage(){
        RecyclerView recyclerView = findViewById(R.id.CookRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterCookPage = new AdapterCookPage(name,phone,pizzas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        recyclerView.addItemDecoration(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterCookPage);
    }


    private final ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.END);
        }

        @Override
        public boolean onMove(@NonNull  RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int idS = viewHolder.getAdapterPosition();
            Log.i(TAG, Integer.toString(idS));
            Date date = new Date();
            Map<String, Object> data= new HashMap<>();
            data.put("dateC", date);
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("orders")
                    .child(key.get(idS));
            ref.updateChildren(data);
            getList();
            Log.i(TAG, "onSwiped");
        }
    };
}