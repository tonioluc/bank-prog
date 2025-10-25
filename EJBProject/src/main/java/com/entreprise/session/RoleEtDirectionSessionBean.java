package com.entreprise.session;

import com.entreprise.dto.DirectionDTO;
import com.entreprise.dto.RoleDTO;
import com.entreprise.remote.RoleEtDirectionSessionRemote;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
import java.util.List;

@Stateful
public class RoleEtDirectionSessionBean implements RoleEtDirectionSessionRemote {
    private List<RoleDTO> userRole;
    private List<DirectionDTO> userDirection;
    
    @Override
    public void setUserData(List<RoleDTO> role, List<DirectionDTO> direction) {
        this.userRole = role;
        this.userDirection = direction;
    }

    @Override
    public List<RoleDTO> getUserRole() {
        return userRole;
    }
    
    @Override
    public List<DirectionDTO> getUserDirection() {
        return userDirection;
    }
    
    @Remove
    @Override
    public void endSession() {
        System.out.println("✅ Session terminée");
        userRole = null;
        userDirection = null;
    }
}