package com.entreprise.dto;

import java.io.Serializable;

public class TypeMouvementDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idTypeMouvement;
    private String description;
    
    public TypeMouvementDTO() {}
    
    public TypeMouvementDTO(Integer idTypeMouvement, String description) {
        this.idTypeMouvement = idTypeMouvement;
        this.description = description;
    }
    
    public Integer getIdTypeMouvement() { return idTypeMouvement; }
    public void setIdTypeMouvement(Integer idTypeMouvement) { this.idTypeMouvement = idTypeMouvement; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public String toString() {
        return "TypeMouvementDTO{idTypeMouvement=" + idTypeMouvement + ", description=" + description + "}";
    }
}