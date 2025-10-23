package com.entreprise.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MouvementCourantDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idMouvement;
    private BigDecimal montant;
    private TypeMouvementDTO typeMouvement;
    private CompteCourantDTO compte;
    
    public MouvementCourantDTO() {}
    
    public MouvementCourantDTO(Integer idMouvement, BigDecimal montant, 
                               TypeMouvementDTO typeMouvement, CompteCourantDTO compte) {
        this.idMouvement = idMouvement;
        this.montant = montant;
        this.typeMouvement = typeMouvement;
        this.compte = compte;
    }
    
    public Integer getIdMouvement() { return idMouvement; }
    public void setIdMouvement(Integer idMouvement) { this.idMouvement = idMouvement; }
    
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
    
    public TypeMouvementDTO getTypeMouvement() { return typeMouvement; }
    public void setTypeMouvement(TypeMouvementDTO typeMouvement) { this.typeMouvement = typeMouvement; }
    
    public CompteCourantDTO getCompte() { return compte; }
    public void setCompte(CompteCourantDTO compte) { this.compte = compte; }
    
    @Override
    public String toString() {
        return "MouvementCourantDTO{idMouvement=" + idMouvement + ", montant=" + montant + "}";
    }
}