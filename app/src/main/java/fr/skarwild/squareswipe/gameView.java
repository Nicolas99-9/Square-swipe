package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nicolas on 29/06/2016.
 */
public class gameView extends SurfaceView implements Runnable {
    private SurfaceHolder ourHolder;
    private Paint paint;
    private boolean playing;
    private  GameBoard board;
    // This variable tracks the game frame rate
    long fps;
    Thread gameThread = null;

    // This is used to help calculate the fps
    private long timeThisFrame;

    private Canvas canvas;
    public gameView(Context context) {
        super(context);

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();
        paint.setAntiAlias(true);
        playing = true;
        board = new GameBoard(5,5);
    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                drawGame(canvas);
                ourHolder.unlockCanvasAndPost(canvas);
            }

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
            }
        }
    }
    public void update() {
        int i = 1;
    }


    void drawGame(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.WHITE);
        drawBoard(canvas);

    }

    void drawBoard(Canvas c){
        paint.setColor(Color.RED);
        RectF rect = new RectF(50,20,100,40);
        int cornersRadius = 25;

        // Finally, draw the rounded corners rectangle object on the canvas
        c.drawRoundRect(
                rect, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paint // Paint
        );
    }
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started theb
// start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


}
