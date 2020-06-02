package com.strandfory.ometely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterDeliverPage extends RecyclerView.Adapter<AdapterDeliverPage.DeliverHolder> {

    private ArrayList<String> name;
    private ArrayList<String> address;
    private ArrayList<String> phone;
    private ArrayList<Integer> price;

    AdapterDeliverPage(ArrayList<String> Aname, ArrayList<String> Aphone, ArrayList<String> Aaddress, ArrayList<Integer> Aprice){
        name = Aname;
        address = Aaddress;
        phone = Aphone;
        price = Aprice;
    }

    @NonNull
    @Override
    public DeliverHolder onCreateViewHolder (ViewGroup parent, int viewType){
        Context context = parent.getContext();

        int layoutIdForListIttem = R.layout.adapter_deliver_panel;

        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(layoutIdForListIttem, parent, false);

        return new DeliverHolder(view);
    }

    static class DeliverHolder extends RecyclerView.ViewHolder{
        TextView nameT;
        TextView phoneT;
        TextView adressT;
        TextView priceT;

        DeliverHolder(@NonNull View itemView) {
            super(itemView);
            nameT = itemView.findViewById(R.id.AnameD);
            phoneT = itemView.findViewById(R.id.AphoneD);
            adressT = itemView.findViewById(R.id.ApizzaOrAadressD);
            priceT = itemView.findViewById(R.id.Aprice);
        }

    }

    void refreshData(ArrayList namik, ArrayList phonik, ArrayList addressik, ArrayList pricik){

        name.clear();
        phone.clear();
        address.clear();
        price.clear();

        name = namik;
        phone = phonik;
        address = addressik;
        price = pricik;


        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(DeliverHolder holder, int position) {
        holder.nameT.setText(name.get(position));
        holder.phoneT.setText(phone.get(position));
        holder.adressT.setText(address.get(position));
        holder.priceT.setText("Стоимость заказа : " + price.get(position) + " руб.");
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}