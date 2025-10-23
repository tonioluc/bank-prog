package com.entreprise.session;

import com.entreprise.dto.DirectionDTO;
import com.entreprise.dto.RoleDTO;
import com.entreprise.dto.UtilisateurDTO;
import com.entreprise.entities.Utilisateur;
import com.entreprise.remote.AuthenticationRemote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class AuthenticationBean implements AuthenticationRemote {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    @Override
    public UtilisateurDTO authenticate(String nom, String password) {
        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u " +
                "JOIN FETCH u.role r " +
                "JOIN FETCH u.direction d " +
                "WHERE u.nom = :nom AND u.password = :password",
                Utilisateur.class
            );
            query.setParameter("nom", nom);
            query.setParameter("password", password);
            
            Utilisateur user = query.getSingleResult();
            
            System.out.println("✅ User trouvé: " + user.getNom());
            System.out.println("✅ Role: " + user.getRole().getNiveau());
            System.out.println("✅ Direction: " + user.getDirection().getType());
            
            // ⭐ CONVERSION Entité → DTO
            RoleDTO roleDTO = new RoleDTO(
                user.getRole().getIdRole(),
                user.getRole().getNiveau()
            );
            
            DirectionDTO directionDTO = new DirectionDTO(
                user.getDirection().getIdDirection(),
                user.getDirection().getType()
            );
            
            UtilisateurDTO dto = new UtilisateurDTO(
                user.getIdUtilisateur(),
                user.getNom(),
                roleDTO,
                directionDTO
            );
            
            System.out.println("✅ DTO créé: " + dto);
            
            return dto;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur authentification: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}