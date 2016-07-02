package fr.skarwild.squareswipe;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity
{

    private gameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new gameView(this);
        setContentView(R.layout.activity_main);

        TextView myTextView=(TextView)findViewById(R.id.scoreText);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"font1.ttf");
        myTextView.setTypeface(typeFace);

        LinearLayout tmp = (LinearLayout) findViewById(R.id.surfaceViewMain);
        tmp.addView(gameView);

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
