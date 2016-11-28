/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


/**
 *
 * @author Gianco
 */
public class Pieza {
    private int u, d, e, w;

    public Pieza(int u, int d, int e, int w) {
        this.u = u;
        this.d = d;
        this.e = e;
        this.w = w;
    }

    public int getUp() {
        return u;
    }

    public int getDown() {
        return d;
    }

    public int getEast() {
        return e;
    }

    public int getWest() {
        return w;
    }

    @Override
    public boolean equals(Object o) {
        if(o!=this || !(o instanceof Pieza)) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode(){
        int res= 1;
        res= res*13 + this.u;
        res= res*11 + this.d;
        res= res*0b1101 + this.e;
        return res*013 + this.w;
    }
    
}
