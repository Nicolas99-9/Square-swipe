package fr.skarwild.squareswipe;

import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by Nicolas on 02/07/2016.
 */
public class BoardUI {
    private ArrayList<ArrayList<RectF>> boardUI;
    private  GameBoard board;
    BoardUI(GameBoard board){
        this.board = board;
    }

}
