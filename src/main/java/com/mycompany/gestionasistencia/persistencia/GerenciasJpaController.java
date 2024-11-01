/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.persistencia;

import com.mycompany.gestionasistencia.modelo.Gerencias;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author carlos
 */
public class GerenciasJpaController implements Serializable {

    public GerenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public GerenciasJpaController(){
        emf = Persistence.createEntityManagerFactory("iciiPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gerencias gerencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(gerencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gerencias gerencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            gerencias = em.merge(gerencias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = gerencias.getId();
                if (findGerencias(id) == null) {
                    throw new NonexistentEntityException("The gerencias with id " + id + " no longer exists.");
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
            Gerencias gerencias;
            try {
                gerencias = em.getReference(Gerencias.class, id);
                gerencias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gerencias with id " + id + " no longer exists.", enfe);
            }
            em.remove(gerencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gerencias> findGerenciasEntities() {
        return findGerenciasEntities(true, -1, -1);
    }

    public List<Gerencias> findGerenciasEntities(int maxResults, int firstResult) {
        return findGerenciasEntities(false, maxResults, firstResult);
    }

    private List<Gerencias> findGerenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gerencias.class));
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

    public Gerencias findGerencias(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gerencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getGerenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gerencias> rt = cq.from(Gerencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
