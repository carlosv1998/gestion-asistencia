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
@Table(name = "departamentos")
public class Departamentos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_gerencia", referencedColumnName = "id")
    private Gerencias gerencia;
    private String departamento;

    public Departamentos() {
    }

    public Departamentos(int id, Gerencias gerencia, String departamento) {
        this.id = id;
        this.gerencia = gerencia;
        this.departamento = departamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gerencias getGerencia() {
        return gerencia;
    }

    public void setGerencia(Gerencias gerencia) {
        this.gerencia = gerencia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    
}
