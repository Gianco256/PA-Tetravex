/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Random;

/**
 *
 * @author Gianco
 */
public class Tablero implements Cloneable{
    public static final int NLIMIT= 9;
    public static int NMIN= 0;

    private boolean logic;
    private Pieza[][] table;
    private int pieces;

    public Tablero(int t, boolean fill){
        t= Math.abs(t);
        this.table= new Pieza[t][t];
        if(fill){this.fillTable(); this.pieces=t*t;}
        this.logic= true;
    }
    public Tablero(int t, int factor){
        this(t, true);
        this.rand(factor);
        this.logic= false;
    }

    private void fillTable(){
        Random r= new Random(System.currentTimeMillis());
        int lim= Tablero.NLIMIT+1;
        int b= Tablero.NMIN;
        this.table[0][0]= new Pieza(r.nextInt(lim)+b,
                                    r.nextInt(lim)+b,
                                    r.nextInt(lim)+b,
                                    r.nextInt(lim)+b);
        
        for(int i= 1; i<this.table.length; i++){
            this.table[0][i]= new Pieza(r.nextInt(lim)+b,
                                        r.nextInt(lim)+b,
                                        r.nextInt(lim)+b,
                                        this.table[0][i-1].getEast());
            
            this.table[i][0]= new Pieza(this.table[i-1][0].getDown(),
                                        r.nextInt(lim)+b,
                                        r.nextInt(lim)+b,
                                        r.nextInt(lim)+b);
        }

        for(int i= 1; i<this.table.length; i++){
            for(int j= 1; j<this.table[i].length; j++){
                this.table[i][j]= new Pieza(this.table[i-1][j].getDown(),
                                            r.nextInt(lim)+b,
                                            r.nextInt(lim)+b,
                                            this.table[i][j-1].getEast());
            }
        }
    }

    private void rand(int factor){
        factor= Math.abs(factor);
        Random r= new Random();
        r.setSeed(System.currentTimeMillis());
        int l= this.table.length;
        for(int i= 0; i<factor; i++){
            int xi= r.nextInt(l);
            int yi= r.nextInt(l);
            int xf= r.nextInt(l);
            int yf= r.nextInt(l);
            Pieza tmp= this.table[xi][yi];
            this.table[xi][yi]= this.table[xf][yf];
            this.table[xf][yf]= tmp;
        }
    }

    public int size(){
        return this.table.length;
    }

    public void setPieza(Pieza p, int x, int y){
        if(this.table[x][y]==null){
            if(p!=null)
                this.pieces++;
        }else if(p==null)
            this.pieces--;
        this.table[x][y]= p;
    }

    public Pieza getPieza(int x, int y){ return this.table[x][y]; }

    public boolean movimientoValido(Pieza p, int x, int y){
        if(Math.abs(x)>=this.table.length) return false;
        if(Math.abs(y)>=this.table.length) return false;
        if(x<0) x= this.table.length+x-1;
        if(y<0) y= this.table.length+y-1;
        //solo se puede mover a espacios vacios
        if(this.table[x][y]!= null) return false;
        if(p==null || !this.logic) return true;

        if(x>0 && this.table[x-1][y]!=null && this.table[x-1][y]!=p &&
                !(this.table[x-1][y].getDown() == p.getUp()))
            return false;
        if(x+1<this.table.length && this.table[x+1][y]!=null && this.table[x+1][y]!=p &&
                !(this.table[x+1][y].getUp() == p.getDown()))
            return false;
        if(y>0 && this.table[x][y-1]!=null && this.table[x][y-1]!=p &&
                !(this.table[x][y-1].getEast() == p.getWest()))
            return false;
        return y+2>this.table.length || this.table[x][y+1]==null || this.table[x][y+1]==p ||
                this.table[x][y+1].getWest() == p.getEast();
    }

    public boolean isLogic() {
        return logic;
    }

    public void setLogic(boolean logic) {
        this.logic = logic;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        Object clone = null;
        try{
            clone = super.clone();
        }catch(CloneNotSupportedException e){}
        return clone;
    }

    public boolean complete() {
        return this.pieces==Math.pow(this.table.length, 2);
    }
}