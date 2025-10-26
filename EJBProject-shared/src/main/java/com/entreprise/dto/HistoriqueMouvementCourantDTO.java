package com.entreprise.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HistoriqueMouvementCourantDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private MouvementCourantDTO mouvement;
    
    private StatutMouvementCourantDTO statut;
    
    private LocalDateTime dateDeChangement;
    
    public HistoriqueMouvementCourantDTO() {}
    
    public HistoriqueMouvementCourantDTO(MouvementCourantDTO mouvement, StatutMouvementCourantDTO statut, LocalDateTime dateDeChangement) {
        this.mouvement = mouvement;
        this.statut = statut;
        this.dateDeChangement = dateDeChangement;
    }
    
    public MouvementCourantDTO getMouvement() { return mouvement; }
    public void setMouvement(MouvementCourantDTO mouvement) { this.mouvement = mouvement; }
    
    public StatutMouvementCourantDTO getStatut() { return statut; }
    public void setStatut(StatutMouvementCourantDTO statut) { this.statut = statut; }
    
    public LocalDateTime getDateDeChangement() { return dateDeChangement; }
    public void setDateDeChangement(LocalDateTime dateDeChangement) { this.dateDeChangement = dateDeChangement; }
}