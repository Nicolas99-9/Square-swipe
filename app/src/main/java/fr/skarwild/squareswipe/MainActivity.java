package fr.skarwild.squareswipe;

import android.app.Activity;
import android.app.IntentService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.icu.text.DecimalFormat;
import android.os.CountDownTimer;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

public class MainActivity extends Activity
{

    private TextView scoreTextView;
    private TextView multiplierTextView;
    private float widthSquare;

    CustomGrid adapter;
    public static GridView gridview;
    private CustomView gameView;
    private int position;

    private int score;
    private float multi;
    private boolean running  = false;
    private CountDownTimer Counter1;

    private boolean isPaused;
    private ImageView pauseView;
    private Animation rotateAnimation;
    String[] t = new String[7*7];
    float decallageHaut;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // gameView = new gameView(this);
        setContentView(R.layout.activity_main);


        initGameVariables();



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





        float OFFSET = 8f;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        widthSquare = Math.min(((width - 5*OFFSET-2*50)/7),200);

        decallageHaut = (height - 7  * (widthSquare + OFFSET))/2f;
        GameBoard g = new GameBoard(7,7);
        adapter = new CustomGrid(MainActivity.this,t,g);
        gridview = (GridView) findViewById(R.id.grid_view);

        gridview.setColumnWidth((int)widthSquare);
        gridview.setPadding(gridview.getLeft(),(int)(decallageHaut),gridview.getRight(),gridview.getBottom());
        gridview.setNumColumns(7);
        gridview.setAdapter(adapter);


        BaseGrid adapter2 = new BaseGrid(MainActivity.this,t,g);
        GridView gridview2 = (GridView) findViewById(R.id.grid_viewSimple);

        gridview2.setColumnWidth((int)widthSquare);
        gridview2.setPadding(gridview.getLeft(),(int)(decallageHaut),gridview.getRight(),gridview.getBottom());
        gridview2.setNumColumns(7);
        gridview2.setAdapter(adapter2);


        gameView = (CustomView) findViewById(R.id.gameview);
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isPaused){
                    return true;
                }
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        checkCollisions(motionEvent.getX(),motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        checkCollisions(motionEvent.getX(),motionEvent.getY());
                        // gameView.invalidate();
                        break;
                    // Player has removed finger from screen
                    case MotionEvent.ACTION_UP:
                        updateScore();
                        gameView.clear();
                        gameView.invalidate();
                        break;
                }
                return true;
            }
        });

        gridview.setAlpha(0);



       /* gridview.setOnTouchListener(new View.OnTouchListener() {
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
        */

        Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                        public void run() {
                               // TODO Auto-generated method stub
                                startShowFirst();
                           }
       }, 500);

        /*Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                for(int i=0;i<gridview.getAdapter().getCount();i++){
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.trans);
                    gridview.getChildAt(i).startAnimation(fadeInAnimation);
                }
            }
        }, 5000);
        */


        Counter1 = new CountDownTimer(120000 , 500) {
            public void onTick(long millisUntilFinished) {
                if(isPaused){
                    return;
                }
                running = true;
                multi -= Math.log(multi)/4f;
                if(multi<=1f){
                    multi = 1.0f;
                    multiplierTextView.setText("x " +String.format("%.2f", multi));
                    Counter1.cancel();
                    running = false;
                    return;
                }

                multiplierTextView.setText("x " +String.format("%.2f", multi));
            }

            public void onFinish() {
                running = false;
                Counter1.start();
            }
        };
        Counter1.start();
        final TextView text = (TextView) findViewById(R.id.timerText);
        text.setTypeface(typeFace);
        final RelativeLayout layoutT = (RelativeLayout) findViewById(R.id.timerLayout);
        new CountDownTimer(5000 , 1000) {
            public void onTick(long millisUntilFinished) {
                text.setText(millisUntilFinished/1000+" !");

            }

            public void onFinish() {
                layoutT.setVisibility(View.GONE);
                isPaused = false;
            }
        }.start();
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotateanim);
        pauseView = (ImageView) findViewById(R.id.pauseB);
        pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPaused = !isPaused;
                if(isPaused){
                    pauseView.startAnimation(rotateAnimation);
                }
                else{
                    pauseView.clearAnimation();
                }
            }
        });

        ImageView b = (ImageView) findViewById(R.id.reloadB);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running ){
                    return;
                }
                initGameVariables();
                GameBoard g = new GameBoard(7,7);
                adapter = new CustomGrid(MainActivity.this,t,g);
                gridview.setAdapter(adapter);
                multiplierTextView.setText("x " +String.format("%.2f", multi));
                score = 0;
                scoreTextView.setText("Score : " + score);
                isPaused = false;
            }
        });
        ImageView skipB = (ImageView) findViewById(R.id.skipB);
        skipB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LovelyStandardDialog(MainActivity.this)
                        .setTopColorRes(R.color.indigo)
                        .setButtonsColorRes(R.color.darkDeepOrange)
                        .setIcon(R.drawable.info)
                        .setTitle(R.string.rate_title)
                        .setMessage(R.string.rate_message)
                        .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    Log.v("QUESRION","yes");
                                    finishGame();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Log.v("QUESRION","no");
                            }
                        })
                        .show();
            }
        });
    }

    private void finishGame() {
        
    }


    private void startShowFirst() {
        gridview.setAlpha(1f);

        for(int i=0;i<gridview.getAdapter().getCount();i++){
            // gridview.getChildAt(i).setAlpha(0f);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_start);
            fadeInAnimation.setStartOffset((int)(Math.random()*gridview.getAdapter().getCount()*50f/3f));
            gridview.getChildAt(i).startAnimation(fadeInAnimation);
        }
       /* Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_start);
        View v = gridview.getChildAt(0);
        v.startAnimation(fadeInAnimation);
        */
        //fadeInAnimation.setStartTime();
    }

    private void initGameVariables() {
        score = 0;
        multi = 1.0f;
        isPaused = true;
    }

    private void updateScore() {
        int taille = gameView.getSize();
        if(taille > 1){
            float newScore = taille * multi;
            score += newScore;
            multi += Math.log(taille)/3f;
            scoreTextView.setText("Score : " + score);
            multiplierTextView.setText("x " +String.format("%.2f", multi));
            if(multi > 1.0f && !running){
                Counter1.cancel();
                Counter1.start();
            }
        }
    }



    private void checkCollisions(float y, float y1) {
        position = gridview.pointToPosition((int)y, (int)y1);
        String s = (String) gridview.getItemAtPosition(position);
        if(s != null) {
            count = Integer.parseInt(s);
            gameView.addInToList(gridview.getChildAt(Integer.parseInt(String.valueOf(gridview.getItemAtPosition(position)))),(count%7),(count/7));

            //gridview.getChildAt(Integer.parseInt(String.valueOf(gridview.getItemAtPosition(position)))).setAlpha(0);
            /*Animation an = new RotateAnimation(0.0f, 360.0f, 0, 0);

            // Set the animation's parameters
            an.setDuration(10000);               // duration in ms
            an.setRepeatCount(0);                // -1 = infinite repeated
            an.setRepeatMode(Animation.REVERSE); // reverses each repeat
            an.setFillAfter(true);               // keep rotation after animation

            View v = gridview.getChildAt(Integer.parseInt(String.valueOf(gridview.getItemAtPosition(position))));

            if(v==null){
                Log.v("VIew is null","null");
            }
            else{
                v.startAnimation(an);
            }
            */

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
