package fr.skarwild.squareswipe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private gameView gameView;
    private TextView scoreTextView;
    private TextView multiplierTextView;

    CustomGrid adapter;
    public static GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new gameView(this);
        setContentView(R.layout.activity_main);



        scoreTextView =(TextView)findViewById(R.id.scoreText);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"font1.ttf");
        scoreTextView.setTypeface(typeFace);

        LinearLayout tmp = (LinearLayout) findViewById(R.id.surfaceViewMain);
        /*SurfaceHolder sfhTrack = gameView.getHolder();
        sfhTrack.setFormat(PixelFormat.TRANSLUCENT);
        */
        tmp.addView(gameView);

        multiplierTextView =(TextView)findViewById(R.id.multiplier);
        multiplierTextView.setTypeface(typeFace);



        String[] t = new String[7*7];
        adapter = new CustomGrid(MainActivity.this,t);
        gridview = (GridView) findViewById(R.id.grid_view);
        gridview.setNumColumns(7);
        gridview.setAdapter(adapter);


    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }
}
