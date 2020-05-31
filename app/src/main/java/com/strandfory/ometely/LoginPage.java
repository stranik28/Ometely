package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class LoginPage extends Activity {

    private int i;
    private boolean admin;
    private ArrayList<String> pass;
    public String adminLogin;
    public String adminPassword;
    private DatabaseReference reference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        i = 0;
        if(adminLogin == null || adminPassword == null) {
            adminPassword = "admin";
            adminLogin = "admin";
        }
        //fillList();
    }

    @Override
    public void onResume(){
        super.onResume();
        i = 0;
        //fillList();
    }

   /* public void Login (View v){

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
        reference = FirebaseDatabase.getInstance().getReference("workers");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                log = new ArrayList<>();
                pass = new ArrayList<>();
                cook = new ArrayList<>();
                deliver = new ArrayList<>();
                manager = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Worker worker = ds.getValue(Worker.class);
                    log.add(worker.login);
                    pass.add(worker.pass);
                    char ch = worker.login.charAt(0);
                    String S = String.valueOf(ch);
                    if("C".equals(S)){
                        cook.add(1);
                        deliver.add(0);
                        manager.add(0);
                    }
                    else if("D".equals(S)){
                        cook.add(0);
                        deliver.add(1);
                        manager.add(0);
                    }
                    else{
                        cook.add(0);
                        deliver.add(0);
                        manager.add(1);
                    }
                    System.out.println(log + " " + pass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
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
    }*/
}