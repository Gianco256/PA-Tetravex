/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.Juego.ModoMovimiento;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Gianco
 */
public class Juego extends JFrame{

    static final Juego INSCREEN= new Juego();
    static final int DEFAULT_LEVEL_DIFICULT= 10;
    private static int SEPARACION= 40;

    private static Dimension getPredefine() {
        return new Dimension(
                Ficha.TAM_MIN*Tablero.MAX_F_TAM*2+Juego.SEPARACION,
                Ficha.TAM_MIN*Tablero.MAX_F_TAM+40);
    }

    private int size, dif;
    private Tablero t, b;

    public static void main(String[] args){}

    private Juego(){
        super("Tetravex");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createMenu();
        this.nuevoJuego(Tablero.MIN_F_TAM, Juego.DEFAULT_LEVEL_DIFICULT);
        this.setResizable(false);
    }

    private void nuevoJuego(int t, int dif){
        this.size= t;
        this.dif= dif;
        control.Juego.nuevoJuego(t, dif);

        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));

        this.t= new Tablero(control.Juego.getTablero());
        this.b= new Tablero(control.Juego.getBucket());

        control.Juego.setTableroGui(this.t);
        control.Juego.setBucketGui(this.b);

        this.t.setAlignmentY(CENTER_ALIGNMENT);
        this.b.setAlignmentY(CENTER_ALIGNMENT);

        c.add(this.t);
        c.add(new JLabel(" < "));
        c.add(this.b);
        c.setBackground(Colors.getBackground());
        this.setContentPane(c);

        this.pack();
        this.setSize(Juego.getPredefine());
        this.setVisible(true);
    }

    private void NuevoNivel(){
        JOptionPane.showMessageDialog(this, "Nivel " + this.dif/10 + " completado.");
        this.nuevoJuego(this.size, this.dif+10);
    }

    public boolean mover(Ficha f){
        if(Tablero.selected==null) return false;

        ModoMovimiento mode = null;

        Tablero de= (Tablero)f.getParent();
        int dest= de.getComponentZOrder(f);

        Tablero fr= (Tablero)Tablero.selected.getParent();
        int from= fr.getComponentZOrder(Tablero.selected);

        if(fr==this.t){
            if(de==this.t){
                mode= ModoMovimiento.TABLE_TABLE;
            }else if(de==this.b){
                mode= ModoMovimiento.TABLE_BUCKET;
            }
        }else if(fr==this.b){
            if(de==this.t){
                mode= ModoMovimiento.BUCKET_TABLE;
            }else if(de==this.b){
                mode= ModoMovimiento.BUCKET_BUCKET;
            }
        }
        boolean tmp= control.Juego.mover(mode, from/this.size, from%this.size, dest/this.size, dest%this.size);

        if(control.Juego.complete()) this.NuevoNivel();
        return tmp;
    }

    private void createMenu() {
        JMenuBar menubar= new JMenuBar();
        //menu de juego
        JMenu tmp= new JMenu("Juego");
        //nuevoJuego
        tmp.add(new JMenuItem("Nuevo juego")).addActionListener((ActionEvent e) -> {
            Juego.INSCREEN.nuevoJuego();
        });
        //nuevo tamaño
        tmp.add(new JMenuItem("Cambiar tamaño")).addActionListener((ActionEvent e) ->{
            Juego.INSCREEN.selectNivel();
        });
        menubar.add(tmp);
        //menu de informacion
        tmp= new JMenu("Ayuda");
        tmp.add(new JMenuItem("Acerca de"));
        tmp.add(new JMenuItem("Como jugar"));
        menubar.add(tmp);
        
        this.setJMenuBar(menubar);
    }

    private void nuevoJuego() {
        this.nuevoJuego(this.size, Juego.DEFAULT_LEVEL_DIFICULT);
    }

    private void selectNivel() {
        Object[] niveles = null;
        try {
            niveles = new Object[Tablero.MAX_F_TAM - Tablero.MIN_F_TAM + 1];
        } catch (Exception e) { return; }

        for (int i = Tablero.MIN_F_TAM; i <= Tablero.MAX_F_TAM; i++){
            niveles[i- Tablero.MIN_F_TAM]= Integer.toString(i);
        }

        try {
            String opt = JOptionPane.showInputDialog(null, "Selecciona el nuevo tamaño:",
                    "", JOptionPane.PLAIN_MESSAGE, null, niveles, niveles[0]).toString();

            this.nuevoJuego(Integer.decode(opt), Juego.DEFAULT_LEVEL_DIFICULT);
        } catch (Exception e){}
    }
}
