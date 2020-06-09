package com.strandfory.ometely;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MonthPage extends Activity {

    private ArrayList<String> month;
    private static AdapterViewMonth adapterViewMonth;
    private static ArrayList<String> nubersOfMonthString;
    private static Context context;
    private RecyclerView recyclerView;
    public static boolean b;
    private static DatabaseReference reference;
    private static ArrayList<String> name;
    private static ArrayList<String> date;
    private static ArrayList<Order> order;
    private static ArrayList<String> key;
    private static boolean j;
    public static Order orderok;
    public static String keys;
    private static String monthe;
    private static  String day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_page);
        month = new ArrayList<>();
        month.add("Январь");
        month.add("Февраль");
        month.add("Март");
        month.add("Апрель");
        month.add("Май");
        month.add("Июнь");
        month.add("Июль");
        month.add("Август");
        month.add("Сентябрь");
        month.add("Октябрь");
        month.add("Ноябрь");
        month.add("Декабрь");
        adapterViewMonth = new AdapterViewMonth(month, getApplicationContext(), null);
        context = getApplicationContext();
        recyclerView = findViewById(R.id.montList);
        b = true;
        j = true;
        setAdapter();
    }

    public void Update(View v){
        Intent intent = new Intent(MonthPage.this, ManagerPage.class);
        startActivity(intent);
    }

    public void logout(View v) {
        WelcomePage.k = true;
        Intent intent = new Intent(MonthPage.this, WelcomePage.class);
        startActivity(intent);
    }

    public void setAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterViewMonth = new AdapterViewMonth(month, getApplicationContext(), null);
        recyclerView.setAdapter(adapterViewMonth);
    }

    public static void dates(int p){

        if(b) {
            monthe = String.valueOf(p);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            calendar = new GregorianCalendar(year, p, 1);
            int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            System.out.println(days);
            ArrayList<Integer> nubersOfMonth = new ArrayList<>();
            nubersOfMonthString = new ArrayList<>();
            for (int i = 0; i < days; i++) {
                nubersOfMonth.add(i + 1);
                nubersOfMonthString.add(String.valueOf(nubersOfMonth.get(i)));
            }
            adapterViewMonth.refreshdata(nubersOfMonthString, context, null);
            b = false;
        }

        else if (j) {
            day = String.valueOf(p+1);
            Log.i("TAG", monthe + "   " + day );
            reference = FirebaseDatabase.getInstance().getReference("orders").child(monthe).child(day);
            name = new ArrayList<>();
            date = new ArrayList<>();
            order = new ArrayList<>();
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name = new ArrayList<>();
                    date = new ArrayList<>();
                    order = new ArrayList<>();
                    key = new ArrayList<>();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Order orderp = ds.getValue(Order.class);
                        System.out.println(orderp.name);
                        if(orderp.dateO.getDate() == p+1) {
                            key.add(ds.getKey());
                            order.add(orderp);
                            name.add(orderp.name);
                            int minutes = orderp.dateO.getMinutes();
                            int hours = orderp.dateO.getHours();
                            if(hours < 10 && minutes < 10)
                                date.add("0" + hours + ":0" + minutes);
                            else if(minutes < 10)
                                date.add(hours + ":0" + minutes);
                            else if(hours < 10)
                                date.add("0" + hours + ":" + minutes);
                            else
                                date.add(hours + ":" + minutes);
                            System.out.println(date.get(0));
                        }
                    }

                    if(name.size() != 0) {
                        adapterViewMonth.refreshdata(name, context, date);
                        j =false;
                    }

                    else {
                        adapterViewMonth.refreshdata(nubersOfMonthString, context, null);
                        Toast toast = Toast.makeText(context,
                                "Кажется в этом месяце еще нет заказов или они удалены", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reference.addValueEventListener(valueEventListener);
        }

        else {
            Intent intent = new Intent(context, PeopleListPage.class);
            orderok = order.get(p);
            keys = key.get(p);
            context.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(j){
            if(!b) {
                b = true;
                setAdapter();
            }
        }
        if(b){
           Intent intent = new Intent(MonthPage.this, ManagerPage.class);
           startActivity(intent);
        }

    }
}