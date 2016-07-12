package fr.skarwild.squareswipe;

/**
 * Created by Nicolas on 12/07/2016.
 */
public class Square {
    public  enum actuel {Vide,Vert,Rose,Bleu,Violet}
    public actuel topGauche;
    public actuel topDroit;
    public actuel basGauche;
    public actuel basDroit;
    Square(actuel topGauche, actuel topDroit, actuel basGauche, actuel basDroit){
        this.topGauche = topGauche;
        this.topDroit = topDroit;
        this.basGauche = basGauche;
        this.basDroit = basDroit;
    }
}
