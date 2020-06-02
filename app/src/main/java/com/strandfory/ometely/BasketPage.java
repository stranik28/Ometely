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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class BasketPage extends Activity {

    private static TextView FinalBuy;
    private static AdapterBasketPage adapteR;
    private static int FinalPrice;

    private DatabaseReference reference;
    private FirebaseDatabase db;
    private String KEY_ORDERS = "orders";


    private void data(String name, String phone, String address, int countPepperonni, int countCalzone, int countSeason, int countCheese, int countMexican){
        reference = FirebaseDatabase.getInstance().getReference(KEY_ORDERS);
        Date date = new Date();
        culculateFprice();
        Order neworder = new Order(date,name,phone,FinalPrice,address,countPepperonni,countCalzone,countCheese,countSeason,countMexican,null,null);
        reference.push().setValue(neworder);
    }

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
        culculateFprice();
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

        String address = " Ул: " + street + " \n " + "Дом:  " + home + " \n " + "Подъезд: " + porch + " \n " + "Этаж: " + level + " \n " + "Кв: " + apprt;


        if(b){
            data(name,phoneNumber,address,CatalogPage.pepperoni.getCount(), CatalogPage.calzone.getCount(), CatalogPage.quattrostagioni.getCount(),CatalogPage.quattroformaggi.getCount(), CatalogPage.mexican.getCount());
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

    public void logout(View v){
        WelcomePage.k = true;
        Intent intent = new Intent(BasketPage.this, WelcomePage.class);
        startActivity(intent);
    }

}