/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.GridLayout;
import javax.swing.JPanel;
import control.TableroGuiCallback;

/**
 *
 * @author Gianco
 */
public class Tablero extends JPanel implements TableroGuiCallback{
    public static Ficha selected= null;
    static final int MIN_F_TAM= 2;
    static final int MAX_F_TAM= 5;

    //TODO: probar el rendimiento sin esta referencia
    private final Ficha[][] fichas;

    public Tablero(models.Tablero c){
        this(c.size());
        int t= c.size();

        for(int i=0; i<t; ++i)
            for(int j=0; j<t; ++j)
                this.setPieza(c.getPieza(i, j), i, j);
    }

    public Tablero(int t){
        super();

        //llenamos nuestra referencia
        this.fichas = new Ficha[t][t];
        for(int i=0; i<t; ++i)
            for(int j=0; j<t; ++j)
                this.fichas[i][j] = new Ficha();

        //llenamos nuestra interfaz
        this.setLayout(new GridLayout(t, t));
        for(int i=0; i<t; ++i)
            for(int j=0; j<t; ++j)
                this.add(this.fichas[i][j]);
    }

    public Ficha getFicha(int x, int y){
        return this.fichas[x][y];
    }

    @Override
    public void setPieza(models.Pieza p, int x, int y){
        if(p==null)
            this.fichas[x][y].borrar();
        else
            this.fichas[x][y].pintar(p);
    }
}
