package com.strandfory.ometely;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterViewMonth extends RecyclerView.Adapter<AdapterViewMonth.PizzasViewHolder> {

    ArrayList<String> month;
    static Context ctx;


    AdapterViewMonth(ArrayList month1, Context ctx1){
        month = month1;
        ctx = ctx1;
    }

    @Override
    public AdapterViewMonth.PizzasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListIttem = R.layout.adapter_month_page;

        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(layoutIdForListIttem, parent, false);


        return new AdapterViewMonth.PizzasViewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(AdapterViewMonth.PizzasViewHolder holder, int position) {
        holder.monthText.setText(month.get(position));
        int y = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    static class PizzasViewHolder extends RecyclerView.ViewHolder {

        TextView monthText;

        PizzasViewHolder(@NonNull View itemView) {
            super(itemView);
            monthText = itemView.findViewById(R.id.textMonth);

            monthText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MonthPage.numberMonth = position;
                    Intent intent = new Intent(ctx, WelcomePage.class);
                    ctx.startActivity(intent);
                }
            });

        }
    }

}
