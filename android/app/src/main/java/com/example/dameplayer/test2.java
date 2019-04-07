package com.example.dameplayer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class test2 extends AppCompatActivity {


    ImageView img;
    int[][] plateau= new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    public void imageSwitch(int x,int  y,ImageView img){
        int typeCase = plateau[x][y] ;
        if (typeCase == 0) {
            img.setImageResource(R.drawable.joueur1);
            plateau[x][y] = 1;
        }else{
            if (typeCase == 1) {
                img.setImageResource(R.drawable.joueur2);
                plateau[x][y] = 2;
            }else{
                if (typeCase == 2) {
                    img.setImageResource(R.drawable.black);
                    plateau[x][y] = 0;
                }
            }
        }
    }

    public void switchImage(View view) {
        switch (view.getId()) {
            case R.id.case1:
                img = (ImageView) findViewById(R.id.case1);
                imageSwitch(0,0,img);
                break;
            case R.id.case2:
                img = (ImageView) findViewById(R.id.case2);
                imageSwitch(0,1,img);
                break;
            case R.id.case3:
                img = (ImageView) findViewById(R.id.case3);
                imageSwitch(0,2,img);
                break;
            case R.id.case4:
                img = (ImageView) findViewById(R.id.case4);
                imageSwitch(0,3,img);
                break;
            case R.id.case5:
                img = (ImageView) findViewById(R.id.case5);
                imageSwitch(1,0,img);
                break;
            case R.id.case6:
                img = (ImageView) findViewById(R.id.case6);
                imageSwitch(1,1,img);
                break;
            case R.id.case7:
                img = (ImageView) findViewById(R.id.case7);
                imageSwitch(1,2,img);
                break;
            case R.id.case8:
                img = (ImageView) findViewById(R.id.case8);
                imageSwitch(1,3,img);
                break;
            case R.id.case9:
                img = (ImageView) findViewById(R.id.case9);
                imageSwitch(2,0,img);
                break;
            case R.id.case10:
                img = (ImageView) findViewById(R.id.case10);
                imageSwitch(2,1,img);
                break;

            case R.id.case11:
                img = (ImageView) findViewById(R.id.case11);
                imageSwitch(2,2,img);
                break;

            case R.id.case12:
                img = (ImageView) findViewById(R.id.case12);
                imageSwitch(2,3,img);
                break;

            case R.id.case13:
                img = (ImageView) findViewById(R.id.case13);
                imageSwitch(3,0,img);
                break;

            case R.id.case14:
                img = (ImageView) findViewById(R.id.case14);
                imageSwitch(3,1,img);
                break;


            case R.id.case15:
                img = (ImageView) findViewById(R.id.case15);
                imageSwitch(3,2,img);
                break;


            case R.id.case16:
                img = (ImageView) findViewById(R.id.case16);
                imageSwitch(3,3,img);
                break;

            case R.id.case17:
                img = (ImageView) findViewById(R.id.case17);
                imageSwitch(4,0,img);
                break;

            case R.id.case18:
                img = (ImageView) findViewById(R.id.case18);
                imageSwitch(4,1,img);
                break;


            case R.id.case19:
                img = (ImageView) findViewById(R.id.case19);
                imageSwitch(4,2,img);
                break;

            case R.id.case20:
                img = (ImageView) findViewById(R.id.case20);
                imageSwitch(4,3,img);
                break;


            case R.id.case21:
                img = (ImageView) findViewById(R.id.case21);
                imageSwitch(5,0,img);
                break;

            case R.id.case22:
                img = (ImageView) findViewById(R.id.case22);
                imageSwitch(5,1,img);
                break;
            case R.id.case23:
                img = (ImageView) findViewById(R.id.case23);
                imageSwitch(5,2,img);
                break;
            case R.id.case24:
                img = (ImageView) findViewById(R.id.case24);
                imageSwitch(5,3,img);
                break;

            case R.id.case25:
                img = (ImageView) findViewById(R.id.case25);
                imageSwitch(6,0,img);
                break;
            case R.id.case26:
                img = (ImageView) findViewById(R.id.case26);
                imageSwitch(6,1,img);
                break;
            case R.id.case27:
                img = (ImageView) findViewById(R.id.case27);
                imageSwitch(6,2,img);
                break;
            case R.id.case28:
                img = (ImageView) findViewById(R.id.case28);
                imageSwitch(6,3,img);
                break;

            case R.id.case29:
                img = (ImageView) findViewById(R.id.case29);
                imageSwitch(7,0,img);
                break;


            case R.id.case30:
                img = (ImageView) findViewById(R.id.case30);
                imageSwitch(7,1,img);
                break;


            case R.id.case31:
                img = (ImageView) findViewById(R.id.case31);
                imageSwitch(7,2,img);
                break;

            case R.id.case32:
                img = (ImageView) findViewById(R.id.case32);
                imageSwitch(7,3,img);
                break;
        }

    }

}
