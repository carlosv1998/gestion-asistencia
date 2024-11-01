package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.RegistroAsistencias;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.ui.CustomButton;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PMisRegistros extends javax.swing.JPanel {
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
    
    // buscador
    JLabel buscadorIcono;
    JTextField buscadorField;
    CustomButton buscadorBoton;
    
    // filtros
    JLabel filtroFechaInicio;
    JDateChooser filtroFechaInicioValue;
    
    JLabel filtroFechaTermino;
    JDateChooser filtroFechaTerminoValue;
    
    // tabla
    JScrollPane panelScroll;
    DefaultTableModel modeloTabla;
    JTable tablaRegistros;
    
    public PMisRegistros(Usuarios usuario) {
        initComponents();
        usuarioActual = usuario;
        width = getWidth();
        height = getHeight();
        anchoBotones = width / anchoDivisor;
        altoBotones = height / altoDivisor;
        crearPanel();
        crearBuscador();
        crearFiltros();
        crearTabla();
        ajustarVentana();
    }
    
    private void crearPanel () {
        fondo.setBackground(new Color(0,0,51));
        fondo.setLayout(null);
        setLayout(new BorderLayout());
        add(fondo, BorderLayout.CENTER);
    }
    
    private void crearBuscador (){
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        
        buscadorIcono = new JLabel();
        fondo.add(buscadorIcono);
        buscadorField = new JTextField();
        fondo.add(buscadorField);
        
        buscadorBoton = new CustomButton("Buscar", fontSize);
        fondo.add(buscadorBoton);
        
        
        
        // icono
        buscadorIcono.setIcon(new javax.swing.ImageIcon(imagenFuente + "iconoBuscar180.png"));
        buscadorIcono.setBounds((int) Math.round(width * 0.01), (int) Math.round(height * 0.01), anchoBotones / 3, altoBotones);
        buscadorIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        // buscador
        buscadorField.setBounds(buscadorIcono.getWidth() + buscadorIcono.getX() + 2, buscadorIcono.getY(), anchoBotones * 2, altoBotones);
        // boton
        buscadorBoton.setBackground(new Color(51,0,153));
        buscadorBoton.setBounds(buscadorField.getWidth() + buscadorField.getX() + 4, buscadorIcono.getY(), anchoBotones, altoBotones);

        
        
        buscadorBoton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                Date fechaI = filtroFechaInicioValue.getDate();
                Date fechaT = filtroFechaTerminoValue.getDate();
                
                if (fechaI == null || fechaT == null) return;
                
                LocalDate fechaInicio = fechaI.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaTermino = fechaT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                
                JTableHeader header = tablaRegistros.getTableHeader();
                header.setBackground(new Color(0,0,51));
                header.setForeground(Color.white);
                header.setFont(new Font("Segoe UI Symbol", 1, 14));
                header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
                tablaRegistros.setRowHeight(30);
                
                List<RegistroAsistencias> listaRegistros = controlador.obtenerRegistroAsistenciasPorIdYRango(usuarioActual.getId(), fechaInicio, fechaTermino);
                
                if (listaRegistros == null){
                    System.out.println("No se encontraron datos");
                    return;
                }
                
                modeloTabla = (DefaultTableModel) tablaRegistros.getModel();
        
                // Limpiar el modelo de la tabla
                modeloTabla.setRowCount(0); // Limpiar las filas
                modeloTabla.setColumnCount(0); // Limpiar las columnas

                // Añadir las columnas
                modeloTabla.addColumn("Nombre");
                modeloTabla.addColumn("Hora entrada");
                modeloTabla.addColumn("Hora salida");
                modeloTabla.addColumn("Entrada especial");
                modeloTabla.addColumn("Salida especial");
                modeloTabla.addColumn("Fecha");
                
                tablaRegistros.setRowHeight(35);
                
                
                

                // Llenar la tabla con los datos
                for (RegistroAsistencias registro : listaRegistros) {
                    Object[] fila = new Object[] {
                        registro.getUsuario().getNombre(),
                        registro.getHoraEntrada(),
                        registro.getHoraSalida(),
                        registro.getEntradaEspecial(),
                        registro.getSalidaEspecial(),
                        registro.getFecha()
                    };
                    modeloTabla.addRow(fila);
                }
                
                tablaRegistros.setModel(modeloTabla);
            }
        });
    }
    
    private void crearTabla (){
        tablaRegistros = new JTable();
        panelScroll = new JScrollPane();
        panelScroll.setViewportView(tablaRegistros);
        fondo.add(panelScroll);
        
        panelScroll.setBounds((int) Math.round(width * 0.01), buscadorField.getY() + buscadorField.getHeight() + 10, (int) Math.round(width - ((width * 0.01) * 4)), (int) Math.round(height - (buscadorField.getY() + buscadorField.getHeight() + 50)));
        tablaRegistros.setBounds(0, 0, width, height);
    }
    
    private void crearFiltros (){
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        Date fechaActual = new Date();
        filtroFechaInicio = new JLabel();
        fondo.add(filtroFechaInicio);
        filtroFechaInicioValue = new JDateChooser();
        fondo.add(filtroFechaInicioValue);
        filtroFechaTermino = new JLabel();
        fondo.add(filtroFechaTermino);
        filtroFechaTerminoValue = new JDateChooser();
        fondo.add(filtroFechaTerminoValue);
        
        filtroFechaInicio.setText("Desde:");
        filtroFechaInicio.setForeground(Color.white);
        filtroFechaInicio.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        filtroFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        filtroFechaInicio.setBounds(buscadorBoton.getX() + buscadorBoton.getWidth() + 10, buscadorBoton.getY(), anchoBotones / 2, altoBotones);
        
        filtroFechaInicioValue.setMaxSelectableDate(fechaActual);
        filtroFechaInicioValue.setDate(fechaActual);
        filtroFechaInicioValue.setBounds(filtroFechaInicio.getX() + filtroFechaInicio.getWidth() + 10, filtroFechaInicio.getY() + (filtroFechaInicio.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
    
        filtroFechaTermino.setText("Hasta:");
        filtroFechaTermino.setForeground(Color.white);
        filtroFechaTermino.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        filtroFechaTermino.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        filtroFechaTermino.setBounds(filtroFechaInicioValue.getX() + filtroFechaInicioValue.getWidth() + 10, filtroFechaInicio.getY(), anchoBotones / 2, altoBotones);
        
        filtroFechaTerminoValue.setMaxSelectableDate(fechaActual);
        filtroFechaTerminoValue.setDate(fechaActual);
        filtroFechaTerminoValue.setBounds(filtroFechaTermino.getX() + filtroFechaTermino.getWidth() + 10, filtroFechaTermino.getY() + (filtroFechaTermino.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
    }
    
    private void ajustarVentana (){
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
               width = getWidth();
               height = getHeight();
               int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
               
               anchoBotones = width / anchoDivisor;
               altoBotones = height / altoDivisor;
               
               // buscador
               buscadorIcono.setBounds((int) Math.round(width * 0.01), (int) Math.round(height * 0.01), anchoBotones / 3, altoBotones);
               buscadorField.setBounds(buscadorIcono.getWidth() + buscadorIcono.getX() + 2, buscadorIcono.getY(), anchoBotones * 2, altoBotones);
               buscadorBoton.setBounds(buscadorField.getWidth() + buscadorField.getX() + 4, buscadorIcono.getY(), anchoBotones, altoBotones);
               buscadorBoton.ajustarTexto(fontSize);
               
               // filtros
               filtroFechaInicio.setBounds(buscadorBoton.getX() + buscadorBoton.getWidth() + 10, buscadorBoton.getY(), anchoBotones / 2, altoBotones);
               filtroFechaInicio.setFont(new Font("Segoe UI Symbol", 1, fontSize));
               filtroFechaInicioValue.setBounds(filtroFechaInicio.getX() + filtroFechaInicio.getWidth() + 10, filtroFechaInicio.getY() + (filtroFechaInicio.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
               filtroFechaTermino.setBounds(filtroFechaInicioValue.getX() + filtroFechaInicioValue.getWidth() + 10, filtroFechaInicio.getY(), anchoBotones / 2, altoBotones);
               filtroFechaTermino.setFont(new Font("Segoe UI Symbol", 1, fontSize));
               filtroFechaTerminoValue.setBounds(filtroFechaTermino.getX() + filtroFechaTermino.getWidth() + 10, filtroFechaTermino.getY() + (filtroFechaTermino.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
               
               //tabla
               panelScroll.setBounds(10, buscadorField.getY() + buscadorField.getHeight() + 10, width - 35, (int) Math.round(height - (buscadorField.getY() + buscadorField.getHeight() + 60)));
               tablaRegistros.setBounds(0, 0, width, height);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 905, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
