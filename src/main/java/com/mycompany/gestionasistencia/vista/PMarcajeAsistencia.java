package com.mycompany.gestionasistencia.vista;

import com.mycompany.gestionasistencia.controlador.Controlador;
import com.mycompany.gestionasistencia.modelo.JornadaDias;
import com.mycompany.gestionasistencia.modelo.RegistroAsistencias;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.utils.EnviarCorreo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.time.DayOfWeek;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PMarcajeAsistencia extends javax.swing.JPanel {
    Controlador controlador = new Controlador();
    Usuarios usuarioActual = null;
    
    ArrayList<Object[]> listaLabels = new ArrayList<>();
    
    JPanel fondo = new JPanel();
    int width;
    int height;

    // labels marcaje
    JLabel lblBienvenida = new JLabel();
    JLabel lblTiempo = new JLabel();
    JLabel lblFecha = new JLabel();
    
    // panel marcaje
    JPanel pMarcaje;

    JLabel lblUltimaMarca = new JLabel();
    JLabel lblUltimaMarcaFecha = new JLabel();
    JLabel lblUltimaMarcaHora = new JLabel();
    JLabel lblUltimaMarcaTipo = new JLabel();
    JLabel lblUltimaMarcaFechaValue = new JLabel();
    JLabel lblUltimaMarcaHoraValue = new JLabel();
    JLabel lblUltimaMarcaTipoValue = new JLabel();
    
    // jornadas
    
    JLabel lbllJornada = new JLabel();
    JLabel lblJornadaHoraEntrada = new JLabel();
    JLabel lblJornadaHoraSalida = new JLabel();
    JLabel lblJornadaHoraEntradaValue = new JLabel();
    JLabel lblJornadaHoraSalidaValue = new JLabel();
    
    // botones
    JPanel btnRegistrarIngreso;
    JPanel btnRegistrarSalida;
    
    JLabel lblRegistrarIngreso;
    JLabel lblRegistrarSalida;
    
    public PMarcajeAsistencia(Usuarios usuario) {
        initComponents();
        usuarioActual = usuario;
        width = getWidth();
        height = getHeight();
        crearPanel();
        crearMarcajeLabels();
        crearLabels();
        crearMarcajeValues();
        crearBotones();
        ajustarVentana();
        iniciarContadorReloj();
        iniciarMarcaje();
    }
    
    private void crearPanel () {
        fondo.setBackground(new Color(20,0,100));
        fondo.setLayout(null);
        //JFrame
        setLayout(new BorderLayout());
        add(fondo, BorderLayout.CENTER);
    }
    
    private void crearMarcajeLabels (){
        lblBienvenida = new JLabel();
        lblTiempo = new JLabel();
        lblFecha = new JLabel();
        pMarcaje = new JPanel();
        
        
        fondo.add(lblBienvenida);
        fondo.add(lblTiempo);
        fondo.add(lblFecha);
        fondo.add(pMarcaje);
        
        // bienvenida
        lblBienvenida.setText("Inicie sesión");
        lblBienvenida.setForeground(Color.white);
        lblBienvenida.setFont(new Font("Segoe UI Symbol", 1, 24));
        lblBienvenida.setBounds((int) Math.round(width * 0.1), (int) Math.round(height * 0.05), width/3, height/8);
        
        // fecha
        lblFecha.setText("Fecha: 01-10-2024");
        lblFecha.setForeground(Color.white);
        lblFecha.setFont(new Font("Segoe UI Symbol", 1, 24));
        lblFecha.setBounds((int) Math.round(width - (width / 3) - 20), (int) Math.round(height * 0.02), width / 3, height / 10);
        
        // tiempo
        lblTiempo.setText("Hora: 13:34:40");
        lblTiempo.setForeground(Color.white);
        lblTiempo.setFont(new Font("Segoe UI Symbol", 1, 24));
        lblTiempo.setBounds((int) Math.round(width - (width / 3) - 20), lblFecha.getHeight(), width / 3, height / 10);
        
        // panel marcaje
        pMarcaje.setBackground(new Color(0,0,51));
        pMarcaje.setLayout(null);
        pMarcaje.setBounds((int) Math.round(width * 0.1), lblTiempo.getY() + 10, width, height);
    }
    
    private void crearLabels (){
        // labels marcaje
        listaLabels.add(new Object[] {lblUltimaMarca,"Última marca","RIGHT", 0.01, 0.15, 5});
        listaLabels.add(new Object[] {lblUltimaMarcaFecha, "Fecha", "LEFT", 0.25, 0.01, 5});
        listaLabels.add(new Object[] {lblUltimaMarcaHora, "Hora", "LEFT", 0.50, 0.01, 5});
        listaLabels.add(new Object[] {lblUltimaMarcaTipo, "Tipo", "LEFT", 0.75, 0.01, 5});
        listaLabels.add(new Object[] {lblUltimaMarcaFechaValue, "-", "LEFT", 0.25, 0.15, 5});
        listaLabels.add(new Object[] {lblUltimaMarcaHoraValue, "-", "LEFT", 0.50, 0.15, 5});
        listaLabels.add(new Object[] {lblUltimaMarcaTipoValue, "-", "LEFT", 0.75, 0.15, 5});
        
        // labels jornada
        listaLabels.add(new Object[] {lbllJornada, "Jornada de hoy:", "RIGHT", 0.01, 0.49, 5});
        listaLabels.add(new Object[] {lblJornadaHoraEntrada, "Hora de entrada", "LEFT", 0.25, 0.35, 4});
        listaLabels.add(new Object[] {lblJornadaHoraSalida, "Hora de salida", "LEFT", 0.60, 0.35, 4});
        listaLabels.add(new Object[] {lblJornadaHoraEntradaValue, "-", "LEFT", 0.25, 0.49, 4});
        listaLabels.add(new Object[] {lblJornadaHoraSalidaValue, "-", "LEFT", 0.60, 0.49, 4});
    }
    
    private void crearMarcajeValues () {
        int panelMarcajeWidth = pMarcaje.getWidth();
        int panelMarcajeHeight = pMarcaje.getHeight();
        
        for (Object[] labelMarcaje : listaLabels){
            JLabel lblMarcaje = (JLabel) labelMarcaje[0];
            String lblTexto = (String) labelMarcaje[1];
            String lblAlineacion = (String) labelMarcaje[2];
            double lblX = (double) labelMarcaje[3];
            double lblY = (double) labelMarcaje[4];
            int lblWidth = (int) labelMarcaje[5];

            int alineacion;

            alineacion = switch (lblAlineacion) {
                case "RIGHT" -> javax.swing.SwingConstants.RIGHT;
                case "CENTER" -> javax.swing.SwingConstants.CENTER;
                default -> javax.swing.SwingConstants.LEFT;
            };
            
            pMarcaje.add(lblMarcaje);

            lblMarcaje.setText(lblTexto);
            lblMarcaje.setForeground(Color.white);
            lblMarcaje.setHorizontalAlignment(alineacion);
            lblMarcaje.setBounds((int) Math.round(panelMarcajeWidth * lblX), (int) Math.round(panelMarcajeHeight * lblY), width / lblWidth, height / 10 );
            lblMarcaje.setFont(new Font("Segoe UI Symbol", 1, 10));
        }
    }
    
    private void crearBotones (){
        btnRegistrarIngreso = new JPanel();
        btnRegistrarSalida = new JPanel();
        lblRegistrarIngreso = new JLabel();
        lblRegistrarSalida = new JLabel();
        
        pMarcaje.add(btnRegistrarIngreso);
        pMarcaje.add(btnRegistrarSalida);
        
        btnRegistrarIngreso.setBackground(new Color(102,102,102));
        btnRegistrarSalida.setBackground(new Color(102,102,102));
        
        if (usuarioActual != null){
            if (usuarioActual.getContrato() == null){
                JOptionPane.showMessageDialog(null, "Usuario sin contrato", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate fechaActual = LocalDate.now();
            RegistroAsistencias registroAsistencia = controlador.obtenerRegistroAsistenciaPorFecha(fechaActual, usuarioActual.getId());
            
            // verificar el registro de asistencia
            if (registroAsistencia == null){
                // asignar colores a los botones
                LocalTime horaActual = LocalTime.now();
                LocalTime horaIngresoUsuario = usuarioActual.getContrato().getJornadasDeTrabajo().getHoraEntrada().toLocalTime();
                
                if (horaActual.isAfter(horaIngresoUsuario) || horaActual.equals(horaIngresoUsuario)){
                    btnRegistrarIngreso.setBackground(new Color(0,102,0));
                }else {
                    System.out.println("El usuario llegó antes de la hora de entrada.");
                }
            }else {
                btnRegistrarSalida.setBackground(new Color(102,0,0));
            }
        }
        btnRegistrarIngreso.setLayout(null);
        btnRegistrarSalida.setLayout(null);
        
        btnRegistrarIngreso.add(lblRegistrarIngreso);
        lblRegistrarIngreso.setText("Registrar entrada");
        lblRegistrarIngreso.setForeground(Color.white);
        lblRegistrarIngreso.setFont(new Font("Segoe UI Symbol", 1, 16));
        lblRegistrarIngreso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        btnRegistrarSalida.add(lblRegistrarSalida);
        lblRegistrarSalida.setText("Registrar salida");
        lblRegistrarSalida.setForeground(Color.white);
        lblRegistrarSalida.setFont(new Font("Segoe UI Symbol", 1, 16));
        lblRegistrarSalida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        btnRegistrarIngreso.setBounds((int) Math.round(pMarcaje.getWidth() * 0.2), (int) Math.round(pMarcaje.getHeight() - (pMarcaje.getHeight() * 0.1)), 100, 100);
        lblRegistrarIngreso.setBounds(0,0, btnRegistrarIngreso.getWidth(), btnRegistrarIngreso.getHeight());
        btnRegistrarSalida.setBounds((int) Math.round(pMarcaje.getWidth() * 0.6), (int) Math.round(pMarcaje.getHeight() - (pMarcaje.getHeight() * 0.1)), 100, 100);
        lblRegistrarSalida.setBounds(0,0, btnRegistrarSalida.getWidth(), btnRegistrarSalida.getHeight());
        
        
        btnRegistrarIngreso.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if (usuarioActual == null) return;
                if (usuarioActual.getContrato() == null) return;
                LocalDate fechaActual = LocalDate.now();
                
                RegistroAsistencias registroExistente = controlador.obtenerRegistroAsistenciaPorFecha(fechaActual, usuarioActual.getId());
                if (registroExistente != null){
                    JOptionPane.showMessageDialog(null, "Ingreso ya registrado", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                Boolean diaHabil = false;
                LocalTime horaActual = LocalTime.now();
                LocalTime horaIngresoUsuario = usuarioActual.getContrato().getJornadasDeTrabajo().getHoraEntrada().toLocalTime();
                LocalTime horaSalidaUsuario = usuarioActual.getContrato().getJornadasDeTrabajo().getHoraSalida().toLocalTime();
                List<JornadaDias> lista = usuarioActual.getContrato().getJornadasDeTrabajo().getJornadaDias();
                DayOfWeek diaDeLaSemana = fechaActual.getDayOfWeek();
                String diaActual = obtenerDiaDeLaSemana(diaDeLaSemana);

                for (JornadaDias diasDeTrabajo : lista){
                    if (diasDeTrabajo.getDiasDeTrabajo().getDia().equals(diaActual)){
                        diaHabil = true;
                    }
                }
                
                if (!diaHabil) {
                    JOptionPane.showMessageDialog(null, "Día no habilitado para marcar", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if (horaActual.isAfter(horaIngresoUsuario) && horaActual.isBefore(horaSalidaUsuario) || horaActual.equals(horaIngresoUsuario)){
                    RegistroAsistencias nuevoRegistroAsistencia = new RegistroAsistencias();
                    nuevoRegistroAsistencia.setUsuario(usuarioActual);
                    nuevoRegistroAsistencia.setHoraEntrada(Time.valueOf(horaActual));
                    nuevoRegistroAsistencia.setFecha(fechaActual);
                    
                    try {
                        verificarHorasTrabajadasEnLaSemana();
                        controlador.crearRegistroAsistencia(nuevoRegistroAsistencia);
                        JOptionPane.showMessageDialog(null, "Ingreso registrado", "Información", JOptionPane.INFORMATION_MESSAGE);
                        iniciarMarcaje();
                    }catch (HeadlessException e){
                        JOptionPane.showMessageDialog(null, "Error al registrar el ingreso", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    btnRegistrarIngreso.setBackground(new Color(102,102,102));
                    btnRegistrarSalida.setBackground(new Color(102,0,0));
                }else {
                    JOptionPane.showMessageDialog(null, "Debe esperar a su hora de entrada", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegistrarIngreso.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistrarIngreso.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        btnRegistrarSalida.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if (usuarioActual == null) return;
                if (usuarioActual.getContrato() == null) return;
                LocalDate fechaActual = LocalDate.now();
                LocalTime horaActual = LocalTime.now();
                RegistroAsistencias registroExistente = controlador.obtenerRegistroAsistenciaPorFecha(fechaActual, usuarioActual.getId());
                
                if (registroExistente == null){
                    JOptionPane.showMessageDialog(null, "Debe registrar la entrada primero", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if (registroExistente.getHoraSalida() != null){
                    registroExistente.setHoraSalida(Time.valueOf(horaActual));
                    try {
                        controlador.updateRegistroAsistencia(registroExistente);
                        JOptionPane.showMessageDialog(null, "Hora de salida actualizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        iniciarMarcaje();
                        return;
                    } catch (Exception ex) {
                        Logger.getLogger(PMarcajeAsistencia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                if (!fechaActual.toString().equals(registroExistente.getFecha().toString())){
                    JOptionPane.showMessageDialog(null, "Las fechas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    registroExistente.setHoraSalida(Time.valueOf(horaActual));
                    controlador.updateRegistroAsistencia(registroExistente);
                    JOptionPane.showMessageDialog(null, "Salida registrada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Registro día " + fechaActual + " completado", "Información", JOptionPane.INFORMATION_MESSAGE);

                    String correoDeAsistenciaHtml = ""
                            + "<h1>Marcaje de asistencia</h1>"
                            + "<span>Usuario: </span> <p>" + usuarioActual.getNombre() + "</p>"
                            + "<span>Fecha: </span> <p>" + fechaActual + "</p>"
                            + "<span>Hora de entrada registrada: </span> <p>" + registroExistente.getHoraEntrada() + "</p>"
                            + "<span>Hora de salida registrada: </span> <p>" + registroExistente.getHoraSalida() + "</p>"
                            + "";
                    
                    iniciarMarcaje();
                    //EnviarCorreo nuevoCorreo = new EnviarCorreo(usuarioActual.getCorreo(), "Marcaje", correoDeAsistenciaHtml);
                    //nuevoCorreo.enviarEmail();
                    //JOptionPane.showMessageDialog(null, "Se envió un correo a " + usuarioActual.getCorreo() + " con su asistencia", "Información", JOptionPane.INFORMATION_MESSAGE);
                 }catch(Exception e){
                     System.out.println(e);
                     JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
                 }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegistrarSalida.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistrarSalida.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    
    private void iniciarContadorReloj (){
        LocalDate fechaActual = LocalDate.now();
        String fechaFormateada = obtenerFechaFormateada(fechaActual);
        Timer timer = new Timer();
        lblFecha.setText("Fecha: " + fechaFormateada);
        DateTimeFormatter formatoHoras = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter formatoMinutos = DateTimeFormatter.ofPattern("mm");
        DateTimeFormatter formatoSegundos = DateTimeFormatter.ofPattern("ss");

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                LocalTime horaActual = LocalTime.now();
                int horas = Integer.parseInt(horaActual.format(formatoHoras));
                int minutos = Integer.parseInt(horaActual.format(formatoMinutos));
                int segundos = Integer.parseInt(horaActual.format(formatoSegundos));
                lblTiempo.setText("Hora: " + horas + ":" + String.format("%02d", minutos) + ":" + String.format("%02d", segundos));
            }
        };
        timer.scheduleAtFixedRate(tarea, 0, 1000);
    }
    
    private static String obtenerFechaFormateada(LocalDate fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fecha.format(formato);
        return fechaFormateada;
    }
    
    private void ajustarVentana (){
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
               width = getWidth();
               height = getHeight();
               
               int fontSize = (int) Math.floor(Math.min(width, height) * 0.02);
               
               // bienvenida
               lblBienvenida.setBounds((int) Math.round(width * 0.05), (int) Math.round(height * 0.05), width/3, height/10);
               lblBienvenida.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2.5)));
               
               // fecha
               lblFecha.setBounds((int) Math.round(width * 0.6), (int) Math.round(height * 0.02), width/3, height/12);
               lblFecha.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2)));
               
               // tiempo
               lblTiempo.setBounds((int) Math.round(width * 0.6), lblFecha.getHeight(), width / 3, height / 12);
               lblTiempo.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 2)));
               
               // panel marcaje
               pMarcaje.setBounds(0, lblTiempo.getY() + lblTiempo.getHeight() + 5, width, height - (lblTiempo.getY() + lblTiempo.getHeight() + 5));
               
               // panel marcaje labels
               for (Object[] labelMarcaje : listaLabels){
                    JLabel lblMarcaje = (JLabel) labelMarcaje[0];
                    double lblX = (double) labelMarcaje[3];
                    double lblY = (double) labelMarcaje[4];
                    int lblWidth = (int) labelMarcaje[5];
                    lblMarcaje.setBounds((int) Math.round(pMarcaje.getWidth() * lblX), (int) Math.round(pMarcaje.getHeight() * lblY), width / lblWidth, height / 10 );
                    lblMarcaje.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5)));
                }
               
               btnRegistrarIngreso.setBounds(
                       (int) Math.round(pMarcaje.getWidth() * 0.2),
                       (int) Math.round( pMarcaje.getHeight() - pMarcaje.getHeight() * 0.3 ),
                       width / 4,
                       height / 8
                    );
               
               lblRegistrarIngreso.setBounds(0,0, btnRegistrarIngreso.getWidth(), btnRegistrarIngreso.getHeight());
               lblRegistrarIngreso.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5) ));
               
               btnRegistrarSalida.setBounds(
                       (int) Math.round((pMarcaje.getWidth() - width / 4) - ( pMarcaje.getWidth() * 0.2)),
                       (int) Math.round( pMarcaje.getHeight() - pMarcaje.getHeight() * 0.3 ),
                       width / 4,
                       height / 8
                    ); 
               
               lblRegistrarSalida.setBounds(0,0, btnRegistrarSalida.getWidth(), btnRegistrarSalida.getHeight());
               lblRegistrarSalida.setFont(new Font("Segoe UI Symbol", 1, (int) Math.round(fontSize * 1.5)));
            }
        });
    }
    
    private void iniciarMarcaje(){
        if (usuarioActual == null) return;
        if (usuarioActual.getContrato() == null) return;
        String nombreUsuario = obtenerNombre(usuarioActual.getNombre());
        
        lblBienvenida.setText("Bienvenido " + nombreUsuario);
        lblJornadaHoraEntradaValue.setText(usuarioActual.getContrato().getJornadasDeTrabajo().getHoraEntrada().toString());
        lblJornadaHoraSalidaValue.setText(usuarioActual.getContrato().getJornadasDeTrabajo().getHoraSalida().toString());
        
        RegistroAsistencias ultimoRegistroUsuario = controlador.obtenerUltimoRegistroPorId(usuarioActual.getId());
        
        if (ultimoRegistroUsuario == null) return;
        
        LocalTime horaActual = LocalTime.now();
        LocalDate ultimaFecha = ultimoRegistroUsuario.getFecha();
        Time ultimoIngreso = ultimoRegistroUsuario.getHoraEntrada();
        Time ultimaSalida = ultimoRegistroUsuario.getHoraSalida();
        String tipoUltimaMarca = "-";
        Time ultimaHora;
        
        if (ultimoIngreso != null && ultimaSalida != null){
            ultimaHora = ultimaSalida;
            tipoUltimaMarca = "Salida";
            lblUltimaMarcaTipoValue.setForeground(Color.red);
        }else if (ultimoIngreso != null && ultimaSalida == null){
            ultimaHora = ultimoIngreso;
            tipoUltimaMarca = "Entrada";
            lblUltimaMarcaTipoValue.setForeground(Color.green);
        }else {
            ultimaHora = Time.valueOf(horaActual);
        }
        
        lblUltimaMarcaFechaValue.setText(ultimaFecha.toString());
        lblUltimaMarcaHoraValue.setText(ultimaHora.toString());
        lblUltimaMarcaTipoValue.setText(tipoUltimaMarca);
    }
    
    public static String obtenerNombre(String nombreCompleto) {
        int indiceEspacio = nombreCompleto.indexOf(" ");
        if (indiceEspacio != -1) {
            return nombreCompleto.substring(0, indiceEspacio + 2);
        }
        return nombreCompleto;
    }
    
    private String obtenerDiaDeLaSemana (DayOfWeek dia){
        switch (dia) {
            case MONDAY -> {
                return "Lunes";
            }
            case TUESDAY -> {
                return "Martes";
            }
            case WEDNESDAY -> {
                return "Miércoles";
            }
            case THURSDAY -> {
                return "Jueves";
            }
            case FRIDAY -> {
                return "Viernes";
            }
            case SATURDAY -> {
                return "Sábado";
            }
            case SUNDAY -> {
                return "Domingo";
            }
            default -> {
                return "";
            }
        }
    }
    
    public LocalDate obtenerUltimoLunes(LocalDate fecha) {
        while (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            fecha = fecha.minusDays(1);
        }
        return fecha;
    }
    
    public void verificarHorasTrabajadasEnLaSemana(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate ultimoLunes = obtenerUltimoLunes(fechaActual);
        List<RegistroAsistencias> listaRegistrosDesdeElLunes = controlador.obtenerRegistroAsistenciasPorIdYRango(
                usuarioActual.getId(),
                ultimoLunes,
                fechaActual
        );

        long totalMinutosTrabajados = 0;

        for (RegistroAsistencias reg : listaRegistrosDesdeElLunes) {
            if (reg.getHoraSalida() == null) continue;
            Time horaEntrada = reg.getHoraEntrada();
            Time horaSalida = reg.getHoraSalida();
            LocalTime entrada = horaEntrada.toLocalTime();
            LocalTime salida = horaSalida.toLocalTime();
            Duration duracion = Duration.between(entrada, salida);
            long minutosTrabajados = duracion.toMinutes();
            totalMinutosTrabajados += minutosTrabajados;
        }

        double totalHorasTrabajadas = totalMinutosTrabajados / 60.0;

        if (totalHorasTrabajadas > 45) {
            JOptionPane.showMessageDialog(null, "El usuario ha trabajado más de 45 horas esta semana", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String mensaje = String.format("Total horas trabajadas en la semana: %.2f horas", totalHorasTrabajadas);
            JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
