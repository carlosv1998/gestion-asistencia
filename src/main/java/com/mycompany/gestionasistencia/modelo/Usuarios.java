package com.mycompany.gestionasistencia.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    private Roles rol;
    @ManyToOne
    @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    private Cargos cargo;
    @ManyToOne
    @JoinColumn(name = "id_gerencia", referencedColumnName = "id")
    private Gerencias gerencia;
    @ManyToOne
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private Departamentos departamento;
    private String rut;
    private String nombre;
    private int sueldo;
    private String telefono;
    private String correo;
    private String contra;
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
    @OneToOne(mappedBy = "usuario")
    private Contratos contrato;

    public Usuarios() {
    }

    public Usuarios(int id, Roles rol, Cargos cargo, Gerencias gerencia, Departamentos departamento, String rut, String nombre, int sueldo, String telefono, String correo, String contra, LocalDate fechaCreacion, Contratos contrato) {
        this.id = id;
        this.rol = rol;
        this.cargo = cargo;
        this.gerencia = gerencia;
        this.departamento = departamento;
        this.rut = rut;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.telefono = telefono;
        this.correo = correo;
        this.contra = contra;
        this.fechaCreacion = fechaCreacion;
        this.contrato = contrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public Gerencias getGerencia() {
        return gerencia;
    }

    public void setGerencia(Gerencias gerencia) {
        this.gerencia = gerencia;
    }

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departamento) {
        this.departamento = departamento;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Contratos getContrato() {
        return contrato;
    }

    public void setContrato(Contratos contrato) {
        this.contrato = contrato;
    }

    
    
    
    
}
