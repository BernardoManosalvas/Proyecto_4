/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3_ajedrez;

/**
 *
 * @author alejandro
 */
public class SinMovimientos extends Exception{
    SinMovimientos(String error){
        super(error);
    }
}
