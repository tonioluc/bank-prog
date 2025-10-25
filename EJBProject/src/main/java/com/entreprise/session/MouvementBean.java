package com.entreprise.session;

import com.entreprise.dto.*;
import com.entreprise.entities.*;
import com.entreprise.remote.MouvementRemote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MouvementBean implements MouvementRemote {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    @Override
    public List<TypeMouvementDTO> getAllTypesMouvement() {
        try {
            TypedQuery<TypeMouvement> query = em.createQuery(
                "SELECT t FROM TypeMouvement t ORDER BY t.description", 
                TypeMouvement.class
            );
            List<TypeMouvement> types = query.getResultList();
            
            List<TypeMouvementDTO> dtos = new ArrayList<>();
            for (TypeMouvement type : types) {
                dtos.add(new TypeMouvementDTO(type.getIdTypeMouvement(), type.getDescription()));
            }
            
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Erreur getAllTypesMouvement: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<CompteCourantDTO> getAllComptes() {
        try {
            TypedQuery<CompteCourant> query = em.createQuery(
                "SELECT c FROM CompteCourant c JOIN FETCH c.client ORDER BY c.client.nom", 
                CompteCourant.class
            );
            List<CompteCourant> comptes = query.getResultList();
            
            List<CompteCourantDTO> dtos = new ArrayList<>();
            for (CompteCourant compte : comptes) {
                ClientDTO clientDTO = new ClientDTO(
                    compte.getClient().getIdClient(),
                    compte.getClient().getNom()
                );
                
                dtos.add(new CompteCourantDTO(
                    compte.getIdCompte(),
                    compte.getSolde(),
                    clientDTO
                ));
            }
            
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Erreur getAllComptes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public MouvementCourant ajoutDansTableMouv(BigDecimal montant, Integer idTypeMouvement, Integer idCompte){
        try {
            // Récupérer le type de mouvement
            TypeMouvement type = em.find(TypeMouvement.class, idTypeMouvement);
            if (type == null) {
                throw new Exception("Type de mouvement introuvable");
            }
            
            // Récupérer le compte
            CompteCourant compte = em.find(CompteCourant.class, idCompte);
            if (compte == null) {
                throw new Exception("Compte introuvable");
            }
            
            // Créer le mouvement
            MouvementCourant mouvement = new MouvementCourant(montant, type, compte);
            em.persist(mouvement);
            
            return mouvement;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur ajouterMouvement: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public HistoriqueMouvementCourantDTO ajoutDansTableHistoriqueMouvementCourant(MouvementCourant mouvementCourant){
        try {
            //par défaut "en attente"
            StatutMouvementCourant statutMouvementCourant = em.find(StatutMouvementCourant.class, 1);
            if (statutMouvementCourant == null) {
                throw new Exception("Type de mouvement introuvable");
            }

            HistoriqueMouvementCourant historiqueMouvement = new HistoriqueMouvementCourant(mouvementCourant, statutMouvementCourant, LocalDate.now());
            em.persist(historiqueMouvement);
            
            return historiqueMouvement.toDTO();
            
        } catch (Exception e) {
            System.err.println("❌ Erreur ajouterMouvement: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    
    @Override
    public MouvementCourantDTO ajouterMouvement(BigDecimal montant, Integer idTypeMouvement, Integer idCompte) {
        //Ajout dans la table mouvement
        MouvementCourant mouvementCourant = ajoutDansTableMouv(montant, idTypeMouvement, idCompte);
        //mila ajoutena anaty table historique
        ajoutDansTableHistoriqueMouvementCourant(mouvementCourant);
        return mouvementCourant.toDTO();
    }
    
    @Override
    public List<MouvementCourantDTO> getAllMouvements() {
        try {
            TypedQuery<MouvementCourant> query = em.createQuery(
                "SELECT m FROM MouvementCourant m " +
                "JOIN FETCH m.typeMouvement " +
                "JOIN FETCH m.compte c " +
                "JOIN FETCH c.client " +
                "ORDER BY m.idMouvement DESC", 
                MouvementCourant.class
            );
            List<MouvementCourant> mouvements = query.getResultList();
            
            List<MouvementCourantDTO> dtos = new ArrayList<>();
            for (MouvementCourant m : mouvements) {
                ClientDTO clientDTO = new ClientDTO(
                    m.getCompte().getClient().getIdClient(),
                    m.getCompte().getClient().getNom()
                );
                
                CompteCourantDTO compteDTO = new CompteCourantDTO(
                    m.getCompte().getIdCompte(),
                    m.getCompte().getSolde(),
                    clientDTO
                );
                
                TypeMouvementDTO typeDTO = new TypeMouvementDTO(
                    m.getTypeMouvement().getIdTypeMouvement(),
                    m.getTypeMouvement().getDescription()
                );
                
                dtos.add(new MouvementCourantDTO(
                    m.getIdMouvement(),
                    m.getMontant(),
                    typeDTO,
                    compteDTO
                ));
            }
            
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Erreur getAllMouvements: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}