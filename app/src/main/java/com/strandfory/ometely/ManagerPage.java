package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ManagerPage extends Activity {

    private static AdapterDeliverPage adapterDeliverPage;
    private static ArrayList<String> Dname;
    private static ArrayList<String> Dphone;
    private static ArrayList<String> Daddress;
    private static AdapterCookPage adapterCookPage;
    private static ArrayList<String> Cname;
    private static ArrayList<String> Cphone;
    private static ArrayList<String> Cpizzas;
    private static ArrayList<Integer> price;
    private static boolean c;
    private static boolean d;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_page);
        c = true;
        getList();
    }

    public void logout(View v){
        WelcomePage.k = true;
        Intent intent = new Intent(ManagerPage.this, WelcomePage.class);
        startActivity(intent);
    }

    private void getList(){
        Date d = new Date();
        String date = String.valueOf(d.getMonth());
        String day = String.valueOf(d.getDay());
        String KEY_ORDERS = "orders";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(KEY_ORDERS).child(date).child(day);
        ValueEventListener valueEventListener = new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dname = new ArrayList<>();
                Dphone = new ArrayList<>();
                Daddress = new ArrayList<>();
                price = new ArrayList<>();
                Cname = new ArrayList<>();
                Cphone = new ArrayList<>();
                Cpizzas = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    if((order.dateC != null) && (order.dateD == null)) {
                        Dname.add(order.name);
                        Dphone.add(order.phone);
                        Daddress.add(order.address);
                        price.add(order.price);
                    }
                    else if(order.dateC == null) {
                        Cname.add(order.name);
                        Cphone.add(order.phone);
                        String pizza1 = "Пепперони: " + order.countPepperonni + "\n";
                        String pizza2 = "Кальцоне: " + order.countCalzone + "\n";
                        String pizza3 = "Четыре сезона: " + order.countSeason + "\n";
                        String pizza4 = "Четыре сыра: " + order.countCheese + "\n";
                        String pizza5 = "Мексиканская: " + order.countMexican + "\n";
                        String pizaaas = pizza1 + pizza2 + pizza3 + pizza4 + pizza5;
                        Cpizzas.add(pizaaas);
                    }
                }
                if(c){
                    c = false;
                    setAdapterDMangaerPage();
                    setAdapterCManagerPage();
                }
                else{
                    adapterDeliverPage.refreshData(Dname,Dphone,Daddress,price);
                    adapterCookPage.refreshData(Cname,Cphone,Cpizzas);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }


    private void setAdapterCManagerPage(){
        RecyclerView recyclerView = findViewById(R.id.in_cooking);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterCookPage = new AdapterCookPage(Cname,Cphone,Cpizzas);
        recyclerView.setAdapter(adapterCookPage);
    }

    private void setAdapterDMangaerPage(){
        RecyclerView recyclerView2 = findViewById(R.id.in_delivering);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager1);
        adapterDeliverPage = new AdapterDeliverPage(Dname,Dphone,Daddress,price);
        recyclerView2.setAdapter(adapterDeliverPage);
    }

    public void update(View v){
        Intent intent = new Intent(ManagerPage.this, MonthPage.class);
        startActivity(intent);
    }

}