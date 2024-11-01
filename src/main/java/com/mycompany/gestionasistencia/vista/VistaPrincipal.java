package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.ui.CustomButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class VistaPrincipal extends javax.swing.JFrame {
    Controlador controlador = new Controlador();
    Usuarios usuarioActual = null;
    
    private JPanel fondo;
    ArrayList<Object[]> listaBotones;
    
    // medidas
    int width;
    int height;
    
    int anchoBotones;
    int altoBotones;
    int altoBotonesLogin;
    
    int divisionAnchoBotones = 6;
    
    int cantidadBotones;
   
    // botones
    JPanel btnMarcarAsistencia;
    JPanel btnMisRegistros;
    JPanel btnSolicitudes;
    JPanel btnPerfil;
    JPanel btnAgregarUsuarios;
    JPanel btnConsultarUsuarios;
    JPanel btnGenerarInformes;
    
    // labels
    JLabel lblMarcarAsistencia;
    JLabel lblMisRegistros;
    JLabel lblSolicitudes;
    JLabel lblPerfil;
    JLabel lblAgregarUsuarios;
    JLabel lblConsultarUsuarios;
    JLabel lblGenerarInformes;
    
    // iconos
    JLabel imgMarcarAsistencia;
    JLabel imgMisRegistros;
    JLabel imgSolicitudes;
    JLabel imgPerfil;
    JLabel imgAgregarUsuarios;
    JLabel imgConsultarUsuarios;
    JLabel imgGenerarInformes;
    
    // colores
    Color colorSeleccionado = new Color(102,102,255);
    Color colorHover = new Color(51,51,255);
    Color colorDefault = new Color(0,51,204);
    

    // panel central
    JPanel panelCentral = new JPanel();
    
    // login
    JPanel panelLogin;
    
    // datos login
    JLabel labelLogin;
    JLabel labelCorreo;
    JLabel labelContra;
    JTextField fieldCorreo;
    JLabel errorCorreo;
    
    JPasswordField fieldContra;
    JLabel errorContra;
    
    CustomButton btnIngresar;
    
    // datos cerrar sesion
    JLabel cerrarSesion;
    JLabel lblCorreoUsuario;
    JLabel lblRolUsuario;
    
    // cambio de paneles
    PMarcajeAsistencia pMarcajeAsistencia;
    PMisRegistros pMisRegistros;
    PAgregarUsuario pAgregarUsuario;
    PGenerarInformes pGenerarInformes;
    PConsultarUsuarios pConsultarUsuarios;
    PPerfil pPerfil;
    PSolicitudes pSolicitudes;
    
    // panel seleccionado
    JPanel panelSeleccionado;
    
    public VistaPrincipal() {
        initComponents();
        width = getWidth();
        height = getHeight();
        pMarcajeAsistencia = new PMarcajeAsistencia(usuarioActual);
        pMisRegistros = new PMisRegistros(usuarioActual);
        anchoBotones = width / divisionAnchoBotones;
        crearPanel();
        crearPanelLogin();
        crearBotones();
        crearPanelCentral();
        asignarPanel(pMarcajeAsistencia, btnMarcarAsistencia);
        ajustarVentana();
    }
    
    public void crearPanel() {
        //Panel
        fondo = new JPanel();
        fondo.setBackground(new Color(51,0,153));
        fondo.setLayout(null);
        
        //JFrame
        setLayout(new BorderLayout());
        add(fondo, BorderLayout.CENTER);
    }
    
    public void crearBotones(){
        if (listaBotones != null) {
            for (Object[] boton : listaBotones) {
                JPanel botonCasteado = (JPanel) boton[0];
                JLabel labelCasteada = (JLabel) boton[4];
                JLabel imagenCasteada = (JLabel) boton[5];
                JPanel panelCasteado = (JPanel) boton[8];
                fondo.remove(botonCasteado);
                fondo.remove(labelCasteada);
                fondo.remove(imagenCasteada);
                fondo.remove(panelCasteado);
            }
        }
        
        listaBotones = new ArrayList<>();
        String permisos = "Usuario";
        
        if (usuarioActual != null){
            if (usuarioActual.getRol().getRol().equals("Admin")){
                permisos = "Admin";
            }
        }
        
        btnMarcarAsistencia = new JPanel();
        btnMisRegistros = new JPanel();
        btnSolicitudes = new JPanel();
        btnPerfil = new JPanel();
        btnAgregarUsuarios = new JPanel();
        btnConsultarUsuarios = new JPanel();
        btnGenerarInformes = new JPanel();

        // labels
        lblMarcarAsistencia = new JLabel();
        lblMisRegistros = new JLabel();
        lblSolicitudes = new JLabel();
        lblPerfil = new JLabel();
        lblAgregarUsuarios = new JLabel();
        lblConsultarUsuarios = new JLabel();
        lblGenerarInformes = new JLabel();

        // iconos
        imgMarcarAsistencia = new JLabel();
        imgMisRegistros = new JLabel();
        imgSolicitudes = new JLabel();
        imgPerfil = new JLabel();
        imgAgregarUsuarios = new JLabel();
        imgConsultarUsuarios = new JLabel();
        imgGenerarInformes = new JLabel();
        
        // instanciar paneles con el usuario
        pMarcajeAsistencia = new PMarcajeAsistencia(usuarioActual);
        pMisRegistros = new PMisRegistros(usuarioActual);
        pAgregarUsuario = new PAgregarUsuario();
        pGenerarInformes = new PGenerarInformes();
        pConsultarUsuarios = new PConsultarUsuarios(usuarioActual);
        pPerfil = new PPerfil(usuarioActual);
        pSolicitudes = new PSolicitudes(usuarioActual);
        
        listaBotones.add(new Object[]{btnMarcarAsistencia,"Marcar asistencia", colorSeleccionado, 0, lblMarcarAsistencia, imgMarcarAsistencia, "iconoMarcaje.png", "Usuario Admin", pMarcajeAsistencia});
        listaBotones.add(new Object[]{btnMisRegistros, "Mis registros", colorDefault, 1, lblMisRegistros, imgMisRegistros, "iconoRegistroAsistencia.png", "Usuario Admin", pMisRegistros});
        listaBotones.add(new Object[]{btnSolicitudes, "Solicitudes", colorDefault, 2, lblSolicitudes, imgSolicitudes, "iconoSolicitudes.png", "Usuario Admin", pSolicitudes});
        listaBotones.add(new Object[]{btnPerfil, "Perfil", colorDefault, 3, lblPerfil, imgPerfil, "iconoPerfil.png", "Usuario Admin", pPerfil});
        listaBotones.add(new Object[]{btnAgregarUsuarios, "Agregar usuarios", colorDefault, 4, lblAgregarUsuarios, imgAgregarUsuarios, "iconoAgregar.png", "Admin", pAgregarUsuario});
        listaBotones.add(new Object[]{btnConsultarUsuarios, "Consultar usuarios", colorDefault, 5, lblConsultarUsuarios, imgConsultarUsuarios, "iconoBuscar.png", "Admin", pConsultarUsuarios});
        listaBotones.add(new Object[]{btnGenerarInformes, "Generar informes", colorDefault, 6, lblGenerarInformes, imgGenerarInformes, "iconoInformes.png", "Admin", pGenerarInformes});
        
        cantidadBotones = 0;
        
        for (Object[] boton : listaBotones){
            String permisosBoton = (String) boton[7];
            Boolean esVisible = permisosBoton.contains(permisos);
            if (esVisible) cantidadBotones++;
        }
        
        altoBotones = Math.round((height / 2) / cantidadBotones);
        
        for (Object[] boton : listaBotones){
            JPanel botonCasteado = (JPanel) boton[0];
            String texto = (String) boton[1];
            Color color = (Color) boton[2]; 
            int posicion = (int) boton[3];
            JLabel labelCasteada = (JLabel) boton[4];
            JLabel imagenCasteada = (JLabel) boton[5];
            String imagenSrc = (String) boton[6];
            String permisosBoton = (String) boton[7];
            Boolean esVisible = permisosBoton.contains(permisos);
            JPanel pMarcajeA = (JPanel) boton[8];
            
            crearBoton(botonCasteado, texto, color, posicion, labelCasteada, imagenCasteada, imagenSrc, esVisible, pMarcajeA);
        }
    }
    
    private void crearBoton (JPanel botonCreado, String texto, Color color, int posicion, JLabel labelCreada, JLabel imagenCreada, String imagenSrc, Boolean esVisible, JPanel pCambio){
        if (!esVisible){
            return;
        }
        
        final JPanel btnPanel = botonCreado;
        
        
        botonCreado.setBackground(color);
        botonCreado.setBounds(0, posicion * altoBotones, anchoBotones, altoBotones);
        botonCreado.setAlignmentY(CENTER_ALIGNMENT);
        botonCreado.setLayout(null);
        
        btnPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (pCambio == panelSeleccionado){
                    return;
                }
                if (usuarioActual == null) return;
                btnPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnPanel.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (pCambio == panelSeleccionado){
                    return;
                }
                if (usuarioActual == null) return;
                btnPanel.setBackground(colorDefault);
                btnPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
            @Override
            public void mouseClicked(MouseEvent e){
                if (usuarioActual == null) return;
                asignarPanel(pCambio, btnPanel);
            }
        });
        
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        
        
        labelCreada.setText(texto);
        labelCreada.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        labelCreada.setForeground(Color.white);
        labelCreada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        imagenCreada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        botonCreado.add(labelCreada);
        botonCreado.add(imagenCreada);
        
        fondo.add(botonCreado);
        
        labelCreada.setBounds(0, 0, anchoBotones, altoBotones);
        imagenCreada.setBounds(0, 0, altoBotones, altoBotones);
    }
    
    private void asignarPanel (JPanel pCambio, JPanel btnPanel){
        reiniciarColorBotones();
        btnPanel.setBackground(colorSeleccionado);
        panelSeleccionado = pCambio;

        panelCentral.removeAll();
        panelCentral.add(pCambio);
        panelCentral.repaint();
        panelCentral.revalidate();
    }
    
    private void crearPanelCentral (){
        
        panelCentral.setBackground(Color.red);
        panelCentral.setBounds(anchoBotones, 0, width - anchoBotones, height);
        panelCentral.setLayout(new BorderLayout());
        
        fondo.add(panelCentral);
    }
    
    private void ajustarVentana (){
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                
                width = getWidth();
                height = getHeight();
                
                anchoBotones = width / divisionAnchoBotones;
                altoBotones = Math.round((height / 2) / cantidadBotones);
                
                int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
                
                for (Object[] boton : listaBotones){
                    JPanel btnPanel = (JPanel) boton[0];
                    int btnPosicion = (int) boton[3];
                    JLabel btnLabel = (JLabel) boton[4];
                    JLabel btnImagen = (JLabel) boton[5];
                    
                    int btnWidth = btnPanel.getWidth();
                    int btnHeight = btnPanel.getHeight();
                    
                    // redimensionar botones
                    btnPanel.setBounds(0, btnPosicion * altoBotones, anchoBotones, altoBotones);
                    btnLabel.setBounds( 0, 0, anchoBotones, altoBotones);
                    btnLabel.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                    btnImagen.setBounds(0, 0, altoBotones, altoBotones);
                }
                // redimensionar panel central
                panelCentral.setBounds(anchoBotones, 0, width - anchoBotones, height);
                
                // redimensionar panel login
                panelLogin.setBounds(0, height / 2, anchoBotones,  height / 2);
                
                int nuevoPanelLoginWidth = panelLogin.getWidth();
                int nuevoPanelLoginHeight = panelLogin.getHeight();
                altoBotonesLogin = (int) Math.round(panelLogin.getHeight() * 0.2);
                
                // redimensionar datos login
                
                // titulo
                labelLogin.setBounds(0, (int) Math.round(nuevoPanelLoginHeight * 0.01), nuevoPanelLoginWidth, altoBotonesLogin);
                labelLogin.setFont(new Font("Segoe UI Symbol", 1, fontSize * 2));
                
                // correo y contra
                labelCorreo.setBounds((int) Math.round(nuevoPanelLoginWidth * 0.1), (int) Math.round(panelLogin.getHeight() * 0.2), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
                labelCorreo.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                fieldCorreo.setBounds(labelCorreo.getX(), labelCorreo.getY() + labelCorreo.getHeight(), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
                errorCorreo.setBounds(labelCorreo.getX(), fieldCorreo.getY() + fieldCorreo.getHeight(), (int) Math.round(anchoBotones / 1.5), altoBotonesLogin / 3);
                labelContra.setBounds(labelCorreo.getX(), errorCorreo.getY() + errorCorreo.getHeight(), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
                fieldContra.setBounds(labelCorreo.getX(), labelContra.getY() + labelContra.getHeight(), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
                errorContra.setBounds(labelCorreo.getX(), fieldContra.getY() + fieldContra.getHeight(),  (int) Math.round(anchoBotones / 1.5), altoBotonesLogin / 3);
                
                labelContra.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                errorCorreo.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize / 1.5)));
                errorContra.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize / 1.5)));
                
                cerrarSesion.setBounds(0, (int) Math.round(nuevoPanelLoginHeight * 0.01), nuevoPanelLoginWidth, altoBotonesLogin);
                lblCorreoUsuario.setBounds((int) Math.round(nuevoPanelLoginWidth * 0.1), cerrarSesion.getY() + cerrarSesion.getHeight() + 20, nuevoPanelLoginWidth, altoBotonesLogin / 3);
                lblRolUsuario.setBounds(lblCorreoUsuario.getX(), lblCorreoUsuario.getY() + lblCorreoUsuario.getHeight(), nuevoPanelLoginWidth, altoBotonesLogin / 3);
                cerrarSesion.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5)));
                lblCorreoUsuario.setFont(new Font("Segoe UI Symbol", 1, fontSize));
                lblRolUsuario.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5)));
                
                // boton ingresar
                btnIngresar.setBounds((int) Math.round(nuevoPanelLoginWidth * 0.1), (int) Math.round(nuevoPanelLoginHeight * 0.6) + altoBotonesLogin / 2, (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 2);
                btnIngresar.ajustarTexto(fontSize);
            }
        });
    }
    
    private void crearPanelLogin() {
        if (panelLogin != null) {
            fondo.remove(panelLogin);
            fondo.revalidate();
            fondo.repaint();
        }
        
        panelLogin = new JPanel();
        panelLogin.setBackground(new Color(51,0,153));
        panelLogin.setBounds(0, height / 2, anchoBotones,  height / 2);
        panelLogin.setLayout(null);
        fondo.add(panelLogin);
        
        int nuevoPanelLoginWidth = panelLogin.getWidth();
        int nuevoPanelLoginHeight = panelLogin.getHeight();
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        
        // cerrar sesion
        cerrarSesion = new JLabel();
        lblCorreoUsuario = new JLabel();
        lblRolUsuario = new JLabel();
        // login
        labelLogin = new JLabel();
        labelCorreo = new JLabel();
        labelContra = new JLabel();
        errorCorreo = new JLabel();
        errorContra = new JLabel();
        fieldCorreo = new JTextField();
        fieldContra = new JPasswordField();
        
        if (usuarioActual != null){
            panelLogin.add(cerrarSesion);
            panelLogin.add(lblCorreoUsuario);
            panelLogin.add(lblRolUsuario);
            lblCorreoUsuario.setText(usuarioActual.getCorreo());
            lblRolUsuario.setText(usuarioActual.getRol().getRol());
        }else {
            panelLogin.add(labelLogin);
            panelLogin.add(labelCorreo);
            panelLogin.add(labelContra);
            panelLogin.add(errorCorreo);
            panelLogin.add(errorContra);   
            panelLogin.add(fieldCorreo);
            panelLogin.add(fieldContra);
        }
        // login
        labelLogin.setText("Login");
        labelLogin.setFont(new Font("Segoe UI Symbol", 1, fontSize * 2));
        labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLogin.setForeground(Color.white);
        
        labelCorreo.setText("Ingresar correo");
        labelCorreo.setForeground(Color.white);
        labelCorreo.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        
        labelContra.setText("Ingresar contraseña");
        labelContra.setForeground(Color.white);
        labelContra.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        
        // cerrar sesion
        cerrarSesion.setText("Cerrar sesión");
        cerrarSesion.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5)));
        cerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cerrarSesion.setForeground(Color.white);
        
        lblCorreoUsuario.setFont(new Font("Segoe UI Symbol", 1, fontSize));
        lblCorreoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCorreoUsuario.setForeground(Color.white);
        
        lblRolUsuario.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5)));
        lblRolUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRolUsuario.setForeground(Color.white);
        
        cerrarSesion.setBounds(0, (int) Math.round(nuevoPanelLoginHeight * 0.01), nuevoPanelLoginWidth, altoBotonesLogin);
        lblCorreoUsuario.setBounds((int) Math.round(nuevoPanelLoginWidth * 0.1), cerrarSesion.getY() + cerrarSesion.getHeight() + 20, nuevoPanelLoginWidth, altoBotonesLogin / 3);
        lblRolUsuario.setBounds(lblCorreoUsuario.getX(), lblCorreoUsuario.getY() + lblCorreoUsuario.getHeight(), nuevoPanelLoginWidth, altoBotonesLogin / 3); 
                
        // boton ingresar
        String txtBtnIngresar = usuarioActual == null ? "Ingresar" : "Salir";
        btnIngresar = new CustomButton(txtBtnIngresar, fontSize);
        panelLogin.add(btnIngresar);
        
        altoBotonesLogin = (int) Math.round(panelLogin.getHeight() * 0.17);
     
        // titulo
        // label correo y contra
        
        fieldCorreo.setText("ccva1998@gmail.com");
        fieldContra.setText("Hola1234");
        errorCorreo.setForeground(Color.red);
        errorCorreo.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize / 1.5)));
        errorContra.setForeground(Color.red);
        errorContra.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize / 1.5)));
        
        
        labelLogin.setBounds(0, (int) Math.round(nuevoPanelLoginHeight * 0.01), nuevoPanelLoginWidth, altoBotonesLogin);
        labelCorreo.setBounds((int) Math.round(nuevoPanelLoginWidth * 0.1), (int) Math.round(panelLogin.getHeight() * 0.2), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
        fieldCorreo.setBounds(labelCorreo.getX(), labelCorreo.getY() + labelCorreo.getHeight(), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
        errorCorreo.setBounds(labelCorreo.getX(), fieldCorreo.getY() + fieldCorreo.getHeight(), (int) Math.round(anchoBotones / 1.5), altoBotonesLogin / 3);
        labelContra.setBounds(labelCorreo.getX(), errorCorreo.getY() + errorCorreo.getHeight(), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
        fieldContra.setBounds(labelCorreo.getX(), labelContra.getY() + labelContra.getHeight(), (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 3);
        errorContra.setBounds(labelCorreo.getX(), fieldContra.getY() + fieldContra.getHeight(),  (int) Math.round(anchoBotones / 1.5), altoBotonesLogin / 3);
        
        // boton ingresar
        btnIngresar.setBounds((int) Math.round(nuevoPanelLoginWidth * 0.1), (int) Math.round(nuevoPanelLoginHeight * 0.6) + altoBotonesLogin / 2, (int) Math.round(nuevoPanelLoginWidth - (nuevoPanelLoginWidth * 0.1) * 2), altoBotonesLogin / 2);
        
        panelLogin.revalidate();
        panelLogin.repaint();
        
        btnIngresar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (usuarioActual != null){
                    usuarioActual = null;
                    pMarcajeAsistencia = new PMarcajeAsistencia(usuarioActual);
                    crearBotones();
                    asignarPanel(pMarcajeAsistencia, btnMarcarAsistencia);
                    crearPanelLogin();
                    return;
                }
                String correo = fieldCorreo.getText();
                String contra = fieldContra.getText();
                errorCorreo.setText("");
                errorContra.setText("");
                
                Usuarios usuarioEncontrado = controlador.obtenerUsuarioPorCorreo(correo);
                
                if (usuarioEncontrado == null){
                    errorCorreo.setText("El correo no está registrado");
                    return;
                }
                
                if (usuarioEncontrado.getContra().equals(contra)){
                    System.out.println("Usuario existe " + usuarioEncontrado.getCorreo());
                    usuarioActual = usuarioEncontrado;
                    pMarcajeAsistencia = new PMarcajeAsistencia(usuarioActual);
                    crearBotones();
                    asignarPanel(pMarcajeAsistencia, btnMarcarAsistencia);
                    crearPanelLogin();
                }else {
                   errorContra.setText("Contraseña incorrecta");
                }
            }
        });
    }
    
    private void reiniciarColorBotones (){
        for (Object[] boton : listaBotones){
            JPanel bot = (JPanel) boton[0];
            bot.setBackground(colorDefault);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1254, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
