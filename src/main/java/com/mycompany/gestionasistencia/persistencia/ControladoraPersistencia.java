/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.persistencia;

import com.mycompany.gestionasistencia.modelo.Cargos;
import com.mycompany.gestionasistencia.modelo.Contratos;
import com.mycompany.gestionasistencia.modelo.Departamentos;
import com.mycompany.gestionasistencia.modelo.DiasDeTrabajo;
import com.mycompany.gestionasistencia.modelo.Gerencias;
import com.mycompany.gestionasistencia.modelo.JornadaDias;
import com.mycompany.gestionasistencia.modelo.JornadasDeTrabajo;
import com.mycompany.gestionasistencia.modelo.RegistroAsistencias;
import com.mycompany.gestionasistencia.modelo.Roles;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author carlos
 */
public class ControladoraPersistencia {
    RolesJpaController rolesJpa = new RolesJpaController();
    CargosJpaController cargosJpa = new CargosJpaController();
    GerenciasJpaController gerenciasJpa = new GerenciasJpaController();
    DepartamentosJpaController departamentosJpa = new DepartamentosJpaController();
    JornadasDeTrabajoJpaController jornadasDeTrabajoJpa = new JornadasDeTrabajoJpaController();
    ContratosJpaController contratosJpa = new ContratosJpaController();
    UsuariosJpaController usuariosJpa = new UsuariosJpaController();
    RegistroAsistenciasJpaController registroAsistenciasJpa = new RegistroAsistenciasJpaController();
    DiasDeTrabajoJpaController diasDeTrabajoJpa = new DiasDeTrabajoJpaController();
    JornadaDiasJpaController jornadaDiasJpa = new JornadaDiasJpaController();

    public void crearRol(Roles nuevoRol) {
        rolesJpa.create(nuevoRol);
    }

    public void crearCargo(Cargos nuevoCargo) {
        cargosJpa.create(nuevoCargo);
    }

    public RegistroAsistencias obtenerRegistroAsistenciaPorUsuario(int id) {
        RegistroAsistencias registroAsistencias = registroAsistenciasJpa.obtenerRegistroAsistenciaPorUsuario(id);
        return registroAsistencias;
    }

    public void crearRegistroAsistencia(RegistroAsistencias registroAsistenciaActual) {
        registroAsistenciasJpa.create(registroAsistenciaActual);
    }

    public RegistroAsistencias obtenerRegistroAsistenciaPorFecha(LocalDate fechaActual, int idUsuario) {
        RegistroAsistencias registroAsistencia = registroAsistenciasJpa.obtenerRegistroAsistenciaPorFecha(fechaActual, idUsuario);
        return registroAsistencia;
    }

    public void updateRegistroAsistencia(RegistroAsistencias registroAsistencia) throws Exception {
        registroAsistenciasJpa.edit(registroAsistencia);
    }

    public JornadasDeTrabajo obtenerDiasDeTrabajo(int id) {
        JornadasDeTrabajo jornadaDeTrabajo = jornadasDeTrabajoJpa.obtenerJornadaDeTrabajoPorId(id);
        return jornadaDeTrabajo;
    }

    public List<Roles> obtenerRoles() {
        List<Roles> listaRoles = rolesJpa.findRolesEntities();
        return listaRoles;
    }
    
    public List<Cargos> obtenerCargos() {
        List<Cargos> listaCargos = cargosJpa.findCargosEntities();
        return listaCargos;
    }
    
    public List<Gerencias> obtenerGerencias() {
        List<Gerencias> listaGerencias = gerenciasJpa.findGerenciasEntities();
        return listaGerencias;
    }
    
    public List<Departamentos> obtenerDepartamentos() {
        List<Departamentos> listaDepartamentos = departamentosJpa.findDepartamentosEntities();
        return listaDepartamentos;
    }

    public Usuarios crearUsuario(Usuarios nuevoUsuario) {
        return usuariosJpa.create(nuevoUsuario);
    }
    
    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        Usuarios usuario = usuariosJpa.obtenerUsuarioPorCorreo(correo);
        return usuario;
    }

    public Usuarios obtenerUsuarioPorRut(String rut) {
        Usuarios usuario = usuariosJpa.obtenerUsuarioPorRut(rut);
        return usuario;
    }

    public Usuarios obtenerUsuarioPorTelefono(String telefono) {
        Usuarios usuario = usuariosJpa.obtenerUsuarioPorTelefono(telefono);
        return usuario;
    }

    public List<Usuarios> obtenerUsuarios() {
        List<Usuarios> listaUsuarios = usuariosJpa.findUsuariosEntities();
        return listaUsuarios;
    }

    public Usuarios obtenerUsuarioPorId(int id) {
        return usuariosJpa.findUsuarios(id);
    }
    
    public void eliminarUsuario(int id) throws NonexistentEntityException{
        usuariosJpa.destroy(id);
    }
    
    public void modificarUsuario(Usuarios usuario) throws Exception{
        usuariosJpa.edit(usuario);
    }

    public List<RegistroAsistencias> obtenerTodosLosRegistros(int id) {
        return registroAsistenciasJpa.obtenerTodosLosRegistrosPorId(id);
    }

    public RegistroAsistencias obtenerUltimoRegistroPorId(int id) {
        return registroAsistenciasJpa.obtenerUltimoRegistroPorId(id);
    }

    public List<RegistroAsistencias> obtenerRegistroAsistenciasPorRango(LocalDate fechaInicio, LocalDate fechaTermino) {
        return registroAsistenciasJpa.obtenerRegistroAsistenciasPorRango(fechaInicio, fechaTermino);
    }

    public JornadasDeTrabajo crearNuevaJornada(JornadasDeTrabajo nuevaJornada) {
        return jornadasDeTrabajoJpa.create(nuevaJornada);
    }

    public List<DiasDeTrabajo> obtenerTodosLosDiasDeTrabajo() {
        return diasDeTrabajoJpa.findDiasDeTrabajoEntities();
    }

    public void crearJornadaDias(JornadaDias nuevaJornadaDias) {
        jornadaDiasJpa.create(nuevaJornadaDias);
    }

    public void crearContrato(Contratos nuevoContrato) {
        contratosJpa.create(nuevoContrato);
    }

    public List<RegistroAsistencias> obtenerRegistroAsistenciasPorIdYRango(int id, LocalDate fechaInicio, LocalDate fechaTermino) {
        return registroAsistenciasJpa.obtenerRegistroAsistenciasPorIdYRango(id, fechaInicio, fechaTermino);
    }

    public void borrarJornadaDias(int idJornadaDia) throws NonexistentEntityException {
        jornadaDiasJpa.destroy(idJornadaDia);
    }

    public void modificarJornadaUsuario(JornadasDeTrabajo jornadaUsuario) throws Exception {
        jornadasDeTrabajoJpa.edit(jornadaUsuario);
    }

    public void eliminarContrato(int id) throws NonexistentEntityException {
        contratosJpa.destroy(id);
    }

    public void eliminarJornadaDeTrabajo(int id) throws NonexistentEntityException {
        jornadasDeTrabajoJpa.destroy(id);
    }
}
