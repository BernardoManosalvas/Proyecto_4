/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3_ajedrez;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alejandro
 */
public class Tablero {
    public static final int size = 6;

    public static Alfil alfil1;
    public static  Alfil alfil2;

    public static Caballo caballo1;
    public static  Caballo caballo2;

    public static Torre torre1;
    public static Torre torre2;

    public static Rey reyBlanco;
    public static Rey reyNegro;

    public static Ficha[] fichas;

    private static ArrayList<Integer> columnas = new ArrayList<>();

    private static final Scanner input = new Scanner(System.in);
    private static final Pattern nombresFichas = Pattern.compile("((T|H|S)(1|2))|(KW)");

    private static void llenarFichas(){
        Ficha[] fichasTemp = {torre1, torre2, caballo1, caballo2, alfil1, alfil2, reyBlanco, reyNegro};
        fichas = fichasTemp;
    }

    public static void jugar(){
        String s = "";
        s += "MINI AJEDREZ\n\n";
        s += "REGLAS\n";
        s += "El objetivo del juego es hacer jaque mate a las fichas negras.\n";
        s += "Cada turno puedes seleccionar una ficha blanca para mover, y el computador generará aleatoriamente el movimiento.\n";
        s += "Cada turno el computador también moverá su rey negro.\n";
        s += "Para mover una ficha ingrese el nombre de la ficha.\n\n";
        s += "FICHAS\n";
        s += "H: Caballos\t\tT: Torres\t\tS: Alfiles\t\tKW: ReyBlanco\t\tKB: Rey Negro\n";
        System.out.println(s);

        do{
            try{
                elegirTablero();
                mostrar();
                break;
            }
            catch (InputMismatchException  exp){
                System.out.println("Ingrese una opción válida\n");
                input.nextLine();
            }
            catch(IOException exp){
                System.out.println("Ingrese una opción válida\n");
            }
        }while(true);



        do{
            try{
                moverFicha();
                if(reyNegro.posiblesMovimientos(getPosicionesPeligro()).size()==0){
                    throw new SinMovimientos("Ficha Negra");
                }
                mostrar();
            }
            catch(SinMovimientos exp){
                if(exp.getMessage().equals("Ficha Blanca")){
                    System.out.println("Seleccione una Ficha que pueda hacer movimientos\n");
                }
                else if(exp.getMessage().equals("Ficha Negra")){
                    mostrar();
                    System.out.println("\n\n____ Jaque Mate ____");
                    break;
                }
                /*
                else if(exp.getMessage().equals("Tablas")){
                    mostrar();
                    System.out.println("\n\n____ Tablas del ahogado ____");
                    break;
                }
*/
            }
            catch(IOException exp){
                System.out.println("Ingrese una ficha válida !!\n");
            }
        }while(true);



    }

    private static void elegirTablero() throws IOException{
        String t ="";
        t += "Seleccione la distribución inicial del tablero.\n";
        t += "1. Posicion Fija.\n";
        t += "2. Posicion Aleatoria.\n";
        t += "Seleccion: ";
        System.out.print(t);
        int opcion = input.nextInt();
        input.nextLine();
        System.out.println("\n");
        switch (opcion) {
            case 1:
                posicionFija();
                break;
            case 2:
                posicionAleatoria();
                break;
            default:
                throw new IOException("Opcion no valida");
        }
        llenarFichas();

    }

    // Bernardo
    private static void posicionFija(){
        // torre cabballo alfin alfil caballlo torre
        // E, 4 rey negro
        // F, 1 rey blanco
        torre1 = new Torre("T1", 'B', new Cuadrado(1,1));
        caballo1 = new Caballo("H1", 'B', new Cuadrado(1,2));
        alfil1 = new Alfil("S1", 'B', new Cuadrado(1,3));
        alfil2 = new Alfil("S2", 'B', new Cuadrado(1,4));
        caballo2 = new Caballo("H2", 'B', new Cuadrado(1,5));
        torre2 = new Torre("T2", 'B', new Cuadrado(1,6));
        reyBlanco = new Rey("KW", 'B', new Cuadrado(6,1));
        reyNegro = new Rey("KB", 'N', new Cuadrado(5,4));  //(5,4)
    }

    // Recursion para obtener columna aleatoria para posicion inicial de tablero aleatorio
    private static int columnaAleatoria(){
        Random r = new Random();
        int randomNumber = r.nextInt((6 - 1) + 1) + 1;

        boolean flag = false;

        for(int i=0; i<columnas.size(); i++){
            if(randomNumber == columnas.get(i)){
                flag = true;
                break;
            }
        }

        if(flag){
            return columnaAleatoria();
        }else{
            columnas.add(randomNumber);
            return randomNumber;
        }
    }

    private static void posicionarReyNegro(){
        ArrayList<Cuadrado> ocupadas;
        ocupadas = getPosicionesPeligro();
        Cuadrado temp;

        do{
            Random r = new Random();
            int randomFil = r.nextInt(5) + 2;
            int randomCol = r.nextInt(6) + 1;

            temp = new Cuadrado(randomFil, randomCol);

            for(Cuadrado cuadrado: ocupadas){
                if(cuadrado.equals(temp)){
                    temp = null;
                    break;
                }
            }
            if(temp != null){
                reyNegro.setPosicion(temp);
                try {
                    reyNegro.mover(getPosicionesPeligro());
                } catch (SinMovimientos sinMovimientos) {
                    temp = null;
                }
            }
        }while(temp == null);

    }

    private static void posicionAleatoria(){
        Random r = new Random();
        int randomFil = r.nextInt(5)+2;
        int randomCol = r.nextInt(6)+1;

        torre1 = new Torre("T1", 'B', new Cuadrado(1, columnaAleatoria()));
        torre2 = new Torre("T2", 'B', new Cuadrado(1,columnaAleatoria()));
        caballo1 = new Caballo("H1", 'B', new Cuadrado(1, columnaAleatoria()));
        caballo2 = new Caballo("H2", 'B', new Cuadrado(1, columnaAleatoria()));
        alfil1 = new Alfil("S1", 'B', new Cuadrado(1, columnaAleatoria()));
        alfil2 = new Alfil("S2", 'B', new Cuadrado(1, columnaAleatoria()));
        reyBlanco = new Rey("KW", 'B', new Cuadrado(randomFil, randomCol));
        reyNegro = new Rey("KB", 'N', new Cuadrado(-1, 1));

        llenarFichas();

        posicionarReyNegro();
    }

    private static void moverFicha() throws IOException, SinMovimientos{
        System.out.print("\nMover Ficha: ");
        String nombreTemp = input.nextLine();
        Matcher mNombres = nombresFichas.matcher(nombreTemp);

        if(mNombres.matches()){
            System.out.println("\n");
            // Movimiento blancas
            for(Ficha ficha: fichas){
                if(ficha.getNombre().equals(nombreTemp) && ficha!=reyNegro){
                    ficha.mover(getPosicionesOcupadas());
                    break;
                }
            }

            // Movimiento negras
            reyNegro.mover(getPosicionesPeligro());
        }else{
            throw new IOException("Ficha no valida");

        }
    }

    private static final ArrayList<Cuadrado> getPosicionesOcupadas(){
        ArrayList <Cuadrado> posOcupadas = new ArrayList<> ();
        for(Ficha ficha: fichas){
            posOcupadas.add(ficha.getPosicion());
        }
        return posOcupadas;
    }

    private static final ArrayList<Cuadrado> getPosicionesOcupadas(Ficha fichaExcluida){
        ArrayList <Cuadrado> posOcupadas = new ArrayList<> ();
        for(Ficha ficha: fichas){
            if(!ficha.equals(fichaExcluida))
                posOcupadas.add(ficha.getPosicion());
        }
        return posOcupadas;
    }

    public static final ArrayList<Cuadrado> getPosicionesPeligro(){
        ArrayList <Cuadrado> posPeligro = new ArrayList<> ();
        for(Ficha ficha: fichas){
            if(ficha!=reyNegro){
                posPeligro.addAll(ficha.posiblesMovimientos(getPosicionesOcupadas(reyNegro)));
            }
        }
        posPeligro.addAll(getPosicionesOcupadas());
        return posPeligro;
    }

    private static Ficha getFichaPorPos(Cuadrado posicion){
        Ficha temp = null;
        for(int i=0; i<fichas.length; i++){
            temp = fichas[i];
            if(posicion.equals(temp.getPosicion())){
                return temp;
            }
        }
        return null;
    }

    private static String estaOcupada(Cuadrado posicion){
        Ficha temp = null;

        if(posicion != null){
            temp = getFichaPorPos(posicion);
        }
        if (temp != null){
            return " " + temp.getNombre() + " ";
        }else {
            return " __ ";
        }
    }

    private static void mostrar(){
        System.out.printf("\t\t %d\t %d\t %d\t %d\t %d\t %d\t \n", 1,2,3,4,5,6);
        System.out.println("    -----------------------------");
        for (int i=1; i<size+1; i++){
            System.out.print(i + "\t|\t");
            for(int j=1; j<size+1; j++){
                System.out.print(estaOcupada(new Cuadrado(i,j)));
            }
            System.out.println();
        }
//        // Informacion de fichas y posibles movimientos
//        System.out.println("Tablero");
//
//        for(Ficha ficha: fichas){
//            ArrayList<Cuadrado> posibles = null;
//            if(ficha == alfil1 || ficha == alfil2 || ficha == reyBlanco || ficha == caballo1 ||
//                    ficha == caballo2 || ficha == torre1 || ficha == torre2){
//                posibles = ficha.posiblesMovimientos(getPosicionesOcupadas());
//            }
//            if(ficha == reyNegro){
//                ArrayList<Cuadrado> posReyNegro = new ArrayList<>();
//                posReyNegro.addAll(getPosicionesOcupadas());
//                posReyNegro.addAll(getPosicionesPeligro());
//                posibles = ficha.posiblesMovimientos(posReyNegro);
//
//            }
//            if(posibles != null){
//                System.out.print(ficha);
//                String s = "\tPosibles Movimientos: [";
//                for(int i=0; i<posibles.size(); i++){
//                    s += posibles.get(i).toString() + ",";
//                }
//                s += "]";
//                System.out.println(s);
//            }
//
//        }
//
//        System.out.println("");
    }


}
