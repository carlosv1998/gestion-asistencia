package com.mycompany.gestionasistencia.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contratos")
public class Contratos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuarios usuario;
    @ManyToOne
    @JoinColumn(name = "id_jornada", referencedColumnName = "id")
    private JornadasDeTrabajo jornadasDeTrabajo;

    public Contratos() {
    }

    public Contratos(int id, Usuarios usuario, JornadasDeTrabajo jornadasDeTrabajo) {
        this.id = id;
        this.usuario = usuario;
        this.jornadasDeTrabajo = jornadasDeTrabajo;
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

    public JornadasDeTrabajo getJornadasDeTrabajo() {
        return jornadasDeTrabajo;
    }

    public void setJornadasDeTrabajo(JornadasDeTrabajo jornadasDeTrabajo) {
        this.jornadasDeTrabajo = jornadasDeTrabajo;
    }
}
