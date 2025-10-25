package com.entreprise.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.entreprise.dto.HistoriqueMouvementCourantDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "historique_mouvement_courant")
@IdClass(HistoriqueStatutId.class)
public class HistoriqueMouvementCourant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idmouvement")
    private MouvementCourant mouvement;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idstatut")
    private StatutMouvementCourant statut;
    
    @Column(name = "datedechangement")
    private LocalDate dateDeChangement;
    
    public HistoriqueMouvementCourant() {}
    
    public HistoriqueMouvementCourant(MouvementCourant mouvement, StatutMouvementCourant statut, LocalDate dateDeChangement) {
        this.mouvement = mouvement;
        this.statut = statut;
        this.dateDeChangement = dateDeChangement;
    }
    
    public MouvementCourant getMouvement() { return mouvement; }
    public void setMouvement(MouvementCourant mouvement) { this.mouvement = mouvement; }
    
    public StatutMouvementCourant getStatut() { return statut; }
    public void setStatut(StatutMouvementCourant statut) { this.statut = statut; }
    
    public LocalDate getDateDeChangement() { return dateDeChangement; }
    public void setDateDeChangement(LocalDate dateDeChangement) { this.dateDeChangement = dateDeChangement; }
    public HistoriqueMouvementCourantDTO toDTO(){
        return new HistoriqueMouvementCourantDTO(mouvement.toDTO(), statut.toDTO(), dateDeChangement);
    }
}