package com.strandfory.ometely;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class PeopleListPage extends Activity {

    private Order order;
    private String name;
    private String adress;
    private String pizza1;
    private String pizza2;
    private String pizza3;
    private String pizza4;
    private String pizza5;
    private String pizzas;
    private String nameD;
    private String nameC;
    private String phone;
    private String price;
    private String timeO;
    private String timeD;
    private String timeC;
    private TextView nameT;
    private TextView phoneT;
    private TextView adressT;
    private TextView orderT;
    private TextView priceT;
    private TextView dateOT;
    private TextView dateCT;
    private TextView nameCT;
    private TextView dateDT;
    private TextView nameDT;


    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_page);
        init();
        fillpage();
        setText();
    }

    void init(){
        nameT = findViewById(R.id.nameInOrder);
        phoneT = findViewById(R.id.phoneInOrder);
        adressT = findViewById(R.id.adressInOrder);
        orderT = findViewById(R.id.pizzasInOrder);
        priceT = findViewById(R.id.priceInOrder);
        dateOT = findViewById(R.id.dateOInOrder);
        dateCT = findViewById(R.id.dateCInOrder);
        nameCT = findViewById(R.id.cookerInOrder);
        dateDT = findViewById(R.id.dateDInOrder);
        nameDT = findViewById(R.id.deliverInOrder);
    }

    void fillpage(){
        order = MonthPage.orderok;
        name = order.name;
        adress = order.address;
        pizza1 = "Пепперони: " + String.valueOf(order.countPepperonni);
        pizza2 = "Кальцоне: " + String.valueOf(order.countCalzone);
        pizza3 = "Четыре сезона: " + String.valueOf(order.countSeason);
        pizza4 = "Четыре сыра: " + String.valueOf(order.countCheese);
        pizza5 = "Мексиканская: " + String.valueOf(order.countMexican);
        pizzas = " " + pizza1 + " \n " + pizza2 + " \n " + pizza3 + " \n " + pizza4 + " \n " + pizza5;
        phone = order.phone;
        price = String.valueOf(order.price) + " руб.";
        timeO = String.valueOf(order.dateO);
        if(order.dateD == null)
            timeD = "Пока нет информации";
        else
            timeD = String.valueOf(order.dateD);

        if(order.dateC == null)
            timeC = "Пока нет информации";
        else
            timeC = String.valueOf(order.dateC);
        if(order.deliverName == null)
            nameD = "Пока нет информации";
        else
            nameD = order.deliverName;
        if(order.nameCook == null)
            nameC = "Пока нет информации";
        else
            nameC = order.nameCook;
    }

    void setText(){
        nameT.setText(name);
        phoneT.setText(phone);
        adressT.setText(adress);
        orderT.setText(pizzas);
        priceT.setText(price);
        dateOT.setText(timeO);
        dateCT.setText(timeC);
        nameCT.setText(nameC);
        dateDT.setText(timeD);
        nameDT.setText(nameD);
    }
}
