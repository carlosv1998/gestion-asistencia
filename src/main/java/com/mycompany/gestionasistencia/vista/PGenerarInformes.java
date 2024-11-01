package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.RegistroAsistencias;
import com.mycompany.gestionasistencia.ui.CustomButton;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.ss.usermodel.BorderStyle;

//exel
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

public class PGenerarInformes extends javax.swing.JPanel {
    private JPanel fondo;
    Controlador controlador = new Controlador();
    String rutaSeleccionada = "";
    
    int width;
    int height;
    
    int anchoBotones;
    int anchoDivisor = 6;
    int altoBotones;
    int altoDivisor = 20;
    
    String imagenFuente = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\gestionasistencia\\imagenes\\";
    
    Color btnColorDefecto = new Color(102,51,255);
    Color btnColorHover = new Color(102,102,255);
    
    // buscador
    JLabel buscadorIcono;
    JTextField buscadorField;
    CustomButton buscadorBoton;
    
    // filtros
    JLabel filtroFechaInicio;
    JDateChooser filtroFechaInicioValue;
    
    JLabel filtroFechaTermino;
    JDateChooser filtroFechaTerminoValue;
    
    JLabel lblTipoInforme;
    JComboBox<String> comboTipoInforme;
    
    // tabla
    JScrollPane panelScroll;
    DefaultTableModel modeloTabla;
    JTable tablaRegistros;
    
    // generar reportes
    CustomButton btnSeleccionarRuta;
    JLabel txtRuta;
    CustomButton btnGenerarInforme;
    
    public PGenerarInformes() {
        initComponents();
        width = getWidth();
        height = getHeight();
        anchoBotones = width / anchoDivisor;
        altoBotones = height / altoDivisor;
        crearPanel();
        crearBuscador();
        crearFiltros();
        crearTabla();
        generarBotonesReportes();
        ajustarVentana();
    }
    
    private void crearPanel (){
        fondo = new JPanel();
        fondo.setBackground(new Color(0,0,51));
        fondo.setLayout(null);        //JFrame
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
        buscadorField.setBounds(buscadorIcono.getWidth() + buscadorIcono.getX() + 2, buscadorIcono.getY(), anchoBotones, altoBotones);
        // boton
        buscadorBoton.setBounds(buscadorField.getWidth() + buscadorField.getX() + 4, buscadorIcono.getY(), anchoBotones, altoBotones);
        
        
        buscadorBoton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                String buscador = buscadorField.getText();
                Date fechaI = filtroFechaInicioValue.getDate();
                Date fechaT = filtroFechaTerminoValue.getDate();
                String tipoInforme = comboTipoInforme.getSelectedItem().toString();
                
                if (fechaI == null || fechaT == null) return;
                
                LocalDate fechaInicio = fechaI.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaTermino = fechaT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                List<RegistroAsistencias> listaRegistros = controlador.obtenerRegistroAsistenciasPorRango(fechaInicio, fechaTermino);
                
                if (listaRegistros == null){
                    return;
                }
                
                JTableHeader header = tablaRegistros.getTableHeader();
                header.setBackground(new Color(0,0,51));
                header.setForeground(Color.white);
                header.setFont(new Font("Segoe UI Symbol", 1, 14));
                header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
                tablaRegistros.setRowHeight(30);
                if (tipoInforme.equals("Informe general")){
                    modeloTabla = new DefaultTableModel() {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            if (columnIndex == 6 || columnIndex == 7 || columnIndex == 8 || columnIndex == 9 || columnIndex == 10) {
                                return Boolean.class; 
                            }
                            return super.getColumnClass(columnIndex);
                        }
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column == 6 || column == 7 || column == 8 || column == 9 || column == 10) {
                                return false;
                            }
                            return super.isCellEditable(row, column);
                        }
                    };
                    
                    modeloTabla.setRowCount(0);
                    modeloTabla.setColumnCount(0);
                    
                    modeloTabla.addColumn("Nombre");
                    modeloTabla.addColumn("Rut");
                    modeloTabla.addColumn("Jornada");
                    modeloTabla.addColumn("Hora de entrada");
                    modeloTabla.addColumn("Hora de salida");
                    modeloTabla.addColumn("Fecha");
                    modeloTabla.addColumn("Asistio");
                    modeloTabla.addColumn("Inasistencia");
                    modeloTabla.addColumn("Atraso");
                    modeloTabla.addColumn("Entrada especial");
                    modeloTabla.addColumn("Salida especial");

                    
                    for (RegistroAsistencias registro : listaRegistros){
                        Time horaEntradaContrato = registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada();
                        Time horaEntradaRegistrada = registro.getHoraEntrada();
                        Time horaSalidaRegistrada = registro.getHoraSalida();
                        Time horaEntradaEspecial = registro.getEntradaEspecial();
                        Time horaSalidaEspecial = registro.getSalidaEspecial();
                        int tiempoDeAtrasoMinimo = 15;
                        Boolean atraso, asistio, inasistencia, entradaEspecial, salidaEspecial;
                        
                        LocalTime horaEntradaContratoLocal = horaEntradaContrato.toLocalTime();
                        LocalTime horaEntradaRegistradaLocal = horaEntradaRegistrada.toLocalTime();
                        
                        LocalTime horaConMargen = horaEntradaContratoLocal.plusMinutes(tiempoDeAtrasoMinimo);
                        
                        atraso = horaEntradaRegistradaLocal.isAfter(horaConMargen);
                        asistio = horaEntradaRegistrada != null && horaSalidaRegistrada != null;
                        inasistencia = horaEntradaRegistrada == null || horaSalidaRegistrada == null;
                        entradaEspecial = horaEntradaEspecial != null;
                        salidaEspecial = horaSalidaEspecial != null;
                        
                        modeloTabla.addRow(new Object[]{
                            registro.getUsuario().getNombre(),
                            registro.getUsuario().getRut(),
                            registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraSalida(),
                            registro.getHoraEntrada(),
                            registro.getHoraSalida(),
                            registro.getFecha(),
                            asistio,
                            inasistencia,
                            atraso,
                            entradaEspecial,
                            salidaEspecial,
                        });
                    }
                    
                    tablaRegistros.setModel(modeloTabla);
                }
                
                if (tipoInforme.equals("Informe de asistencias")){
                    modeloTabla = new DefaultTableModel() {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            if (columnIndex == 6) {
                                return Boolean.class; 
                            }
                            return super.getColumnClass(columnIndex);
                        }
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column == 6) {
                                return false;
                            }
                            return super.isCellEditable(row, column);
                        }
                    };
                    
                    modeloTabla.setRowCount(0);
                    modeloTabla.setColumnCount(0);
                    
                    // definir columnas
                    modeloTabla.addColumn("Nombre");
                    modeloTabla.addColumn("Rut");
                    modeloTabla.addColumn("Jornada");
                    modeloTabla.addColumn("Hora de entrada");
                    modeloTabla.addColumn("Hora de salida");
                    modeloTabla.addColumn("Fecha");
                    modeloTabla.addColumn("Asistio");
                    
                    
                    for (RegistroAsistencias registro : listaRegistros){
                        Time horaEntradaRegistrada = registro.getHoraEntrada();
                        Time horaSalidaRegistrada = registro.getHoraSalida();
                        
                        Boolean asistio;
                        asistio = horaEntradaRegistrada != null && horaSalidaRegistrada != null;
                        
                        if (!asistio) continue;
                        
                        modeloTabla.addRow(new Object[]{
                            registro.getUsuario().getNombre(),
                            registro.getUsuario().getRut(),
                            registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraSalida(),
                            registro.getHoraEntrada(),
                            registro.getHoraSalida(),
                            registro.getFecha(),
                            asistio
                        });
                    }
                    
                    tablaRegistros.setModel(modeloTabla);
                }
                
                if (tipoInforme.equals("Informe de inasistencias")){
                    modeloTabla = new DefaultTableModel() {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            if (columnIndex == 6) {
                                return Boolean.class; 
                            }
                            return super.getColumnClass(columnIndex);
                        }
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column == 6) {
                                return false;
                            }
                            return super.isCellEditable(row, column);
                        }
                    };
                    
                    modeloTabla.setRowCount(0);
                    modeloTabla.setColumnCount(0);
                    
                    // definir columnas
                    modeloTabla.addColumn("Nombre");
                    modeloTabla.addColumn("Rut");
                    modeloTabla.addColumn("Jornada");
                    modeloTabla.addColumn("Hora de entrada");
                    modeloTabla.addColumn("Hora de salida");
                    modeloTabla.addColumn("Fecha");
                    modeloTabla.addColumn("Inasistencia");
                    
                    
                    for (RegistroAsistencias registro : listaRegistros){
                        Time horaEntradaRegistrada = registro.getHoraEntrada();
                        Time horaSalidaRegistrada = registro.getHoraSalida();
                        
                        Boolean inasistencia;
                        inasistencia = horaEntradaRegistrada == null || horaSalidaRegistrada == null;
                        
                        if (!inasistencia) continue;
                        
                        modeloTabla.addRow(new Object[]{
                            registro.getUsuario().getNombre(),
                            registro.getUsuario().getRut(),
                            registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraSalida(),
                            registro.getHoraEntrada(),
                            registro.getHoraSalida(),
                            registro.getFecha(),
                            inasistencia
                        });
                    }
                    
                    tablaRegistros.setModel(modeloTabla);
                }
                
                if (tipoInforme.equals("Informe de atrasos")){
                    
                    modeloTabla = new DefaultTableModel() {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            if (columnIndex == 6) {
                                return Boolean.class; 
                            }
                            return super.getColumnClass(columnIndex);
                        }
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column == 6) {
                                return false;
                            }
                            return super.isCellEditable(row, column);
                        }
                    };
                    
                    modeloTabla.setRowCount(0);
                    modeloTabla.setColumnCount(0);
                    
                    // definir columnas
                    modeloTabla.addColumn("Nombre");
                    modeloTabla.addColumn("Rut");
                    modeloTabla.addColumn("Jornada");
                    modeloTabla.addColumn("Hora de entrada");
                    modeloTabla.addColumn("Hora de salida");
                    modeloTabla.addColumn("Fecha");
                    modeloTabla.addColumn("Atraso");
                    modeloTabla.addColumn("Tiempo de atraso");
                    
                    
                    for (RegistroAsistencias registro : listaRegistros){
                        Time horaEntradaContrato = registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada();
                        Time horaEntradaRegistrada = registro.getHoraEntrada();
                        int tiempoDeAtrasoMinimo = 5;
                        Boolean atraso;
                        
                        LocalTime horaEntradaContratoLocal = horaEntradaContrato.toLocalTime();
                        LocalTime horaEntradaRegistradaLocal = horaEntradaRegistrada.toLocalTime();
                        
                        LocalTime horaConMargen = horaEntradaContratoLocal.plusMinutes(tiempoDeAtrasoMinimo);
                        
                        atraso = horaEntradaRegistradaLocal.isAfter(horaConMargen);
                        
                        long millisEntradaContrato = horaEntradaContrato.getTime();
                        long millisEntradaRegistrada = horaEntradaRegistrada.getTime();
                        
                        long diferenciaMillis = Math.abs(millisEntradaContrato - millisEntradaRegistrada);
                        Duration duracion = Duration.ofMillis(diferenciaMillis);
                        
                        long horas = duracion.toHours();
                        long minutos = duracion.toMinutesPart();
                        long segundos = duracion.toSecondsPart();
                        
                        String tiempoAtraso = String.format("%02d:%02d:%02d", horas, minutos, segundos);
                        
                        
                        if (!atraso) continue;
                        
                        modeloTabla.addRow(new Object[]{
                            registro.getUsuario().getNombre(),
                            registro.getUsuario().getRut(),
                            registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraSalida(),
                            registro.getHoraEntrada(),
                            registro.getHoraSalida(),
                            registro.getFecha(),
                            atraso,
                            tiempoAtraso
                        });
                    }
                    
                    tablaRegistros.setModel(modeloTabla);
                }
                
                if (tipoInforme.equals("Informe entradas especiales")){
                    modeloTabla = new DefaultTableModel() {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            if (columnIndex == 6) {
                                return Boolean.class; 
                            }
                            return super.getColumnClass(columnIndex);
                        }
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column == 6) {
                                return false;
                            }
                            return super.isCellEditable(row, column);
                        }
                    };
                    
                    modeloTabla.setRowCount(0);
                    modeloTabla.setColumnCount(0);
                    
                    // definir columnas
                    modeloTabla.addColumn("Nombre");
                    modeloTabla.addColumn("Rut");
                    modeloTabla.addColumn("Jornada");
                    modeloTabla.addColumn("Hora de entrada");
                    modeloTabla.addColumn("Hora de salida");
                    modeloTabla.addColumn("Fecha");
                    modeloTabla.addColumn("Entrada especial");
                    
                    
                    for (RegistroAsistencias registro : listaRegistros){
                        Time horaEntradaEspecial = registro.getEntradaEspecial();
                        
                        Boolean entradaEspecial;
                        entradaEspecial = horaEntradaEspecial != null;
                        
                        if (!entradaEspecial) continue;
                        
                        modeloTabla.addRow(new Object[]{
                            registro.getUsuario().getNombre(),
                            registro.getUsuario().getRut(),
                            registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraSalida(),
                            registro.getHoraEntrada(),
                            registro.getHoraSalida(),
                            registro.getFecha(),
                            entradaEspecial
                        });
                    }
                    
                    tablaRegistros.setModel(modeloTabla);
                }
                
                if (tipoInforme.equals("Informe salidas especiales")){
                    modeloTabla = new DefaultTableModel() {
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            if (columnIndex == 6) {
                                return Boolean.class; 
                            }
                            return super.getColumnClass(columnIndex);
                        }
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if (column == 6) {
                                return false;
                            }
                            return super.isCellEditable(row, column);
                        }
                    };
                    
                    modeloTabla.setRowCount(0);
                    modeloTabla.setColumnCount(0);
                    
                    // definir columnas
                    modeloTabla.addColumn("Nombre");
                    modeloTabla.addColumn("Rut");
                    modeloTabla.addColumn("Jornada");
                    modeloTabla.addColumn("Hora de entrada");
                    modeloTabla.addColumn("Hora de salida");
                    modeloTabla.addColumn("Fecha");
                    modeloTabla.addColumn("Salida especial");
                    
                    
                    for (RegistroAsistencias registro : listaRegistros){
                        Time horaSalidaEspecial = registro.getSalidaEspecial();
                        
                        Boolean salidaEspecial;
                        salidaEspecial = horaSalidaEspecial != null;
                        
                        if (!salidaEspecial) continue;
                        
                        modeloTabla.addRow(new Object[]{
                            registro.getUsuario().getNombre(),
                            registro.getUsuario().getRut(),
                            registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + registro.getUsuario().getContrato().getJornadasDeTrabajo().getHoraSalida(),
                            registro.getHoraEntrada(),
                            registro.getHoraSalida(),
                            registro.getFecha(),
                            salidaEspecial
                        });
                    }
                    
                    tablaRegistros.setModel(modeloTabla);
                }
            }
        });
    }
    
    private void crearTabla (){
        tablaRegistros = new JTable();
        panelScroll = new JScrollPane();
        panelScroll.setViewportView(tablaRegistros);
        fondo.add(panelScroll);
        
        panelScroll.setBounds((int) Math.round(width * 0.01), buscadorField.getY() + buscadorField.getHeight() + 10, (int) Math.round(width - ((width * 0.01) * 4)), (int) Math.round(height - height * 0.3));
        tablaRegistros.setBounds(0, 0, width, height);
    }
    
    private void crearFiltros (){
        int margin = (int) Math.floor(width * 0.05);
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
        filtroFechaInicio.setBounds(buscadorBoton.getX() + buscadorBoton.getWidth() + margin / 2, buscadorBoton.getY(), anchoBotones / 4, altoBotones);
        
        filtroFechaInicioValue.setMaxSelectableDate(fechaActual);
        filtroFechaInicioValue.setDate(fechaActual);
        filtroFechaInicioValue.setBounds(filtroFechaInicio.getX() + filtroFechaInicio.getWidth() + 5, filtroFechaInicio.getY() + (filtroFechaInicio.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
    
        filtroFechaTermino.setText("Hasta:");
        filtroFechaTermino.setForeground(Color.white);
        filtroFechaTermino.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        filtroFechaTermino.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        filtroFechaTermino.setBounds(filtroFechaInicioValue.getX() + filtroFechaInicioValue.getWidth() + margin / 2, filtroFechaInicio.getY(), anchoBotones / 4, altoBotones);
        
        filtroFechaTerminoValue.setMaxSelectableDate(fechaActual);
        filtroFechaTerminoValue.setDate(fechaActual);
        filtroFechaTerminoValue.setBounds(filtroFechaTermino.getX() + filtroFechaTermino.getWidth() + 5, filtroFechaTermino.getY() + (filtroFechaTermino.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
        
        // tipo informe
        lblTipoInforme = new JLabel();
        fondo.add(lblTipoInforme);
        String[] tiposInforme = { "Informe general", "Informe de asistencias", "Informe de inasistencias", "Informe de atrasos", "Informe entradas especiales", "Informe salidas especiales" };
        comboTipoInforme = new JComboBox<>(tiposInforme);
        fondo.add(comboTipoInforme);
        
        lblTipoInforme.setText("Tipo:");
        lblTipoInforme.setForeground(Color.white);
        lblTipoInforme.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoInforme.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        lblTipoInforme.setBounds(filtroFechaTerminoValue.getX() + filtroFechaTerminoValue.getWidth() + margin / 2, filtroFechaTermino.getY(), anchoBotones / 4, altoBotones);
        
        comboTipoInforme.setBackground(new Color(0,0,51));
        comboTipoInforme.setForeground(Color.white);
        comboTipoInforme.setBounds(lblTipoInforme.getX() + lblTipoInforme.getWidth() + 5, filtroFechaTermino.getY(), anchoBotones, altoBotones);
    }
    
    private void generarBotonesReportes (){
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        btnSeleccionarRuta = new CustomButton("Seleccionar ruta", fontSize);
        fondo.add(btnSeleccionarRuta);
        btnSeleccionarRuta.setBounds((width / 2) - anchoBotones - 10, panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);
        
        btnGenerarInforme = new CustomButton("Generar informe", fontSize);
        fondo.add(btnGenerarInforme);
        btnGenerarInforme.setBounds((width / 2) + 10, panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);

        //texto de la ruta
        txtRuta = new JLabel();
        fondo.add(txtRuta);
        
        txtRuta.setForeground(Color.white);
        txtRuta.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 0.6)));
        txtRuta.setBounds(btnSeleccionarRuta.getX(), btnSeleccionarRuta.getY() + btnSeleccionarRuta.getHeight(), anchoBotones * 2, altoBotones);
        
        btnSeleccionarRuta.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JFileChooser selectorCarpeta = new JFileChooser();
                selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                
                int option = selectorCarpeta.showOpenDialog(null);
                
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = selectorCarpeta.getSelectedFile();
                    String folderPath = selectedFolder.getAbsolutePath();
                    rutaSeleccionada = folderPath;
                    txtRuta.setText(rutaSeleccionada);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSeleccionarRuta.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnSeleccionarRuta.setBackground(btnColorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSeleccionarRuta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnSeleccionarRuta.setBackground(btnColorDefecto);
            }
        });
        
        btnGenerarInforme.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if (rutaSeleccionada.equals("")){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar la ruta", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (modeloTabla == null || modeloTabla.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay datos en la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    try (Workbook libro = new XSSFWorkbook()) {
                        String tipoInforme = comboTipoInforme.getSelectedItem().toString();
                        
                        Sheet hoja = (Sheet) libro.createSheet("Hoja 1");
                        org.apache.poi.ss.usermodel.Row filaTablaColumnas = hoja.createRow(3);
                        
                        // estilos
                        CellStyle estiloEncabezado = libro.createCellStyle();
                        org.apache.poi.ss.usermodel.Font fuenteEncabezado = libro.createFont();
                        
                        fuenteEncabezado.setBold(true); // fuente
                        fuenteEncabezado.setColor(IndexedColors.WHITE.getIndex());
                        
                        estiloEncabezado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                        estiloEncabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        estiloEncabezado.setFont(fuenteEncabezado);
                        
                        // bordes
                        CellStyle estiloBordes = libro.createCellStyle();
                        estiloBordes.setBorderTop(BorderStyle.THIN);
                        estiloBordes.setBorderBottom(BorderStyle.THIN);
                        estiloBordes.setBorderLeft(BorderStyle.THIN);
                        estiloBordes.setBorderRight(BorderStyle.THIN);
                        
                        estiloEncabezado.setBorderTop(BorderStyle.THIN);
                        estiloEncabezado.setBorderBottom(BorderStyle.THIN);
                        estiloEncabezado.setBorderLeft(BorderStyle.THIN);
                        estiloEncabezado.setBorderRight(BorderStyle.THIN);
                        
                        org.apache.poi.ss.usermodel.Row filaTituloTabla = hoja.createRow(1);
                        Cell celdaTituloTabla = filaTituloTabla.createCell(1);
                        celdaTituloTabla.setCellValue(tipoInforme);
                        celdaTituloTabla.setCellStyle(estiloEncabezado);
                        
                        
                        
                        for (int col = 1; col <= modeloTabla.getColumnCount(); col++){
                            String columnName = modeloTabla.getColumnName(col - 1);
                            Cell celdaColumna = filaTablaColumnas.createCell(col);
                            celdaColumna.setCellValue(columnName);
                            celdaColumna.setCellStyle(estiloEncabezado);
                        }
                        
                        for (int row = 0; row < modeloTabla.getRowCount(); row++){
                            org.apache.poi.ss.usermodel.Row nuevaFila = hoja.createRow(row + 4);
                            for (int col = 0; col < modeloTabla.getColumnCount(); col++){
                                Object value = modeloTabla.getValueAt(row, col);
                                Cell celda = nuevaFila.createCell(col + 1);
                                
                                if (value instanceof Boolean boolValue){
                                    celda.setCellValue(boolValue ? "Si" : "No");
                                }else if (value == null){
                                    celda.setCellValue("");
                                }else {
                                    celda.setCellValue(value.toString());
                                }
                                celda.setCellStyle(estiloBordes);
                            }
                        }
                        
                        for (int col = 1; col <= modeloTabla.getColumnCount(); col++) {
                            hoja.autoSizeColumn(col);
                        }

                        
                        
                        String ubicacionArchivoSalida = rutaSeleccionada + "\\" + tipoInforme + ".xlsx";
                        FileOutputStream outputStream = new FileOutputStream(ubicacionArchivoSalida);
                        libro.write(outputStream);
                        JOptionPane.showMessageDialog(null, "Informe creado en " + rutaSeleccionada, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
              
                } catch (IOException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Error al crear el informe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGenerarInforme.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnGenerarInforme.setBackground(btnColorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnGenerarInforme.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnGenerarInforme.setBackground(btnColorDefecto);
            }
        });
    }
    
    private void ajustarVentana (){
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
               width = getWidth();
               height = getHeight();
               int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
               int margin = (int) Math.floor(width * 0.05);
               
               anchoBotones = width / anchoDivisor;
               altoBotones = height / altoDivisor;
               
               // buscador
               buscadorIcono.setBounds((int) Math.round(width * 0.01), (int) Math.round(height * 0.01), anchoBotones / 3, altoBotones);
               buscadorField.setBounds(buscadorIcono.getWidth() + buscadorIcono.getX() + 2, buscadorIcono.getY(), anchoBotones, altoBotones);
               buscadorBoton.setBounds(buscadorField.getWidth() + buscadorField.getX() + 4, buscadorIcono.getY(), anchoBotones, altoBotones);
               buscadorBoton.ajustarTexto(fontSize);
               
               // filtros
               filtroFechaInicio.setBounds(buscadorBoton.getX() + buscadorBoton.getWidth() + margin / 2, buscadorBoton.getY(), anchoBotones / 4, altoBotones);
               filtroFechaInicio.setFont(new Font("Segoe UI Symbol", 1, fontSize));
               filtroFechaInicioValue.setBounds(filtroFechaInicio.getX() + filtroFechaInicio.getWidth() + 5, filtroFechaInicio.getY() + (filtroFechaInicio.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
               
               filtroFechaTermino.setBounds(filtroFechaInicioValue.getX() + filtroFechaInicioValue.getWidth() + margin / 2, filtroFechaInicio.getY(), anchoBotones / 4, altoBotones);
               filtroFechaTermino.setFont(new Font("Segoe UI Symbol", 1, fontSize));
               filtroFechaTerminoValue.setBounds(filtroFechaTermino.getX() + filtroFechaTermino.getWidth() + 5, filtroFechaTermino.getY() + (filtroFechaTermino.getHeight() / 4), anchoBotones / 2, altoBotones / 2);
               
               // tipo informes
               lblTipoInforme.setFont(new Font("Segoe UI Symbol", 1, fontSize));
               lblTipoInforme.setBounds(filtroFechaTerminoValue.getX() + filtroFechaTerminoValue.getWidth() + margin / 2, filtroFechaTermino.getY(), anchoBotones / 4, altoBotones);
               comboTipoInforme.setBounds(lblTipoInforme.getX() + lblTipoInforme.getWidth() + 5, filtroFechaTermino.getY(), anchoBotones, altoBotones);
               
               //tabla
               panelScroll.setBounds(10, buscadorField.getY() + buscadorField.getHeight() + 10, width - 35, (int) Math.round(height - height * 0.3));
               tablaRegistros.setBounds(0, 0, width, height);
               
               // botones generar registros
               btnSeleccionarRuta.setBounds((width / 2) - (int) Math.round(anchoBotones * 1.5), panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);
               btnSeleccionarRuta.ajustarTexto(fontSize);
            
               btnGenerarInforme.setBounds((width / 2) + 10, panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);
               btnGenerarInforme.ajustarTexto(fontSize);
               
               //texto ruta
               txtRuta.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 0.6)));
               txtRuta.setBounds(btnSeleccionarRuta.getX(), btnSeleccionarRuta.getY() + btnSeleccionarRuta.getHeight(), anchoBotones * 3, altoBotones);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(418, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(295, 295, 295))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(293, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
