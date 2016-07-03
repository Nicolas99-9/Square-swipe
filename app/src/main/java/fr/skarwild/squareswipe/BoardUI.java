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
    private Context c;



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

        paint = new Paint();
        paint.setAntiAlias(true);
        col = Color.argb(255,238,238,238);
        paint.setColor(col);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10f);

        paintViolet = new Paint();
        paintViolet.setAntiAlias(true);
        paintViolet.setStrokeCap(Paint.Cap.ROUND);
        paintViolet.setColor(Color.argb(255,128,76,169));
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
        paint.setColor(Color.RED);
        Log.v("position",""+positionsClick.size());
        for(int i=1;i<positionsClick.size();i++){
            Pair<Integer, Integer> p1 = positionsClick.get(i-1);
            Pair<Integer, Integer> p2 = positionsClick.get(i);

            RectF e1 = boardUI.get(p1.first).get(p1.second);
            RectF e2 = boardUI.get(p2.first).get(p2.second);
            c.drawLine(e1.centerX(),e1.centerY(),e2.centerX(),e2.centerY(),paint);
        }


        drawSquare(boardUI.get(0).get(3),c);
    }

    public  void drawSquare(RectF rect,Canvas c){
        tmpDrawHalf = new RectF(rect.left,rect.top,0,0);

        c.drawRoundRect(
                tmpDrawHalf, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paintViolet // Paint
        );


        Drawable d = ContextCompat.getDrawable(this.c, R.drawable.squaretest);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP));


        d.setBounds((int)rect.left, (int)rect.top, (int)rect.centerX(), (int)rect.bottom);
        d.draw(c);

        d = ContextCompat.getDrawable(this.c, R.drawable.squaretest2);
        d.setBounds((int)rect.centerX(), (int)rect.top, (int)rect.right, (int)rect.bottom);
        d.draw(c);


        /*
        http://stackoverflow.com/questions/5896234/how-to-use-android-canvas-to-draw-a-roundrect-with-only-topleft-and-topright-cor/28655800#28655800
        http://stackoverflow.com/questions/1705239/how-should-i-give-images-rounded-corners-in-android
        http://stackoverflow.com/questions/17225374/rounding-only-one-image-corner-not-all-four
         */
    }

    public void checkCollision(float x, float y) {
        for(int i=0;i< boardUI.size();i++) {
            for (int j = 0; j < boardUI.get(0).size(); j++) {
                if(boardUI.get(i).get(j).contains(x,y)){
                    if(positionsClick.size()==0){
                        positionsClick.add(new Pair<Integer, Integer>(i,j));
                    }
                    else{
                        if(positionsClick.get(positionsClick.size()-1).first != i || positionsClick.get(positionsClick.size()-1).second != j ){
                            positionsClick.add(new Pair<Integer, Integer>(i,j));
                        }
                    }
                }
            }
        }
    }

    public void reloadPositionClick(){
        positionsClick.clear();
    }


}
