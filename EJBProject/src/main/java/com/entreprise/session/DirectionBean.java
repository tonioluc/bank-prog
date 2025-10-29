/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entreprise.session;

import com.entreprise.dto.DirectionDTO;
import com.entreprise.entities.Direction;
import com.entreprise.remote.DirectionRemote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antonio
 */
public class DirectionBean implements DirectionRemote{
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    @Override
    public List<DirectionDTO> getAllDirection() {
        try {
            TypedQuery<Direction> query = em.createQuery(
                "SELECT t FROM Direction", 
                Direction.class
            );
            List<Direction> types = query.getResultList();
            
            List<DirectionDTO> dtos = new ArrayList<>();
            for (Direction type : types) {
                dtos.add(new DirectionDTO(type.getIdDirection(), type.getType()));
            }
            
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Erreur getAllTypesMouvement: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
