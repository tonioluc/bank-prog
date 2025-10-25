package com.entreprise.entities;

import java.io.Serializable;

import com.entreprise.dto.TypeMouvementDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "type_mouvement")
public class TypeMouvement implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtypemouvement")
    private Integer idTypeMouvement;
    
    @Column(name = "description", length = 50)
    private String description;
    
    public TypeMouvement() {}
    
    public TypeMouvement(String description) {
        this.description = description;
    }
    
    public Integer getIdTypeMouvement() { return idTypeMouvement; }
    public void setIdTypeMouvement(Integer idTypeMouvement) { this.idTypeMouvement = idTypeMouvement; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public String toString() {
        return "TypeMouvement{idTypeMouvement=" + idTypeMouvement + ", description=" + description + "}";
    }

    public TypeMouvementDTO toDTO(){
        return new TypeMouvementDTO(idTypeMouvement, description);
    }
}