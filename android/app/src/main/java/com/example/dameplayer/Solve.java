package com.example.dameplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solve extends AppCompatActivity {

    Integer[][] situation;

    private Integer[][] transform88(Integer[][] l){
        Integer[][] res= new Integer[][] {
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1},
                {-1,-1,-1,-1-1,-1,-1,-1}};

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                if (x % 2 ==0){
                    res[x][y*2] = l[x][y];
                }else{
                    res[x][y*2+1] = l[x][y];
                }

            }
        }
        return res;
    }


    private Boolean limit(Integer x){
        return (x >= 0 ) && (x <8);
    }



    private List<Integer[]> getCoupJoueur1(Integer[][] plateau) {
        List<Integer[]> res = new ArrayList<Integer[]>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                if (limit(y+1) && limit(x+1) ) {

                    if (plateau[x+1][y+1] == 0){
                        //res.add(new Integer[]{x,y,x+1,y+1});
                    }
                }
                if (limit(y-1) && limit(x+1) ) {
                    if (plateau[x+1][y-1] == 0){
                        //res.add(new Integer[]{x,y,x+1,y-1});
                    }
                }

                if (limit(y+2) && limit(x+2)){
                    if (plateau[x+2][y+2] == 0){
                        if (plateau[x+1][y+1] == 2){
                            res.add(new Integer[]{x,y,x+2,y+2});
                        }
                    }
                }
                if (limit(y-2) && limit(x+2)){
                    if (plateau[x+2][y-2] == 0){
                        if (plateau[x+1][y-1] == 2){
                            res.add(new Integer[]{x,y,x+2,y-2});
                        }
                    }
                }

            }
        }
        return res;
    }

    private Integer[][] joueCoup(Integer[] coord,Integer[][] plateau){
        Integer tab = plateau[coord[0]][coord[1]];
        plateau[coord[0]][coord[1]] = 0;
        plateau[coord[1]][coord[2]] = tab;
        if (Math.abs(coord[0] - coord[2]) == 2){
            if (coord[0] - coord[2] < 0){
                if (coord[1] - coord[3] < 0){
                    plateau[coord[0]+1][coord[1]+1] = 0;
                }else{
                    plateau[coord[0]+1][coord[1]-1] = 0;
                }

            }else{
                if (coord[1] - coord[3] < 0){
                    plateau[coord[0]-1][coord[1]+1] = 0;
                }else{
                    plateau[coord[0]-1][coord[1]-1] = 0;
                }
            }
        }
        return plateau;
    }


    private Boolean finPartie(Integer[][] plateau){
        Boolean res = false;
        for (int i = 0; i<8; i++ ){
            if (plateau[0][i] == 2){
                res = true;
            }
            if (plateau[8][i] == 1){
                res = true;
            }
        }
        return res;
    }


    private Boolean  partieAlea(Integer[][] plateau,Integer tour){
        Integer[][] newPlateau;
        Random rand = new Random();
        Boolean fin = false;
        Boolean victoire = true;
        if (tour == 1){
            List<Integer[]> listCoup = getCoupJoueur1(plateau);
            if (listCoup.size() == 0){
                fin = true;
                victoire = true;
            }
            newPlateau = joueCoup(listCoup.get(rand.nextInt(listCoup.size())), plateau);
            fin = finPartie(newPlateau);
            if (finPartie(newPlateau) && !fin){
                victoire = true;
                fin = true;
            }
        }else{
            List<Integer[]> listCoup = getCoupJoueur2(plateau);
            if (listCoup.size() == 0){
                fin = true;
                victoire = false;
            }
            newPlateau = joueCoup(listCoup.get(rand.nextInt(listCoup.size())), plateau);
            fin = finPartie(newPlateau);
            if (finPartie(newPlateau) && !fin){
                victoire = false;
                fin = true;
            }
        }
        if (!fin){
            if (tour == 1){
                victoire = partieAlea(newPlateau,2);
            }else{
                victoire = partieAlea(newPlateau,1);
            }
        }

        return victoire;
    }

    private Integer[] mcJ1(Integer[][] plateau){
        List<Integer[]> listCoup = getCoupJoueur1(plateau);

        Integer xavantMax = listCoup.get(0)[0];
        Integer yavantMax = listCoup.get(0)[1];
        Integer xapresMax = listCoup.get(0)[2];
        Integer yapresMax = listCoup.get(0)[3];
        Integer best = 0;


        for (int i = 0 ;  i < listCoup.size();i++){
            Integer xavant = listCoup.get(i)[0];
            Integer yavant = listCoup.get(i)[1];
            Integer xapres = listCoup.get(i)[2];
            Integer yapres = listCoup.get(i)[3];
            Integer score =0;
            for (int j = 0;j<20;j++){
                Boolean simu = partieAlea(joueCoup(listCoup.get(i),plateau),2);
                if (simu){
                    score = score +1;
                }
            }
            if (score > best){
                best = score;
                xavantMax = xavant;
                yavantMax = yavant;
                xapresMax = xapres ;
                yapresMax = yapres;
            }
        }
        return new Integer[] {xavantMax,yavantMax,xapresMax,yapresMax};
    }




    private List<Integer[]> getCoupJoueur2(Integer[][] plateau) {
        List<Integer[]> res = new ArrayList<Integer[]>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (limit(y+1) && limit(x-1) ) {
                    if (plateau[x-1][y+1] == 0){
                        res.add(new Integer[]{x,y,x-1,y+1});
                    }
                }
                if (limit(y-1) && limit(x-1) ) {
                    if (plateau[x-1][y-1] == 0){
                        res.add(new Integer[]{x,y,x-1,y-1});
                    }
                }

                if (limit(y+2) && limit(x-2)){
                    if (plateau[x-2][y+2] == 0){
                        if (plateau[x-1][y+1] == 1){
                            res.add(new Integer[]{x,y,x-2,y+2});
                        }
                    }
                }
                if (limit(y-2) && limit(x-2)){
                    if (plateau[x-2][y-2] == 0){
                        if (plateau[x-1][y-1] == 1){
                            res.add(new Integer[]{x,y,x-2,y-2});
                        }
                    }
                }
            }
        }
        return res;
    }


    private Boolean setImage(ImageView v, Integer val){
        if (val == 0) {
            v.setImageResource(R.drawable.black);
        }
        if (val == 1) {
            v.setImageResource(R.drawable.joueur1);
        }
        if (val == 2) {
            v.setImageResource(R.drawable.joueur2);
        }
        return true;
    }



    private Boolean afficherBoard(Integer[][] Pion){

        setImage((ImageView) findViewById(R.id.case1),Pion[0][0])  ;
        setImage((ImageView) findViewById(R.id.case2),Pion[0][1])  ;
        setImage((ImageView) findViewById(R.id.case3),Pion[0][2])  ;
        setImage((ImageView) findViewById(R.id.case4),Pion[0][3])  ;
        setImage((ImageView) findViewById(R.id.case5),Pion[1][0])  ;
        setImage((ImageView) findViewById(R.id.case6),Pion[1][1])  ;
        setImage((ImageView) findViewById(R.id.case7),Pion[1][2])  ;
        setImage((ImageView) findViewById(R.id.case8),Pion[1][3])  ;
        setImage((ImageView) findViewById(R.id.case9),Pion[2][0])  ;
        setImage((ImageView) findViewById(R.id.case10),Pion[2][1])  ;
        setImage((ImageView) findViewById(R.id.case11),Pion[2][2])  ;
        setImage((ImageView) findViewById(R.id.case12),Pion[2][3])  ;
        setImage((ImageView) findViewById(R.id.case13),Pion[3][0])  ;
        setImage((ImageView) findViewById(R.id.case14),Pion[3][1])  ;
        setImage((ImageView) findViewById(R.id.case15),Pion[3][2])  ;
        setImage((ImageView) findViewById(R.id.case16),Pion[3][3])  ;
        setImage((ImageView) findViewById(R.id.case17),Pion[4][0])  ;
        setImage((ImageView) findViewById(R.id.case18),Pion[4][1])  ;
        setImage((ImageView) findViewById(R.id.case19),Pion[4][2])  ;
        setImage((ImageView) findViewById(R.id.case20),Pion[4][3])  ;
        setImage((ImageView) findViewById(R.id.case21),Pion[5][0])  ;
        setImage((ImageView) findViewById(R.id.case22),Pion[5][1])  ;
        setImage((ImageView) findViewById(R.id.case23),Pion[5][2])  ;
        setImage((ImageView) findViewById(R.id.case24),Pion[5][3])  ;
        setImage((ImageView) findViewById(R.id.case25),Pion[6][0])  ;
        setImage((ImageView) findViewById(R.id.case26),Pion[6][1])  ;
        setImage((ImageView) findViewById(R.id.case27),Pion[6][2])  ;
        setImage((ImageView) findViewById(R.id.case28),Pion[6][3])  ;
        setImage((ImageView) findViewById(R.id.case29),Pion[7][0])  ;
        setImage((ImageView) findViewById(R.id.case30),Pion[7][1])  ;
        setImage((ImageView) findViewById(R.id.case31),Pion[7][2])  ;
        setImage((ImageView) findViewById(R.id.case32),Pion[7][3])  ;
        return true;
    }


    public void toMenu(View view){
        Intent backToMenu = new Intent(this,Menu.class);
        startActivity(backToMenu);
    }



    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Integer tour = getIntent().getIntExtra("joueur", 0);
        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("pion");
        if (objectArray != null) {
            Integer[][] positionAvant = new Integer[objectArray.length][];
            for (int i = 0; i < objectArray.length; i++) {
                positionAvant[i] = (Integer[]) objectArray[i];
            }
            //situation = transform88(positionAvant);
            //getCoupJoueur1(situation);
            if (tour == 1) {
                positionAvant[2][1] = 0;
                positionAvant[3][1] = 0;
                positionAvant[4][2] = 1;
                afficherBoard(positionAvant);
            }else{
                positionAvant[5][0] = 0;
                positionAvant[4][0] = 2;
                positionAvant[4][2] = 1;
                positionAvant[2][1] = 0;
                positionAvant[3][1] = 0;
                positionAvant[4][2] = 1;
                afficherBoard(positionAvant);
            }
        }






    }
}
