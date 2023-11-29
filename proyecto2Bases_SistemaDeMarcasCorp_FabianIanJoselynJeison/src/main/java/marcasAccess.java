
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import static java.sql.JDBCType.DATE;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fabia
 */
public class marcasAccess {
    
        

    private static boolean tiene30Dias(int mes) {
        return mes == 4 || mes == 6 || mes == 9 || mes == 11;
    }
    
    public static void ReproducirSonido(String nombreSonido){
       try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
       } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
         System.out.println("Error al reproducir el sonido.");
       }
     }
    public static void crearMarcas(int codEmpleado, int annoI, int mesI, int diaI, int annoF
            , int mesF, int diaF, int porcentajeAusencia, int planta, int probTardia, int probOMarca ) throws ParseException{
        try {
            mySQLConnectionSimul SQL = new mySQLConnectionSimul();
            // Llamas al método que tiene la clase y te devuelve una conexión
            Connection conn = SQL.conectarMySQL(planta);
            String query = "{CALL CheckEmpleadoExists(?, ?)}";
            System.out.println("luego del call check");
            CallableStatement stmt = conn.prepareCall(query);

            // Establecer el parámetro para el procedimiento almacenado
            stmt.setInt(1, codEmpleado); // Suponiendo que 'codEmpleado' es un int

            // Registrar el parámetro de salida
            stmt.registerOutParameter(2, Types.BIT);

            // Ejecutar el procedimiento almacenado
            stmt.execute();

            // Leer el parámetro de salida
            boolean empleadoExists = stmt.getBoolean(2);

            // Usar 'empleadoExists' según sea necesario en tu aplicación
            System.out.println("Empleado exists: " + empleadoExists);
            // Query que usarás para hacer lo que necesites
            String sSQL =   "";
            String fecha="";
            int cantDias=0;
            int restaAnno=0;
            int restaMes=0;
            int restaDia=0;
            int horaEntrada=0;
            int horaSalida=0;
            int horaE=0;
            int horaS=0;
            int minutoE=0;
            int minutoS=0;
            int probabilidadSalto=0;
            int probabilidadTardia=0;
            int probabilidadOMarca=0;
            int fI=((annoI*10000)+(mesI*100)+(diaI));
            int fF=((annoF*10000)+(mesF*100)+(diaF));
            Random hora= new Random();
            Random minutos=new Random();
            Random porcentaje=new Random();
            Random probT=new Random();
            Random probOM=new Random();
            restaAnno=annoF-annoI;
            restaMes=mesF-mesI;
            restaDia=diaF-diaI;
            String nombreArchivo="Del "+annoI+"-"+mesI+"-"+diaI+" al "+annoF+"-"+mesF+"-"+diaF;
            String str="";
            ManejadorArchivos archivo =new ManejadorArchivos(nombreArchivo);
            if (empleadoExists) {
                if (restaAnno!=0)
                    cantDias=cantDias+365;
                if (restaMes!=0)
                    cantDias=cantDias+30;
                if (restaDia!=0)
                    cantDias=cantDias+restaDia;
                for (int i = 0; i < cantDias+1; i++) {
                    probabilidadSalto=porcentaje.nextInt(100);
                    probabilidadTardia=probT.nextInt(100);
                    probabilidadOMarca=probOM.nextInt(100);
                    if (probabilidadSalto>porcentajeAusencia) {
                        if (probabilidadTardia>probTardia) {
                            do {            
                                minutoE=minutos.nextInt(60);
                                minutoS=minutos.nextInt(60);
                                horaE=5;
                                horaS=hora.nextInt(24);
                                horaEntrada=(minutoE+horaE*100);
                                horaSalida=(minutoS+horaS*100);
                            } while (horaSalida<horaEntrada);    
                        } 
                        else{
                            do {            
                                minutoE=minutos.nextInt(60);
                                minutoS=minutos.nextInt(60);
                                horaE=hora.nextInt(8, 10);
                                horaS=hora.nextInt(24);
                                horaEntrada=(minutoE+horaE*100);
                                horaSalida=(minutoS+horaS*100);
                            } while (horaSalida<horaEntrada);

                        }
                        java.sql.Time timeEntrada;
                        java.sql.Time timeSalida;
                        if (probabilidadOMarca>probOMarca) {
                            String horaEntradaString  =  horaE+":"+minutoE;
                            String horaSalidaString  =  horaS+":"+minutoS;
                            DateFormat formatter = new SimpleDateFormat("HH:mm");
                            timeEntrada = new java.sql.Time(formatter.parse(horaEntradaString).getTime());
                            timeSalida = new java.sql.Time(formatter.parse(horaSalidaString).getTime());
                        } else {
                            String horaEntradaString  =  horaE+":"+minutoE;
                            DateFormat formatter = new SimpleDateFormat("HH:mm");
                            timeEntrada = new java.sql.Time(formatter.parse(horaEntradaString).getTime());
                            timeSalida = null;
                        }
                            
                        if (tiene30Dias(mesI)){
                            if (diaI<=30) {
                                fecha=annoI+"-"+mesI+"-"+diaI; 
                                /*Se envía el dato a la base de datos */
                                diaI=diaI+1;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                sSQL="CALL InsertMarcaPlanta(?,?,?,?)";
                                CallableStatement a=conn.prepareCall(sSQL);
                                a.setInt(1, codEmpleado);
                                a.setDate(2,java.sql.Date.valueOf(fecha));
                                a.setTime(3, timeEntrada);
                                a.setTime(4, timeSalida);

                                a.execute();
                                
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                                fecha=annoI+"-"+mesI+"-"+diaI;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                /*Se envía el dato a la base de datos */
                                sSQL="CALL InsertMarcaPlanta(?,?,?,?)";
                                CallableStatement b=conn.prepareCall(sSQL);
                                b.setInt(1, codEmpleado);
                                b.setDate(2,java.sql.Date.valueOf(fecha));
                                b.setTime(3, timeEntrada);
                                b.setTime(4, timeSalida);

                                b.execute();
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                        }
                        else{
                            if (diaI<=31) {
                                fecha=annoI+"-"+mesI+"-"+diaI;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                /*Se envía el dato a la base de datos */
                                sSQL="CALL InsertMarcaPlanta(?,?,?,?)";
                                CallableStatement c=conn.prepareCall(sSQL);
                                c.setInt(1, codEmpleado);
                                c.setDate(2,java.sql.Date.valueOf(fecha));
                                c.setTime(3, timeEntrada);
                                c.setTime(4, timeSalida);

                                c.execute();
                                diaI=diaI+1;
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                                fecha=annoI+"-"+mesI+"-"+diaI;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                /*Se envía el dato a la base de datos */
                                sSQL="CALL InsertMarcaPlanta(?,?,?,?) ";
                                CallableStatement d=conn.prepareCall(sSQL);
                                d.setInt(1, codEmpleado);
                                d.setDate(2,java.sql.Date.valueOf(fecha));
                                d.setTime(3, timeEntrada);
                                d.setTime(4, timeSalida);

                                d.execute();
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                        }
                    }
                    else{
                        if (tiene30Dias(mesI)){
                            if (diaI<=30) {
                                diaI=diaI+1;
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                            }
                        }
                        else{
                            if (diaI<=31) {
                                diaI=diaI+1;
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                            }
                        }
                    }
                }
                ReproducirSonido("src\\sound\\Minecraft-XP-Sound.wav");
                JOptionPane.showMessageDialog(null, "Felicidades se añadieron las marcas de tiempo", "Confirmación de ingreso de datos", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Terminado");
            }
            else
            {
                ReproducirSonido("src\\sound\\GTA-V-Wasted.wav");
                JOptionPane.showMessageDialog(null, "Error, el usuario ingresado no existe en la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
                
            } 
            conn.close();
        } catch (SQLException se) {
        // Manejo de errores de JDBC
            se.printStackTrace();
        }
         
    }
    public static void crearMarcasPrueba(int codEmpleado, int annoI, int mesI, int diaI, int annoF
            , int mesF, int diaF, int porcentajeAusencia, int planta, int probTardia, int probOMarca ) throws ParseException{
        try {
            mySQLConnectionSimul SQL = new mySQLConnectionSimul();
            // Llamas al método que tiene la clase y te devuelve una conexión
            Connection conn = SQL.conectarMySQL(planta);
            String query = "{CALL CheckEmpleadoExists(?, ?)}";
            CallableStatement stmt = conn.prepareCall(query);

            // Establecer el parámetro para el procedimiento almacenado
            stmt.setInt(1, codEmpleado); // Suponiendo que 'codEmpleado' es un int

            // Registrar el parámetro de salida
            stmt.registerOutParameter(2, Types.BIT);

            // Ejecutar el procedimiento almacenado
            stmt.execute();

            // Leer el parámetro de salida
            boolean empleadoExists = stmt.getBoolean(2);

            // Usar 'empleadoExists' según sea necesario en tu aplicación
            System.out.println("Empleado exists: " + empleadoExists);
            // Query que usarás para hacer lo que necesites
            String sSQL =   "";
            String fecha="";
            int cantDias=0;
            int restaAnno=0;
            int restaMes=0;
            int restaDia=0;
            int horaEntrada=0;
            int horaSalida=0;
            int horaE=0;
            int horaS=0;
            int minutoE=0;
            int minutoS=0;
            int probabilidadSalto=0;
            int probabilidadTardia=0;
            int probabilidadOMarca=0;
            int fI=((annoI*10000)+(mesI*100)+(diaI));
            int fF=((annoF*10000)+(mesF*100)+(diaF));
            Random hora= new Random();
            Random minutos=new Random();
            Random porcentaje=new Random();
            Random probT=new Random();
            Random probOM=new Random();
            restaAnno=annoF-annoI;
            restaMes=mesF-mesI;
            restaDia=diaF-diaI;
            String nombreArchivo="Del "+annoI+"-"+mesI+"-"+diaI+" al "+annoF+"-"+mesF+"-"+diaF;
            String str="";
            ManejadorArchivos archivo =new ManejadorArchivos(nombreArchivo);
            if (empleadoExists) {
                if (restaAnno!=0)
                    cantDias=cantDias+365;
                if (restaMes!=0)
                    cantDias=cantDias+30;
                if (restaDia!=0)
                    cantDias=cantDias+restaDia;
                for (int i = 0; i < cantDias+1; i++) {
                    probabilidadSalto=porcentaje.nextInt(100);
                    probabilidadTardia=probT.nextInt(100);
                    probabilidadOMarca=probOM.nextInt(100);
                    if (probabilidadSalto>porcentajeAusencia) {
                        if (probabilidadTardia>probTardia) {
                            do {            
                                minutoE=minutos.nextInt(60);
                                minutoS=minutos.nextInt(60);
                                horaE=5;
                                horaS=hora.nextInt(24);
                                horaEntrada=(minutoE+horaE*100);
                                horaSalida=(minutoS+horaS*100);
                            } while (horaSalida<horaEntrada);    
                        } 
                        else{
                            do {            
                                minutoE=minutos.nextInt(60);
                                minutoS=minutos.nextInt(60);
                                horaE=hora.nextInt(8, 10);
                                horaS=hora.nextInt(24);
                                horaEntrada=(minutoE+horaE*100);
                                horaSalida=(minutoS+horaS*100);
                            } while (horaSalida<horaEntrada);

                        }
                        java.sql.Time timeEntrada;
                        java.sql.Time timeSalida;
                        if (probabilidadOMarca>probOMarca) {
                            String horaEntradaString  =  horaE+":"+minutoE;
                            String horaSalidaString  =  horaS+":"+minutoS;
                            DateFormat formatter = new SimpleDateFormat("HH:mm");
                            timeEntrada = new java.sql.Time(formatter.parse(horaEntradaString).getTime());
                            timeSalida = new java.sql.Time(formatter.parse(horaSalidaString).getTime());
                        } else {
                            String horaEntradaString  =  horaE+":"+minutoE;
                            DateFormat formatter = new SimpleDateFormat("HH:mm");
                            timeEntrada = new java.sql.Time(formatter.parse(horaEntradaString).getTime());
                            timeSalida = null;
                        }
                            
                        if (tiene30Dias(mesI)){
                            if (diaI<=30) {
                                fecha=annoI+"-"+mesI+"-"+diaI; 
                                /*Se envía el dato a la base de datos */
                                diaI=diaI+1;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                                fecha=annoI+"-"+mesI+"-"+diaI;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                /*Se envía el dato a la base de datos */
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                        }
                        else{
                            if (diaI<=31) {
                                fecha=annoI+"-"+mesI+"-"+diaI;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                /*Se envía el dato a la base de datos */
                                diaI=diaI+1;
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                                fecha=annoI+"-"+mesI+"-"+diaI;
                                str=codEmpleado+" "+fecha+" "+timeEntrada+" "+timeSalida;
                                archivo.escribirEnArchivo(str);
                                /*Se envía el dato a la base de datos */
                                System.out.println(fecha+ " "+horaEntrada+" "+horaSalida);
                            }
                        }
                    }
                    else{
                        if (tiene30Dias(mesI)){
                            if (diaI<=30) {
                                diaI=diaI+1;
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                            }
                        }
                        else{
                            if (diaI<=31) {
                                diaI=diaI+1;
                            }
                            else{
                                diaI=01;
                                mesI=mesI+1;
                            }
                        }
                    }
                }
                ReproducirSonido("src\\sound\\Minecraft-XP-Sound.wav");
                JOptionPane.showMessageDialog(null, "Felicidades se añadieron las marcas de tiempo", "Confirmación de ingreso de datos", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Terminado");
            }
            else
            {
                ReproducirSonido("src\\sound\\GTA-V-Wasted.wav");
                JOptionPane.showMessageDialog(null, "Error, el usuario ingresado no existe en la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
                
            } 
            conn.close();
            /*
        // Suponiendo que tienes una variable de conexión 'conn'.
            String query = "{CALL CheckEmpleadoExists(?, ?)}";
            stmt = conn.prepareCall(query);

            // Establecer el parámetro para el procedimiento almacenado
            stmt.setInt(1, codEmpleado); // Suponiendo que 'codEmpleado' es un int

            // Registrar el parámetro de salida
            stmt.registerOutParameter(2, Types.BIT);

            // Ejecutar el procedimiento almacenado
            stmt.execute();

            // Leer el parámetro de salida
            boolean empleadoExists = stmt.getBoolean(2);

            // Usar 'empleadoExists' según sea necesario en tu aplicación
            System.out.println("Empleado exists: " + empleadoExists);
*/
        } catch (SQLException se) {
        // Manejo de errores de JDBC
            se.printStackTrace();
        }
            
        
        /*
        String str = "";
        try{
            Connection connection = 
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectSql = "EXEC GET_AUTOR";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getString(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getDate(3) +"\t" + 
                        resultSet.getDate(4) +"\t" + 
                        resultSet.getInt(5) + "\t" + 
                        resultSet.getFloat(6) + "\t"+
                        resultSet.getString(7) + "\t" + "\n";
                //System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
*/
    }
}
