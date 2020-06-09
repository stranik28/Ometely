package com.strandfory.ometely;

import java.util.Date;

public class Order {
    public String name;
    public String phone;
    public String address;
    public int countPepperonni;
    public int countCalzone;
    public int countSeason;
    public int countCheese;
    public int countMexican;
    public Date dateO;
    public Date dateC;
    public Date dateD;
    public int price;
    public String nameCook;
    public String deliverName;
    public String uidUSer;

    public Order(){

    }

    public Order(Date dateO,String name, String phone, int price,  String address, int countPepperonni, int countCalzone, int countSeason, int countCheese, int countMexican, Date dateD, Date dateC, String nameCook, String DeliverName, String uidUser){ ;
        this.dateO = dateO;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.address = address;
        this.countPepperonni = countPepperonni;
        this.countCalzone = countCalzone;
        this.countCheese = countCheese;
        this.countSeason = countSeason;
        this.countMexican = countMexican;
        this.dateC = dateC;
        this.dateD = dateD;
        this.nameCook = nameCook;
        this.deliverName = DeliverName;
        this.uidUSer = uidUser;
    }

}
