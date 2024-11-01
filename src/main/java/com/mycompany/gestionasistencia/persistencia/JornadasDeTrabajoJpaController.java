/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.gestionasistencia.modelo.JornadaDias;
import com.mycompany.gestionasistencia.modelo.JornadasDeTrabajo;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author carlos
 */
public class JornadasDeTrabajoJpaController implements Serializable {

    public JornadasDeTrabajoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public JornadasDeTrabajoJpaController(){
        emf = Persistence.createEntityManagerFactory("iciiPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public JornadasDeTrabajo create(JornadasDeTrabajo jornadasDeTrabajo) {
        if (jornadasDeTrabajo.getJornadaDias() == null) {
            jornadasDeTrabajo.setJornadaDias(new ArrayList<JornadaDias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<JornadaDias> attachedJornadaDias = new ArrayList<JornadaDias>();
            for (JornadaDias jornadaDiasJornadaDiasToAttach : jornadasDeTrabajo.getJornadaDias()) {
                jornadaDiasJornadaDiasToAttach = em.getReference(jornadaDiasJornadaDiasToAttach.getClass(), jornadaDiasJornadaDiasToAttach.getId());
                attachedJornadaDias.add(jornadaDiasJornadaDiasToAttach);
            }
            jornadasDeTrabajo.setJornadaDias(attachedJornadaDias);
            em.persist(jornadasDeTrabajo);
            for (JornadaDias jornadaDiasJornadaDias : jornadasDeTrabajo.getJornadaDias()) {
                JornadasDeTrabajo oldJornadaDeTrabajoOfJornadaDiasJornadaDias = jornadaDiasJornadaDias.getJornadaDeTrabajo();
                jornadaDiasJornadaDias.setJornadaDeTrabajo(jornadasDeTrabajo);
                jornadaDiasJornadaDias = em.merge(jornadaDiasJornadaDias);
                if (oldJornadaDeTrabajoOfJornadaDiasJornadaDias != null) {
                    oldJornadaDeTrabajoOfJornadaDiasJornadaDias.getJornadaDias().remove(jornadaDiasJornadaDias);
                    oldJornadaDeTrabajoOfJornadaDiasJornadaDias = em.merge(oldJornadaDeTrabajoOfJornadaDiasJornadaDias);
                }
            }
            em.getTransaction().commit();
            return jornadasDeTrabajo;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JornadasDeTrabajo jornadasDeTrabajo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JornadasDeTrabajo persistentJornadasDeTrabajo = em.find(JornadasDeTrabajo.class, jornadasDeTrabajo.getId());
            List<JornadaDias> jornadaDiasOld = persistentJornadasDeTrabajo.getJornadaDias();
            List<JornadaDias> jornadaDiasNew = jornadasDeTrabajo.getJornadaDias();
            List<JornadaDias> attachedJornadaDiasNew = new ArrayList<JornadaDias>();
            for (JornadaDias jornadaDiasNewJornadaDiasToAttach : jornadaDiasNew) {
                jornadaDiasNewJornadaDiasToAttach = em.getReference(jornadaDiasNewJornadaDiasToAttach.getClass(), jornadaDiasNewJornadaDiasToAttach.getId());
                attachedJornadaDiasNew.add(jornadaDiasNewJornadaDiasToAttach);
            }
            jornadaDiasNew = attachedJornadaDiasNew;
            jornadasDeTrabajo.setJornadaDias(jornadaDiasNew);
            jornadasDeTrabajo = em.merge(jornadasDeTrabajo);
            for (JornadaDias jornadaDiasOldJornadaDias : jornadaDiasOld) {
                if (!jornadaDiasNew.contains(jornadaDiasOldJornadaDias)) {
                    jornadaDiasOldJornadaDias.setJornadaDeTrabajo(null);
                    jornadaDiasOldJornadaDias = em.merge(jornadaDiasOldJornadaDias);
                }
            }
            for (JornadaDias jornadaDiasNewJornadaDias : jornadaDiasNew) {
                if (!jornadaDiasOld.contains(jornadaDiasNewJornadaDias)) {
                    JornadasDeTrabajo oldJornadaDeTrabajoOfJornadaDiasNewJornadaDias = jornadaDiasNewJornadaDias.getJornadaDeTrabajo();
                    jornadaDiasNewJornadaDias.setJornadaDeTrabajo(jornadasDeTrabajo);
                    jornadaDiasNewJornadaDias = em.merge(jornadaDiasNewJornadaDias);
                    if (oldJornadaDeTrabajoOfJornadaDiasNewJornadaDias != null && !oldJornadaDeTrabajoOfJornadaDiasNewJornadaDias.equals(jornadasDeTrabajo)) {
                        oldJornadaDeTrabajoOfJornadaDiasNewJornadaDias.getJornadaDias().remove(jornadaDiasNewJornadaDias);
                        oldJornadaDeTrabajoOfJornadaDiasNewJornadaDias = em.merge(oldJornadaDeTrabajoOfJornadaDiasNewJornadaDias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = jornadasDeTrabajo.getId();
                if (findJornadasDeTrabajo(id) == null) {
                    throw new NonexistentEntityException("The jornadasDeTrabajo with id " + id + " no longer exists.");
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
            JornadasDeTrabajo jornadasDeTrabajo;
            try {
                jornadasDeTrabajo = em.getReference(JornadasDeTrabajo.class, id);
                jornadasDeTrabajo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornadasDeTrabajo with id " + id + " no longer exists.", enfe);
            }
            List<JornadaDias> jornadaDias = jornadasDeTrabajo.getJornadaDias();
            for (JornadaDias jornadaDiasJornadaDias : jornadaDias) {
                jornadaDiasJornadaDias.setJornadaDeTrabajo(null);
                jornadaDiasJornadaDias = em.merge(jornadaDiasJornadaDias);
            }
            em.remove(jornadasDeTrabajo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JornadasDeTrabajo> findJornadasDeTrabajoEntities() {
        return findJornadasDeTrabajoEntities(true, -1, -1);
    }

    public List<JornadasDeTrabajo> findJornadasDeTrabajoEntities(int maxResults, int firstResult) {
        return findJornadasDeTrabajoEntities(false, maxResults, firstResult);
    }

    private List<JornadasDeTrabajo> findJornadasDeTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JornadasDeTrabajo.class));
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

    public JornadasDeTrabajo findJornadasDeTrabajo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JornadasDeTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadasDeTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JornadasDeTrabajo> rt = cq.from(JornadasDeTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public JornadasDeTrabajo obtenerJornadaDeTrabajoPorId(int id) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT j FROM JornadasDeTrabajo j WHERE j.usuario.id = :id");
            query.setParameter("id", id);
            return (JornadasDeTrabajo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
