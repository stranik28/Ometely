package com.strandfory.ometely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCookPage extends RecyclerView.Adapter<AdapterCookPage.CookHolder> {

    private ArrayList<String> name;
    private ArrayList<String> pizza;
    private ArrayList<String> phone;

    AdapterCookPage(ArrayList<String> Aname, ArrayList<String> Aphone, ArrayList<String> Apizza){
        name = Aname;
        pizza = Apizza;
        phone = Aphone;
    }

    @NonNull
    @Override
    public CookHolder onCreateViewHolder (ViewGroup parent, int viewType){
    Context context = parent.getContext();

        int layoutIdForListIttem = R.layout.adapter_cook_panel;

        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(layoutIdForListIttem, parent, false);

        return new CookHolder(view);
    }

    static class CookHolder extends RecyclerView.ViewHolder{
        TextView nameT;
        TextView phoneT;
        TextView pizzaT;

        CookHolder(@NonNull View itemView) {
            super(itemView);
            nameT = itemView.findViewById(R.id.Aname);
            phoneT = itemView.findViewById(R.id.Aphone);
            pizzaT = itemView.findViewById(R.id.ApizzaOrAadress);
        }

    }

    void refreshData(ArrayList namik, ArrayList phonik, ArrayList pizzik){

        name.clear();
        phone.clear();
        pizza.clear();

        name = namik;
        phone = phonik;
        pizza = pizzik;


        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CookHolder holder, int position) {
        holder.nameT.setText(name.get(position));
        holder.phoneT.setText(phone.get(position));
        holder.pizzaT.setText(pizza.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}
