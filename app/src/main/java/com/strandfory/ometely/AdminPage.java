package com.strandfory.ometely;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ArrayList<String> key;
    private static AdapterAdminPage adapterAdminPage;
    private boolean b;
    private String KEY_WOERKERS = "workers";
    private DatabaseReference reference;

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
        id = new ArrayList<>();
        logins = new ArrayList<>();
        passwords = new ArrayList<>();
        adapterAdminPage = new AdapterAdminPage(id,logins,passwords);
        b = true;
        getList();
    }

    public void AdminClickD(View v){
        reference = FirebaseDatabase.getInstance().getReference(KEY_WOERKERS);
        String Nlog = "Deliver_" + logD.getText().toString();
        String Npass = passD.getText().toString();
        Worker worker = new Worker(Nlog, Npass);
        reference.push().setValue(worker);
        logD.setText("");
        passD.setText("");
    }

    public void AdminClickC(View v){
        reference = FirebaseDatabase.getInstance().getReference(KEY_WOERKERS);
        String Nlog = "Cook_" + logC.getText().toString();
        String Npass = passC.getText().toString();
        Worker worker = new Worker(Nlog,Npass);
        reference.push().setValue(worker);
        logC.setText("");
        passC.setText("");
    }

    public void AdminClickM(View v){
        reference = FirebaseDatabase.getInstance().getReference(KEY_WOERKERS);
        String Nlog = "Manager_" + logM.getText().toString();
        String Npass = passM.getText().toString();
        Worker worker = new Worker(Nlog,Npass);
        reference.push().setValue(worker);
        logM.setText("");
        passM.setText("");
    }

    private void getList(){
        reference = FirebaseDatabase.getInstance().getReference(KEY_WOERKERS);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id = new ArrayList<>();
                logins = new ArrayList<>();
                passwords = new ArrayList<>();
                key =  new ArrayList<>();
                int i = 1;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Worker worker = ds.getValue(Worker.class);
                    key.add(ds.getKey());
                    id.add(i);
                    i++;
                    logins.add(worker.login);
                    passwords.add(worker.pass);
                }

                if(b){
                    setAdapter();
                    b = false;
                }
                else{
                    adapterAdminPage.refreshData(id,logins,passwords);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
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
                reference = FirebaseDatabase.getInstance().getReference(KEY_WOERKERS);
                reference.child(key.get(idS)).removeValue();
                getList();
            }
        }
    };
}