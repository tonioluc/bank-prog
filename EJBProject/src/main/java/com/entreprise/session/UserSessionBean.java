package com.entreprise.session;

import com.entreprise.dto.DirectionDTO;
import com.entreprise.dto.RoleDTO;
import com.entreprise.dto.UtilisateurDTO;
import com.entreprise.remote.UserSessionRemote;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;

@Stateful
public class UserSessionBean implements UserSessionRemote {
    
    private UtilisateurDTO user;
    private RoleDTO userRole;
    private DirectionDTO userDirection;
    
    @Override
    public void setUserData(RoleDTO role, DirectionDTO direction , UtilisateurDTO user) {
        this.user = user;
        this.userRole = role;
        this.userDirection = direction;
        System.out.println("✅ Session créée - Role niveau: " + role.getNiveau() + 
                           ", Direction: " + direction.getType());
    }

    @Override
    public RoleDTO getUserRole() {
        return userRole;
    }
    
    @Override
    public DirectionDTO getUserDirection() {
        return userDirection;
    }
    
    @Remove
    @Override
    public void endSession() {
        System.out.println("✅ Session terminée");
        userRole = null;
        userDirection = null;
        user = null;
    }

    @Override
    public UtilisateurDTO getUser() {
        return user;
    }
}