package com.example.dameplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowBoard extends AppCompatActivity {

    ImageView img;
    TextView t;
    Integer threshold = 50;
    Integer WIDTH = 300;
    Integer HEIGHT = 300;
    List<Integer[]> l = new ArrayList<Integer[]>();
    Integer lsize = 0;
    Integer count = 0;
    Integer xTopLeft = 0;
    Integer yTopLeft = 0;
    Integer yBottomRight = 0;
    Integer xBottomRight = 0;

    Double blueFixe1 = -1.0;
    Double redFixe1 = -1.0;
    Double greenFixe1 = -1.0;

    Double blueFixe2 = -1.0;
    Double redFixe2 = -1.0;
    Double greenFixe2 = -1.0;

    Double blueFixe3 = -1.0;
    Double redFixe3 = -1.0;
    Double greenFixe3 = -1.0;

    Integer[][] Pion= new Integer[][] {{1,1,1,1},{1,0,1,1},{1,1,1,1},{0,2,1,0},{0,2,0,0},{2,2,0,2},{2,2,0,2},{2,2,2,2}};


    private Boolean  checkBlack (Bitmap b,int x, int y){
        Boolean res = true;
        int pixelColor = b.getPixel(x, y);
        int pixelRed = Color.red(pixelColor);
        int pixelGreen = Color.green(pixelColor);
        int pixelBlue = Color.blue(pixelColor);
        if ((pixelBlue < threshold) || (pixelGreen < threshold ) || (pixelRed < threshold)){
            res = false;
        }
        return res;
    }

    private Boolean blur(Bitmap b,int x, int y){
        Boolean res = true;
        Integer xleft = x - 5 ;
        if (xleft <0){
            xleft = 0;
        }

        Integer xright = x + 5 ;
        if (xright >WIDTH -1) {
            xright = WIDTH -1;
        }
        Integer ydown = y - 5 ;
        if (ydown <0){
            ydown = 0;
        }
        Integer yup = y + 5 ;
        if (yup >HEIGHT - 1) {
            yup = HEIGHT - 1;
        }
        res = res && checkBlack (b,xleft,yup );
        res = res && checkBlack (b,xleft,y );
        res = res && checkBlack (b,xleft, ydown);
        res = res && checkBlack (b,x , yup);
        res = res && checkBlack (b,x ,ydown );
        res = res && checkBlack (b,xright, yup);
        res = res && checkBlack (b,xright, ydown);
        res = res && checkBlack (b,xright, y);
        return res;
    }


    private Boolean changeRecord(int x,int y){

        if (x < xTopLeft){
            xTopLeft = x;
        }
        if (x > xBottomRight){
            xBottomRight = x;
        }
        if (y < yTopLeft){
            yTopLeft = y;
        }
        if (y > yBottomRight){
            yBottomRight= y;
        }

        return true;
    }

    private Boolean coloringCheckLimit(Bitmap b,int x,int y){
        Integer xleft = x - 1 ;
        if (xleft <0){
            xleft = 0;
        }

        Integer xright = x + 1 ;
        if (xright >WIDTH -1) {
            xright = WIDTH -1;
        }
        Integer ydown = y - 1 ;
        if (ydown <0){
            ydown = 0;
        }
        Integer yup = y + 1 ;
        if (yup >HEIGHT - 1) {
            yup = HEIGHT - 1;
        }
        coloring(b,xright,y);
        coloring(b,x,yup);
        coloring(b,xleft,y);
        coloring(b,x,ydown);



        return true;
    }

    private Boolean coloring(Bitmap b,int x,int y){
        int pixelColor = b.getPixel(x, y);
        int pixelAlpha = Color.alpha(pixelColor);
        int pixelRed = Color.red(pixelColor);
        int pixelGreen = Color.green(pixelColor);
        int pixelBlue = Color.blue(pixelColor);
        int newPixel = 0;
        if ((pixelRed == 255) && (pixelGreen == 255) && (pixelGreen == 255)){
            newPixel = Color.argb(
                    pixelAlpha, 0,255,0);
            b.setPixel(x, y, newPixel);
            count = count + 1 ;
            changeRecord(x,y);
            coloringCheckLimit(b,x,y);
        }
        return true;
    }

    private Boolean coloringSetUp(Bitmap b,int x,int y){

        int newPixel = 0;
        int pixelColor = b.getPixel(x, y);
        int pixelAlpha = Color.alpha(pixelColor);
        newPixel = Color.argb(
                pixelAlpha, 0,255,0);
        b.setPixel(x, y, newPixel);
        count =  1 ;
        xTopLeft = x;
        xBottomRight =x;
        yTopLeft = y;
        yBottomRight =y;

        coloringCheckLimit(b,x,y);

        if (count > 50){
            if ((Math.abs(xBottomRight - xTopLeft) > 10 ) && (Math.abs(xBottomRight - xTopLeft) < 100 )){
                if ((Math.abs(yBottomRight - yTopLeft) > 10 ) && (Math.abs(yBottomRight - yTopLeft) < 100 )){
                    Integer[] res= new Integer[] {count,xTopLeft,xBottomRight,yTopLeft,yBottomRight};
                    l.add(res);
                    lsize = lsize + 1 ;
                }
            }
        }


        return true;
    }


    private Boolean checkForxTopLeft(Integer[][] a,int value){
        int c = 0;
        for (int i =0; i < lsize ;i++) {
            int val = a[i][1] ;
            if (( val < (value + 10)) && (val > (value - 10) )) {
                c = c + 1;
            }
        }
        return (c == 4);
    }

    private Boolean checkForyTopLeft(Integer[][] a,int value){
        int c = 0;
        for (int i =0; i < lsize ;i++) {
            int val = a[i][3] ;
            if (( val < (value + 10)) && (val > (value - 10) )) {
                c = c + 1;
            }
        }
        return (c == 4);
    }

    private Boolean checkForxBottomRight(Integer[][] a,int value){
        int c = 0;
        for (int i =0; i < lsize ;i++) {
            int val = a[i][2] ;
            if (( val < (value + 10)) && (val > (value - 10) )) {
                c = c + 1;
            }
        }
        return (c == 4);
    }



    private Boolean checkForyBottomRight(Integer[][] a,int value){
        int c = 0;
        for (int i =0; i < lsize ;i++) {
            int val = a[i][4] ;
            if (( val < (value + 10)) && (val > (value - 10) )) {
                c = c + 1;
            }
        }
        return (c == 4);
    }



    private List<Integer[]> getBoard(Integer[][] a){
        List<Integer[]> res = new ArrayList<Integer[]>();
        for (int i =0; i < lsize ;i++) {
            Boolean inBoard = true;
            inBoard = inBoard && checkForxTopLeft(a,a[i][1]);
            inBoard = inBoard && checkForyTopLeft(a,a[i][3]);
            inBoard = inBoard && checkForxBottomRight(a,a[i][2]);
            inBoard = inBoard && checkForyBottomRight(a,a[i][4]);
            if (inBoard){
                res.add(a[i]);
            }
        }
        return res;
    }


    private Integer[] getCoordA1(List<Integer[]> L,Bitmap b ){
        Integer[] res = new Integer[4];
        int xLeft = WIDTH;
        int yLeft = 0;
        int xRight = WIDTH;
        int yRight = 0;
        for (int i = 0;i < L.size();i++){
            Integer[] item = L.get(i);
            //faut prendre xleft le plus petit
            //y left le plus grand
            //
            if (item[1] < xLeft) {
                xLeft = item[1];
            }
            if (item[2] < xRight) {
                xRight = item[2];
            }
            if (item[3] > yLeft) {
                yLeft = item[3];
            }
            if (item[4] > yRight){
                yRight = item[4];
            }
        }

        for(int x = xLeft; x < xRight ; x++) {
            for (int y = yLeft; y < yRight; y++) {
                int newPixel = 0;
                int pixelColor = b.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);

                newPixel = Color.argb(
                        pixelAlpha, 0, 0, 255);
                b.setPixel(x, y, newPixel);
            }
        }

        return new Integer[] {xLeft,xRight,yLeft,yRight};
    }



    private Boolean compareMoyenne(Double fixe,Double mobile,Integer interval){
        return ((fixe + interval > mobile) &&  (fixe - interval < mobile));
    }



    //Working
    private Integer compareMoyenne(Double moyenneBlue, Double moyenneGreen,Double moyenneRed){
        Integer res= 4;
        if (blueFixe1 < 0 ){
            blueFixe1 = moyenneBlue;
            redFixe1 = moyenneRed;
            greenFixe1 = moyenneGreen;
            res =4;
        }else {
            if (compareMoyenne(blueFixe1, moyenneBlue, 50) && compareMoyenne(redFixe1, moyenneRed, 50) && compareMoyenne(greenFixe1, moyenneGreen, 50)) {
                res = 4;
            } else {
                if (blueFixe2 < 0) {
                    blueFixe2 = moyenneBlue;
                    redFixe2 = moyenneRed;
                    greenFixe2 = moyenneGreen;
                    res = 5;
                } else {
                    if (compareMoyenne(blueFixe2, moyenneBlue, 50) && compareMoyenne(redFixe2, moyenneRed, 50) && compareMoyenne(greenFixe2, moyenneGreen, 50)) {
                        res = 5;
                    } else {
                        if (blueFixe3 < 0) {
                            blueFixe3 = moyenneBlue;
                            redFixe3 = moyenneRed;
                            greenFixe3 = moyenneGreen;
                            res = 6;
                        } else {
                            if (compareMoyenne(blueFixe3, moyenneBlue, 50) && compareMoyenne(redFixe3, moyenneRed, 50) && compareMoyenne(greenFixe3, moyenneGreen, 50)) {
                                res = 6;
                            }else{
                                //res = pireCas();
                                res = 4;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    //working
    private Integer ExtractValue(int xl,int xr,int yl,int yr,Bitmap vraieImage){
        Double moyenneBlue = 0.0;
        Double moyenneRed = 0.0;
        Double moyenneGreen = 0.0;
        Integer c = 0;
        for(int x = xl; x < xr; x++) {
            for (int y = yl; y < yr; y++) {
                int pixelColor = vraieImage.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);

                moyenneBlue = moyenneBlue + pixelBlue;
                moyenneGreen = moyenneGreen + pixelGreen;
                moyenneRed = moyenneRed + pixelRed;
            }
        }
        moyenneBlue = moyenneBlue / c;
        moyenneGreen = moyenneGreen / c;
        moyenneRed = moyenneRed / c ;
        return compareMoyenne(moyenneBlue,moyenneGreen,moyenneRed);
    }



    private Integer[][] ExtractAllValue(Integer[] caseA1,Bitmap vraieImage){
        Integer [][] res = new Integer[4][4];

        Integer xLA1 = caseA1[0];
        Integer xRA1 = caseA1[1];
        Integer yLA1 = caseA1[2];
        Integer yRA1 = caseA1[0];

        Integer HeightCase = Math.abs(yLA1-yRA1);
        Integer WidthCase = Math.abs(xLA1-xRA1);
        for (int i = 0;i < 8;i++){
            for (int j = 0;j<4;j++){
                res[i][j] = ExtractValue(xLA1 + 2*j*WidthCase,xRA1+ 2*j*WidthCase,yLA1 - 2*i * HeightCase,yRA1- 2*i * HeightCase,vraieImage);
            }
        }
        return res;
    }


    private Boolean ConvertValue(Integer[][] val) {
        for (int i = 0;i < 8;i++){
            for (int j = 0;j<4;j++){
                if (val[i][j] == 4){
                    Pion[i][j] = 1;
                }
                if (val[i][j] == 5){
                    Pion[i][j] = 0;
                }
                if (val[i][j] == 6){
                    Pion[i][j] = 2;
                }
            }
        }
        return true;
    }



    private Boolean checkBoard(Bitmap b,Bitmap vraieImage){
        Boolean res = false;
        for(int x = 0; x < b.getWidth(); x++) {
            for (int y = 0; y < b.getHeight(); y++) {
                int pixelColor = b.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);
                if ((pixelRed == 255) && (pixelGreen == 255) && (pixelBlue == 255)){
                    coloringSetUp(b,x,y);
                }
            }
        }
        int newPixel;
        int pixelColor = b.getPixel(10, 20);
        int pixelAlpha = Color.alpha(pixelColor);
        newPixel = Color.argb(pixelAlpha,255,255,255);

        if (lsize >= 32){
            Integer[][] d = new Integer[l.size()][];

            for (int i = 0; i < l.size();i++){
                d[i] = l.get(i);
            }
            List<Integer[]> newList = new ArrayList<Integer[]>();
            newList = getBoard(d);
            if (newList.size() >= 10){
                Integer[] caseA1 = getCoordA1(newList,b);

                Integer[][] listValue = ExtractAllValue(caseA1,vraieImage);

                ConvertValue(listValue);

                res = true;
            }else{
                //message d'erreur
            }
        }
        return res;
    }


    private Boolean setImage(ImageView v,Integer val){
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



    private Boolean afficherBoard(){

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



    public void imageSwitch(int x,int  y,ImageView img){
        int typeCase = Pion[x][y] ;
        if (typeCase == 0) {
            img.setImageResource(R.drawable.joueur1);
            Pion[x][y] = 1;
        }else{
            if (typeCase == 1) {
                img.setImageResource(R.drawable.joueur2);
                Pion[x][y] = 2;
            }else{
                if (typeCase == 2) {
                    img.setImageResource(R.drawable.black);
                    Pion[x][y] = 0;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String img_path = getIntent().getStringExtra("img_path");
        Uri myUri =  Uri.parse(img_path);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmap, WIDTH, HEIGHT, false);



        Bitmap baw = resizedBitmap.copy(resizedBitmap.getConfig(), true);



        for(int x = 0; x < resizedBitmap.getWidth(); x++) {
            for (int y = 0; y < resizedBitmap.getHeight(); y++) {

                int pixelColor = resizedBitmap.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);
                int newPixel  = 0;
                if ((pixelBlue < threshold) || (pixelGreen < threshold ) || (pixelRed < threshold)){

                    newPixel = Color.argb(
                            pixelAlpha, 255,255,255);
                }else{
                    if (blur(resizedBitmap,x,y)){
                        newPixel = Color.argb(
                                pixelAlpha, 0, 0, 0);
                    }else{
                        newPixel = Color.argb(
                                pixelAlpha, 100,0,0);
                    }
                }
                baw.setPixel(x, y, newPixel);
            }
        }
        //checkBoard(baw,resizedBitmap);
        afficherBoard();
    }


    public void solvePlayer1(View view){
        Intent startSolve = new Intent(this,Solve.class);


        Bundle mBundle = new Bundle();
        mBundle.putSerializable("pion",  Pion);
        startSolve.putExtra("joueur",1);
        startSolve.putExtras(mBundle);
        startActivity(startSolve);
    }

    public void solvePlayer2(View view){
        Intent startSolve = new Intent(this,Solve.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("pion",  Pion);
        startSolve.putExtra("joueur",2);
        startSolve.putExtras(mBundle);
        startActivity(startSolve);
    }





}
