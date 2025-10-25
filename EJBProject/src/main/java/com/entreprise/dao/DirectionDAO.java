package com.entreprise.dao;

import com.entreprise.entities.Direction;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DirectionDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public Direction create(Direction direction) {
        em.persist(direction);
        return direction;
    }
    
    public Direction findById(Integer id) {
        return em.find(Direction.class, id);
    }
    
    public List<Direction> findAll() {
        return em.createQuery("SELECT d FROM Direction d ORDER BY d.type", Direction.class)
                 .getResultList();
    }
    
    public Direction update(Direction direction) {
        return em.merge(direction);
    }
    
    public void delete(Integer id) {
        Direction direction = findById(id);
        if (direction != null) {
            em.remove(direction);
        }
    }
}