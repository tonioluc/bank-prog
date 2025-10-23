package com.entreprise.dto;

import java.io.Serializable;

public class UtilisateurDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idUtilisateur;
    private String nom;
    private RoleDTO role;
    private DirectionDTO direction;
    
    // Constructeurs
    public UtilisateurDTO() {}
    
    public UtilisateurDTO(Integer idUtilisateur, String nom, 
                          RoleDTO role, DirectionDTO direction) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.role = role;
        this.direction = direction;
    }
    
    // Getters et Setters
    public Integer getIdUtilisateur() { 
        return idUtilisateur; 
    }
    
    public void setIdUtilisateur(Integer idUtilisateur) { 
        this.idUtilisateur = idUtilisateur; 
    }
    
    public String getNom() { 
        return nom; 
    }
    
    public void setNom(String nom) { 
        this.nom = nom; 
    }
    
    public RoleDTO getRole() { 
        return role; 
    }
    
    public void setRole(RoleDTO role) { 
        this.role = role; 
    }
    
    public DirectionDTO getDirection() { 
        return direction; 
    }
    
    public void setDirection(DirectionDTO direction) { 
        this.direction = direction; 
    }
    
    @Override
    public String toString() {
        return "UtilisateurDTO{idUtilisateur=" + idUtilisateur + 
               ", nom=" + nom + ", role=" + role + ", direction=" + direction + "}";
    }
}