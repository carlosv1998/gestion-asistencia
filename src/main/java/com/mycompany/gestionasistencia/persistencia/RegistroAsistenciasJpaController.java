/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.persistencia;

import com.mycompany.gestionasistencia.modelo.RegistroAsistencias;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author carlos
 */
public class RegistroAsistenciasJpaController implements Serializable {

    public RegistroAsistenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public RegistroAsistenciasJpaController(){
        emf = Persistence.createEntityManagerFactory("iciiPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroAsistencias registroAsistencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(registroAsistencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroAsistencias registroAsistencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            registroAsistencias = em.merge(registroAsistencias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = registroAsistencias.getId();
                if (findRegistroAsistencias(id) == null) {
                    throw new NonexistentEntityException("The registroAsistencias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroAsistencias registroAsistencias;
            try {
                registroAsistencias = em.getReference(RegistroAsistencias.class, id);
                registroAsistencias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroAsistencias with id " + id + " no longer exists.", enfe);
            }
            em.remove(registroAsistencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroAsistencias> findRegistroAsistenciasEntities() {
        return findRegistroAsistenciasEntities(true, -1, -1);
    }

    public List<RegistroAsistencias> findRegistroAsistenciasEntities(int maxResults, int firstResult) {
        return findRegistroAsistenciasEntities(false, maxResults, firstResult);
    }

    private List<RegistroAsistencias> findRegistroAsistenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroAsistencias.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RegistroAsistencias findRegistroAsistencias(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroAsistencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroAsistenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroAsistencias> rt = cq.from(RegistroAsistencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public RegistroAsistencias obtenerRegistroAsistenciaPorUsuario(int id) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM RegistroAsistencias r WHERE r.usuario.id = :id");
            query.setParameter("id", id);
            return (RegistroAsistencias) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public RegistroAsistencias obtenerRegistroAsistenciaPorFecha(LocalDate fechaActual, int idUsuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM RegistroAsistencias r WHERE r.fecha = :fecha AND r.usuario.id = :idUsuario");
            query.setParameter("fecha", fechaActual);
            query.setParameter("idUsuario", idUsuario);
            return (RegistroAsistencias) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<RegistroAsistencias> obtenerTodosLosRegistrosPorId (int id){
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM RegistroAsistencias r WHERE r.usuario.id = :id");
            query.setParameter("id", id);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public RegistroAsistencias obtenerUltimoRegistroPorId(int id) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM RegistroAsistencias r WHERE r.usuario.id = :id ORDER BY r.fecha DESC");
            query.setParameter("id", id);
            query.setMaxResults(1);
            return (RegistroAsistencias) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<RegistroAsistencias> obtenerRegistroAsistenciasPorRango (LocalDate fechaInicio, LocalDate fechaTermino){
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM RegistroAsistencias r WHERE r.fecha >= :fechaInicio AND r.fecha <= :fechaTermino ORDER BY r.fecha DESC");
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaTermino", fechaTermino);
            return query.getResultList();
        }catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
    
    public List<RegistroAsistencias> obtenerRegistroAsistenciasPorIdYRango (int id, LocalDate fechaInicio, LocalDate fechaTermino){
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM RegistroAsistencias r WHERE r.usuario.id = :id AND r.fecha >= :fechaInicio AND r.fecha <= :fechaTermino ORDER BY r.fecha DESC");
            query.setParameter("id", id);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaTermino", fechaTermino);
            return query.getResultList();
        }catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}
