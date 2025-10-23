package com.entreprise.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "compte_courant")
public class CompteCourant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcompte")
    private Integer idCompte;
    
    @Column(name = "solde", precision = 15, scale = 2)
    private BigDecimal solde;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idclient", nullable = false, unique = true)
    private Client client;
    
    public CompteCourant() {}
    
    public CompteCourant(BigDecimal solde, Client client) {
        this.solde = solde;
        this.client = client;
    }
    
    public Integer getIdCompte() { return idCompte; }
    public void setIdCompte(Integer idCompte) { this.idCompte = idCompte; }
    
    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }
    
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    
    @Override
    public String toString() {
        return "CompteCourant{idCompte=" + idCompte + ", solde=" + solde + ", client=" + client + "}";
    }
}