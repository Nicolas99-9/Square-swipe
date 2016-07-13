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

public class BaseGrid extends BaseAdapter {

    //Variable setup
    private Context context;
    private LayoutInflater mInflater;
    private String[] values;
    private GameBoard game;
    public  static ArrayList<Drawable> combinaisons;
    private Drawable empty;
    //Construct the image parameter, get context, the array of images, and the height/width of them
    public BaseGrid(Context context, String[] _values,GameBoard gameBoard) {
        this.context = context;
        if(combinaisons ==null){
            combinaisons = new ArrayList<>();
            for(int i=0;i<4;i++){
                int nb = i/5;
                Drawable d = ContextCompat.getDrawable(context, R.drawable.squaretest);
                if(i<1){
                    d = ContextCompat.getDrawable(context, R.drawable.topleft);
                }
                else if(i<2){
                    d = ContextCompat.getDrawable(context, R.drawable.topright);
                }
                else if(i<3){
                    d = ContextCompat.getDrawable(context, R.drawable.bottomright);
                }
                else{
                    d = ContextCompat.getDrawable(context, R.drawable.bottomleft);
                }
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,238,238,238), PorterDuff.Mode.SRC_ATOP));
                combinaisons.add(d);
            }
            for(int i=0;i<2;i++){
                Drawable d;
                if(i <1){
                    d = ContextCompat.getDrawable(context, R.drawable.bottomright);
                }
                else{
                    d = ContextCompat.getDrawable(context, R.drawable.bottomleft);
                }
                    d.setColorFilter(new
                            PorterDuffColorFilter(Color.argb(255,238,238,238), PorterDuff.Mode.SRC_ATOP));
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.casesimple, viewGroup, false);
        }
        ImageView t = (ImageView) convertView.findViewById(R.id.topLeft);
        t.setImageDrawable(combinaisons.get(0));

        t = (ImageView) convertView.findViewById(R.id.topRight);
        t.setImageDrawable(combinaisons.get(1));


        t = (ImageView) convertView.findViewById(R.id.bottomLeft2);
        t.setImageDrawable(combinaisons.get(2));

        t = (ImageView) convertView.findViewById(R.id.bottomLeft);
        t.setImageDrawable(combinaisons.get(3));

        t = (ImageView) convertView.findViewById(R.id.bottomRight2);
        t.setImageDrawable(combinaisons.get(4));

        t = (ImageView) convertView.findViewById(R.id.bottomRight);
        t.setImageDrawable(combinaisons.get(5));

        return convertView;
    }


}