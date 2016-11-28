/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;

/**
 *
 * @author Gianco
 */
public abstract class Colors {
    //TODO: archivo de configuracion de colores
    static Color getColor(int i){
        switch(i){
            case 0: return Color.BLUE;
            case 1: return Color.GREEN;
            case 2: return Color.CYAN;
            case 3: return Color.LIGHT_GRAY;
            case 4: return Color.ORANGE;
            case 5: return Color.PINK;
            case 6: return Color.YELLOW;
            case 7: return Color.DARK_GRAY;
            case 8: return Color.GRAY;
            case 9: return Color.MAGENTA;
            default: return Color.black;
        }
    }

    static Color getBackground() {
        return Color.GREEN;
    }
}
