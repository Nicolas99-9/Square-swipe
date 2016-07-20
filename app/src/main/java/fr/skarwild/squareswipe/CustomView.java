package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;
import android.os.Debug;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Nicolas on 09/07/2016.
 */
public class CustomView extends View {
    private Paint paint;
    private ArrayList<Pair<Integer,Integer>> positionsClick;
    private int colorLine;
    private int y =0;
    private int x=0;

    private int lastX;
    private int lastY;
    private Path path;
    private Long lastUpdate;
    private GameBoard board;
    private Square lastSquare;
    private Square current;
    private boolean ajout= false;

    private Point p1;
    private Point p2;


    public CustomView(Context context) {
        super(context);
        init();
    }


    public CustomView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }



    private void init() {
        positionsClick = new ArrayList<>(50);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(15f);
        colorLine = Color.argb(255,212,195,227);
        paint.setColor(colorLine);
        path = new Path();
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       /* for(int i=1;i<positionsClick.size();i++){
            Pair<Integer, Integer> p1 = positionsClick.get(i-1);
            Pair<Integer, Integer> p2 = positionsClick.get(i);
            canvas.drawLine(p1.first,p1.second,p2.first,p2.second,paint);
        }
        */
        canvas.drawPath(path,paint);



    }

    public  void clear(){
        // this.positionsClick.clear();
        path.reset();
    }

    public void addInToList(View childAt,int posX, int posY) {
        if(path.isEmpty()){
            lastSquare = board.getBoard().get(posY).get(posX);
            lastX = posX;
            lastY = posY;
            int[] result = new int[2];
            childAt.getLocationOnScreen(result);
            path.moveTo(result[0]+childAt.getWidth()/2 ,result[1]);
            p1 = new Point(posX, posY);

            invalidate();
        }
        else{
            if((lastX == posX && lastY == posY) ){
                return;
            }
            /*if(Math.sqrt(Math.pow(lastX - posX, 2) + Math.pow(lastY- posY, 2)) != 1){
                return;
            }
            */
            p2 = new Point(posX, posY);
            current = board.getBoard().get(posY).get(posX);

            if(posX < lastX){
                ajout = (current.topDroit == lastSquare.topGauche) || (current.basDroit == lastSquare.basGauche);
            }
            else if(posX > lastY){
                ajout = (current.topGauche == lastSquare.topDroit) || (current.basGauche == lastSquare.basDroit);
            }
            else if(posY > lastY){
                ajout =  (current.basDroit == lastSquare.topDroit) || (current.basGauche == lastSquare.topGauche);
            }
            else if(posY < lastY){
                ajout =  (current.topDroit == lastSquare.basDroit) || (current.topGauche == lastSquare.basGauche);
            }
            if(!ajout){
                return;
            }
            lastX = posX;
            lastY = posY;
            int[] result = new int[2];
            childAt.getLocationOnScreen(result);
            // positionsClick.add(new Pair<Integer, Integer>(result[0]+childAt.getWidth()/2 ,result[1]));
            path.lineTo(result[0]+childAt.getWidth()/2 ,result[1]);
            lastSquare = board.getBoard().get(posY).get(posX);
            ajout = false;
            invalidate();
        }
    }

    public int getSize() {
        return 5;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }
}
