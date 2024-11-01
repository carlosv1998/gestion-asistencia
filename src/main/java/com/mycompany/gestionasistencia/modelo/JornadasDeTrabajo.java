package com.mycompany.gestionasistencia.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "jornadas_de_trabajo")
public class JornadasDeTrabajo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "hora_entrada")
    private Time horaEntrada;
    @Column(name = "hora_salida")
    private Time horaSalida;
    @OneToMany(mappedBy = "jornadaDeTrabajo", cascade = CascadeType.ALL)
    private List<JornadaDias> jornadaDias;

    public JornadasDeTrabajo() {
    }

    public JornadasDeTrabajo(int id, Time horaEntrada, Time horaSalida, List<JornadaDias> jornadaDias) {
        this.id = id;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.jornadaDias = jornadaDias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<JornadaDias> getJornadaDias() {
        return jornadaDias;
    }

    public void setJornadaDias(List<JornadaDias> jornadaDias) {
        this.jornadaDias = jornadaDias;
    }
}
