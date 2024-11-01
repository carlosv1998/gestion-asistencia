/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.persistencia;

import com.mycompany.gestionasistencia.modelo.JornadaDias;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.gestionasistencia.modelo.JornadasDeTrabajo;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author carlos
 */
public class JornadaDiasJpaController implements Serializable {

    public JornadaDiasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public JornadaDiasJpaController(){
        emf = Persistence.createEntityManagerFactory("iciiPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JornadaDias jornadaDias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JornadasDeTrabajo jornadaDeTrabajo = jornadaDias.getJornadaDeTrabajo();
            if (jornadaDeTrabajo != null) {
                jornadaDeTrabajo = em.getReference(jornadaDeTrabajo.getClass(), jornadaDeTrabajo.getId());
                jornadaDias.setJornadaDeTrabajo(jornadaDeTrabajo);
            }
            em.persist(jornadaDias);
            if (jornadaDeTrabajo != null) {
                jornadaDeTrabajo.getJornadaDias().add(jornadaDias);
                jornadaDeTrabajo = em.merge(jornadaDeTrabajo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JornadaDias jornadaDias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JornadaDias persistentJornadaDias = em.find(JornadaDias.class, jornadaDias.getId());
            JornadasDeTrabajo jornadaDeTrabajoOld = persistentJornadaDias.getJornadaDeTrabajo();
            JornadasDeTrabajo jornadaDeTrabajoNew = jornadaDias.getJornadaDeTrabajo();
            if (jornadaDeTrabajoNew != null) {
                jornadaDeTrabajoNew = em.getReference(jornadaDeTrabajoNew.getClass(), jornadaDeTrabajoNew.getId());
                jornadaDias.setJornadaDeTrabajo(jornadaDeTrabajoNew);
            }
            jornadaDias = em.merge(jornadaDias);
            if (jornadaDeTrabajoOld != null && !jornadaDeTrabajoOld.equals(jornadaDeTrabajoNew)) {
                jornadaDeTrabajoOld.getJornadaDias().remove(jornadaDias);
                jornadaDeTrabajoOld = em.merge(jornadaDeTrabajoOld);
            }
            if (jornadaDeTrabajoNew != null && !jornadaDeTrabajoNew.equals(jornadaDeTrabajoOld)) {
                jornadaDeTrabajoNew.getJornadaDias().add(jornadaDias);
                jornadaDeTrabajoNew = em.merge(jornadaDeTrabajoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = jornadaDias.getId();
                if (findJornadaDias(id) == null) {
                    throw new NonexistentEntityException("The jornadaDias with id " + id + " no longer exists.");
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
            JornadaDias jornadaDias;
            try {
                jornadaDias = em.getReference(JornadaDias.class, id);
                jornadaDias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornadaDias with id " + id + " no longer exists.", enfe);
            }
            JornadasDeTrabajo jornadaDeTrabajo = jornadaDias.getJornadaDeTrabajo();
            if (jornadaDeTrabajo != null) {
                jornadaDeTrabajo.getJornadaDias().remove(jornadaDias);
                jornadaDeTrabajo = em.merge(jornadaDeTrabajo);
            }
            em.remove(jornadaDias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JornadaDias> findJornadaDiasEntities() {
        return findJornadaDiasEntities(true, -1, -1);
    }

    public List<JornadaDias> findJornadaDiasEntities(int maxResults, int firstResult) {
        return findJornadaDiasEntities(false, maxResults, firstResult);
    }

    private List<JornadaDias> findJornadaDiasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JornadaDias.class));
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

    public JornadaDias findJornadaDias(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JornadaDias.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaDiasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JornadaDias> rt = cq.from(JornadaDias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
