package com.entreprise.dto;

import java.io.Serializable;

public class RoleDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idRole;
    private Integer niveau;
    
    // Constructeurs
    public RoleDTO() {}
    
    public RoleDTO(Integer idRole, Integer niveau) {
        this.idRole = idRole;
        this.niveau = niveau;
    }
    
    // Getters et Setters
    public Integer getIdRole() { 
        return idRole; 
    }
    
    public void setIdRole(Integer idRole) { 
        this.idRole = idRole; 
    }
    
    public Integer getNiveau() { 
        return niveau; 
    }
    
    public void setNiveau(Integer niveau) { 
        this.niveau = niveau; 
    }
    
    @Override
    public String toString() {
        return "RoleDTO{idRole=" + idRole + ", niveau=" + niveau + "}";
    }
}