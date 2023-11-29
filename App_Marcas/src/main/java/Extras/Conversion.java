/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Extras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sotic
 */
public class Conversion {
    
    public static java.sql.Time strAHora(String hora) throws ParseException{
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        java.util.Date utilDate = formatoHora.parse(hora);
        java.sql.Time horaTime = new java.sql.Time(utilDate.getTime());
        return horaTime;
    }
    
    public static String obtenerDiaSemana(String fechaString){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            // Parsear el String a un objeto Date
            Date fecha = formato.parse(fechaString);
            
            // Obtener el nombre del día de la semana
            SimpleDateFormat formatoDiaSemana = new SimpleDateFormat("EEEE");
            String nombreDiaSemana = formatoDiaSemana.format(fecha);
            return nombreDiaSemana;
            
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
            return "";
        }
    };
   
}
