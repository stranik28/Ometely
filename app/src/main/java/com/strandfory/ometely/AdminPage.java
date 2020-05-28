package com.strandfory.ometely;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AdminPage extends Activity {

    WorkBD workBD;
    private int counter;
    private EditText logD;
    private EditText passD;
    private EditText logC;
    private EditText passC;
    private EditText logM;
    private EditText passM;
    private ArrayList<String> logins;
    private ArrayList<String> passwords;
    private ArrayList<Integer> id;
    private static AdapterAdminPage adapterAdminPage;
    private boolean b;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);
        workBD = new WorkBD(this);
        logD = findViewById(R.id.LoginTextD);
        passD = findViewById(R.id.PasswordTextD);
        logC = findViewById(R.id.LoginTextC);
        passC = findViewById(R.id.PasswordTextC);
        logM = findViewById(R.id.LoginTextM);
        passM = findViewById(R.id.PasswordTextM);
        db = workBD.getReadableDatabase();
        getList();
        counter = 0;
    }

    public void AdminClickD(View v){
        ContentValues contentValues = new ContentValues();
        String Nlog = "Deliver_" + logD.getText().toString();
        String Npass = passD.getText().toString();
        contentValues.put(WorkBD.KEY_USERNAME, Nlog);
        contentValues.put(WorkBD.KEY_USERPASS, Npass);
        contentValues.put(WorkBD.KEY_DELIVER, 1);
        db.insert(WorkBD.TABLE_WORKERS, null, contentValues);
        logD.setText("");
        passD.setText("");
        b = true;
        getList();
    }

    public void AdminClickC(View v){
        ContentValues contentValues = new ContentValues();
        String Nlog = "Cook_" + logC.getText().toString();
        String Npass = passC.getText().toString();
        contentValues.put(WorkBD.KEY_USERNAME, Nlog);
        contentValues.put(WorkBD.KEY_USERPASS, Npass);
        contentValues.put(WorkBD.KEY_COOK, 1);
        db.insert(WorkBD.TABLE_WORKERS, null, contentValues);
        logC.setText("");
        passC.setText("");
        b = true;
        getList();
    }

    public void AdminClickM(View v){
        ContentValues contentValues = new ContentValues();
        String Nlog = "Manager_" + logM.getText().toString();
        String Npass = passM.getText().toString();
        contentValues.put(WorkBD.KEY_USERNAME, Nlog);
        contentValues.put(WorkBD.KEY_USERPASS, Npass);
        contentValues.put(WorkBD.KEY_MANAGER, 1);
        db.insert(WorkBD.TABLE_WORKERS, null, contentValues);
        logM.setText("");
        passM.setText("");
        b = true;
        getList();
    }

    private void getList(){
        id = new ArrayList<>();
        logins = new ArrayList<>();
        passwords = new ArrayList<>();
        Cursor cursor = db.query(WorkBD.TABLE_WORKERS, null, null, null, null, null, null);
        cursor.moveToFirst();
        try {
            do {
                id.add(cursor.getInt(cursor.getColumnIndex(WorkBD.KEY_ID)));
                logins.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_USERNAME)));
                passwords.add(cursor.getString(cursor.getColumnIndex(WorkBD.KEY_USERPASS)));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Кажется у вас ещё нет зарегистрированных работников.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(b) {
            adapterAdminPage.refreshData(id, logins, passwords);
            b = false;
        }
        else
            setAdapter();
    }

    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.AdminRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        recyclerView.addItemDecoration(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapterAdminPage = new AdapterAdminPage(id,logins,passwords);
        recyclerView.setAdapter(adapterAdminPage);
    }

    private final ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.END);
        }

        @Override
        public boolean onMove(@NonNull  RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int idS = viewHolder.getAdapterPosition();
            counter++;
            if(counter == 1){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Сделайте свай еще раз, для подтверждения удаления.", Toast.LENGTH_SHORT);
                toast.show();
                setAdapter();
            }
            if(counter == 2){
                counter = 0;
                logins.remove(idS);
                passwords.remove(idS);
                db.execSQL("DROP TABLE IF EXISTS " + WorkBD.TABLE_WORKERS);
                db.execSQL("CREATE TABLE " + WorkBD.TABLE_WORKERS + " (" + WorkBD.KEY_ID + " INTEGER PRIMARY KEY, " + WorkBD.KEY_USERNAME + " TEXT, " + WorkBD.KEY_USERPASS + " TEXT, " + WorkBD.KEY_COOK + " BLOB DEFAULT 0, " + WorkBD.KEY_DELIVER + " BLOB DEFAULT 0, " + WorkBD.KEY_MANAGER + " BLOB DEFAULT 0 )");
                for(int i = 0; i < logins.size(); i++){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(WorkBD.KEY_USERNAME, logins.get(i));
                    contentValues.put(WorkBD.KEY_USERPASS, passwords.get(i));
                    int cook = logins.get(i).indexOf("Ca");
                    int deliver = logins.get(i).indexOf("D");
                    if(cook == 0) {
                        contentValues.put(WorkBD.KEY_COOK, 1);
                        System.out.println("Hey");
                    }
                    else if(deliver == 0)
                        contentValues.put(WorkBD.KEY_DELIVER, 1);
                    else
                        contentValues.put(WorkBD.KEY_MANAGER, 1);
                    db.insert(WorkBD.TABLE_WORKERS, null, contentValues);
                }
                b = true;
                getList();
            }
        }
    };
}