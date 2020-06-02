package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CatalogPage extends Activity{
    
    public static class pizza implements Serializable {
        
        int price;
        int count;
        
        void setCount(int x){

            this.count=x;
        }
        
        int getCount(){
            return this.count;
        }

        void setPrice(int x){
            this.price = x;
        }

        int getFinalPrice(){
            return this.price * this.count;
        }

        static int Allcount(){
            return pepperoni.getCount() + calzone.getCount() + quattrostagioni.getCount() + quattroformaggi.getCount() + mexican.getCount();

        }
        
    }



    public static pizza pepperoni = new pizza();
    public static pizza calzone = new pizza();
    public static pizza quattrostagioni = new pizza();
    public static pizza quattroformaggi = new pizza();
    public static pizza mexican = new pizza();
    private static ArrayList<String> pizzas;
    private static ArrayList<String> descriptions;


    TextView setterok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_page);
        setterok = findViewById(R.id.TextDown);
        pepperoni.setPrice(300);
        calzone.setPrice(380);
        quattrostagioni.setPrice(300);
        quattroformaggi.setPrice(350);
        mexican.setPrice(430);
        pepperoni.setCount(0);
        calzone.setCount(0);
        quattrostagioni.setCount(0);
        quattroformaggi.setCount(0);
        mexican.setCount(0);
        CountPizzas();
        fillList();
    }

    private void fillList(){
        pizzas = new ArrayList<>();
        descriptions = new ArrayList<>();
        pizzas.add("ПЕППЕРОНИ");
        pizzas.add("Кальцоне");
        pizzas.add("Четыре сезона");
        pizzas.add("Четыре сыра");
        pizzas.add("Мексиканская");
        descriptions.add("Класическй рецепт пепперони с аппетитными колбасками пепперони и сыром моцарелла");
        descriptions.add("Сочная Кальцоне со свежими грибами, аппетитными помидорами черри и оливками");
        descriptions.add("Эта пицца идеально подойдёт для  тех, кто считает что пицца прекрасна и удивительна в своем многообразии");
        descriptions.add("Классичсекий рецепт из моцарелла, пармезана, сыра горгонзола и артишоки не оставит равнодушных");
        descriptions.add("Острая пицца прямиком из мексики c усочками перца чили подойдет для любит по острее");
    }


    public void PepperoniClick(View view) {
        pepperoni.setCount(pepperoni.getCount()+1);
        System.out.println(pepperoni.getCount());
        CountPizzas();
    }

    public void CalzoneClick(View view) {
        calzone.setCount(calzone.getCount()+1);
        CountPizzas();
    }

    public void QuattroStagioniClick(View view) {
        quattrostagioni.setCount(quattroformaggi.getCount()+1);
        CountPizzas();
    }

    public void QuattroFormaggiClick(View view) {
        quattroformaggi.setCount(quattroformaggi.getCount()+1);
        CountPizzas();
    }

    public void MexicanClick(View view) {
        mexican.setCount(mexican.getCount()+1);
        CountPizzas();
    }

    private void CountPizzas(){
        if (pizza.Allcount() % 10 == 0 || pizza.Allcount() % 10 > 4 || (pizza.Allcount() % 100 >= 5 && pizza.Allcount() % 100 < 20)) {
            setterok.setText(getString(R.string.OrderText1) + " " + pizza.Allcount() + " " + getString(R.string.OrderText2Null));
        } else if (pizza.Allcount() % 10 == 1) {
            setterok.setText(getString(R.string.OrderText1) + " " + pizza.Allcount() + " " + getString(R.string.OrderText2One));
        } else {
            setterok.setText(getString(R.string.OrderText1) + " " + pizza.Allcount() + " " + getString(R.string.OrderText2Two));
        }
    }

    public void goToBacket(View view){
        Intent perehod = new Intent(CatalogPage.this , BasketPage.class);
        startActivity(perehod);
    }

    public void logout(View v){
        WelcomePage.k = true;
        Intent intent = new Intent(CatalogPage.this, WelcomePage.class);
        startActivity(intent);
    }
}