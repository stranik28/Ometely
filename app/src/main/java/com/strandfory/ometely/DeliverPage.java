package com.strandfory.ometely;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private static ArrayList<Integer> id;
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
                id = new ArrayList<>();
                name = new ArrayList<>();
                phone = new ArrayList<>();
                address = new ArrayList<>();
                price = new ArrayList<>();
                key = new ArrayList<>();
                int i = 1;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    System.out.println("hey");
                    if((order.dateC != null) && (order.dateD == null)) {
                        i++;
                        System.out.println("heya");
                        key.add(ds.getKey());
                        System.out.println(key);
                        id.add(i);
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

    /*private void getItemForAdapter(){
        id = new ArrayList<>();
        name = new ArrayList<>();
        phone = new ArrayList<>();
        address = new ArrayList<>();
        price = new ArrayList<>();
        Log.i(TAG, "IT`s Deliver Adapter");
        SQLiteDatabase db = workBD.getReadableDatabase();
        Cursor cursor = db.query(WorkBD.TABLE_CONTACTS, null, null, null, null, null, null);
        cursor.moveToFirst();
        TextView t;
        do{
            try {
                int deliv = cursor.getInt(cursor.getColumnIndex("iscook"));
                if (deliv == 1) {
                    id.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_ID)));
                    name.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NAME)));
                    phone.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NUMBER)));
                    address.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_ADDRESS)));
                    price.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_PRICE)));
                    t = findViewById(R.id.NoOrdersD);
                    t.setText("");
                    System.out.println("Hey i`s do, while " + cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_ID)));
                    }
            }
            catch (Exception e){
                t = findViewById(R.id.NoOrdersD);
                t.setText("ПОКА \n НЕТ \n ЗАКАЗОВ \n :/ \n \n \n");
            }

            if(b){
                adapterDeliverPage.refreshData(name,phone,address,price);
                b = false;
            }
            else
                setAdapterDeliverPage();
        }
        while (cursor.moveToNext());
        db.close();
        if(id.size() == 0){
            t = findViewById(R.id.NoOrdersD);
            t.setText("ПОКА \n НЕТ \n ЗАКАЗОВ \n :/ \n \n \n");
        }
    }*/

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
}