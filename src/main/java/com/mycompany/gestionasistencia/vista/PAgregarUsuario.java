package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.Cargos;
import com.mycompany.gestionasistencia.modelo.Contratos;
import com.mycompany.gestionasistencia.modelo.Departamentos;
import com.mycompany.gestionasistencia.modelo.DiasDeTrabajo;
import com.mycompany.gestionasistencia.modelo.Gerencias;
import com.mycompany.gestionasistencia.modelo.JornadaDias;
import com.mycompany.gestionasistencia.modelo.JornadasDeTrabajo;
import com.mycompany.gestionasistencia.modelo.Roles;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.ui.CustomButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class PAgregarUsuario extends javax.swing.JPanel {
    JPanel fondo = new JPanel();
    JTabbedPane panelTabs;
    
    Controlador controlador = new Controlador();
    List<Gerencias> listaGerencias = controlador.obtenerGerencias();
    List<Departamentos> listaDepartamentos = controlador.obtenerDepartamentos();
    List<Cargos> listaCargos = controlador.obtenerCargos();
    
    List<Object[]> listaDatosUsuario;
    List<Object[]> listaFieldsDatosUsuario;
    List<Object[]> listaDatosErrores;
    List<Object[]> listaCombos;
    List<Object[]> listaBotonesDias;
    
    Color colorFondo = new Color(0,0,51);
    
    JPanel panelDatosUsuario;
    JPanel panelJornadaUsuario;
    
    int width;
    int height;
    
    int anchoBotones;
    int anchoDivisor = 6;
    int altoBotones;
    int altoDivisor = 20;
    
    
    // datos usuario
    JLabel lblAgregarUsuarios;
    JLabel lblRut;
    JLabel lblNombre;
    JLabel lblSueldo;
    JLabel lblTelefono;
    JLabel lblCorreo;
    JLabel lblContra;
    
    JTextField fieldRut;
    JTextField fieldNombre;
    JTextField fieldSueldo;
    JTextField fieldTelefono;
    JTextField fieldCorreo;
    JTextField fieldContra;
    
    // datos usuarios errores
    JLabel errorRut;
    JLabel errorNombre;
    JLabel errorSueldo;
    JLabel errorTelefono;
    JLabel errorCorreo;
    JLabel errorContra;
    
    
    // datos usuario seleccionables
    JLabel lblRol;
    JLabel lblGerencia;
    JLabel lblDepartamento;
    JLabel lblCargo;
    
    // combos
    JComboBox comboRoles;
    JComboBox comboGerencias;
    JComboBox comboDepartamentos;
    JComboBox comboCargos;
    
    // datos jornada
    JLabel lblJornadaUsuario;
    JLabel lblHoraEntrada;
    JLabel lblHoraSalida;
    
    // jornada spiners
    JSpinner pEntradaHoras;
    JLabel separador1;
    JSpinner pEntradaMinutos;
    
    JLabel errorJornada;
    
    JSpinner pSalidaHoras;
    JLabel separador2;
    JSpinner pSalidaMinutos;
    
    // jornada dias
    JRadioButton btnLunes;
    JRadioButton btnMartes;
    JRadioButton btnMiercoles;
    JRadioButton btnJueves;
    JRadioButton btnViernes;
    JRadioButton btnSabado;
    
    JLabel errorDias;
    
    // botones
    // datos usuario
    CustomButton botonDatosUsuario;
    
    // jornada usuario
    CustomButton botonJornadaSiguiente;
    
    CustomButton botonJornadaAnterior;
    
    
    public PAgregarUsuario() {
        initComponents();
        width = getWidth();
        height = getHeight();
        anchoBotones = width / anchoDivisor;
        altoBotones = height / altoDivisor;
        crearPaneles();
        crearInputsDatosUsuario();
        crearCombos();
        listarDatos();
        agregarEventos();
        crearDatosJornada();
        crearBotones();
        ajustarVentana();
    }
    
    private void crearPaneles() {
        fondo.setBackground(colorFondo);
        fondo.setLayout(null);
        setLayout(new BorderLayout());
        add(fondo, BorderLayout.CENTER);
        
        panelTabs = new JTabbedPane();
        fondo.add(panelTabs);
        
        panelTabs.setBounds(0,0,width - 15,height -40);
        
        panelDatosUsuario = new JPanel();
        panelTabs.addTab("Datos usuario", panelDatosUsuario);
        panelDatosUsuario.setLayout(null);
        panelDatosUsuario.setBackground(colorFondo);
        panelDatosUsuario.setBounds(0,0,panelTabs.getWidth(), panelTabs.getHeight());
        
        panelJornadaUsuario = new JPanel();
        panelTabs.addTab("Jornada usuario", panelJornadaUsuario);
        panelJornadaUsuario.setLayout(null);
        panelJornadaUsuario.setBackground(colorFondo);
        panelJornadaUsuario.setBounds(0,0,panelTabs.getWidth(), panelTabs.getHeight());
    }
    
    private void crearInputsDatosUsuario (){
        int panelTabsWidth = panelTabs.getWidth();
        int panelTabsHeight = panelTabs.getHeight();
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        
        lblAgregarUsuarios = new JLabel();
        panelDatosUsuario.add(lblAgregarUsuarios);
        
        lblAgregarUsuarios.setText("Ingresar datos del usuario");
        lblAgregarUsuarios.setForeground(Color.white);
        lblAgregarUsuarios.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2)));
        lblAgregarUsuarios.setBounds((int) Math.round(panelTabsWidth * 0.01),(int) Math.round(panelTabsHeight * 0.01), anchoBotones * 2, altoBotones);
        
        // datos del usuario rut, nombre, etc.
        lblRut = new JLabel();
        lblNombre = new JLabel();
        lblSueldo = new JLabel();
        lblTelefono = new JLabel();
        lblCorreo = new JLabel();
        lblContra = new JLabel();
        
        // datos del usuario seleccionables
        lblRol = new JLabel();
        lblGerencia = new JLabel();
        lblDepartamento = new JLabel();
        lblCargo = new JLabel();
        
        int margin = (int) Math.floor(panelTabs.getHeight() * 0.1);
        int posXLabels = lblAgregarUsuarios.getX();
        int posYLabels = lblAgregarUsuarios.getY() + lblAgregarUsuarios.getHeight() * 2;
        
        
        // labels usuario
        listaDatosUsuario = new ArrayList<>();
        listaDatosUsuario.add(new Object[]{lblRut,"Rut:", 0, "izquierda"});
        listaDatosUsuario.add(new Object[]{lblNombre,"Nombre:", 1, "izquierda"});
        listaDatosUsuario.add(new Object[]{lblSueldo,"Sueldo:", 2, "izquierda"});
        listaDatosUsuario.add(new Object[]{lblTelefono,"Teléfono:", 3, "izquierda"});
        listaDatosUsuario.add(new Object[]{lblCorreo,"Correo:", 4, "izquierda"});
        listaDatosUsuario.add(new Object[]{lblContra,"Contraseña:", 5, "izquierda"});
        
        listaDatosUsuario.add(new Object[]{lblRol,"Rol:", 0, "derecha"});
        listaDatosUsuario.add(new Object[]{lblGerencia,"Gerencia:", 1, "derecha"});
        listaDatosUsuario.add(new Object[]{lblDepartamento,"Departamento:", 2, "derecha"});
        listaDatosUsuario.add(new Object[]{lblCargo,"Cargo:", 3, "derecha"});
        
        for (Object[] objeto: listaDatosUsuario){
            JLabel labelCasteada = (JLabel) objeto[0];
            String stringCasteada = (String) objeto[1];
            int posicion = (int) objeto[2];
            String lado = (String) objeto[3];
            
            panelDatosUsuario.add(labelCasteada);
            labelCasteada.setText(stringCasteada);
            labelCasteada.setForeground(Color.white);
            labelCasteada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
            labelCasteada.setHorizontalAlignment(SwingConstants.RIGHT);
            
            int xPosFinal = lado.equals("izquierda") ? posXLabels : posXLabels + (int) Math.round(anchoBotones * 2.5);
            
            labelCasteada.setBounds(xPosFinal, posYLabels + (margin * posicion), anchoBotones, altoBotones);
        }
        
        // fields usuario
        
        fieldRut = new JTextField();
        fieldNombre = new JTextField();
        fieldSueldo = new JTextField();
        fieldTelefono = new JTextField();
        fieldCorreo = new JTextField();
        fieldContra = new JTextField();
        
        listaFieldsDatosUsuario = new ArrayList<>();
        int posXFields = lblRut.getX() + lblRut.getWidth() + margin;
        int posYFields = lblRut.getY();
        
        listaFieldsDatosUsuario.add(new Object[]{fieldRut, 0});
        listaFieldsDatosUsuario.add(new Object[]{fieldNombre, 1});
        listaFieldsDatosUsuario.add(new Object[]{fieldSueldo, 2});
        listaFieldsDatosUsuario.add(new Object[]{fieldTelefono, 3});
        listaFieldsDatosUsuario.add(new Object[]{fieldCorreo, 4});
        listaFieldsDatosUsuario.add(new Object[]{fieldContra, 5});
        
        for (Object[] objeto : listaFieldsDatosUsuario){
            JTextField fieldCasteada = (JTextField) objeto[0];
            int posicion = (int) objeto[1];
            
            panelDatosUsuario.add(fieldCasteada);
            fieldCasteada.setBounds(posXFields, posYFields + (margin * posicion), anchoBotones, altoBotones);
        }
        
        errorRut = new JLabel();
        errorNombre = new JLabel();
        errorSueldo = new JLabel();
        errorTelefono = new JLabel();
        errorCorreo = new JLabel();
        errorContra = new JLabel();
        
        int posXErrores = fieldRut.getX();
        int posYErrores = fieldRut.getY() + fieldRut.getHeight();
        
        listaDatosErrores = new ArrayList<>();
        
        listaDatosErrores.add(new Object[]{errorRut, 0});
        listaDatosErrores.add(new Object[]{errorNombre, 1});
        listaDatosErrores.add(new Object[]{errorSueldo, 2});
        listaDatosErrores.add(new Object[]{errorTelefono, 3});
        listaDatosErrores.add(new Object[]{errorCorreo, 4});
        listaDatosErrores.add(new Object[]{errorContra, 5});
        
        for (Object[] objeto : listaDatosErrores){
            JLabel labelCasteada = (JLabel) objeto[0];
            int posicion = (int) objeto[1];
            panelDatosUsuario.add(labelCasteada);
            labelCasteada.setForeground(Color.red);
            labelCasteada.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize / 1.5)));
            labelCasteada.setBounds(posXErrores, posYErrores + (margin * posicion), anchoBotones * 2, altoBotones);
        }
    }
    
    private void crearCombos () {
        comboRoles = new JComboBox();
        comboGerencias = new JComboBox();
        comboDepartamentos = new JComboBox();
        comboCargos = new JComboBox();
        
        listaCombos = new ArrayList<>();
        listaCombos.add(new Object[]{comboRoles, 0});
        listaCombos.add(new Object[]{comboGerencias, 1});
        listaCombos.add(new Object[]{comboDepartamentos, 2});
        listaCombos.add(new Object[]{comboCargos, 3});
        
        int margin = (int) Math.floor(panelTabs.getHeight() * 0.1);
        int posXCombos = lblRol.getX() + lblRol.getWidth() + margin;
        int posYCombos = lblRol.getY();
        
        for (Object[] objeto : listaCombos){
            JComboBox comboCasteado = (JComboBox) objeto[0];
            int posicion = (int) objeto[1];
            
            panelDatosUsuario.add(comboCasteado);
            comboCasteado.setBounds(posXCombos, posYCombos + (margin * posicion), anchoBotones, altoBotones);
        }
    }
    
    private void listarRoles() {
        comboRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Roles roles) {
                    setText(roles.getRol());
                }
                return this;
            }
        });
        
        List<Roles> listaRoles = controlador.obtenerRoles();
        DefaultComboBoxModel<Roles> comboBoxRoles = new DefaultComboBoxModel<>();
        for (Roles rol : listaRoles) {
            comboBoxRoles.addElement(rol);
        }
        comboRoles.setModel(comboBoxRoles);
    }
    
    private void listarCargos(List<Cargos> listaCar) {
        comboCargos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cargos cargos) {
                    setText(cargos.getCargo());
                }
                return this;
            }
        });
        DefaultComboBoxModel<Cargos> comboBoxCargos = new DefaultComboBoxModel<>();
        for (Cargos cargo : listaCar) {
            comboBoxCargos.addElement(cargo);
        }
        comboCargos.setModel(comboBoxCargos);
    }
    
    private void listarGerencias() {
        comboGerencias.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Gerencias gerencias) {
                    setText(gerencias.getGerencia());
                }
                return this;
            }
        });
        DefaultComboBoxModel<Gerencias> comboBoxGerencias = new DefaultComboBoxModel<>();
        for (Gerencias gerencia : listaGerencias) {
            comboBoxGerencias.addElement(gerencia);
        }
        comboGerencias.setModel(comboBoxGerencias);
    }
    
    private void listarDepartamentos(List<Departamentos> listaDep) {
        comboDepartamentos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Departamentos departamentos) {
                    setText(departamentos.getDepartamento());
                }
                return this;
            }
        });
        DefaultComboBoxModel<Departamentos> comboBoxDepartamentos = new DefaultComboBoxModel<>();
        for (Departamentos departamento : listaDep) {
            comboBoxDepartamentos.addElement(departamento);
        }
        comboDepartamentos.setModel(comboBoxDepartamentos);
    }
    
    private void listarDatos (){
        listarRoles();
        listarGerencias(); 
        
        Gerencias gerenciaSeleccionada = (Gerencias) comboGerencias.getSelectedItem();
        List<Departamentos> nuevaListaDepartamentos = new ArrayList<>();

        for (Departamentos departamento : listaDepartamentos){
            if (departamento.getGerencia().getId() == gerenciaSeleccionada.getId()){
                nuevaListaDepartamentos.add(departamento);
            }
        }

        Departamentos primerDepartamento = nuevaListaDepartamentos.getFirst();

        List<Cargos> nuevaListaCargos = new ArrayList<>();

        for (Cargos cargo: listaCargos){
            if (cargo.getDepartamento().getId() == primerDepartamento.getId()){
                nuevaListaCargos.add(cargo);
            }
        }
        
        listarDepartamentos(nuevaListaDepartamentos);
        listarCargos(nuevaListaCargos);
    }
    
    private void agregarEventos (){
        comboGerencias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gerencias gerenciaSeleccionada = (Gerencias) comboGerencias.getSelectedItem();
                List<Departamentos> nuevaListaDepartamentos = new ArrayList<>();
                
                for (Departamentos departamento : listaDepartamentos){
                    if (departamento.getGerencia().getId() == gerenciaSeleccionada.getId()){
                        nuevaListaDepartamentos.add(departamento);
                    }
                }
                
                Departamentos primerDepartamento = nuevaListaDepartamentos.getFirst();
                
                List<Cargos> nuevaListaCargos = new ArrayList<>();
                
                for (Cargos cargo: listaCargos){
                    if (cargo.getDepartamento().getId() == primerDepartamento.getId()){
                        nuevaListaCargos.add(cargo);
                    }
                }
                
                listarDepartamentos(nuevaListaDepartamentos);
                listarCargos(nuevaListaCargos);
            }
        });
        
        comboDepartamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Departamentos departamentoSeleccionado = (Departamentos) comboDepartamentos.getSelectedItem();
                List<Cargos> nuevaListaCargos = new ArrayList<>();
                
                for (Cargos cargo: listaCargos){
                    if (cargo.getDepartamento().getId() == departamentoSeleccionado.getId()){
                        nuevaListaCargos.add(cargo);
                    }
                }
                listarCargos(nuevaListaCargos);
            }
        });
    }
    
    // jornadas
    private void crearDatosJornada() {
        int panelTabsWidth = panelTabs.getWidth();
        int panelTabsHeight = panelTabs.getHeight();
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        int margin = (int) Math.floor(panelTabs.getHeight() * 0.1);
        
        lblJornadaUsuario = new JLabel();
        panelJornadaUsuario.add(lblJornadaUsuario);
        
        lblJornadaUsuario.setText("Ingresar jornada del usuario");
        lblJornadaUsuario.setForeground(Color.white);
        lblJornadaUsuario.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2)));
        lblJornadaUsuario.setBounds((int) Math.round(panelTabsWidth * 0.01),(int) Math.round(panelTabsHeight * 0.01), anchoBotones * 2, altoBotones);
        
        lblHoraEntrada = new JLabel();
        lblHoraSalida = new JLabel();
        
        panelJornadaUsuario.add(lblHoraEntrada);
        panelJornadaUsuario.add(lblHoraSalida);
        
        lblHoraEntrada.setText("Hora de entrada:");
        lblHoraEntrada.setForeground(Color.white);
        lblHoraEntrada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        lblHoraEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHoraEntrada.setBounds(margin, (panelTabsHeight / 2) - (altoBotones * 4), anchoBotones, altoBotones);
        
        lblHoraSalida.setText("Hora de salida:");
        lblHoraSalida.setForeground(Color.white);
        lblHoraSalida.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        lblHoraSalida.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHoraSalida.setBounds(lblHoraEntrada.getX(), lblHoraEntrada.getHeight() + lblHoraEntrada.getY() + margin, anchoBotones, altoBotones);
        
        SpinnerNumberModel modeloHorasEntrada = new SpinnerNumberModel(8, 8, 14, 1);
        SpinnerNumberModel modeloMinutosEntrada = new SpinnerNumberModel(0, 0, 59, 1);
        
        SpinnerNumberModel modeloHorasSalida = new SpinnerNumberModel(14, 14, 20, 1);
        SpinnerNumberModel modeloMinutosSalida = new SpinnerNumberModel(0, 0, 59, 1);
        
        pEntradaHoras = new JSpinner(modeloHorasEntrada);
        pEntradaMinutos = new JSpinner(modeloMinutosEntrada);
        separador1 = new JLabel();

        pSalidaHoras = new JSpinner(modeloHorasSalida);
        pSalidaMinutos = new JSpinner(modeloMinutosSalida);
        separador2 = new JLabel();
        
        panelJornadaUsuario.add(pEntradaHoras);
        panelJornadaUsuario.add(pEntradaMinutos);
        panelJornadaUsuario.add(separador1);
        panelJornadaUsuario.add(pSalidaHoras);
        panelJornadaUsuario.add(pSalidaMinutos);
        panelJornadaUsuario.add(separador2);
        
        separador1.setForeground(Color.white);
        separador1.setText(":");
        separador1.setHorizontalAlignment(SwingConstants.CENTER);
        separador2.setForeground(Color.white);
        separador2.setText(":");
        separador2.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        pEntradaHoras.setBounds(lblHoraEntrada.getX() + lblHoraEntrada.getWidth() + 10, lblHoraEntrada.getY(), anchoBotones / 2, altoBotones);
        separador1.setBounds(pEntradaHoras.getX() + pEntradaHoras.getWidth() + 2, pEntradaHoras.getY(), anchoBotones / 10, altoBotones);
        pEntradaMinutos.setBounds(separador1.getX() + separador1.getWidth() + 2, separador1.getY(), anchoBotones / 2, altoBotones);
        
        pSalidaHoras.setBounds(lblHoraSalida.getX() + lblHoraSalida.getWidth() + 10, lblHoraSalida.getY(), anchoBotones / 2, altoBotones);
        separador2.setBounds(pSalidaHoras.getX() + pSalidaHoras.getWidth() + 2, pSalidaHoras.getY(), anchoBotones / 10, altoBotones);
        pSalidaMinutos.setBounds(separador2.getX() + separador2.getWidth() + 2, separador2.getY(), anchoBotones / 2, altoBotones);
        
        errorJornada = new JLabel();
        panelJornadaUsuario.add(errorJornada);
        errorJornada.setForeground(Color.red);
        errorJornada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        errorJornada.setBounds(pSalidaHoras.getX(), pSalidaHoras.getY() + pSalidaHoras.getHeight() + 10, anchoBotones, altoBotones);
        
        btnLunes = new JRadioButton();
        btnMartes = new JRadioButton();
        btnMiercoles = new JRadioButton();
        btnJueves = new JRadioButton();
        btnViernes = new JRadioButton();
        btnSabado = new JRadioButton();
        
        listaBotonesDias = new ArrayList<>();
        listaBotonesDias.add(new Object[]{btnLunes, 0, "Lunes"});
        listaBotonesDias.add(new Object[]{btnMartes, 1, "Martes"});
        listaBotonesDias.add(new Object[]{btnMiercoles, 2, "Miércoles"});
        listaBotonesDias.add(new Object[]{btnJueves, 3, "Jueves"});
        listaBotonesDias.add(new Object[]{btnViernes, 4, "Viernes"});
        listaBotonesDias.add(new Object[]{btnSabado, 5, "Sábado"});
        
        int posXBotones = panelTabsWidth - (anchoBotones * 2);
        int posYBotones = (int) Math.round(panelTabsHeight * 0.2);
        
        for (Object[] objeto : listaBotonesDias){
            JRadioButton botonCasteado = (JRadioButton) objeto[0];
            int posicion = (int) objeto[1];
            String texto = (String) objeto[2];
            
            panelJornadaUsuario.add(botonCasteado);
            botonCasteado.setText(texto);
            botonCasteado.setBackground(colorFondo);
            botonCasteado.setForeground(Color.white);
            botonCasteado.setFont(new Font("Segoe UI Symbol", 1, fontSize));
            botonCasteado.setBounds(posXBotones, posYBotones + (posicion * margin), anchoBotones, altoBotones);
        }
        
        errorDias = new JLabel();
        panelJornadaUsuario.add(errorDias);
        errorDias.setForeground(Color.red);
        errorDias.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        errorDias.setBounds(btnSabado.getX(), btnSabado.getY() + btnSabado.getHeight(), anchoBotones, altoBotones);
    }
    
    private void crearBotones (){
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        // datos usuario
        botonDatosUsuario = new CustomButton("Siguiente", fontSize);
        panelDatosUsuario.add(botonDatosUsuario);
        botonDatosUsuario.setBounds(panelTabs.getWidth() - anchoBotones * 2, panelTabs.getHeight() - altoBotones * 3, anchoBotones, altoBotones);
        
        

        // jornadas
        botonJornadaSiguiente = new CustomButton("Crear usuario", fontSize);
        panelJornadaUsuario.add(botonJornadaSiguiente);
        botonJornadaSiguiente.setBounds(panelTabs.getWidth() - anchoBotones * 2, panelTabs.getHeight() - altoBotones * 3, anchoBotones, altoBotones);

        
        botonJornadaAnterior = new CustomButton("Anterior", fontSize);
        panelJornadaUsuario.add(botonJornadaAnterior);
        botonJornadaAnterior.setBounds(anchoBotones, panelTabs.getHeight() - altoBotones * 3, anchoBotones, altoBotones);
        
        botonDatosUsuario.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (verificarDatosNuevoUsuario()){
                    panelTabs.setSelectedIndex(1);
                }else {
                    JOptionPane.showMessageDialog(null, "Hay datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        botonJornadaSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (verificarJornadaNuevoUsuario()){
                    crearUsuario();
                }else {
                    JOptionPane.showMessageDialog(null, "Jornada inválida", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        
        botonJornadaAnterior.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                panelTabs.setSelectedIndex(0);
            }
        });
    }
    
    private void ajustarVentana() {
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                width = getWidth();
                height = getHeight();
                anchoBotones = width / anchoDivisor;
                altoBotones = height / altoDivisor;
                int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
                
                
                // paneles tabs
                panelTabs.setBounds(0,0,width - 15,height -40);
                int panelTabsWidth = panelTabs.getWidth();
                int panelTabsHeight = panelTabs.getHeight();
                
                // datos usuario
                lblAgregarUsuarios.setBounds((int) Math.round(panelTabsWidth * 0.01),(int) Math.round(panelTabsHeight * 0.01), anchoBotones * 2, altoBotones);
                lblAgregarUsuarios.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2)));
                
                int posX = lblAgregarUsuarios.getX();
                int posY = lblAgregarUsuarios.getY() + lblAgregarUsuarios.getHeight() * 2;
                int margin = (int) Math.floor(panelTabs.getHeight() * 0.1);
                
                for (Object[] objeto: listaDatosUsuario){
                    JLabel labelCasteada = (JLabel) objeto[0];
                    int position = (int) objeto[2];
                    String lado = (String) objeto[3];
                    labelCasteada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                    
                    int xPosFinal = lado.equals("izquierda") ? posX : posX + (int) Math.round(anchoBotones * 2.5);
                    
                    labelCasteada.setBounds(xPosFinal, posY + (margin * position), anchoBotones, altoBotones);
                }
                
                // fields datos usuario
                
                int posXFields = lblRut.getX() + lblRut.getWidth() + margin;
                int posYFields = lblRut.getY();
                
                for (Object[] objeto : listaFieldsDatosUsuario){
                    JTextField fieldCasteada = (JTextField) objeto[0];
                    int posicion = (int) objeto[1];

                    panelDatosUsuario.add(fieldCasteada);
                    fieldCasteada.setBounds(posXFields, posYFields + (margin * posicion), anchoBotones, altoBotones);
                }
                
                // errores datos usuario
                
                int posXErrores = fieldRut.getX();
                int posYErrores = fieldRut.getY() + fieldRut.getHeight();
                
                for (Object[] objeto : listaDatosErrores){
                    JLabel labelCasteada = (JLabel) objeto[0];
                    int posicion = (int) objeto[1];
                    labelCasteada.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize / 1.5)));
                    labelCasteada.setBounds(posXErrores, posYErrores + (margin * posicion), anchoBotones * 2, altoBotones);
                }
                
                // combos
                
                int posXCombos = lblRol.getX() + lblRol.getWidth() + margin;
                int posYCombos = lblRol.getY();

                for (Object[] objeto : listaCombos){
                    JComboBox comboCasteado = (JComboBox) objeto[0];
                    int posicion = (int) objeto[1];
                    
                    comboCasteado.setBounds(posXCombos, posYCombos + (margin * posicion), anchoBotones, altoBotones);
                }
                
                // jornadas usuario
                lblJornadaUsuario.setBounds((int) Math.round(panelTabsWidth * 0.01),(int) Math.round(panelTabsHeight * 0.01), anchoBotones * 2, altoBotones);
                lblJornadaUsuario.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2)));
                
                lblHoraEntrada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                lblHoraEntrada.setBounds(margin, (panelTabsHeight / 2) - (altoBotones * 4), anchoBotones, altoBotones);
                lblHoraSalida.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                lblHoraSalida.setBounds(lblHoraEntrada.getX(), lblHoraEntrada.getHeight() + lblHoraEntrada.getY() + margin, anchoBotones, altoBotones);
                
                // jornadas horas
                pEntradaHoras.setBounds(lblHoraEntrada.getX() + lblHoraEntrada.getWidth() + 10, lblHoraEntrada.getY(), anchoBotones / 2, altoBotones);
                separador1.setBounds(pEntradaHoras.getX() + pEntradaHoras.getWidth() + 2, pEntradaHoras.getY(), anchoBotones / 10, altoBotones);
                pEntradaMinutos.setBounds(separador1.getX() + separador1.getWidth() + 2, separador1.getY(), anchoBotones / 2, altoBotones);
                
                pSalidaHoras.setBounds(lblHoraSalida.getX() + lblHoraSalida.getWidth() + 10, lblHoraSalida.getY(), anchoBotones / 2, altoBotones);
                separador2.setBounds(pSalidaHoras.getX() + pSalidaHoras.getWidth() + 2, pSalidaHoras.getY(), anchoBotones / 10, altoBotones);
                pSalidaMinutos.setBounds(separador2.getX() + separador2.getWidth() + 2, separador2.getY(), anchoBotones / 2, altoBotones);
                
                errorJornada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                errorJornada.setBounds(pSalidaHoras.getX(), pSalidaHoras.getY() + pSalidaHoras.getHeight() + 10, anchoBotones, altoBotones);
                
                // jornadas dias
                int posXBotones = panelTabsWidth - (anchoBotones * 2);
                int posYBotones = (int) Math.round(panelTabsHeight * 0.1);

                for (Object[] objeto : listaBotonesDias){
                    JRadioButton botonCasteado = (JRadioButton) objeto[0];
                    int posicion = (int) objeto[1];
                    
                    botonCasteado.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                    botonCasteado.setBounds(posXBotones, posYBotones + (posicion * margin), anchoBotones, altoBotones);
                }
                
                errorDias.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                errorDias.setBounds(btnSabado.getX(), btnSabado.getY() + btnSabado.getHeight(), anchoBotones, altoBotones);
                
                // botones
                
                botonDatosUsuario.setBounds(panelTabsWidth - anchoBotones * 2, panelTabsHeight - altoBotones * 3, anchoBotones, altoBotones);
                botonDatosUsuario.ajustarTexto(fontSize);
                
                botonJornadaSiguiente.setBounds(panelTabsWidth - anchoBotones * 2, panelTabsHeight - altoBotones * 3, anchoBotones, altoBotones);
                botonJornadaSiguiente.ajustarTexto(fontSize);
                
                botonJornadaAnterior.setBounds(anchoBotones, panelTabsHeight - altoBotones * 3, anchoBotones, altoBotones);
                botonJornadaAnterior.ajustarTexto(fontSize);
            }
        });
    }
    
    public static boolean esDiferenciaMinimaSeisHoras(Time entrada, Time salida) {
        long millisEntrada = entrada.getTime();
        long millisSalida = salida.getTime();
        long diferenciaMillis = millisSalida - millisEntrada;
        long diferenciaHoras = TimeUnit.MILLISECONDS.toHours(diferenciaMillis);
        return diferenciaHoras >= 6;
    }
    
    private void crearUsuario(){
        if (verificarDatosNuevoUsuario() && verificarJornadaNuevoUsuario()){
            Usuarios nuevoUsuario = new Usuarios();
            
            String rut = fieldRut.getText();
            String nombre = fieldNombre.getText();
            String telefono = fieldTelefono.getText();
            String correo = fieldCorreo.getText();
            String sueldo = fieldSueldo.getText();
            String contra = fieldContra.getText();
            Roles rol = (Roles) comboRoles.getSelectedItem();
            Gerencias gerencia = (Gerencias) comboGerencias.getSelectedItem();
            Departamentos departamento = (Departamentos) comboDepartamentos.getSelectedItem();
            Cargos cargo = (Cargos) comboCargos.getSelectedItem();
            LocalDate fechaActual = LocalDate.now();
            
            // crear nuevo usuario
            
            nuevoUsuario.setRut(rut);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setSueldo(Integer.parseInt(sueldo));
            nuevoUsuario.setContra(contra);
            nuevoUsuario.setRol(rol);
            nuevoUsuario.setGerencia(gerencia);
            nuevoUsuario.setDepartamento(departamento);
            nuevoUsuario.setCargo(cargo);
            nuevoUsuario.setFechaCreacion(fechaActual);
            
            try {
                // crear usuario en la BD
                Usuarios usuarioCreado = controlador.crearUsuario(nuevoUsuario);
                
                // crear nueva jornada
                JornadasDeTrabajo nuevaJornada = new JornadasDeTrabajo();
                int horasEntrada = (Integer) pEntradaHoras.getValue();
                int minutosEntrada = (Integer) pEntradaMinutos.getValue();

                int horasSalida = (Integer) pSalidaHoras.getValue();
                int minutosSalida = (Integer) pSalidaMinutos.getValue();

                LocalTime entrada = LocalTime.of(horasEntrada, minutosEntrada);
                LocalTime salida = LocalTime.of(horasSalida, minutosSalida);

                Time timeEntrada = Time.valueOf(entrada);
                Time timeSalida = Time.valueOf(salida);
                
                nuevaJornada.setHoraEntrada(timeEntrada);
                nuevaJornada.setHoraSalida(timeSalida);
                
                // crear nueva jornada en la BD
                JornadasDeTrabajo jornadaCreada = controlador.crearJornada(nuevaJornada);
                
                // obtener dias de trabajo
                List<DiasDeTrabajo> listaDias = controlador.obtenerTodosLosDiasDeTrabajo();
                
                // asignar dias a la nueva jornada
                for (Object[] objeto : listaBotonesDias){
                    JRadioButton botonCasteado = (JRadioButton) objeto[0];
                    String dia = (String) objeto[2];
                    
                    for (DiasDeTrabajo diaDeTrabajo : listaDias){
                        if (diaDeTrabajo.getDia().equals(dia) && botonCasteado.isSelected()){
                            JornadaDias nuevaJornadaDias = new JornadaDias();
                            nuevaJornadaDias.setJornadaDeTrabajo(jornadaCreada);
                            nuevaJornadaDias.setDiasDeTrabajo(diaDeTrabajo);
                            // crear jornada dias en la BD
                            controlador.crearJornadaDias(nuevaJornadaDias);
                        }
                    }
                }
                
                // crear contrato
                Contratos nuevoContrato = new Contratos();
                nuevoContrato.setJornadasDeTrabajo(jornadaCreada);
                nuevoContrato.setUsuario(usuarioCreado);
                
                // crear contrato en la BD
                controlador.crearContrato(nuevoContrato);
                
                JOptionPane.showMessageDialog(null, "Usuario creado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                resetInputs();
            }catch (HeadlessException e){
                JOptionPane.showMessageDialog(null, "Error al crear usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Hay datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Boolean verificarDatosNuevoUsuario(){
        String rut = fieldRut.getText();
        String nombre = fieldNombre.getText();
        String telefono = fieldTelefono.getText();
        String correo = fieldCorreo.getText();
        String sueldo = fieldSueldo.getText();
        String contra = fieldContra.getText();

        String rutRegex = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9Kk]{1}$";
        String nombreRegex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        String telefonoRegex = "^9[3-9][0-9]{7}$";
        String correoRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        String sueldoRegex = "^[0-9]+$";
        String contraRegex = "^(?=.*[A-Z])(?=.*[0-9]).{6,}$";

        Boolean rutValido = false;
        Boolean nombreValido;
        Boolean telefonoValido = false;
        Boolean correoValido = false;
        Boolean sueldoValido;
        Boolean contraValida;

        if (!rut.matches(rutRegex)){
            errorRut.setText("Rut inválido, ej: 20.024.263-K");
            errorRut.setForeground(Color.red);
            rutValido = false;
        }else {
            Usuarios usuarioRut = controlador.obtenerUsuarioPorRut(rut);
            if (usuarioRut != null){
                errorRut.setText("Rut ya registrado");
                JOptionPane.showMessageDialog(null, "Rut ya registrado", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                errorRut.setText("");
                rutValido = true;
            }
        }

        if (!nombre.matches(nombreRegex)){
            errorNombre.setText("Nombre inválido, solo se permiten letras");
            errorNombre.setForeground(Color.red);
            nombreValido = false;
        }else {
            errorNombre.setText("");
            nombreValido = true;
        }

        if (!telefono.matches(telefonoRegex)){
            errorTelefono.setText("Teléfono inválido, ej: 931734155");
            errorTelefono.setForeground(Color.red);
            telefonoValido = false;
        }else {
            Usuarios usuarioTelefono = controlador.obtenerUsuarioPorTelefono(telefono);
            if (usuarioTelefono != null){
                errorTelefono.setText("Teléfono ya registrado");
                JOptionPane.showMessageDialog(null, "Teléfono ya registrado", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                errorTelefono.setText("");
                telefonoValido = true;
            }
        }

        if (!correo.matches(correoRegex)){
            errorCorreo.setText("Correo inválido, ej: ejemplo@gmail.com");
            errorCorreo.setForeground(Color.red);
            correoValido = false;
        }else {
            Usuarios usuarioCorreo = controlador.obtenerUsuarioPorCorreo(correo);
            if (usuarioCorreo != null){
                errorCorreo.setText("Correo ya registrado");
                JOptionPane.showMessageDialog(null, "Correo ya registrado", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                errorCorreo.setText("");
                correoValido = true;
            }
        }

        if (!sueldo.matches(sueldoRegex)){
            errorSueldo.setText("Sueldo inválido, solo se aceptan números enteros");
            errorSueldo.setForeground(Color.red);
            sueldoValido = false;
        }else {
            errorSueldo.setText("");
            sueldoValido = true;
        }

        if (!contra.matches(contraRegex)){
            errorContra.setText("Contraseña inválida, ej: Contra1234");
            errorContra.setForeground(Color.red);
            contraValida = false;
        }else {
            errorContra.setText("");
            contraValida = true;
        }

        return rutValido && nombreValido && telefonoValido && correoValido && sueldoValido && contraValida;
    }
    
    private Boolean verificarJornadaNuevoUsuario(){
        int horasEntrada = (Integer) pEntradaHoras.getValue();
        int minutosEntrada = (Integer) pEntradaMinutos.getValue();

        int horasSalida = (Integer) pSalidaHoras.getValue();
        int minutosSalida = (Integer) pSalidaMinutos.getValue();

        LocalTime entrada = LocalTime.of(horasEntrada, minutosEntrada);
        LocalTime salida = LocalTime.of(horasSalida, minutosSalida);

        Time timeEntrada = Time.valueOf(entrada);
        Time timeSalida = Time.valueOf(salida);
        
        Boolean horasCorrectas = false;
        Boolean diasCorrectos = false;

        if (esDiferenciaMinimaSeisHoras(timeEntrada, timeSalida)){
            errorJornada.setText("Jornada correcta");
            errorJornada.setForeground(Color.green);
            horasCorrectas = true;
        }else {
            JOptionPane.showMessageDialog(null, "Mínimo de 6 horas de trabajo", "Error", JOptionPane.ERROR_MESSAGE);
            errorJornada.setText("Jornada incorrecta, mínimo 6 horas");
            errorJornada.setForeground(Color.red);
        }

        int contadorDias = 0;

        for (Object[] objeto : listaBotonesDias){
            JRadioButton botonCasteado = (JRadioButton) objeto[0];
            if (botonCasteado.isSelected()) contadorDias++;
        }


        if (contadorDias >= 5){
            errorDias.setText("Días correctos");
            errorDias.setForeground(Color.green);
            diasCorrectos = true;
        }else {
            errorDias.setText("Mínimo 5 días");
            errorDias.setForeground(Color.red);
        }
        
        return horasCorrectas && diasCorrectos;
    }
    
    private void resetInputs (){
        fieldRut.setText("");
        fieldNombre.setText("");
        fieldTelefono.setText("");
        fieldCorreo.setText("");
        fieldSueldo.setText("");
        fieldContra.setText("");
        comboRoles.setSelectedIndex(0);
        comboGerencias.setSelectedIndex(0);
        errorRut.setText("");
        errorNombre.setText("");
        errorSueldo.setText("");
        errorTelefono.setText("");
        errorCorreo.setText("");
        errorContra.setText("");
        btnLunes.setSelected(false);
        btnMartes.setSelected(false);
        btnMiercoles.setSelected(false);
        btnJueves.setSelected(false);
        btnViernes.setSelected(false);
        btnSabado.setSelected(false);
        errorJornada.setText("");
        errorDias.setText("");
        pEntradaHoras.setValue(8);
        pEntradaMinutos.setValue(0);
        pSalidaHoras.setValue(14);
        pSalidaMinutos.setValue(0);
        panelTabs.setSelectedIndex(0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 871, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
