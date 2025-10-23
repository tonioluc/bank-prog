package com.entreprise.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompteCourantDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idCompte;
    private BigDecimal solde;
    private ClientDTO client;
    
    public CompteCourantDTO() {}
    
    public CompteCourantDTO(Integer idCompte, BigDecimal solde, ClientDTO client) {
        this.idCompte = idCompte;
        this.solde = solde;
        this.client = client;
    }
    
    public Integer getIdCompte() { return idCompte; }
    public void setIdCompte(Integer idCompte) { this.idCompte = idCompte; }
    
    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }
    
    public ClientDTO getClient() { return client; }
    public void setClient(ClientDTO client) { this.client = client; }
    
    @Override
    public String toString() {
        return "CompteCourantDTO{idCompte=" + idCompte + ", solde=" + solde + ", client=" + client + "}";
    }
}