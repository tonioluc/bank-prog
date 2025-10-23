package com.entreprise.webclient.servlet;

import com.entreprise.dto.CompteCourantDTO;
import com.entreprise.dto.MouvementCourantDTO;
import com.entreprise.dto.TypeMouvementDTO;
import com.entreprise.remote.MouvementRemote;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;

@WebServlet(name = "AjouterMouvementServlet", urlPatterns = {"/ajouterMouvement"})
public class AjouterMouvementServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Vérifier la session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            InitialContext ic = new InitialContext();
            MouvementRemote mouvementBean = (MouvementRemote) ic.lookup(
                "java:global/EJBProject-1.0-SNAPSHOT/MouvementBean!com.entreprise.remote.MouvementRemote"
            );
            
            // Récupérer les types de mouvement
            List<TypeMouvementDTO> types = mouvementBean.getAllTypesMouvement();
            request.setAttribute("typesMouvement", types);
            
            // Récupérer les comptes
            List<CompteCourantDTO> comptes = mouvementBean.getAllComptes();
            request.setAttribute("comptes", comptes);
            
            // Afficher le formulaire
            request.getRequestDispatcher("ajouterMouvement.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("ajouterMouvement.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Vérifier la session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Récupérer les paramètres
            String montantStr = request.getParameter("montant");
            String idTypeMouvementStr = request.getParameter("idTypeMouvement");
            String idCompteStr = request.getParameter("idCompte");
            
            // Validation
            if (montantStr == null || montantStr.trim().isEmpty() ||
                idTypeMouvementStr == null || idCompteStr == null) {
                throw new Exception("Tous les champs sont obligatoires");
            }
            
            BigDecimal montant = new BigDecimal(montantStr);
            Integer idTypeMouvement = Integer.parseInt(idTypeMouvementStr);
            Integer idCompte = Integer.parseInt(idCompteStr);
            
            // Appeler l'EJB
            InitialContext ic = new InitialContext();
            MouvementRemote mouvementBean = (MouvementRemote) ic.lookup(
                "java:global/EJBProject-1.0-SNAPSHOT/MouvementBean!com.entreprise.remote.MouvementRemote"
            );
            
            MouvementCourantDTO mouvement = mouvementBean.ajouterMouvement(montant, idTypeMouvement, idCompte);
            
            if (mouvement != null) {
                request.setAttribute("success", "Mouvement ajouté avec succès ! ID: " + mouvement.getIdMouvement());
            } else {
                request.setAttribute("error", "Erreur lors de l'ajout du mouvement");
            }
            
            // Recharger les listes
            List<TypeMouvementDTO> types = mouvementBean.getAllTypesMouvement();
            request.setAttribute("typesMouvement", types);
            
            List<CompteCourantDTO> comptes = mouvementBean.getAllComptes();
            request.setAttribute("comptes", comptes);
            
            request.getRequestDispatcher("ajouterMouvement.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            
            // Recharger les listes pour le formulaire
            try {
                InitialContext ic = new InitialContext();
                MouvementRemote mouvementBean = (MouvementRemote) ic.lookup(
                    "java:global/EJBProject-1.0-SNAPSHOT/MouvementBean!com.entreprise.remote.MouvementRemote"
                );
                
                List<TypeMouvementDTO> types = mouvementBean.getAllTypesMouvement();
                request.setAttribute("typesMouvement", types);
                
                List<CompteCourantDTO> comptes = mouvementBean.getAllComptes();
                request.setAttribute("comptes", comptes);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            request.getRequestDispatcher("ajouterMouvement.jsp").forward(request, response);
        }
    }
}