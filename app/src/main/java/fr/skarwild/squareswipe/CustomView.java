package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(15f);
        colorLine = Color.argb(255,212,195,227);
        paint.setColor(colorLine);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Log.v("position",""+positionsClick.size());
        for(int i=1;i<positionsClick.size();i++){
            Pair<Integer, Integer> p1 = positionsClick.get(i-1);
            Pair<Integer, Integer> p2 = positionsClick.get(i);
            canvas.drawLine(p1.first,p1.second,p2.first,p2.second,paint);
        }


    }

    public  void clear(){
        this.positionsClick.clear();
    }

    public void addInToList(View childAt,int posX, int posY) {

        Log.v("LASTX ", lastX + " last y " + lastY);
            if(positionsClick.size()==0){
                lastX = posX;
                lastY = posY;
            }
            else{

                if(Math.abs(lastX - posX) + Math.abs(lastY - posY) != 1){
                    return;
                }
                lastX = posX;
                lastY = posY;
            }
            int[] result = new int[2];
            childAt.getLocationOnScreen(result);
            positionsClick.add(new Pair<Integer, Integer>(result[0]+childAt.getWidth()/2 ,result[1]));
            invalidate();
    }
}
