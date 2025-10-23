package com.entreprise.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclient")
    private Integer idClient;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    
    public Client() {}
    
    public Client(String nom) {
        this.nom = nom;
    }
    
    public Integer getIdClient() { return idClient; }
    public void setIdClient(Integer idClient) { this.idClient = idClient; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    @Override
    public String toString() {
        return "Client{idClient=" + idClient + ", nom=" + nom + "}";
    }
}