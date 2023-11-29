/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validaciones;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author sotic
 */
public class Validaciones {
    
    public static boolean validarIdEmpleado(String cadena) {
        String regex = "^-?\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }
    
    public static boolean validarFecha(String cadena) {
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }
    
    public static boolean validarHora(String hora) {
        String patronHora = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        Pattern patron = Pattern.compile(patronHora);
        Matcher matcher = patron.matcher(hora);
        return matcher.matches();
    }
}
