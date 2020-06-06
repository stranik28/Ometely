package com.strandfory.ometely;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterViewMonth extends RecyclerView.Adapter<AdapterViewMonth.PizzasViewHolder> {

    ArrayList <String> month;
    static Context ctx;
    ArrayList <String> time;


    AdapterViewMonth(ArrayList month1, Context ctx1, ArrayList time1){
        month = month1;
        ctx = ctx1;
        time = time1;
    }

    void refreshdata(ArrayList<String > days, Context ctxp, ArrayList<String> time1){
        month.clear();
        ctx = null;
        try {
            time.clear();
        }
        catch (NullPointerException e){

        }

        ctx = ctxp;
        month = days;
        time = time1;
        try {
            System.out.println(time.get(0));
        }
        catch (NullPointerException e){
            Log.i("TAG", "бывает");
        }
        notifyDataSetChanged();
    }

    @Override
    public AdapterViewMonth.PizzasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListIttem = R.layout.adapter_month_page;
        try{
            if(time.get(0) != null)
                layoutIdForListIttem = R.layout.adapter_month_page;
        }
        catch (NullPointerException e) {
        }

        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(layoutIdForListIttem, parent, false);

        return new AdapterViewMonth.PizzasViewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(AdapterViewMonth.PizzasViewHolder holder, int position) {
        holder.monthText.setText(month.get(position));
        try {
            holder.dataText.setText(time.get(position));
        }
        catch (NullPointerException e){
            try {
                System.out.println(time.get(1));
            }
            catch (NullPointerException ee){
                System.out.println("3,14здец полный если ты это видишь");
            }
        }

    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    static class PizzasViewHolder extends RecyclerView.ViewHolder {

        TextView monthText;
        TextView dataText;

        PizzasViewHolder(@NonNull View itemView) {
            super(itemView);
            monthText = itemView.findViewById(R.id.textMonth);
            dataText = itemView.findViewById(R.id.timeOrder);

            monthText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MonthPage.dates(position);
                }
            });

            dataText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MonthPage.dates(position);
                }
            });

        }
    }

}
