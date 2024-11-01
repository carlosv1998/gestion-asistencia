/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author carlos
 */
public class CustomButton extends JPanel{
    
    Color btnColorDefecto = new Color(102,51,255);
    Color btnColorHover = new Color(102,102,255);
    final private JLabel label;
    int fontSize;
    
    
    public CustomButton(String text, int size) {
        label = new JLabel(text, SwingConstants.CENTER);
        fontSize = size;
        configurarEstilo();
    }
    
    private void configurarEstilo(){
        setBackground(btnColorDefecto);
        setLayout(new BorderLayout());
        label.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setBackground(btnColorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setBackground(btnColorDefecto);
            }
        });
    }
    
    public void ajustarTexto(int size){
        label.setFont(new Font("Segoe UI Symbol", 1, size));
    }
}
