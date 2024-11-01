package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class PSolicitudes extends javax.swing.JPanel {
    JPanel fondo = new JPanel();
    Controlador controlador = new Controlador();
    Usuarios usuarioActual = null;
    int width;
    int height;
    
    String imagenFuente = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\gestionasistencia\\imagenes\\";
    
    int anchoBotones;
    int anchoDivisor = 6;
    int altoBotones;
    int altoDivisor = 20;
    
    
    public PSolicitudes(Usuarios usuario) {
        initComponents();
        usuarioActual = usuario;
        width = getWidth();
        height = getHeight();
        anchoBotones = width / anchoDivisor;
        altoBotones = height / altoDivisor;
        crearPanel();
    }
    
    private void crearPanel () {
        fondo.setBackground(new Color(0,0,51));
        fondo.setLayout(null);
        setLayout(new BorderLayout());
        add(fondo, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
