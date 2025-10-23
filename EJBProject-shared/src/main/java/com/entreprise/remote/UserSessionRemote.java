package com.entreprise.remote;

import com.entreprise.dto.DirectionDTO;
import com.entreprise.dto.RoleDTO;
import com.entreprise.dto.UtilisateurDTO;
import jakarta.ejb.Remote;

@Remote
public interface UserSessionRemote {
    void setUserData(RoleDTO role, DirectionDTO direction , UtilisateurDTO user);
    UtilisateurDTO getUser();
    RoleDTO getUserRole();
    DirectionDTO getUserDirection();
    void endSession();
}