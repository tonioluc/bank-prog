package com.entreprise.devises.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DeviseDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String code;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal cours;
    
    public DeviseDTO() {}
    
    public DeviseDTO(String code, LocalDate dateDebut, LocalDate dateFin, BigDecimal cours) {
        this.code = code;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cours = cours;
    }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public BigDecimal getCours() { return cours; }
    public void setCours(BigDecimal cours) { this.cours = cours; }
    
    @Override
    public String toString() {
        return code + " = " + cours + " Ar";
    }
}