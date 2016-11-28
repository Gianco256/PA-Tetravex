/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import models.Pieza;
import models.Tablero;

/**
 *
 * @author Gianco
 */
public class Juego {

    public enum ModoMovimiento{
        TABLE_TABLE,
        TABLE_BUCKET,
        BUCKET_TABLE,
        BUCKET_BUCKET
    }

    private static Tablero tablero, bucket;
    private static TableroGuiCallback tableroGui, bucketGui;

    public static boolean nuevoJuego(int t, int dificultad){
        if(t<=0) return false;
        Juego.tablero= new Tablero(t, false);
        Juego.bucket= new Tablero(t, dificultad);
        return true;
    }

    public static boolean complete() {
        return Juego.tablero!=null && Juego.tablero.complete();
    }

    public static void setTableroGui(TableroGuiCallback t){
        Juego.tableroGui= t;
    }

    public static TableroGuiCallback getTableroGui() {
        return Juego.tableroGui;
    }

    public static void setBucketGui(TableroGuiCallback b){
        Juego.bucketGui= b;
    }

    public static TableroGuiCallback getBucketGui() {
        return Juego.bucketGui;
    }

    public static Tablero getTablero(){
        try {
            return (Tablero)Juego.tablero.clone();
        } catch (CloneNotSupportedException ex) {}
        return null;
    }

    public static Tablero getBucket(){
        try {
            return (Tablero)Juego.bucket.clone();
        } catch (CloneNotSupportedException ex) {}
        return null;
    }
    
    public static boolean mover(ModoMovimiento m, int fromX, int fromY, int destX, int destY){
        Tablero f, d;
        TableroGuiCallback fg, dg;
        switch(m){
            case TABLE_TABLE:
                f= Juego.tablero;
                d= f;
                fg= Juego.tableroGui;
                dg= fg;
                break;
            case TABLE_BUCKET:
                f= Juego.tablero;
                d= Juego.bucket;
                fg= Juego.tableroGui;
                dg= Juego.bucketGui;
                break;
            case BUCKET_TABLE:
                f= Juego.bucket;
                d= Juego.tablero;
                fg= Juego.bucketGui;
                dg= Juego.tableroGui;
                break;
            case BUCKET_BUCKET:
                f= Juego.bucket;
                d= f;
                fg= Juego.bucketGui;
                dg= fg;
                break;
            default: return false;
        }

        Pieza p= f.getPieza(fromX, fromY);

        if(d.movimientoValido(p, destX, destY)){
            d.setPieza(p, destX, destY);
            dg.setPieza(p, destX, destY);
            f.setPieza(null, fromX, fromY);
            fg.setPieza(null, fromX, fromY);
            return true;
        }
        return false;
    }
}
