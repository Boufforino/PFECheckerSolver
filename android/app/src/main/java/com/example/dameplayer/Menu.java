package com.example.dameplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button buttonLoad;
    Button rules;
    Button Credits;




    public void getLoading(View view){
        Intent startLoading= new Intent(this, LoadImage.class);
        startActivity(startLoading);
    }

    public void getRules(View view){
        Intent startRules= new Intent(this,Rules.class);
        startActivity(startRules);
    }

    public void getCredits(View view){
        Intent startCredits= new Intent(this,Credits.class);
        startActivity(startCredits);
    }

    public void getTest(View view){
        Intent startTest= new Intent(this,test2.class);
        startActivity(startTest);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
