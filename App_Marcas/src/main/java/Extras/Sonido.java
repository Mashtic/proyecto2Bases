/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Extras;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author sotic
 */
public class Sonido {
      
    public static void ReproducirSonido(String nombreSonido) {
    try {
        InputStream audioInputStream = Sonido.class.getResourceAsStream(nombreSonido);
        if (audioInputStream != null) {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioInputStream));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } else {
            System.out.println("No se pudo cargar el archivo de audio");
        }
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        System.out.println("Error al reproducir el sonido");
    }
}
}
