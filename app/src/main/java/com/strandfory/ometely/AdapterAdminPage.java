package com.strandfory.ometely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAdminPage extends RecyclerView.Adapter<AdapterAdminPage.AdminHolder> {

    private ArrayList<Integer> id;
    private ArrayList<String> log;
    private ArrayList<String> pass;

    AdapterAdminPage(ArrayList<Integer> Aid, ArrayList<String> Alog, ArrayList<String> Apass){
        id = Aid;
        log = Alog;
        pass = Apass;
    }

    @NonNull
    @Override
    public AdminHolder onCreateViewHolder (ViewGroup parent, int viewType){
        Context context = parent.getContext();

        int layoutIdForListIttem = R.layout.adapter_admin_page;

        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(layoutIdForListIttem, parent, false);

        return new AdminHolder(view);
    }

    static class AdminHolder extends RecyclerView.ViewHolder{
        TextView idT;
        TextView logT;
        TextView passT;

        AdminHolder(@NonNull View itemView) {
            super(itemView);
            idT = itemView.findViewById(R.id.Aid);
            logT = itemView.findViewById(R.id.Alog);
            passT = itemView.findViewById(R.id.Apass);
        }

    }

    void refreshData(ArrayList Aid, ArrayList Alog, ArrayList Apass){

        id.clear();
        log.clear();
        pass.clear();

        id = Aid;
        log = Alog;
        pass = Apass;

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AdminHolder holder, int position) {
        holder.idT.setText(Integer.toString(id.get(position)));
        holder.logT.setText(log.get(position));
        holder.passT.setText(pass.get(position));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }
}
