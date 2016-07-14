package fr.skarwild.squareswipe;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nicolas on 02/07/2016.
 */
public class GameBoard {
    //premiere valeur : haut gauche, haut droite, bas droite, bas gauche
    //R : rose , B : bleu , V : vert , O : violet


    private ArrayList<ArrayList<Square>> board;
    private int score;
    private float multiplier;


    public GameBoard(int lignes, int colonnes){
        board = new ArrayList<>();
        for(int i=0;i< lignes;i++){
            board.add(new ArrayList<Square>());
            for(int j=0;j<colonnes;j++){
                board.get(i).add(new Square(Square.actuel.Bleu,Square.actuel.Bleu,Square.actuel.Violet,Square.actuel.Violet,0f));
            }
        }

        debugShow();
        multiplier = 1.0f;
        score = 0;
    }

    public  void debugShow(){
        for(int i =0 ;i < board.size();i++){
            String buffer = "";
            for(int j=0;j<board.get(0).size();j++){
                buffer = buffer +  board.get(i).get(j).toString();

            }

        }
    }
    public  ArrayList<ArrayList<Square>> getBoard(){
        return board;
    }
}
