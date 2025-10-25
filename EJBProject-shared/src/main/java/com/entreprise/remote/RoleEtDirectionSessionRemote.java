package com.entreprise.remote;

import com.entreprise.dto.DirectionDTO;
import com.entreprise.dto.RoleDTO;
import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface RoleEtDirectionSessionRemote {
    void setUserData(List<RoleDTO> role, List<DirectionDTO> direction);
    List<RoleDTO> getUserRole();
    List<DirectionDTO> getUserDirection();
    void endSession();
}