package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WelcomePage extends Activity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    static final int GOOGLE_SIGN_IN = 123;
    private ArrayList<String> log;
    private ArrayList<Integer> cook;
    private ArrayList<Integer> deliver;
    private ArrayList<Integer> manager;
    private ArrayList<String > pass;
    private DatabaseReference reference;
    Task<GoogleSignInAccount> task;
    GoogleSignInOptions gso;
    public static boolean k;

    public void singInGoogle(View v){
        System.out.println("singInGoogle");
        Intent singintent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(singintent, GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Hey i`. here");
        if(requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                System.out.println(account + "  ---------------- account");
                if(account != null) firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                System.out.println(e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        System.out.println("TAG" + "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Log.d("TAG", "signInWithCredential:success");

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        System.out.println(mAuth.getCurrentUser().getEmail());
                    } else {
                        Log.w("TAG", "signInWithCredential:failure", task.getException());

                        Toast.makeText(WelcomePage.this, "Ошибка аунтефикаци.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());
            Log.i("TAG", name + " " + "email " + "photo");
            fillList();
        } else {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        log = new ArrayList<>();
        cook = new ArrayList<>();
        deliver = new ArrayList<>();
        manager = new ArrayList<>();
        pass = new ArrayList<>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        try{
            if(currentUser.getEmail() != null)
                fillList();
        }
        catch (NullPointerException e){
            Log.e("TAG", String.valueOf(e));
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(k)
            onretern();
        k = false;
    }

    public void onretern(){
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
        System.out.println("Success");
    }


    public void workerLog(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!= null) {
            boolean d = false;
            boolean m = false;
            boolean a = false;
            boolean c = false;
            System.out.println("HEY++++++++++++++" + log.size());
            for(int i = 0; i < log.size(); i++) {
                if (currentUser.getEmail().equals(pass.get(i))){
                    System.out.println("HEY------");
                    if(deliver.get(i) == 1)
                        d = true;
                    else if (cook.get(i) == 1)
                        c = true;
                    else if(manager.get(i) == 1)
                        m = true;
                    else
                        a = true;
                }
            }
            if (a){
                Intent perehod = new Intent(WelcomePage.this, AdminPage.class);
                startActivity(perehod);
            }
            else if (m){
                Intent perehod = new Intent(WelcomePage.this, ManagerPage.class);
                startActivity(perehod);
            }
            else if (d){
                Intent perehod = new Intent(WelcomePage.this, DeliverPage.class);
                startActivity(perehod);
            }
            else if (c){
                Intent perehod = new Intent(WelcomePage.this , CookPage.class);
                startActivity(perehod);
            }
            else{
                Intent intent = new Intent(this, CatalogPage.class);
                startActivity(intent);
            }
        }
    }

    private void fillList(){
        reference = FirebaseDatabase.getInstance().getReference("workers");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("fingerprints");
                log = new ArrayList<>();
                cook = new ArrayList<>();
                deliver = new ArrayList<>();
                manager = new ArrayList<>();
                pass = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Worker worker = ds.getValue(Worker.class);
                    log.add(worker.login);
                    pass.add(worker.pass);
                    char ch = worker.login.charAt(0);
                    String S = String.valueOf(ch);
                    System.out.println(S);
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
                    else if ("M".equals(S)){
                        System.out.println("Manager");
                        cook.add(0);
                        deliver.add(0);
                        manager.add(1);
                    }
                    else{
                        cook.add(0);
                        deliver.add(0);
                        manager.add(0);
                    }
                }
                workerLog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("JOJPFngidfbnklfdmnldfn");
            }
        };
        reference.addValueEventListener(valueEventListener);
    }

}