package com.entreprise.dto;

import java.io.Serializable;

public class ClientDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idClient;
    private String nom;
    
    public ClientDTO() {}
    
    public ClientDTO(Integer idClient, String nom) {
        this.idClient = idClient;
        this.nom = nom;
    }
    
    public Integer getIdClient() { return idClient; }
    public void setIdClient(Integer idClient) { this.idClient = idClient; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    @Override
    public String toString() {
        return "ClientDTO{idClient=" + idClient + ", nom=" + nom + "}";
    }
}