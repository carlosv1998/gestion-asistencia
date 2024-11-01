package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.Contratos;
import com.mycompany.gestionasistencia.modelo.JornadaDias;
import com.mycompany.gestionasistencia.modelo.JornadasDeTrabajo;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import com.mycompany.gestionasistencia.ui.CustomButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PConsultarUsuarios extends javax.swing.JPanel {
    private JPanel fondo;
    Usuarios usuarioActual = null;
    Controlador controlador = new Controlador();
    
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
    
    // tabla
    JScrollPane panelScroll;
    DefaultTableModel modeloTabla;
    JTable tablaRegistros;
    
    // generar reportes
    CustomButton btnModificarUsuario;
    
    CustomButton btnEliminarUsuario;
    
    
    public PConsultarUsuarios(Usuarios usuario) {
        initComponents();
        usuarioActual = usuario;
        width = getWidth();
        height = getHeight();
        anchoBotones = width / anchoDivisor;
        altoBotones = height / altoDivisor;
        crearPanel();
        crearBuscador();
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
        buscadorBoton.setBackground(btnColorDefecto);
        buscadorBoton.setBounds(buscadorField.getWidth() + buscadorField.getX() + 4, buscadorIcono.getY(), anchoBotones, altoBotones);

        
        buscadorBoton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                consultarUsuarios();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                buscadorBoton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                buscadorBoton.setBackground(btnColorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buscadorBoton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                buscadorBoton.setBackground(btnColorDefecto);
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
    
    private void generarBotonesReportes (){
        int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
        btnModificarUsuario = new CustomButton("Modificar", fontSize);
        fondo.add(btnModificarUsuario);
        btnModificarUsuario.setBounds((width / 2) - anchoBotones - 10, panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);
        
        btnEliminarUsuario = new CustomButton("Eliminar", fontSize);
        fondo.add(btnEliminarUsuario);
        btnEliminarUsuario.setBounds((width / 2) + 10, panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);

        
        btnModificarUsuario.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                int filaSeleccionada = tablaRegistros.getSelectedRow();
                
                if (filaSeleccionada != -1){
                    DefaultTableModel modelo = (DefaultTableModel) tablaRegistros.getModel();
                    Object idTabla = modelo.getValueAt(filaSeleccionada, 0);

                    int id = Integer.parseInt(idTabla.toString());
                    Usuarios usuarioAModificar = controlador.obtenerUsuarioPorId(id);
                    
                    if (usuarioAModificar == null){
                        JOptionPane.showMessageDialog(null, "Usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    VistaModificarUsuario vistaModificarUsuario = new VistaModificarUsuario(usuarioAModificar);
                    vistaModificarUsuario.setVisible(true);
                    vistaModificarUsuario.setLocationRelativeTo(null);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnModificarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnModificarUsuario.setBackground(btnColorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnModificarUsuario.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnModificarUsuario.setBackground(btnColorDefecto);
            }
        });
        
        btnEliminarUsuario.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                int filaSeleccionada = tablaRegistros.getSelectedRow();
                
                if (filaSeleccionada != -1){
                    DefaultTableModel modelo = (DefaultTableModel) tablaRegistros.getModel();
                    Object idTabla = modelo.getValueAt(filaSeleccionada, 0);

                    int id = Integer.parseInt(idTabla.toString());
                    Usuarios usuarioAEliminar = controlador.obtenerUsuarioPorId(id);

                    if (usuarioAEliminar == null){
                        JOptionPane.showMessageDialog(null, "Usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Contratos contratoUsuario = usuarioAEliminar.getContrato();
                    JornadasDeTrabajo jornadaUsuario = contratoUsuario.getJornadasDeTrabajo();
                    List<JornadaDias> diasJornada = jornadaUsuario.getJornadaDias();
                    
                    try{
                        // borrar dias de trabajo
                        for (JornadaDias dias : diasJornada){
                            controlador.borrarJornadaDias(dias.getId());
                        }
                        // borrar contrato
                        controlador.eliminarContrato(contratoUsuario.getId());
                        // borrar jornada
                        controlador.eliminarJornadaDeTrabajo(jornadaUsuario.getId());
                        // eliminar usuario
                        controlador.eliminarUsuario(usuarioAEliminar.getId());
                        
                        JOptionPane.showMessageDialog(null, "Usuario eliminado", "Error", JOptionPane.INFORMATION_MESSAGE);
                        consultarUsuarios();
                    }catch (NonexistentEntityException e){
                        JOptionPane.showMessageDialog(null, "Error al eliminar usuario", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEliminarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnEliminarUsuario.setBackground(btnColorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEliminarUsuario.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnEliminarUsuario.setBackground(btnColorDefecto);
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
               
               //tabla
               panelScroll.setBounds(10, buscadorField.getY() + buscadorField.getHeight() + 10, width - 35, (int) Math.round(height - height * 0.3));
               tablaRegistros.setBounds(0, 0, width, height);
               
               // botones cambios usuario
               btnModificarUsuario.setBounds((width / 2) - (int) Math.round(anchoBotones * 1.5), panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);
               btnModificarUsuario.ajustarTexto(fontSize);
            
               btnEliminarUsuario.setBounds((width / 2) + 10, panelScroll.getY() + panelScroll.getHeight() + 10, anchoBotones, altoBotones);
               btnEliminarUsuario.ajustarTexto(fontSize);

            }
        });
    }
    
    private void consultarUsuarios (){
        List<Usuarios> listaUsuarios = controlador.obtenerUsuarios();

        if (listaUsuarios == null){
            return;
        }

        JTableHeader header = tablaRegistros.getTableHeader();
        header.setBackground(new Color(0,0,51));
        header.setForeground(Color.white);
        header.setFont(new Font("Segoe UI Symbol", 1, 14));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        tablaRegistros.setRowHeight(30);

        modeloTabla = new DefaultTableModel();

        modeloTabla.setColumnCount(0);
        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Rut");
        modeloTabla.addColumn("Sueldo");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Gerencia");
        modeloTabla.addColumn("Departamento");
        modeloTabla.addColumn("Cargo");
        modeloTabla.addColumn("Jornada");
        modeloTabla.addColumn("Fecha de creación");

        for (Usuarios usuario : listaUsuarios){
            if (usuario.getId() == usuarioActual.getId()) continue;
            
            modeloTabla.addRow(new Object[]{
                usuario.getId(),
                usuario.getNombre(),
                usuario.getRut(),
                usuario.getSueldo(),
                usuario.getTelefono(),
                usuario.getCorreo(),
                usuario.getRol().getRol(),
                usuario.getGerencia().getGerencia(),
                usuario.getDepartamento().getDepartamento(),
                usuario.getCargo().getCargo(),
                usuario.getContrato().getJornadasDeTrabajo().getHoraEntrada().toString() + "-" + usuario.getContrato().getJornadasDeTrabajo().getHoraSalida(),
                usuario.getFechaCreacion()
            });
        }

        tablaRegistros.setModel(modeloTabla);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 587, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
