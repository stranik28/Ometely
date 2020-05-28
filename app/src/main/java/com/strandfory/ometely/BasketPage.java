package com.strandfory.ometely;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class BasketPage extends Activity {

    private static TextView FinalBuy;
    private static AdapterBasketPage adapteR;
    private static int FinalPrice;
    WorkBD workBD;
    CatalogPage mainActivity;



    private static ArrayList<String> listokadd(){
        ArrayList<String> listok = new ArrayList<>();
        if (CatalogPage.pepperoni.getCount() != 0)
            listok.add("Пепперони: " + CatalogPage.pepperoni.getCount() + " шт.");
        if (CatalogPage.calzone.getCount() != 0)
            listok.add("Кальцоне: " + CatalogPage.calzone.getCount() + " шт.");
        if (CatalogPage.quattrostagioni.getCount() != 0)
            listok.add("Четыре сезона: " + CatalogPage.quattrostagioni.getCount() + " шт.");
        if (CatalogPage.quattroformaggi.getCount() != 0)
            listok.add("Четыре сыра: " + CatalogPage.quattroformaggi.getCount() + " шт.");
        if (CatalogPage.mexican.getCount() != 0)
            listok.add("Мексиканская: " + CatalogPage.mexican.getCount() + " шт.");
        return listok;
    }

    private static ArrayList<String> listok (){
        ArrayList<String> Pizzas = new ArrayList<>();
        if (CatalogPage.pepperoni.getCount() != 0)
            Pizzas.add("pizza1");
        if (CatalogPage.calzone.getCount() != 0)
            Pizzas.add("pizza2");
        if (CatalogPage.quattrostagioni.getCount() != 0)
            Pizzas.add("pizza3");
        if (CatalogPage.quattroformaggi.getCount() != 0)
            Pizzas.add("pizza4");
        if (CatalogPage.mexican.getCount() != 0)
            Pizzas.add("pizza5");
        return Pizzas;
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bascket_page);
        FinalBuy = findViewById(R.id.buyText);
        mainActivity= new CatalogPage();
        culculateFprice();
        workBD = new WorkBD(this);
        Recycler();
    }

    public void finish_check(View v){
        TextView t = findViewById(R.id.errorview);
        EditText namik = findViewById(R.id.name);
        EditText phonik = findViewById(R.id.phone);
        EditText streetik = findViewById(R.id.street);
        EditText homeik = findViewById(R.id.home);
        EditText porchik = findViewById(R.id.porch);
        EditText levelik = findViewById(R.id.level);
        EditText apprtik = findViewById(R.id.room);

        String name = namik.getText().toString();
        boolean b = true;


        if (namik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String phoneNumber = phonik.getText().toString();

        if (phonik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String street = streetik.getText().toString();

        if (streetik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String home = homeik.getText().toString();

        if (homeik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String porch = porchik.getText().toString();

        if (porchik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String level = levelik.getText().toString();


        if (levelik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String apprt = apprtik.getText().toString();


        if (apprtik.getText().length() == 0){
            t.setText(getString(R.string.error_final));
            b = false;
        }

        String address = " Улица: " + street + " \n " + "Дом:  " + home + " \n " + "Подъезд: " + porch + " \n " + "Этаж: " + level + " \n " + "Квартира: " + apprt;


        if(b){
            SQLiteDatabase database = workBD.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(WorkBD.KEY_NAME , name);
            contentValues.put(WorkBD.KEY_NUMBER, phoneNumber);
            contentValues.put(WorkBD.KEY_ADDRESS, address);
            contentValues.put(WorkBD.KEY_PIZZA1, CatalogPage.pepperoni.getCount());
            contentValues.put(WorkBD.KEY_PIZZA2, CatalogPage.calzone.getCount());
            contentValues.put(WorkBD.KEY_PIZZA3, CatalogPage.quattrostagioni.getCount());
            contentValues.put(WorkBD.KEY_PIZZA4, CatalogPage.quattroformaggi.getCount());
            contentValues.put(WorkBD.KEY_PIZZA5, CatalogPage.mexican.getCount());
            contentValues.put(WorkBD.KEY_PRICE, FinalPrice);
            database.insert(WorkBD.TABLE_CONTACTS, null, contentValues);
            Log.i(TAG, "Hey I`m put all data");
            Intent perehod = new Intent(BasketPage.this, WelcomePage.class);
            startActivity(perehod);
        }
    }

    private static void culculateFprice(){
        int Pprice = CatalogPage.pepperoni.getFinalPrice();
        int Cprice = CatalogPage.calzone.getFinalPrice();
        int QCprice = CatalogPage.quattrostagioni.getFinalPrice();
        int QFprice = CatalogPage.quattroformaggi.getFinalPrice();
        int Mprice = CatalogPage.mexican.getFinalPrice();
        FinalPrice = Pprice + Cprice + QCprice + QFprice + Mprice;
        if(FinalBuy != null)
            FinalBuy.setText("Стоимось вашего заказа: " + FinalPrice + " руб.");
    }


    private void Recycler(){
        ArrayList list = listokadd();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapteR = new AdapterBasketPage(list);
        recyclerView.setAdapter(adapteR);
    }

    public static void onPlus(int i){
        ArrayList<String> list = listok();
        switch (list.get(i)){
            case "pizza1":
                CatalogPage.pepperoni.setCount(CatalogPage.pepperoni.getCount() + 1);
                break;
            case "pizza2":
                CatalogPage.calzone.setCount(CatalogPage.calzone.getCount() + 1);
                break;
            case "pizza4":
                CatalogPage.quattroformaggi.setCount(CatalogPage.quattroformaggi.getCount() + 1);
                break;
            case "pizza3":
                CatalogPage.quattrostagioni.setCount(CatalogPage.quattrostagioni.getCount() + 1);
                break;
            case "pizza5":
                CatalogPage.mexican.setCount(CatalogPage.mexican.getCount() + 1);
                break;
        }
        adapteR.refreshData(listokadd());
        culculateFprice();
    }

    public static void onMinus(int i){
        ArrayList<String> list = listok();
        switch (list.get(i)){
            case "pizza1":
                CatalogPage.pepperoni.setCount(CatalogPage.pepperoni.getCount() - 1);
                break;
            case "pizza2":
                CatalogPage.calzone.setCount(CatalogPage.calzone.getCount() - 1);
                break;
            case "pizza3":
                CatalogPage.quattrostagioni.setCount(CatalogPage.quattrostagioni.getCount() - 1);
                break;
            case "pizza4":
                CatalogPage.quattroformaggi.setCount(CatalogPage.quattroformaggi.getCount() - 1);
                break;
            case "pizza5":
                CatalogPage.mexican.setCount(CatalogPage.mexican.getCount() - 1);
                break;
        }
        adapteR.refreshData(listokadd());
        culculateFprice();
    }

}