/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author CR104978
 */
public class Numeros {

    public static int entero(String valor) {
        int numero;
        try {
            numero = Integer.parseInt(valor.replaceAll(",", ""));
        } catch (Exception x) {
            numero = -1;
        }
        return numero;
    }

    public static double numero(String valor) {
        double numero;
        try {
            numero = Double.parseDouble(valor.replaceAll(",", ""));
        } catch (NumberFormatException x) {
            numero = -1;
        }
        return numero;
    }
     public static BigDecimal numeroBigD(String valor) {
     
       BigDecimal numero = new BigDecimal(valor);
     
        return numero;
    }
    public static String formateado(double valor, String formato) {
        return new DecimalFormat(formato).format(valor);
    }
}
