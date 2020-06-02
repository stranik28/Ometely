package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MonthPage extends Activity {

    private ArrayList<String> month = new ArrayList<>();
    private AdapterViewMonth adapterViewMonth;
    public static int numberMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_page);
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
        RecyclerView recyclerView = findViewById(R.id.montList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterViewMonth = new AdapterViewMonth(month, getApplicationContext());
        recyclerView.setAdapter(adapterViewMonth);
    }
}
