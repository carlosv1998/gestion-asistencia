package com.mycompany.gestionasistencia.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registro_asistencias")
public class RegistroAsistencias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuarios usuario;
    @Column(name = "hora_entrada")
    private Time horaEntrada;
    @Column(name = "hora_salida")
    private Time horaSalida;
    private LocalDate fecha;
    @Column(name = "entrada_especial")
    private Time entradaEspecial;
    @Column(name = "salida_especial")
    private Time salidaEspecial;

    public RegistroAsistencias() {
    }

    public RegistroAsistencias(int id, Usuarios usuario, Time horaEntrada, Time horaSalida, LocalDate fecha, Time entradaEspecial, Time salidaEspecial) {
        this.id = id;
        this.usuario = usuario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.fecha = fecha;
        this.entradaEspecial = entradaEspecial;
        this.salidaEspecial = salidaEspecial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Time getEntradaEspecial() {
        return entradaEspecial;
    }

    public void setEntradaEspecial(Time entradaEspecial) {
        this.entradaEspecial = entradaEspecial;
    }

    public Time getSalidaEspecial() {
        return salidaEspecial;
    }

    public void setSalidaEspecial(Time salidaEspecial) {
        this.salidaEspecial = salidaEspecial;
    }
    
    
}
