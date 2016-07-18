package fr.skarwild.squareswipe;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bougi on 18/07/2016.
 */
public class endUI extends Fragment {
    private MainActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.end, container, false);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"font1.ttf");
        activity = (MainActivity) getActivity();
        TextView score = (TextView) v.findViewById(R.id.score);
        score.setText(activity.getScore()+"");
        score.setTypeface(typeFace);

        TextView scoreT = (TextView) v.findViewById(R.id.textView);
        scoreT.setTypeface(typeFace);

        //multi
        TextView mutli = (TextView) v.findViewById(R.id.multi);
        mutli.setText(activity.getMulti()+"");
        mutli.setTypeface(typeFace);

        TextView multiT = (TextView) v.findViewById(R.id.textView3);
        multiT.setTypeface(typeFace);

        //best multi
        TextView bestMulti = (TextView) v.findViewById(R.id.best);
        bestMulti.setText(activity.getScore()+"");
        bestMulti.setTypeface(typeFace);

        TextView bestMultiT = (TextView) v.findViewById(R.id.textView4);
        bestMultiT.setTypeface(typeFace);

        ////number games
        TextView Bestscore = (TextView) v.findViewById(R.id.number);
        Bestscore.setText(activity.getScore()+"");
        Bestscore.setTypeface(typeFace);

        TextView BestscoreT = (TextView) v.findViewById(R.id.textView2);
        BestscoreT.setTypeface(typeFace);


        return v;
    }

}
