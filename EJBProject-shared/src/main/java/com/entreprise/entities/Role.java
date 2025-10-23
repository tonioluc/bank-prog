package com.entreprise.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    
    //private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrole")
    private Integer idRole;
    
    @Column(name = "niveau")
    private Integer niveau;
    
    public Role() {}
    
    public Role(Integer niveau) {
        this.niveau = niveau;
    }
    
    public Integer getIdRole() { return idRole; }
    public void setIdRole(Integer idRole) { this.idRole = idRole; }
    
    public Integer getNiveau() { return niveau; }
    public void setNiveau(Integer niveau) { this.niveau = niveau; }
    
    @Override
    public String toString() {
        return "Role{id=" + idRole + ", niveau=" + niveau + "}";
    }
}