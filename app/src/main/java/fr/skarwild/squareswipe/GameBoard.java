package fr.skarwild.squareswipe;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nicolas on 02/07/2016.
 */
public class GameBoard {
    //premiere valeur : haut gauche, haut droite, bas droite, bas gauche
    //R : rose , B : bleu , V : vert , O : violet


    private ArrayList<ArrayList<Square>> board;
    private int score;
    public float multiplier;
    public ArrayList<ArrayList<Integer>> canDoIt;
    private int colonnes;


    public GameBoard(int lignes, int colonnes){
        board = new ArrayList<>();
        this.colonnes = colonnes;
        for(int i=0;i< lignes;i++){
            board.add(new ArrayList<Square>());
            for(int j=0;j<colonnes;j++){
                //Square.actuel.Bleu,Square.actuel.Bleu,Square.actuel.Violet,Square.actuel.Violet,0f
                board.get(i).add(getRandomSquare());
            }
        }

        debugShow();
        multiplier = 1.0f;
        score = 0;



        generateGoodValues();
    }

    private void generateGoodValues() {
        canDoIt = new ArrayList<>();
        for(int i=0;i<colonnes*colonnes;i++){
            ArrayList tmp = new ArrayList<Integer>();
            for(int j=0;j<colonnes*colonnes;j++){
                int count = 0 ;
                int lastX = i%colonnes ;
                int lastY =  i/colonnes ;
                int posX =  j%colonnes;
                int posY =  j/colonnes;
                Square current = board.get(posY).get(posX);
                Square lastSquare =  board.get(lastY).get(lastX);;
                Boolean ajout  = false;
                if(posX < lastX){
                    ajout = (current.topDroit == lastSquare.topGauche) || (current.basDroit == lastSquare.basGauche);
                }
                else if(posX > lastY){
                    ajout = (current.topGauche == lastSquare.topDroit) || (current.basGauche == lastSquare.basDroit);
                }
                else if(posY > lastY){
                    ajout =  (current.basDroit == lastSquare.topDroit) || (current.basGauche == lastSquare.topGauche);
                }
                else if(posY < lastY){
                    ajout =  (current.topDroit == lastSquare.basDroit) || (current.topGauche == lastSquare.basGauche);
                }

                if(Math.sqrt(Math.pow(lastX - posX, 2) + Math.pow(lastY- posY, 2)) != 1){
                    ajout = false;
                }
                if(ajout){
                    count = 1;
                }
                tmp.add(count);
            }
            canDoIt.add(tmp);
        }

        int listSize = canDoIt.size();
        for (int i = 0; i<listSize; i++){
            String buffer ="";
            for(Integer t  : canDoIt.get(i)){
                buffer += " " + t;
            }
            Log.v("BUFFER " , buffer);
        }
        //test
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

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    public Square getRandomSquare() {
        Square randomSquare = new Square(Square.actuel.Vide,Square.actuel.Vide,Square.actuel.Vide,Square.actuel.Vide,0f);
        int mode = randInt(0,1);
        if(mode == 0){
            //horizontal
            int e1 = randInt(1,Square.actuel.values().length-1);
            int e2;
            do{
                e2 = randInt(1,Square.actuel.values().length-1);
            }while(e1 == e2);
            randomSquare.topGauche = Square.actuel.values()[e1];
            randomSquare.topDroit = Square.actuel.values()[e1];
            randomSquare.basDroit = Square.actuel.values()[e2];
            randomSquare.basGauche = Square.actuel.values()[e2];
        }
        else{
            //vertical
            int e1 = randInt(1,Square.actuel.values().length-1);
            int e2;
            do{
                e2 = randInt(1,Square.actuel.values().length-1);
            }while(e1 == e2);
            randomSquare.topGauche = Square.actuel.values()[e1];
            randomSquare.basGauche = Square.actuel.values()[e1];
            randomSquare.basDroit = Square.actuel.values()[e2];
            randomSquare.topDroit = Square.actuel.values()[e2];
        }
        return randomSquare;
    }

    public boolean canDoItS(int lastX, int lastX1, int posX, int posY) {
        return canDoIt.get(posY*colonnes + posX).get(lastX1*colonnes+ lastX)==1;
    }
}
