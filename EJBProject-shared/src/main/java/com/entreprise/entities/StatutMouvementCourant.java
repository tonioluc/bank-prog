package com.entreprise.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "statut_mouvement_courant")
public class StatutMouvementCourant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstatut")
    private Integer idStatut;
    
    @Column(name = "description", length = 50)
    private String description;
    
    public StatutMouvementCourant() {}
    
    public StatutMouvementCourant(String description) {
        this.description = description;
    }
    
    public Integer getIdStatut() { return idStatut; }
    public void setIdStatut(Integer idStatut) { this.idStatut = idStatut; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public String toString() {
        return "StatutMouvementCourant{idStatut=" + idStatut + ", description=" + description + "}";
    }
}