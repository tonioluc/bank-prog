package com.entreprise.entities;

import java.io.Serializable;
import java.util.Objects;

public class HistoriqueStatutId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer mouvement;
    private Integer statut;
    
    public HistoriqueStatutId() {}
    
    public HistoriqueStatutId(Integer mouvement, Integer statut) {
        this.mouvement = mouvement;
        this.statut = statut;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoriqueStatutId that = (HistoriqueStatutId) o;
        return Objects.equals(mouvement, that.mouvement) && Objects.equals(statut, that.statut);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mouvement, statut);
    }
}