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

import java.util.Random;

public class CustomGrid extends BaseAdapter {

    //Variable setup
    private Context context;
    private LayoutInflater mInflater;
    private String[] values;
    private GameBoard game;
    //Construct the image parameter, get context, the array of images, and the height/width of them
    public CustomGrid(Context context, String[] _values,GameBoard gameBoard) {
        this.context = context;
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
        //top left
        ImageView t = (ImageView) convertView.findViewById(R.id.topLeft);
        Drawable d =ContextCompat.getDrawable(context, R.drawable.topleft);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.argb(255,253,51,89), PorterDuff.Mode.SRC_ATOP));
        t.setImageDrawable(d);

        //top right
        t = (ImageView) convertView.findViewById(R.id.topRight);
        d =ContextCompat.getDrawable(context, R.drawable.topright);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.argb(255,128,76,169), PorterDuff.Mode.SRC_ATOP));
        t.setImageDrawable(d);
        //bottom left


        t = (ImageView) convertView.findViewById(R.id.bottomLeft2);
        d =ContextCompat.getDrawable(context, R.drawable.bottomleft);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.argb(255,253,51,89), PorterDuff.Mode.SRC_ATOP));
        t.setImageDrawable(d);

        t = (ImageView) convertView.findViewById(R.id.bottomLeft);
        d =ContextCompat.getDrawable(context, R.drawable.bottomleft);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.argb(255,233,37,78), PorterDuff.Mode.SRC_ATOP));
        t.setImageDrawable(d);

        t = (ImageView) convertView.findViewById(R.id.bottomRight2);
        d =ContextCompat.getDrawable(context, R.drawable.bottomright);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.argb(255,128,76,169), PorterDuff.Mode.SRC_ATOP));
        t.setImageDrawable(d);

        t = (ImageView) convertView.findViewById(R.id.bottomRight);
        d =ContextCompat.getDrawable(context, R.drawable.bottomright);
        d.setColorFilter(new
                PorterDuffColorFilter(Color.argb(255,107,64,142), PorterDuff.Mode.SRC_ATOP));
        t.setImageDrawable(d);


        //ts

        return convertView;
    }


}