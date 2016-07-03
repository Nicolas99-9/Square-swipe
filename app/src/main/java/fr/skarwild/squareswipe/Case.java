package fr.skarwild.squareswipe;

import android.graphics.RectF;

import java.util.Random;

/**
 * Created by Nicolas on 03/07/2016.
 */
public class Case {
    private int i;
    private int j;
    private RectF rectangle;

    private int col1;//haut gauche
    private int col2;//haut droit
    private int col3; //bas gauche
    private int col4; //bas droit

    Case(int i, int j, RectF rect){
        this.i = i;
        this.j = j;
        this.rectangle = rect;
        initColors();
    }
    private int random_val(int min, int max){
        Random r = new Random();
        return  (r.nextInt(max - min) + min);
    }
    private void initColors(){
        int n = random_val(0,2);
        //horizontal
        if(n==0){
            col1 = random_val(0,4);
            col2 = col1;
            do {
                col3 = random_val(0,4);

            }while(col1 == col3);
            col4 = col3;
        }
        else{
            //vertical

            col1 = random_val(0,4);
            col3 = col1;
            do {
                col2 = random_val(0,4);

            }while(col1 == col2);
            col4 = col2;
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public RectF getRectangle() {
        return rectangle;
    }

    public void setRectangle(RectF rectangle) {
        this.rectangle = rectangle;
    }

    public int getCol2() {
        return col2;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    public int getCol4() {
        return col4;
    }

    public void setCol4(int col4) {
        this.col4 = col4;
    }

    public int getCol1() {
        return col1;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public int getCol3() {
        return col3;
    }

    public void setCol3(int col3) {
        this.col3 = col3;
    }
}
