package com.mycompany.gestionasistencia.controlador;

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
import com.mycompany.gestionasistencia.persistencia.ControladoraPersistencia;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.util.List;

public class Controlador {
    ControladoraPersistencia controladoraPersistencia = new ControladoraPersistencia();
    
    public void crearRol (Roles nuevoRol){
        controladoraPersistencia.crearRol(nuevoRol);
    }
    
    public void crearCargo (Cargos nuevoCargo){
        controladoraPersistencia.crearCargo(nuevoCargo);
    }
    
    public Usuarios obtenerUsuarioPorCorreo (String correo){
        Usuarios usuario = controladoraPersistencia.obtenerUsuarioPorCorreo(correo);
        return usuario;
    }

    public RegistroAsistencias obtenerRegistroAsistencia(int id) {
        RegistroAsistencias registroAsistencia = controladoraPersistencia.obtenerRegistroAsistenciaPorUsuario(id);
        return registroAsistencia;
    }

    public void crearRegistroAsistencia(RegistroAsistencias registroAsistenciaActual) {
        controladoraPersistencia.crearRegistroAsistencia(registroAsistenciaActual);
    }

    public RegistroAsistencias obtenerRegistroAsistenciaPorFecha(LocalDate fechaActual, int idUsuario) {
        RegistroAsistencias registroAsistencia = controladoraPersistencia.obtenerRegistroAsistenciaPorFecha(fechaActual, idUsuario);
        return registroAsistencia;
    }

    public void updateRegistroAsistencia(RegistroAsistencias registroAsistencia) throws Exception {
        controladoraPersistencia.updateRegistroAsistencia(registroAsistencia);
    }

    public void obtenerDiasDeTrabajo(int id) {
        controladoraPersistencia.obtenerDiasDeTrabajo(id);
    }
    
    public List<Roles> obtenerRoles(){
        List<Roles> listaRoles = controladoraPersistencia.obtenerRoles();
        return listaRoles;
    }
    
    public List<Cargos> obtenerCargos(){
        List<Cargos> listaCargos = controladoraPersistencia.obtenerCargos();
        return listaCargos;
    }
    
    public List<Gerencias> obtenerGerencias(){
        List<Gerencias> listaGerencias = controladoraPersistencia.obtenerGerencias();
        return listaGerencias;
    }
    
    public List<Departamentos> obtenerDepartamentos(){
        List<Departamentos> listaDepartamentos = controladoraPersistencia.obtenerDepartamentos();
        return listaDepartamentos;
    }

    public Usuarios crearUsuario(Usuarios nuevoUsuario) {
        return controladoraPersistencia.crearUsuario(nuevoUsuario);
    }

    public Usuarios obtenerUsuarioPorRut(String rut) {
        Usuarios usuario = controladoraPersistencia.obtenerUsuarioPorRut(rut);
        return usuario;
    }

    public Usuarios obtenerUsuarioPorTelefono(String telefono) {
        Usuarios usuario = controladoraPersistencia.obtenerUsuarioPorTelefono(telefono);
        return usuario;
    }

    public List<Usuarios> obtenerUsuarios() {
        List<Usuarios> listaUsuarios = controladoraPersistencia.obtenerUsuarios();
        return listaUsuarios;
    }

    public Usuarios obtenerUsuarioPorId(int id) {
        Usuarios usuario = controladoraPersistencia.obtenerUsuarioPorId(id);
        return usuario;
    }

    public void eliminarUsuario(int id) throws NonexistentEntityException {
        controladoraPersistencia.eliminarUsuario(id);
    }
    
    public void modificarUsuario(Usuarios usuario) throws Exception{
        controladoraPersistencia.modificarUsuario(usuario);
    }

    public List<RegistroAsistencias> obtenerTodosLosRegistros(int id) {
        return controladoraPersistencia.obtenerTodosLosRegistros(id);
    }
    
    public RegistroAsistencias obtenerUltimoRegistroPorId(int id){
        return controladoraPersistencia.obtenerUltimoRegistroPorId(id);
    }

    public List<RegistroAsistencias> obtenerRegistroAsistenciasPorRango(LocalDate fechaInicio, LocalDate fechaTermino) {
        return controladoraPersistencia.obtenerRegistroAsistenciasPorRango(fechaInicio, fechaTermino);
    }

    public JornadasDeTrabajo crearJornada(JornadasDeTrabajo nuevaJornada) {
        return controladoraPersistencia.crearNuevaJornada(nuevaJornada);
    }

    public List<DiasDeTrabajo> obtenerTodosLosDiasDeTrabajo() {
        return controladoraPersistencia.obtenerTodosLosDiasDeTrabajo();
    }

    public void crearJornadaDias(JornadaDias nuevaJornadaDias) {
        controladoraPersistencia.crearJornadaDias(nuevaJornadaDias);
    }

    public void crearContrato(Contratos nuevoContrato) {
        controladoraPersistencia.crearContrato(nuevoContrato);
    }

    public List<RegistroAsistencias> obtenerRegistroAsistenciasPorIdYRango(int id, LocalDate fechaInicio, LocalDate fechaTermino) {
        return controladoraPersistencia.obtenerRegistroAsistenciasPorIdYRango(id, fechaInicio, fechaTermino);
    }

    public void borrarJornadaDias(int idJornadaDia) throws NonexistentEntityException {
        controladoraPersistencia.borrarJornadaDias(idJornadaDia);
    }

    public void modificarJornadaUsuario(JornadasDeTrabajo jornadaUsuario) throws Exception {
        controladoraPersistencia.modificarJornadaUsuario(jornadaUsuario);
    }

    public void eliminarContrato(int id) throws NonexistentEntityException {
        controladoraPersistencia.eliminarContrato(id);
    }

    public void eliminarJornadaDeTrabajo(int id) throws NonexistentEntityException {
        controladoraPersistencia.eliminarJornadaDeTrabajo(id);
    }
}
