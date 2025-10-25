package com.entreprise.entities;

import java.io.Serializable;

import com.entreprise.dto.UtilisateurDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {
    
    //private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idutilisateur")
    private Integer idUtilisateur;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "password")
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idrole", nullable = false)
    private Role role;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddirection", nullable = false)
    private Direction direction;
    
    public Utilisateur() {}
    
    public Integer getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Integer idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }
    
    @Override
    public String toString() {
        return "Utilisateur{id=" + idUtilisateur + ", nom=" + nom + "}";
    }

    public UtilisateurDTO toDTO(){
        return new UtilisateurDTO(idUtilisateur, nom, role.toDTO(), direction.toDTO());
    }
}