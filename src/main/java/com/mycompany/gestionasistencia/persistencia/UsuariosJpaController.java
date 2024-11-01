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
import com.mycompany.gestionasistencia.modelo.Contratos;
import com.mycompany.gestionasistencia.modelo.Usuarios;
import com.mycompany.gestionasistencia.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author carlos
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public UsuariosJpaController(){
        emf = Persistence.createEntityManagerFactory("iciiPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Usuarios create(Usuarios usuarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contratos contrato = usuarios.getContrato();
            if (contrato != null) {
                contrato = em.getReference(contrato.getClass(), contrato.getId());
                usuarios.setContrato(contrato);
            }
            em.persist(usuarios);
            if (contrato != null) {
                Usuarios oldUsuarioOfContrato = contrato.getUsuario();
                if (oldUsuarioOfContrato != null) {
                    oldUsuarioOfContrato.setContrato(null);
                    oldUsuarioOfContrato = em.merge(oldUsuarioOfContrato);
                }
                contrato.setUsuario(usuarios);
                contrato = em.merge(contrato);
            }
            em.getTransaction().commit();
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Contratos contratoOld = persistentUsuarios.getContrato();
            Contratos contratoNew = usuarios.getContrato();
            if (contratoNew != null) {
                contratoNew = em.getReference(contratoNew.getClass(), contratoNew.getId());
                usuarios.setContrato(contratoNew);
            }
            usuarios = em.merge(usuarios);
            if (contratoOld != null && !contratoOld.equals(contratoNew)) {
                contratoOld.setUsuario(null);
                contratoOld = em.merge(contratoOld);
            }
            if (contratoNew != null && !contratoNew.equals(contratoOld)) {
                Usuarios oldUsuarioOfContrato = contratoNew.getUsuario();
                if (oldUsuarioOfContrato != null) {
                    oldUsuarioOfContrato.setContrato(null);
                    oldUsuarioOfContrato = em.merge(oldUsuarioOfContrato);
                }
                contratoNew.setUsuario(usuarios);
                contratoNew = em.merge(contratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Contratos contrato = usuarios.getContrato();
            if (contrato != null) {
                contrato.setUsuario(null);
                contrato = em.merge(contrato);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.correo = :correo");
            query.setParameter("correo", correo);
            return (Usuarios) query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Usuarios obtenerUsuarioPorRut(String rut) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.rut = :rut");
            query.setParameter("rut", rut);
            return (Usuarios) query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Usuarios obtenerUsuarioPorTelefono(String telefono) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.telefono = :telefono");
            query.setParameter("telefono", telefono);
            return (Usuarios) query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
