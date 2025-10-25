package com.entreprise.dao;

import com.entreprise.entities.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RoleDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public Role create(Role role) {
        em.persist(role);
        return role;
    }
    
    public Role findById(Integer id) {
        return em.find(Role.class, id);
    }
    
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r ORDER BY r.niveau", Role.class)
                 .getResultList();
    }
    
    public Role update(Role role) {
        return em.merge(role);
    }
    
    public void delete(Integer id) {
        Role role = findById(id);
        if (role != null) {
            em.remove(role);
        }
    }
}