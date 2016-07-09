package fr.skarwild.squareswipe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nicolas on 29/06/2016.
 */
public class gameView extends SurfaceView implements Runnable {
    private SurfaceHolder ourHolder;
    private boolean playing;

    private  GameBoard board;
    private BoardUI boardUI;
    // This variable tracks the game frame rate
    Thread gameThread = null;

    // This is used to help calculate the fps
    private long timeThisFrame;



    private Canvas canvas;
    private double lastFrame;


    int sleepTime;
    int numberOfFramesSkipped;
    int maxFrameSkips;
    long beginTime;
    long endTime;
    long lastTime;
    int differenceTime;
    int framePeriod;
    int frameCount;
    private float fps = 30f;

    private long lastUpdate;

    public gameView(Activity context) {
        super(context.getApplicationContext());

        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        board = new GameBoard(7,7);
        boardUI = new BoardUI(board,width,height,context);



        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        //ourHolder.setFormat(PixelFormat.TRANSLUCENT);
        playing = true;
        lastFrame = System.currentTimeMillis();

        this.framePeriod = (int)(1000/fps);
        this.maxFrameSkips = 5;
        lastTime = System.currentTimeMillis();
        beginTime = System.currentTimeMillis();

        lastUpdate =  System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (playing) {
            if (ourHolder.getSurface().isValid()) {
                beginTime = System.currentTimeMillis();
                updateS();
                canvas = ourHolder.lockCanvas();
                Draw(canvas);
                ourHolder.unlockCanvasAndPost(canvas);

            }
            frameCount++;

            if(lastTime + 1000 < System.currentTimeMillis()) {
                lastTime = System.currentTimeMillis();
                frameCount = 0;
            }

            endTime = System.currentTimeMillis();;
            differenceTime = (int) (endTime - beginTime);
            sleepTime = (int) (framePeriod - differenceTime);

            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            else {
                while(sleepTime < 0 && numberOfFramesSkipped < this.maxFrameSkips) {
                    updateS();
                    sleepTime += framePeriod;
                    numberOfFramesSkipped++;
                }
            }
            /*try {
                gameThread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            /*try {
                Thread.sleep(16);
            } catch (InterruptedException e) {} //ignore
            Log.v("FPS",""+fps);
            */
        }
    }
   public void updateS()
    {
       boardUI.updateGame();
    }


    protected void Draw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.WHITE);
       //drawBoard(canvas);

    }

    void drawBoard(Canvas c){
        boardUI.drawBoard(c);
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

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                boardUI.checkCollision(motionEvent.getX(),motionEvent.getY());
                break;
            case MotionEvent.ACTION_MOVE:

                /*try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
                //Log.v("MORION DOWN","MOVE" + motionEvent.getEventTime());
                boardUI.checkCollision(motionEvent.getX(),motionEvent.getY());
                break;
            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                boardUI.resetXY();
                break;
        }
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*ùtry {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        return true;

        /*       switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
        }





        */
    }

}
