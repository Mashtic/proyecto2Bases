
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fabia
 */
public class ManejadorArchivos {

    private String nombreArchivo;

    // Constructor que recibe el nombre del archivo al instanciar la clase
    public ManejadorArchivos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    // Método para escribir una cadena de texto en el archivo
    public void escribirEnArchivo(String texto) {
        try {
            // Utilizamos FileWriter y BufferedWriter para escribir en el archivo de manera eficiente
            FileWriter fileWriter = new FileWriter(nombreArchivo, true); // El segundo parámetro true indica que se añadirá al final del archivo si ya existe
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Escribimos el texto en el archivo
            bufferedWriter.write(texto);
            bufferedWriter.newLine(); // Agregamos una nueva línea después de cada escritura

            // Cerramos el BufferedWriter para liberar recursos
            bufferedWriter.close();
        } catch (IOException e) {
            // Manejamos cualquier excepción de IO que pueda ocurrir
            e.printStackTrace();
        }
    }

    // Otros métodos relacionados con la manipulación de archivos podrían añadirse según sea necesario
}
