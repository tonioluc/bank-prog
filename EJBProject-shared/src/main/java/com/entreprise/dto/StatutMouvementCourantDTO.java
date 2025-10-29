package com.entreprise.dto;

import java.io.Serializable;

public class StatutMouvementCourantDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idStatut;
    
    private String description;
    
    public StatutMouvementCourantDTO() {}
    
    public StatutMouvementCourantDTO(String description) {
        this.description = description;
    }
    
    public Integer getIdStatut() { return idStatut; }
    public void setIdStatut(Integer idStatut) { this.idStatut = idStatut; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String toString() {
        return "StatutMouvementCourant{idStatut=" + idStatut + ", description=" + description + "}";
    }

    
}