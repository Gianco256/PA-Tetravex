/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.Pieza;

/**
 *
 * @author Gianco
 */
public class Ficha extends JPanel implements MouseListener{
    public static final int TAM_MIN= 80;

    private Pieza actual;

    Ficha(){
        super();
        this.setPreferredSize(new Dimension(Ficha.TAM_MIN, Ficha.TAM_MIN));
        this.addMouseListener(this);
        this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        this.setBackground(Colors.getBackground());
        this.actual= null;
    }

    Ficha(Pieza p){
        this();
        this.actual= p;
    }
    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        super.paint(g);
        if(this.actual==null) return;

        //print up
        g.setColor(Colors.getColor(this.actual.getUp()));
        g.fillPolygon(this.getUp());
        //print down
        g.setColor(Colors.getColor(this.actual.getDown()));
        g.fillPolygon(this.getDown());
        //print east
        g.setColor(Colors.getColor(this.actual.getEast()));
        g.fillPolygon(this.getEast());
        //print west
        g.setColor(Colors.getColor(this.actual.getWest()));
        g.fillPolygon(this.getWest());

        //labels
        g.setColor(Color.BLACK);
        g.drawString("" + this.actual.getUp(), this.getWidth()/2-3, 20);
        g.drawString("" + this.actual.getDown(), this.getWidth()/2-3, this.getHeight()-10);
        g.drawString("" + this.actual.getWest(), 8, this.getHeight()/2+3);
        g.drawString("" + this.actual.getEast(), this.getWidth()-16, this.getHeight()/2+3);
        //lines
        g.drawLine(2, 2, this.getWidth()-2, this.getHeight()-2);
        g.drawLine(2, this.getHeight()-2, this.getWidth()-2, 2);
    }

    void borrar(){
        if(this.actual==null) return;
        this.actual= null;
        this.repaint();
    }

    void pintar(Pieza p){
        if(p==null) return;
        this.actual= p;
        this.repaint();
    }

    private Polygon getUp() {
        Polygon p= new Polygon();
        p.addPoint(1, 1);
        p.addPoint(this.getWidth()-1, 1);
        p.addPoint(this.getWidth()/2, this.getHeight()/2);
        return p;
    }

    private Polygon getEast() {
        Polygon p= new Polygon();
        p.addPoint(this.getWidth()-1, 1);
        p.addPoint(this.getWidth()-1, this.getHeight()-1);
        p.addPoint(this.getWidth()/2, this.getHeight()/2);
        return p;
    }

    private Polygon getDown() {
        Polygon p= new Polygon();
        p.addPoint(this.getWidth()-1, this.getHeight()-1);
        p.addPoint(1, this.getHeight()-1);
        p.addPoint(this.getWidth()/2, this.getHeight()/2);
        return p;
    }

    private Polygon getWest(){
        Polygon p= new Polygon();
        p.addPoint(1, this.getHeight()-1);
        p.addPoint(1, 1);
        p.addPoint(this.getWidth()/2, this.getHeight()/2);
        return p;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(Tablero.selected==null){
            if(this.actual!=null){
                Tablero.selected= this;
                this.setBorder(BorderFactory.createLoweredSoftBevelBorder());
            }
        }else if(Tablero.selected==this){
            Tablero.selected= null;
            this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        }else if(this.actual==null){
            if(Juego.INSCREEN.mover(this)){
                Tablero.selected.setBorder(BorderFactory.createRaisedSoftBevelBorder());
                Tablero.selected= null;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
