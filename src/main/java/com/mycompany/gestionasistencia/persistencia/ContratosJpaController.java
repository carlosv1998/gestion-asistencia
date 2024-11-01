/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionasistencia.persistencia;

import com.mycompany.gestionasistencia.modelo.Contratos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author carlos
 */
public class ContratosJpaController implements Serializable {

    public ContratosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ContratosJpaController(){
        emf = Persistence.createEntityManagerFactory("iciiPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contratos contratos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario = contratos.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                contratos.setUsuario(usuario);
            }
            em.persist(contratos);
            if (usuario != null) {
                Contratos oldContratoOfUsuario = usuario.getContrato();
                if (oldContratoOfUsuario != null) {
                    oldContratoOfUsuario.setUsuario(null);
                    oldContratoOfUsuario = em.merge(oldContratoOfUsuario);
                }
                usuario.setContrato(contratos);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contratos contratos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contratos persistentContratos = em.find(Contratos.class, contratos.getId());
            Usuarios usuarioOld = persistentContratos.getUsuario();
            Usuarios usuarioNew = contratos.getUsuario();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                contratos.setUsuario(usuarioNew);
            }
            contratos = em.merge(contratos);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setContrato(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Contratos oldContratoOfUsuario = usuarioNew.getContrato();
                if (oldContratoOfUsuario != null) {
                    oldContratoOfUsuario.setUsuario(null);
                    oldContratoOfUsuario = em.merge(oldContratoOfUsuario);
                }
                usuarioNew.setContrato(contratos);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = contratos.getId();
                if (findContratos(id) == null) {
                    throw new NonexistentEntityException("The contratos with id " + id + " no longer exists.");
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
            Contratos contratos;
            try {
                contratos = em.getReference(Contratos.class, id);
                contratos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratos with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuario = contratos.getUsuario();
            if (usuario != null) {
                usuario.setContrato(null);
                usuario = em.merge(usuario);
            }
            em.remove(contratos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contratos> findContratosEntities() {
        return findContratosEntities(true, -1, -1);
    }

    public List<Contratos> findContratosEntities(int maxResults, int firstResult) {
        return findContratosEntities(false, maxResults, firstResult);
    }

    private List<Contratos> findContratosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contratos.class));
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

    public Contratos findContratos(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contratos.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contratos> rt = cq.from(Contratos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
