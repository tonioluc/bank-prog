package com.entreprise.dao;

import com.entreprise.entities.Client;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ClientDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public Client create(Client client) {
        em.persist(client);
        return client;
    }
    
    public Client findById(Integer id) {
        return em.find(Client.class, id);
    }
    
    public List<Client> findAll() {
        return em.createQuery("SELECT c FROM Client c ORDER BY c.nom", Client.class)
                 .getResultList();
    }
    
    public Client update(Client client) {
        return em.merge(client);
    }
    
    public void delete(Integer id) {
        Client client = findById(id);
        if (client != null) {
            em.remove(client);
        }
    }
    
    public Client findByNom(String nom) {
        TypedQuery<Client> query = em.createQuery(
            "SELECT c FROM Client c WHERE c.nom = :nom", 
            Client.class
        );
        query.setParameter("nom", nom);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}