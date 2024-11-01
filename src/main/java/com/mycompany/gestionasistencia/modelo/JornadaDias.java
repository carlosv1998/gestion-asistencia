package com.mycompany.gestionasistencia.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jornada_dias")
public class JornadaDias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_jornada", referencedColumnName = "id")
    private JornadasDeTrabajo jornadaDeTrabajo;
    @ManyToOne
    @JoinColumn(name = "id_dia", referencedColumnName = "id")
    private DiasDeTrabajo diasDeTrabajo;

    public JornadaDias() {
    }

    public JornadaDias(int id, JornadasDeTrabajo jornadaDeTrabajo, DiasDeTrabajo diasDeTrabajo) {
        this.id = id;
        this.jornadaDeTrabajo = jornadaDeTrabajo;
        this.diasDeTrabajo = diasDeTrabajo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JornadasDeTrabajo getJornadaDeTrabajo() {
        return jornadaDeTrabajo;
    }

    public void setJornadaDeTrabajo(JornadasDeTrabajo jornadaDeTrabajo) {
        this.jornadaDeTrabajo = jornadaDeTrabajo;
    }

    public DiasDeTrabajo getDiasDeTrabajo() {
        return diasDeTrabajo;
    }

    public void setDiasDeTrabajo(DiasDeTrabajo diasDeTrabajo) {
        this.diasDeTrabajo = diasDeTrabajo;
    }

    
    
}
