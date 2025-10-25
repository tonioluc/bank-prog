package com.entreprise.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import com.entreprise.dto.MouvementCourantDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "mouvement_courant")
public class MouvementCourant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmouvement")
    private Integer idMouvement;
    
    @Column(name = "montant", precision = 15, scale = 2)
    private BigDecimal montant;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtypemouvement", nullable = false)
    private TypeMouvement typeMouvement;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcompte", nullable = false)
    private CompteCourant compte;
    
    public MouvementCourant() {}
    
    public MouvementCourant(BigDecimal montant, TypeMouvement typeMouvement, CompteCourant compte) {
        this.montant = montant;
        this.typeMouvement = typeMouvement;
        this.compte = compte;
    }
    
    public Integer getIdMouvement() { return idMouvement; }
    public void setIdMouvement(Integer idMouvement) { this.idMouvement = idMouvement; }
    
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
    
    public TypeMouvement getTypeMouvement() { return typeMouvement; }
    public void setTypeMouvement(TypeMouvement typeMouvement) { this.typeMouvement = typeMouvement; }
    
    public CompteCourant getCompte() { return compte; }
    public void setCompte(CompteCourant compte) { this.compte = compte; }
    
    @Override
    public String toString() {
        return "MouvementCourant{idMouvement=" + idMouvement + ", montant=" + montant + "}";
    }

    public MouvementCourantDTO toDTO(){
        return new MouvementCourantDTO(idMouvement, montant, typeMouvement.toDTO(), compte.toDTO());
    }
}