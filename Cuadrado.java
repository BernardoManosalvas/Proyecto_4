/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3_ajedrez;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author alejandro
 */
public class Cuadrado {
    protected int columna; // 123456
    protected int fila; // ABCDEF


    Cuadrado(int fila, int columna){
        setFila(fila);
        setColumna(columna);

    }

    public String toString(){
        return "(" + fila + "," + columna + ")";
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }

    public void filaToChar(){
        // 0 -> A
        // 1 -> B
        // etc
    }

    public boolean equals(Cuadrado cuadrado){
        return this.fila == cuadrado.fila && this.columna == cuadrado.columna;
    }

    public static boolean isCuadradoIn(Cuadrado cuadrado, ArrayList<Cuadrado> cuadrados){
        for (Cuadrado cuadradoTemp : cuadrados){
            if(cuadradoTemp.equals(cuadrado)){
                return true;
            }
        }
        return false;
    }

    public static Cuadrado cuadradoRandom(ArrayList<Cuadrado> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

}
