/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3_ajedrez;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 *
 * @author alejandro
 */
public class Caballo extends Ficha {

    Caballo(String nombre, char color, Cuadrado posicion){
        super(nombre, color, posicion);
    }

    private static int menorQueDos(int i){
        if(Math.abs(i)==2){
            return 2;
        }else if(Math.abs(i)==1){
            return 1;
        }else if(Math.abs(i)==0){
            return 0;
        }else {
            return 4;
        }
    }

    @Override
    void mover(ArrayList<Cuadrado> posicionesOcupadas)  throws SinMovimientos{
        ArrayList<Cuadrado> posMovimientos = posiblesMovimientos(posicionesOcupadas);
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
                Cuadrado temp = new Cuadrado (i, j);
                if(menorQueDos(deltaFila)+menorQueDos(deltaColumna) == 3 && !Cuadrado.isCuadradoIn(temp, posOcupadas)){
                    posibles.add(new Cuadrado(i,j));
                }
            }
        }
        return posibles;
    }
}
