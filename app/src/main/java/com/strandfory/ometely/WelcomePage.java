package com.strandfory.ometely;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class WelcomePage extends Activity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    static final int GOOGLE_SIGN_IN = 123;
    private ArrayList<String> log;
    private ArrayList<Integer> cook;
    private ArrayList<Integer> deliver;
    private ArrayList<Integer> manager;
    private ArrayList<String> pass;
    private DatabaseReference reference;
    public static String name;
    private String xd;
    GoogleSignInOptions gso;
    public static boolean k;
    AlertDialog alertDialog;
    public String phone;
    private String verificationId;

    public void singInGoogle(View v) {
        System.out.println("singInGoogle");
        Intent singintent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(singintent, GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Hey i`. here");
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);

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
            name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());
            Log.i("TAG", name + " " + "email " + "photo");
            fillList();
            //add later
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
        try {
            if (currentUser.getEmail() != null || currentUser.getPhoneNumber() != null)
                fillList();
        } catch (NullPointerException e) {
            Log.e("TAG", String.valueOf(e));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (k)
            onretern();
        k = false;
    }

    public void onretern() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> updateUI(null));
    }


    public void workerLog() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String UidUser = currentUser.getUid();
            boolean d = false;
            boolean m = false;
            boolean a = false;
            boolean c = false;
            String passi;
            try {
                passi = currentUser.getEmail();
            } catch (NullPointerException e) {
                passi = currentUser.getPhoneNumber();
            }
            if (passi == null) {
                passi = currentUser.getPhoneNumber();
            }
            System.out.println("HEY++++++++++++++" + log.size());
            for (int i = 0; i < log.size(); i++) {
                if (passi.equals(pass.get(i))) {
                    System.out.println("HEY------");
                    if (deliver.get(i) == 1)
                        d = true;
                    else if (cook.get(i) == 1)
                        c = true;
                    else if (manager.get(i) == 1)
                        m = true;
                    else
                        a = true;
                }
            }
            if (a) {
                Intent perehod = new Intent(WelcomePage.this, AdminPage.class);
                startActivity(perehod);
            } else if (m) {
                Intent perehod = new Intent(WelcomePage.this, ManagerPage.class);
                startActivity(perehod);
            } else if (d) {
                Intent perehod = new Intent(WelcomePage.this, DeliverPage.class);
                startActivity(perehod);
            } else if (c) {
                Intent perehod = new Intent(WelcomePage.this, CookPage.class);
                startActivity(perehod);
            } else {
                Intent intent = new Intent(this, CatalogPage.class);
                startActivity(intent);
            }
        }
    }

    private void fillList() {
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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Worker worker = ds.getValue(Worker.class);
                    log.add(worker.login);
                    pass.add(worker.pass);
                    char ch = worker.login.charAt(0);
                    String S = String.valueOf(ch);
                    System.out.println(S);
                    switch (S) {
                        case "C":
                            cook.add(1);
                            deliver.add(0);
                            manager.add(0);
                            break;
                        case "D":
                            cook.add(0);
                            deliver.add(1);
                            manager.add(0);
                            break;
                        case "M":
                            System.out.println("Manager");
                            cook.add(0);
                            deliver.add(0);
                            manager.add(1);
                            break;
                        default:
                            cook.add(0);
                            deliver.add(0);
                            manager.add(0);
                            break;
                    }
                }
                workerLog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    public void test(View v) {
        Context context = this;
        LayoutInflater li = LayoutInflater.from(context);
        View dialogView = li.inflate(R.layout.dialog_layaot, null);
        Button phonew = (Button) dialogView.findViewById(R.id.phoneB);
        Button login = (Button) dialogView.findViewById(R.id.loginWithPhone);


        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

        mDialogBuilder.setView(dialogView);

        final EditText phoneNumberE = (EditText) dialogView.findViewById(R.id.phoneNumberLog);

        alertDialog = mDialogBuilder.create();

        alertDialog.show();
        phonew.setOnClickListener(v12 -> {
            xd = phoneNumberE.getText().toString();
            sendVerificationCode(xd);
        });

        login.setOnClickListener((View.OnClickListener) v1 -> {
            EditText smsCode = (EditText) dialogView.findViewById(R.id.smsCode);
            String code = smsCode.getText().toString();
            verifyCode(code);
        });
        alertDialog.closeOptionsMenu();
    }

    private void verifyCode(String code) {
        Log.i("TAG", "verifyCode");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        Log.i("TAG", "signInWithCredential");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("TAG", "onComplete");
                        fillList();
                        //add later

                    } else {
                        Log.i("TAG", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
                    }
                });
    }

    private void sendVerificationCode(String number) {
        Log.i("TAG", "sendVerificationCode");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+7" + number,
                20,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.i("TAG", "onCodeSent");
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.i("TAG", "onVerificationCompleted");
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                Log.i("TAG", "code != null");
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.i("TAG", "onVerificationFailed" + "  " + e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
