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
public abstract class Ficha {

    protected String nombre;
    protected char color;
    protected Cuadrado posicion;

    Ficha(){}

    Ficha(String nombre, char color, Cuadrado posicion){
        setNombre(nombre);
        setColor(color);
        setPosicion(posicion);
    }

    @Override
    public String toString(){
        String s = "";
        s += "Nombre: " + nombre;
        s += "\tPosicion: " + posicion.toString();
        s += "\tColor: " + color;
        return s;

    }

    public void setPosicion(Cuadrado posicion) {
        this.posicion = posicion;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cuadrado getPosicion() {
        return posicion;
    }

    public char getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    abstract void mover(ArrayList<Cuadrado> posicionesOcupadas) throws SinMovimientos;

    abstract ArrayList<Cuadrado> posiblesMovimientos(ArrayList<Cuadrado> posOcupadas);

    public boolean hayCamino(Cuadrado posFinal, ArrayList<Cuadrado> posOcupadas){
        int deltaFila = posFinal.fila - posicion.fila;
        int deltaColumna = posFinal.columna - posicion.columna;

        if(deltaColumna !=0){
            deltaColumna = deltaColumna/Math.abs(deltaColumna);
        }
        if(deltaFila != 0){
            deltaFila = deltaFila/Math.abs(deltaFila);
        }

        ArrayList <Cuadrado> camino = new ArrayList <>();
        int fila = posicion.fila;
        int columna = posicion.columna;
        Cuadrado temporal;
        do{
            fila += deltaFila;
            columna += deltaColumna;
            temporal = new Cuadrado(fila, columna);
            camino.add(temporal);
        }while(!temporal.equals(posFinal));


        for(int i=0; i<camino.size(); i++){
            if(Cuadrado.isCuadradoIn(camino.get(i), posOcupadas)){
                return false;
            }
        }

        return true;
    }


}

// MOVER
// tiene una direccion y se mueve uno por uno para verificar que no haya fichas en el camino (menos el caballo)
// pasar las posiciones de las otras fichas

// throw exception si no tiene posiciones disponibles para mover

