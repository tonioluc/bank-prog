package com.entreprise.remote;

import com.entreprise.dto.UtilisateurDTO;
import jakarta.ejb.Remote;

@Remote
public interface AuthenticationRemote {
    UtilisateurDTO authenticate(String nom, String password);
}