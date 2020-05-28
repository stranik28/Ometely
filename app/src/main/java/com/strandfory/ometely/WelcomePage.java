package com.strandfory.ometely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomePage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
    }

    public void guestLog(View v){
        Intent intent = new Intent(this, CatalogPage.class);
        startActivity(intent);
    }

    public void workerLog(View v){
        Intent intentn = new Intent(this, LoginPage.class);
        startActivity(intentn);
    }
}
