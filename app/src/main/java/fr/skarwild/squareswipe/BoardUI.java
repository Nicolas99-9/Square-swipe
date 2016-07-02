package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
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

    BoardUI(GameBoard gameBoard, int width, int height){
        this.board = gameBoard;
        boardUI = new ArrayList<>();
        ArrayList<ArrayList<GameBoard.actuel>> tmp = gameBoard.getBoard();


        this.width = width;
        this.height = height;
        float OFFSET = 8f;
        float  widthSquare = Math.min(((width - 5*OFFSET-2*50)/tmp.get(0).size()),200);
        paint = new Paint();
        paint.setAntiAlias(true);
        int col = Color.argb(255,238,238,238);
        paint.setColor(col);

        float decallageHaut = (height - tmp.size()  * (widthSquare + OFFSET))/2f;
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
    }


    public void drawBoard(Canvas c){
        float cornersRadius =18f;
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
    }

    public void checkCollision(float x, float y) {
        for(int i=0;i< boardUI.size();i++) {
            for (int j = 0; j < boardUI.get(0).size(); j++) {
                if(boardUI.get(i).get(j).contains(x,y)){
                    Log.v("Intersection " , " " + i + " " + j);
                }
            }
        }
    }
}
