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
public class Torre extends Ficha {

    Torre(String nombre, char color, Cuadrado posicion){
        super(nombre, color, posicion);
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
        int sum = fila + columna;

        ArrayList<Cuadrado> posibles = new ArrayList<>();
        for(int i=1; i<Tablero.size+1; i++){
            for(int j=1; j<Tablero.size+1; j++){
                if(i==fila || j==columna && i+j!=sum){
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
