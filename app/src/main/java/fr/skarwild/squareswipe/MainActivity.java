package fr.skarwild.squareswipe;

import android.app.Activity;
import android.app.IntentService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity
{

    private TextView scoreTextView;
    private TextView multiplierTextView;
    private float widthSquare;

    CustomGrid adapter;
    public static GridView gridview;
    private CustomView gameView;
    private int position;
    private int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // gameView = new gameView(this);
        setContentView(R.layout.activity_main);



        scoreTextView =(TextView)findViewById(R.id.scoreText);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"font1.ttf");
        scoreTextView.setTypeface(typeFace);

        LinearLayout tmp = (LinearLayout) findViewById(R.id.surfaceViewMain);
        /*SurfaceHolder sfhTrack = gameView.getHolder();
        sfhTrack.setFormat(PixelFormat.TRANSLUCENT);
        */
        //tmp.addView(gameView);

        multiplierTextView =(TextView)findViewById(R.id.multiplier);
        multiplierTextView.setTypeface(typeFace);



        String[] t = new String[7*7];

        float OFFSET = 8f;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        widthSquare = Math.min(((width - 5*OFFSET-2*50)/7),200);

        float decallageHaut = (height - 7  * (widthSquare + OFFSET))/2f;
        GameBoard g = new GameBoard(7,7);
        adapter = new CustomGrid(MainActivity.this,t,g);
        gridview = (GridView) findViewById(R.id.grid_view);

        gridview.setColumnWidth((int)widthSquare);
        gridview.setPadding(gridview.getLeft(),(int)(decallageHaut),gridview.getRight(),gridview.getBottom());
        gridview.setNumColumns(7);
        gridview.setAdapter(adapter);

        gameView = (CustomView) findViewById(R.id.gameview);
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        /*checkCollisions(motionEvent.getX(),motionEvent.getY());
                        gameView.invalidate();
                        */
                        break;
                    case MotionEvent.ACTION_MOVE:
                        checkCollisions(motionEvent.getX(),motionEvent.getY());
                        // gameView.invalidate();
                        break;
                    // Player has removed finger from screen
                    case MotionEvent.ACTION_UP:
                        gameView.clear();
                        gameView.invalidate();
                        break;
                }
                return true;
            }
        });



        gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();  // MotionEvent types such as ACTION_UP, ACTION_DOWN
                float currentXPosition = motionEvent.getX();
                float currentYPosition = motionEvent.getY();
                int position = gridview.pointToPosition((int) currentXPosition, (int) currentYPosition);
                // Access text in the cell, or the object itself
                String s = (String) gridview.getItemAtPosition(position);
                Log.v("POSITION",position+ " s " + s);

                return true;
            }
        });

    }

    private void checkCollisions(float y, float y1) {
        position = gridview.pointToPosition((int)y, (int)y1);
        String s = (String) gridview.getItemAtPosition(position);
        if(s != null) {
            t = Integer.parseInt(s);
            gameView.addInToList(gridview.getChildAt(Integer.parseInt(String.valueOf(gridview.getItemAtPosition(position)))),(t%7),(t/7));


                 }
        }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        //gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        //gameView.pause();
    }
}
