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
public class Rey extends Ficha {

    Rey(String nombre, char color, Cuadrado posicion){
        super(nombre, color, posicion);
    }
    /*
    @Override
    void mover2(ArrayList<Cuadrado> posOcupadas)  throws SinMovimientos{
        ArrayList<Cuadrado> posMovimientos = posiblesMovimientos(posOcupadas);

        if(posMovimientos.size()>0){
            Cuadrado posNueva = Cuadrado.cuadradoRandom(posMovimientos);
            this.setPosicion(posNueva);
            int nuevosMov = posiblesMovimientos(Tablero.getPosicionesPeligro()).size();
            if(nuevosMov == 0 && this.color == 'N'){
                throw new SinMovimientos("Tablas");
            }
            System.out.println(posiblesMovimientos(Tablero.getPosicionesPeligro()).size());

        }else{
            if(this.color == 'N'){
                if(!Cuadrado.isCuadradoIn(this.posicion, Tablero.getPosicionesPeligro())){
                    throw new SinMovimientos("Tablas");
                }
                throw new SinMovimientos("Ficha Negra");
            }else{
                throw new SinMovimientos("Ficha Blanca");
            }
        }

    }
    */

    @Override
    void mover(ArrayList<Cuadrado> posOcupadas)  throws SinMovimientos{
        ArrayList<Cuadrado> posMovimientos = posiblesMovimientos(posOcupadas);

        if(posMovimientos.size()>0){
            Cuadrado posNueva = Cuadrado.cuadradoRandom(posMovimientos);
            this.setPosicion(posNueva);

        }else{
            if(this.color == 'N'){
                throw new SinMovimientos("Ficha Negra");
            }else{
                throw new SinMovimientos("Ficha Blanca");
            }
        }

    }


    @Override
    ArrayList<Cuadrado> posiblesMovimientos(ArrayList<Cuadrado> posOcupadas) {
        int fila = this.posicion.fila;
        int columna = this.posicion.columna;

        // Movimientos generales de un Rey
        ArrayList<Cuadrado> posibles = new ArrayList<>();
        for(int i=1; i<Tablero.size+1; i++){
            for(int j=1; j<Tablero.size+1; j++){
                int deltaFila = i-fila;
                int deltaColumna = j-columna;
                if( (Math.abs(deltaFila)==1 && Math.abs(deltaColumna)==1) ||
                    (Math.abs(deltaFila)==1 && Math.abs(deltaColumna)==0) ||
                    (Math.abs(deltaFila)==0 && Math.abs(deltaColumna)==1) ) {

                    posibles.add(new Cuadrado(i,j));
                }
            }
        }

        // Eliminar las posiciones bloqueadas
        for(int i=0; i<posibles.size(); i++){
            if(Cuadrado.isCuadradoIn(posibles.get(i), posOcupadas)){
                posibles.remove(i);
                i--; // Recordar que el ArrayList crece dinamicamente
            }
        }



        return posibles;
    }
}
