package com.entreprise.dao;

import java.util.List;

import com.entreprise.entities.HistoriqueMouvementCourant;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class HistoriqueMouvementCourantDAO {
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;

    public HistoriqueMouvementCourant create(HistoriqueMouvementCourant HistoriqueMouvementCourant) {
        em.persist(HistoriqueMouvementCourant);
        return HistoriqueMouvementCourant;
    }

    public HistoriqueMouvementCourant findById(Integer id) {
        return em.find(HistoriqueMouvementCourant.class, id);
    }

    public List<HistoriqueMouvementCourant> findAll() {
        return em
                .createQuery("SELECT d FROM HistoriqueMouvementCourant d ORDER BY d.type",
                        HistoriqueMouvementCourant.class)
                .getResultList();
    }

    public HistoriqueMouvementCourant update(HistoriqueMouvementCourant HistoriqueMouvementCourant) {
        return em.merge(HistoriqueMouvementCourant);
    }

    public void delete(Integer id) {
        HistoriqueMouvementCourant HistoriqueMouvementCourant = findById(id);
        if (HistoriqueMouvementCourant != null) {
            em.remove(HistoriqueMouvementCourant);
        }
    }

    @SuppressWarnings("unchecked")
    public List<HistoriqueMouvementCourant> findByStatut(Integer idStatut) {
        return em.createNativeQuery(
                "SELECT h.idmouvement, h.idstatut, h.datedechangement\n" + //
                                        "FROM historique_mouvement_courant h\n" + //
                                        "JOIN (\n" + //
                                        "    SELECT idmouvement, MAX(datedechangement) AS max_date\n" + //
                                        "    FROM historique_mouvement_courant\n" + //
                                        "    GROUP BY idmouvement\n" + //
                                        ") last_change\n" + //
                                        "  ON h.idmouvement = last_change.idmouvement\n" + //
                                        " AND h.datedechangement = last_change.max_date\n" + //
                                        "WHERE h.idstatut = ?;\n" + //
                                        "",
                HistoriqueMouvementCourant.class)
                .setParameter(1, idStatut)
                .getResultList();
    }

}
