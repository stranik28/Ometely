package com.strandfory.ometely;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ManagerPage extends Activity {

    private static AdapterDeliverPage adapterDeliverPage;
    private static ArrayList<String> Dname;
    private static ArrayList<String> Dphone;
    private static ArrayList<String> Daddress;
    private static AdapterCookPage adapterCookPage;
    private static ArrayList<String> Cname;
    private static ArrayList<String> Cphone;
    private static ArrayList<String> Cpizzas;
    private static ArrayList<Integer> Price;
    private static boolean c;
    private static boolean d;
    WorkBD workBD;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_page);
        workBD = new WorkBD(this);
        getListForCook();
        getListDeliver();

    }

    private void getListForCook(){
        Cname = new ArrayList<>();
        Cphone = new ArrayList<>();
        Cpizzas = new ArrayList<>();
        SQLiteDatabase db = workBD.getReadableDatabase();
        Cursor cursor = db.query(WorkBD.TABLE_CONTACTS, null, null, null, null, null, null);
        cursor.moveToFirst();
        do{
            try {
                String pizza1 = "Пепперони: " + cursor.getInt(cursor.getColumnIndex("pizza1")) + "\n";
                String pizza2 = "Кальцоне: " + cursor.getInt(cursor.getColumnIndex("pizza2")) + "\n";
                String pizza3 = "Четыре сезона: " + cursor.getInt(cursor.getColumnIndex("pizza3")) + "\n";
                String pizza4 = "Четыре сыра: " + cursor.getInt(cursor.getColumnIndex("pizza4")) + "\n";
                String pizza5 = "Мексиканская: " + cursor.getInt(cursor.getColumnIndex("pizza5")) + "\n";
                int deliv = cursor.getInt(cursor.getColumnIndex("iscook"));
                if( deliv == 0) {
                    Cname.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NAME)));
                    Cphone.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NUMBER)));
                    Cpizzas.add(pizza1 + pizza2 + pizza3 + pizza4 + pizza5);
                }
                if(c){
                    adapterCookPage.refreshData(Cname,Cphone,Cpizzas);
                    c = false;
                }
                else
                    setAdapterCManagerPage();
            }

            catch (Exception e){}
        }
        while (cursor.moveToNext());
    }

    private void setAdapterCManagerPage(){
        RecyclerView recyclerView = findViewById(R.id.in_cooking);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterCookPage = new AdapterCookPage(Cname,Cphone,Cpizzas);
        recyclerView.setAdapter(adapterCookPage);
    }

    private void getListDeliver(){
        Dname = new ArrayList<>();
        Dphone = new ArrayList<>();
        Daddress = new ArrayList<>();
        Price = new ArrayList<>();
        Log.i(TAG, "IT`s Deliver Adapter");
        SQLiteDatabase db = workBD.getReadableDatabase();
        Cursor cursor = db.query(WorkBD.TABLE_CONTACTS, null, null, null, null, null, null);
        cursor.moveToFirst();
        do{
            try {
                int deliv = cursor.getInt(cursor.getColumnIndex("iscook"));
                if (deliv == 1) {
                    Dname.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NAME)));
                    Dphone.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_NUMBER)));
                    Daddress.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_ADDRESS)));
                    Price.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_PRICE)));
                }
            }
            catch (Exception e){
            }

            if(d){
                adapterDeliverPage.refreshData(Dname,Dphone,Daddress,Price);
                d = false;
            }
            else
                setAdapterDMangaerPage();
        }
        while (cursor.moveToNext());
        db.close();
    }

    private void setAdapterDMangaerPage(){
        RecyclerView recyclerView2 = findViewById(R.id.in_delivering);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager1);
        adapterDeliverPage = new AdapterDeliverPage(Dname,Dphone,Daddress,Price);
        recyclerView2.setAdapter(adapterDeliverPage);
    }

    public void Update(View v){
        c = true;
        d = true;
        getListForCook();
        getListDeliver();
    }

}