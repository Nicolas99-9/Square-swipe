package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Pair;
import android.view.Display;

import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by Nicolas on 02/07/2016.
 */
public class BoardUI {
    private ArrayList<ArrayList<RectF>> boardUI;
    private  GameBoard board;

    private int width;
    private int height;
    private Paint paint;
    private int col;

    //pour le dessing
    private float widthSquare;
    private float decallageHaut;

    private Paint paintViolet;
    private float cornersRadius =18f;

    private RectF tmpDrawHalf;

    private ArrayList<Pair<Integer,Integer>> positionsClick;

    private ArrayList<Drawable> combinaisons;

    private Context c;
    private Drawable tmpD;

    private int tailleOmbre = 8;
    private int colorLine;

    private ArrayList<ArrayList<Case>> caseBoard;
    private Pair<Integer, Integer> last;

    BoardUI(GameBoard gameBoard, int width, int height,Context c){
        this.board = gameBoard;
        boardUI = new ArrayList<>();
        ArrayList<ArrayList<GameBoard.actuel>> tmp = gameBoard.getBoard();
        positionsClick = new ArrayList<>();

        this.width = width;
        this.height = height;
        float OFFSET = 8f;
        widthSquare = Math.min(((width - 5*OFFSET-2*50)/tmp.get(0).size()),200);
        this.c = c;

        decallageHaut = (height - tmp.size()  * (widthSquare + OFFSET))/2f;
        float accumulateur = -widthSquare + decallageHaut - OFFSET;
        Log.v("decallageHaut",""+decallageHaut);

        for(int i=0;i< tmp.size();i++){
            boardUI.add(new ArrayList<RectF>());
            accumulateur += widthSquare + OFFSET;
            for(int j=0;j<tmp.get(0).size();j++){
                float decallageGauche =50+j*widthSquare + j * OFFSET;
                //left top right bottom
                boardUI.get(i).add(new RectF(decallageGauche,accumulateur ,decallageGauche+widthSquare,accumulateur +  widthSquare));
                //tst
            }
        }

        caseBoard = new ArrayList<ArrayList<Case>>();
        for(int i=0;i< tmp.size();i++){
            caseBoard.add(new ArrayList<Case>());
            for(int j=0;j<tmp.get(0).size();j++){
                caseBoard.get(i).add(new Case(i,j, boardUI.get(i).get(j)));
            }
        }


        paint = new Paint();
        paint.setAntiAlias(true);
        col = Color.argb(255,238,238,238);
        paint.setColor(col);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(15f);
        colorLine = Color.argb(255,212,195,227);

        paintViolet = new Paint();
        paintViolet.setAntiAlias(true);
        paintViolet.setStrokeCap(Paint.Cap.ROUND);
        paintViolet.setColor(Color.argb(255,128,76,169));

        combinaisons = new ArrayList<>();
        for(int i=0;i<16;i++){
            int nb = i/4;
            Drawable d = ContextCompat.getDrawable(this.c, R.drawable.squaretest);
            if(nb==0){
                d = ContextCompat.getDrawable(this.c, R.drawable.topleft);
            }
            else if(nb==1){
                d = ContextCompat.getDrawable(this.c, R.drawable.topright);
            }
            else if(nb ==2){
                d = ContextCompat.getDrawable(this.c, R.drawable.bottomright);
            }
            else{
                d = ContextCompat.getDrawable(this.c, R.drawable.bottomleft);
            }
            if(i%4==0){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,132,202,37), PorterDuff.Mode.SRC_ATOP));
            }
            if(i%4==1){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,253,51,89), PorterDuff.Mode.SRC_ATOP));
            }
            if(i%4==2){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,64,145,227), PorterDuff.Mode.SRC_ATOP));
            }
            if(i%4==3){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,128,76,169), PorterDuff.Mode.SRC_ATOP));
            }
            combinaisons.add(d);
        }
        for(int i=0;i<8;i++){
            int nb = i/4;
            Drawable d;
            if(nb ==0){
                d = ContextCompat.getDrawable(this.c, R.drawable.bottomright);
            }
            else{
                d = ContextCompat.getDrawable(this.c, R.drawable.bottomleft);
            }
            if(i%4==0){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,113,173,32), PorterDuff.Mode.SRC_ATOP));
            }
            if(i%4==1){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,233,37,78), PorterDuff.Mode.SRC_ATOP));
            }
            if(i%4==2){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,64,124,227), PorterDuff.Mode.SRC_ATOP));
            }
            if(i%4==3){
                d.setColorFilter(new
                        PorterDuffColorFilter(Color.argb(255,107,64,142), PorterDuff.Mode.SRC_ATOP));
            }
            combinaisons.add(d);
        }
    }


    public void drawBoard(Canvas c){
        paint.setColor(col);
        for(int i=0;i< boardUI.size();i++) {
                for (int j = 0; j < boardUI.get(0).size(); j++) {
                c.drawRoundRect(
                        boardUI.get(i).get(j), // rect
                        cornersRadius, // rx
                        cornersRadius, // ry
                        paint // Paint
                );


            }
        }

        drawSquare(boardUI.get(0).get(3),c);

        paint.setColor(colorLine);
       // Log.v("position",""+positionsClick.size());
        for(int i=1;i<positionsClick.size();i++){
            Pair<Integer, Integer> p1 = positionsClick.get(i-1);
            Pair<Integer, Integer> p2 = positionsClick.get(i);

            RectF e1 = boardUI.get(p1.first).get(p1.second);
            RectF e2 = boardUI.get(p2.first).get(p2.second);
            c.drawLine(e1.centerX(),e1.centerY(),e2.centerX(),e2.centerY(),paint);
        }



    }

    public  void drawSquare(RectF rect,Canvas c){
        tmpDrawHalf = new RectF(rect.left,rect.top,0,0);

        c.drawRoundRect(
                tmpDrawHalf, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paintViolet // Paint
        );


        /*
        Drawable d = ContextCompat.getDrawable(this.c, R.drawable.squaretest);

        d.setColorFilter(new
                PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP));


        d.setBounds((int)rect.left, (int)rect.top, (int)rect.centerX(), (int)rect.bottom);
        d.draw(c);

        d = ContextCompat.getDrawable(this.c, R.drawable.squaretest2);
        d.setBounds((int)rect.centerX(), (int)rect.top, (int)rect.right, (int)rect.bottom);
        d.draw(c);
        */


        /*
        http://stackoverflow.com/questions/5896234/how-to-use-android-canvas-to-draw-a-roundrect-with-only-topleft-and-topright-cor/28655800#28655800
        http://stackoverflow.com/questions/1705239/how-should-i-give-images-rounded-corners-in-android
        http://stackoverflow.com/questions/17225374/rounding-only-one-image-corner-not-all-four
         */
        //top left

        for(int i=0;i< boardUI.size();i++) {
            for (int j = 0; j < boardUI.get(0).size(); j++) {
                drawCase(caseBoard.get(i).get(j),c);
            }
        }



    }

    private void drawCase(Case aCase,Canvas c) {
        RectF rect = aCase.getRectangle();
        tmpD = combinaisons.get(aCase.getCol1());
        tmpD.setBounds((int)rect.left, (int)rect.top, (int)rect.centerX(), (int)rect.centerY());
        tmpD.draw(c);

        //top right
        tmpD = combinaisons.get(aCase.getCol2()+4);
        tmpD.setBounds((int)rect.centerX(), (int)rect.top, (int)rect.right, (int)rect.centerY());
        tmpD.draw(c);


        //bottom right2
        tmpD = combinaisons.get(aCase.getCol4()+16);
        tmpD.setBounds((int)rect.centerX(), (int)rect.centerY(), (int)rect.right, (int)rect.bottom);
        tmpD.draw(c);

        //bottom left2
        tmpD = combinaisons.get(aCase.getCol3()+20);
        tmpD.setBounds((int)rect.left, (int)rect.centerY(), (int)rect.centerX(), (int)rect.bottom);
        tmpD.draw(c);

        //bottom right
        tmpD = combinaisons.get(aCase.getCol4()+8);
        tmpD.setBounds((int)rect.centerX(), (int)rect.centerY(), (int)rect.right, (int)rect.bottom-tailleOmbre);
        tmpD.draw(c);

        //bottom left
        tmpD = combinaisons.get(aCase.getCol3()+12);
        tmpD.setBounds((int)rect.left, (int)rect.centerY(), (int)rect.centerX(), (int)rect.bottom-tailleOmbre);
        tmpD.draw(c);

    }

    public void checkCollision(float x, float y) {

        for(int i=0;i< boardUI.size();i++) {
            for (int j = 0; j < boardUI.get(0).size(); j++) {
                if (boardUI.get(i).get(j).contains(x, y)) {
                    if (positionsClick.size() == 0) {
                        positionsClick.add(new Pair<Integer, Integer>(i, j));
                        last = positionsClick.get(positionsClick.size() - 1);
                        return;
                    } else {
                        if ((last.first != i || last.second != j)) {
                           /* if(Math.abs(positionsClick.get(positionsClick.size() - 1).first-i) + Math.abs(positionsClick.get(positionsClick.size() - 1).second-j)==1){
                                positionsClick.add(new Pair<Integer, Integer>(i, j));
                                return;
                            }
                            */

                           /* if(Math.abs(positionsClick.get(positionsClick.size()-1).first-i) + Math.abs(positionsClick.get(positionsClick.size()-1).second-j)==1 ){
                                positionsClick.add(new Pair<Integer, Integer>(i, j));
                                return;
                            }
                            */


                            positionsClick.add(new Pair<Integer, Integer>(i, j));
                            return;



                        }

                        /*if(Math.abs(positionsClick.get(positionsClick.size()-1).first-i) + Math.abs(positionsClick.get(positionsClick.size()-1).second-j)==1 ){
                            positionsClick.add(new Pair<Integer, Integer>(i,j));
                        }
                        */

                    }
                }
            }
        }


       /* if(positionsClick.size()==0) {
            for (int i = 0; i < boardUI.size(); i++) {
                for (int j = 0; j < boardUI.get(0).size(); j++) {
                    if (boardUI.get(i).get(j).contains(x, y)) {
                        positionsClick.add(new Pair<Integer, Integer>(i, j));
                        return;
                    }
                }
            }
        }
        else{
            last = positionsClick.get(positionsClick.size()-1);
            if(last.first > 0){
                if(boardUI.get(last.first-1).get(last.second).contains(x, y)){
                    positionsClick.add(new Pair<Integer, Integer>(last.first-1, last.second));
                    return;
                }
            }
            if(last.first < 6){
                if(boardUI.get(last.first+1).get(last.second).contains(x, y)){
                    positionsClick.add(new Pair<Integer, Integer>(last.first+1, last.second));
                    return;
                }
            }
            if(last.second > 0){
                if(boardUI.get(last.first).get(last.second-1).contains(x, y)){
                    positionsClick.add(new Pair<Integer, Integer>(last.first, last.second-1));
                    return;
                }
            }
            if(last.second < 6){
                if(boardUI.get(last.first).get(last.second+1).contains(x, y)){
                    positionsClick.add(new Pair<Integer, Integer>(last.first, last.second+1));
                    return;
                }
            }
        }
        */
    }

    public void reloadPositionClick(){
        positionsClick.clear();
    }


}
