/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3_ajedrez;

import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class Alfil extends Ficha {

    Alfil(String nombre, char color, Cuadrado posicion){
        super(nombre, color, posicion);
    }

    @Override

    /*      posOcupadas : Por otras fichas
            posMovimientos : Posiblidades de movimiento

    */
    void mover(ArrayList<Cuadrado> posOcupadas) throws SinMovimientos {
        ArrayList<Cuadrado> posMovimientos = posiblesMovimientos(posOcupadas);
        if(posMovimientos.size()>0){
            Cuadrado posNueva = Cuadrado.cuadradoRandom(posMovimientos);
            this.setPosicion(posNueva);
        }else{
            throw new SinMovimientos("Ficha Blanca");
        }
    }


    @Override
    ArrayList<Cuadrado> posiblesMovimientos(ArrayList<Cuadrado> posOcupadas) {
        int fila = this.posicion.fila;
        int columna = this.posicion.columna;

        ArrayList<Cuadrado> posibles = new ArrayList<>();
        for(int i=1; i<Tablero.size+1; i++){
            for(int j=1; j<Tablero.size+1; j++){
                int deltaFila = i-fila;
                int deltaColumna = j-columna;
                if( Math.abs(deltaFila)==Math.abs(deltaColumna) && deltaFila != 0){
                    posibles.add(new Cuadrado(i,j));
                }
            }
        }

        // Verifica que haya camino que conecte las posiciones final e inicial
        for(int i=0; i<posibles.size(); i++){
            if(!hayCamino (posibles.get(i), posOcupadas)){
                posibles.remove(i);
                i--;
            }
        }
        return posibles;
    }

}
