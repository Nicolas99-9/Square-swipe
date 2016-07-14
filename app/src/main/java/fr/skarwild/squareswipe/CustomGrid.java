package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class CustomGrid extends BaseAdapter {

    //Variable setup
    private Context context;
    private LayoutInflater mInflater;
    private String[] values;
    private GameBoard game;
    public  static ArrayList<Drawable> combinaisons;
    private Drawable empty;
    //Construct the image parameter, get context, the array of images, and the height/width of them
    public CustomGrid(Context context, String[] _values,GameBoard gameBoard) {
        this.context = context;
        if(combinaisons ==null){
            combinaisons = new ArrayList<>();
            for(int i=0;i<20;i++){
                int nb = i/5;
                Drawable d = ContextCompat.getDrawable(context, R.drawable.squaretest);
                if(i<5){
                    d = ContextCompat.getDrawable(context, R.drawable.topleft);
                }
                else if(i<10){
                    d = ContextCompat.getDrawable(context, R.drawable.topright);
                }
                else if(i<15){
                    d = ContextCompat.getDrawable(context, R.drawable.bottomright);
                }
                else{
                    d = ContextCompat.getDrawable(context, R.drawable.bottomleft);
                }
                if(i%5==0){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,238,238,238), PorterDuff.Mode.SRC_ATOP));
                }
                else if(i%5==1){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,132,202,37), PorterDuff.Mode.SRC_ATOP));
                }
                else if(i%5==2){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,253,51,89), PorterDuff.Mode.SRC_ATOP));
                }
                else if(i%5==3){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,64,145,227), PorterDuff.Mode.SRC_ATOP));
                }
                else if(i%5==4){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,128,76,169), PorterDuff.Mode.SRC_ATOP));
                }
                combinaisons.add(d);
            }
            for(int i=0;i<10;i++){
                Drawable d;
                if(i <5){
                    d = ContextCompat.getDrawable(context, R.drawable.bottomright);
                }
                else{
                    d = ContextCompat.getDrawable(context, R.drawable.bottomleft);
                }
                if(i%5==0){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,238,238,238), PorterDuff.Mode.SRC_ATOP));
                }
                if(i%5==1){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,113,173,32), PorterDuff.Mode.SRC_ATOP));
                }
                if(i%5==2){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,233,37,78), PorterDuff.Mode.SRC_ATOP));
                }
                if(i%5==3){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,64,124,227), PorterDuff.Mode.SRC_ATOP));
                }
                if(i%5==4){
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,107,64,142), PorterDuff.Mode.SRC_ATOP));
                }
                combinaisons.add(d);
            }
            empty =  ContextCompat.getDrawable(context, R.drawable.empty);
        }
        this.values = _values;
        this.game = gameBoard;
        mInflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    //Convert Dp to pixels for modularity
    private int dpToPx(int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    @Override
    public int getCount() {
        if(values==null){
            return 0;
        }
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return i+"";
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        /*ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(values[i]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //  int pad = dpToPx(1);
//    imageView.setPadding(pad,pad,pad,pad);
//      Log.w("Derp", "" + "Height- "+values[i].getHeight());
//    Log.w("Derp", "" + "Width- "+values[i].getWidth());
        imageView.setLayoutParams(new GridView.LayoutParams(values[i].getWidth(), values[i].getHeight()));
        */


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.casesimple, viewGroup, false);
        }

        int x = i%game.getBoard().get(0).size();
        int y = i/game.getBoard().size();
        Square tmpS = game.getBoard().get(y).get(x);



        //top le
        ImageView t = (ImageView) convertView.findViewById(R.id.topLeft);
        t.setImageDrawable(combinaisons.get(game.getBoard().get(y).get(x).topGauche.ordinal()));


        t = (ImageView) convertView.findViewById(R.id.topRight);
        t.setImageDrawable(combinaisons.get(game.getBoard().get(y).get(x).topDroit.ordinal()+ Square.actuel.values().length));


        t = (ImageView) convertView.findViewById(R.id.bottomLeft2);
        t.setImageDrawable(combinaisons.get(game.getBoard().get(y).get(x).basGauche.ordinal()+ 3*Square.actuel.values().length));

        t = (ImageView) convertView.findViewById(R.id.bottomLeft);
        t.setImageDrawable(combinaisons.get(game.getBoard().get(y).get(x).basGauche.ordinal()+ 5*Square.actuel.values().length));

        t = (ImageView) convertView.findViewById(R.id.bottomRight2);
        t.setImageDrawable(combinaisons.get(game.getBoard().get(y).get(x).basDroit.ordinal()+ 2*Square.actuel.values().length));

        t = (ImageView) convertView.findViewById(R.id.bottomRight);
        t.setImageDrawable(combinaisons.get(game.getBoard().get(y).get(x).basDroit.ordinal()+ 4*Square.actuel.values().length));


        /*
        //top right
        t = (ImageView) convertView.findViewById(R.id.topRight);
        t.setImageDrawable(d);
        //bottom left


        t = (ImageView) convertView.findViewById(R.id.bottomLeft2);
        t.setImageDrawable(d);

        t = (ImageView) convertView.findViewById(R.id.bottomLeft);
        t.setImageDrawable(d);

        t = (ImageView) convertView.findViewById(R.id.bottomRight2);
        t.setImageDrawable(d);

        t = (ImageView) convertView.findViewById(R.id.bottomRight);
        t.setImageDrawable(d);
        */


        //ts
       // convertView.setAlpha(tmpS.alpha);
        return convertView;
    }


}