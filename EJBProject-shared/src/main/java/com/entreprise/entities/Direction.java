package com.entreprise.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "direction")
public class Direction implements Serializable {
    
    //private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddirection")
    private Integer idDirection;
    
    @Column(name = "type")
    private String type;
    
    public Direction() {}
    
    public Direction(String type) {
        this.type = type;
    }
    
    public Integer getIdDirection() { return idDirection; }
    public void setIdDirection(Integer idDirection) { this.idDirection = idDirection; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    @Override
    public String toString() {
        return "Direction{id=" + idDirection + ", type=" + type + "}";
    }
}