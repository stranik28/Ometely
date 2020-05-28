package com.strandfory.ometely;


import android.app.Activity;
import android.content.ContentValues;
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


import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class CookPage extends Activity {

    private static AdapterCookPage adapterCookPage;
    private static ArrayList<Integer> id;
    private static ArrayList<String> name;
    private static ArrayList<String> phone;
    private static ArrayList<String> pizzas;
    WorkBD workBD;
    private static boolean b;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_page);
        workBD = new WorkBD(this);
    }

    public void Refresh(View v){
        getItemForAdapter();
    }

    private void getItemForAdapter(){
        id = new ArrayList<>();
        name = new ArrayList<>();
        phone = new ArrayList<>();
        pizzas = new ArrayList<>();
        SQLiteDatabase db = workBD.getReadableDatabase();
        Cursor cursor = db.query(WorkBD.TABLE_CONTACTS, null, null, null, null, null, null);
        cursor.moveToFirst();
        TextView t;
        do{
            try {
                String pizza1 = "Пепперони: " + cursor.getInt(cursor.getColumnIndex("pizza1")) + "\n";
                String pizza2 = "Кальцоне: " + cursor.getInt(cursor.getColumnIndex("pizza2")) + "\n";
                String pizza3 = "Четыре сезона: " + cursor.getInt(cursor.getColumnIndex("pizza3")) + "\n";
                String pizza4 = "Четыре сыра: " + cursor.getInt(cursor.getColumnIndex("pizza4")) + "\n";
                String pizza5 = "Мексиканская: " + cursor.getInt(cursor.getColumnIndex("pizza5")) + "\n";
                int deliv = cursor.getInt(cursor.getColumnIndex("iscook"));
                t = findViewById(R.id.NoOrders);
                t.setText("");
                if( deliv == 0) {
                    id.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_ID)));
                    name.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NAME)));
                    phone.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NUMBER)));
                    pizzas.add(pizza1 + pizza2 + pizza3 + pizza4 + pizza5);
                }
                if(b){
                    adapterCookPage.refreshData(name,phone,pizzas);
                    b = false;
                }
                else
                    setAdapterCookPage();
            }
            catch (Exception e){
                t = findViewById(R.id.NoOrders);
                t.setText("ПОКА \n НЕТ \n ЗАКАЗОВ \n :/ \n \n \n");
            }
        }
        while (cursor.moveToNext());

        db.close();

        if(id.size() == 0) {
            t = findViewById(R.id.NoOrders);
            t.setText("ПОКА \n НЕТ \n ЗАКАЗОВ \n :/ \n \n \n");
        }
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
            SQLiteDatabase db = workBD.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(WorkBD.KEY_ISCOOK , 1);
            db.execSQL("UPDATE pizzaOrders SET iscook = 1 WHERE _id = " + id.get(idS) + ";");
            b = true;
            getItemForAdapter();
            Log.i(TAG, "onSwiped");
        }
    };
}