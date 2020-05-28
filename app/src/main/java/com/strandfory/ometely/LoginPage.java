package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class LoginPage extends Activity {

    WorkBD workBD;
    private int i;
    private boolean admin;
    private ArrayList<String> log;
    private ArrayList<String> pass;
    private ArrayList<Integer> cook;
    private ArrayList<Integer> deliver;
    private ArrayList<Integer> manager;
    public String adminLogin;
    public String adminPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        i = 0;
        if(adminLogin == null || adminPassword == null) {
            adminPassword = "admin";
            adminLogin = "admin";
        }
        workBD = new WorkBD(this);
        fillList();
    }

    @Override
    public void onResume(){
        super.onResume();
        fillList();
    }

    public void Login (View v){

        EditText Login = findViewById(R.id.LoginText);
        EditText Password = findViewById(R.id.PasswordText);
        String LoginS = Login.getText().toString();
        String PasswordS = Password.getText().toString();
        CheckBox checkBox = findViewById(R.id.admin);
        TextView setter = findViewById(R.id.Error);

        boolean bcook = checkLogPass(1, LoginS, PasswordS);
        boolean bdeliver = checkLogPass(0, LoginS, PasswordS);

        Log.i(TAG, bcook + "     " + bdeliver);

        if(admin && LoginS.equals(adminLogin) && PasswordS.equals(adminPassword)) {
            Login.setText("");
            Password.setText("");
            Intent perehod = new Intent(LoginPage.this, AdminPage.class);
            startActivity(perehod);
        }

        else if(bdeliver){
            Login.setText("");
            Password.setText("");
            setter.setText("");
            Intent perehod = new Intent(LoginPage.this, DeliverPage.class);
            startActivity(perehod);
        }

        else if(bcook){
            setter.setText("");
            Login.setText("");
            Password.setText("");
            Intent perehod = new Intent(LoginPage.this , CookPage.class);
            startActivity(perehod);
        }

        else if(checkBox.isChecked()){
            boolean managerB = false;

            for(int i = 0; i < manager.size(); i++){
                if(LoginS.equals(log.get(i)) && PasswordS.equals(pass.get(i)) && manager.get(i) == 1){
                    managerB = true;
                    break;
                }
            }

            if(managerB) {
                Login.setText("");
                Password.setText("");
                setter.setText("");
                Intent perehod = new Intent(LoginPage.this, ManagerPage.class);
                startActivity(perehod);
            }

            else{
                Password.setText("");
                setter.setText(R.string.error);
            }
        }

        else {
            Password.setText("");
            setter.setText(R.string.error);
        }
    }

    public void onClick(View v) {
        i++;
        if (i == 5){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы успешно активировали возможность входа, как администратор !", Toast.LENGTH_SHORT);
            toast.show();
            admin = true;
        }
    }

    private void fillList(){
        log = new ArrayList<>();
        pass = new ArrayList<>();
        cook = new ArrayList<>();
        deliver = new ArrayList<>();
        manager = new ArrayList<>();
        SQLiteDatabase db = workBD.getReadableDatabase();
        Cursor cursor = db.query(WorkBD.TABLE_WORKERS, null,null,null,null,null,null);
        cursor.moveToFirst();
        try{
            do {
                log.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_USERNAME)));
                pass.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_USERPASS)));
                cook.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_COOK)));
                deliver.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_DELIVER)));
                manager.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_MANAGER)));
            }
            while (cursor.moveToNext());
        }
        catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Не найдено аккаунтов для работников обратитесь к администратору.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean checkLogPass(int l, String lo, String pa){
        boolean b = false;
        if(l == 1){
            for(int i = 0; i < log.size(); i++){
                if(lo.equals(log.get(i)) && pa.equals(pass.get(i)) && cook.get(i) == 1)
                    b = true;
            }
        }

        if(l == 0) {
            for (int i = 0; i < log.size(); i++) {
                if (lo.equals(log.get(i)) && pa.equals(pass.get(i)) && deliver.get(i) == 1)
                    b = true;
                Log.i(TAG, Boolean.toString(b));
            }
        }

        return b;
    }
}