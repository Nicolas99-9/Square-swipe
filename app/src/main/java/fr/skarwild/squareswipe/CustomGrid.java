package fr.skarwild.squareswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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

    //Construct the image parameter, get context, the array of images, and the height/width of them
    public CustomGrid(Context context, String[] _values) {
        this.context = context;
        this.values = _values;
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
        return values[i];
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
        return convertView;
    }


}