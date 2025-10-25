package com.entreprise.remote;

import com.entreprise.dto.CompteCourantDTO;
import com.entreprise.dto.MouvementCourantDTO;
import com.entreprise.dto.TypeMouvementDTO;
import jakarta.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface MouvementRemote {
    
    // Récupérer tous les types de mouvement
    List<TypeMouvementDTO> getAllTypesMouvement();
    
    // Récupérer tous les comptes
    List<CompteCourantDTO> getAllComptes();
    
    // Ajouter un mouvement
    MouvementCourantDTO ajouterMouvement(BigDecimal montant, Integer idTypeMouvement, Integer idCompte);
    
    // Récupérer tous les mouvements
    List<MouvementCourantDTO> getAllMouvements();

    List<MouvementCourantDTO> getMouvementsEnAttente();
}