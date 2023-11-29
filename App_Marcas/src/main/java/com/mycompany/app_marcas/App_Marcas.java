/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.app_marcas;

import Extras.*;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 *
 * @author sotic
 */

public class App_Marcas {

    public static void main(String[] args) throws UnsupportedAudioFileException {
        JFrame frame = new JFrame("My Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Interfaz interfazPanel = new Interfaz();
        frame.getContentPane().add(interfazPanel);
        frame.setSize(550, 400);
        frame.setVisible(true);
    }
}
