package com.entreprise.dao;

import java.util.List;

import com.entreprise.entities.StatutMouvementCourant;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class StatutMouvementCourantDAO {

    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;

    // 🔹 Créer un statut
    public StatutMouvementCourant create(StatutMouvementCourant statut) {
        em.persist(statut);
        return statut;
    }

    // 🔹 Trouver un statut par son ID
    public StatutMouvementCourant findById(Integer id) {
        return em.find(StatutMouvementCourant.class, id);
    }

    // 🔹 Récupérer tous les statuts
    public List<StatutMouvementCourant> findAll() {
        return em.createQuery(
                "SELECT s FROM StatutMouvementCourant s ORDER BY s.idStatut",
                StatutMouvementCourant.class)
                .getResultList();
    }

    // 🔹 Mettre à jour un statut
    public StatutMouvementCourant update(StatutMouvementCourant statut) {
        return em.merge(statut);
    }

    // 🔹 Supprimer un statut
    public void delete(Integer id) {
        StatutMouvementCourant statut = findById(id);
        if (statut != null) {
            em.remove(statut);
        }
    }

    // 🔹 Trouver un statut par sa description
    public StatutMouvementCourant findByDescription(String description) {
        List<StatutMouvementCourant> results = em.createQuery(
                "SELECT s FROM StatutMouvementCourant s WHERE s.description = :desc",
                StatutMouvementCourant.class)
                .setParameter("desc", description)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
