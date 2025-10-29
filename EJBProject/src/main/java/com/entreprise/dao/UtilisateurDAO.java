package com.entreprise.dao;

import com.entreprise.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UtilisateurDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public Utilisateur create(Utilisateur utilisateur) {
        em.persist(utilisateur);
        return utilisateur;
    }
    
    public Utilisateur findById(Integer id) {
        return em.find(Utilisateur.class, id);
    }
    
    public Utilisateur findByIdWithDetails(Integer id) {
        TypedQuery<Utilisateur> query = em.createQuery(
            "SELECT u FROM Utilisateur u " +
            "JOIN FETCH u.role " +
            "JOIN FETCH u.direction " +
            "WHERE u.idUtilisateur = :id", 
            Utilisateur.class
        );
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Utilisateur findByNomAndPassword(String nom, String password) {
        TypedQuery<Utilisateur> query = em.createQuery(
            "SELECT u FROM Utilisateur u " +
            "JOIN FETCH u.role " +
            "JOIN FETCH u.direction " +
            "WHERE u.nom = :nom AND u.password = :password", 
            Utilisateur.class
        );
        query.setParameter("nom", nom);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Utilisateur> findAll() {
        return em.createQuery(
            "SELECT u FROM Utilisateur u " +
            "JOIN FETCH u.role " +
            "JOIN FETCH u.direction " +
            "ORDER BY u.nom", 
            Utilisateur.class
        ).getResultList();
    }
    
    public Utilisateur update(Utilisateur utilisateur) {
        return em.merge(utilisateur);
    }
    
    public void delete(Integer id) {
        Utilisateur utilisateur = findById(id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }
}