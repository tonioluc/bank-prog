package com.entreprise.dto;

import java.io.Serializable;

public class DirectionDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idDirection;
    private String type;
    
    // Constructeurs
    public DirectionDTO() {}
    
    public DirectionDTO(Integer idDirection, String type) {
        this.idDirection = idDirection;
        this.type = type;
    }
    
    // Getters et Setters
    public Integer getIdDirection() { 
        return idDirection; 
    }
    
    public void setIdDirection(Integer idDirection) { 
        this.idDirection = idDirection; 
    }
    
    public String getType() { 
        return type; 
    }
    
    public void setType(String type) { 
        this.type = type; 
    }
    
    @Override
    public String toString() {
        return "DirectionDTO{idDirection=" + idDirection + ", type=" + type + "}";
    }
}