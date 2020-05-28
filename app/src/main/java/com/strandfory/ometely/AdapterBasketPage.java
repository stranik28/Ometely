package com.strandfory.ometely;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


public class AdapterBasketPage extends RecyclerView.Adapter<AdapterBasketPage.PizzasViewHolder> {

    private List<String> items;


    AdapterBasketPage(ArrayList<String> arrayList) {
        items = arrayList;
    }

    void refreshData(ArrayList table){

        items.clear();

        items = table;

        notifyDataSetChanged();
    }

    @Override
    public PizzasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListIttem = R.layout.adapter_backet;

        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(layoutIdForListIttem, parent, false);

        return new PizzasViewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(PizzasViewHolder holder, int position) {
        holder.nameText.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PizzasViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        Button plus;
        Button minus;

        PizzasViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textok1);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    BasketPage.onPlus(position);
                }
            });


            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    BasketPage.onMinus(position);
                }
            });
        }
    }
}