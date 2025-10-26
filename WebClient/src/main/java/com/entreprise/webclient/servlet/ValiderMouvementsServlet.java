package com.entreprise.webclient.servlet;

import com.entreprise.dto.MouvementCourantDTO;
import com.entreprise.remote.MouvementRemote;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import javax.naming.InitialContext;

@WebServlet(name = "ValiderMouvementsServlet", urlPatterns = {"/validerMouvements"})
public class ValiderMouvementsServlet extends HttpServlet {
    
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
            
            // Récupérer les mouvements en attente
            List<MouvementCourantDTO> mouvementsEnAttente = mouvementBean.getMouvementsEnAttente();
            request.setAttribute("mouvements", mouvementsEnAttente);
            
            // Afficher la page
            request.getRequestDispatcher("validerMouvements.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("validerMouvements.jsp").forward(request, response);
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
            String action = request.getParameter("action");
            String idMouvementStr = request.getParameter("idMouvement");
            
            if (action == null || idMouvementStr == null) {
                throw new Exception("Paramètres manquants");
            }
            
            Integer idMouvement = Integer.parseInt(idMouvementStr);
            
            InitialContext ic = new InitialContext();
            MouvementRemote mouvementBean = (MouvementRemote) ic.lookup(
                "java:global/EJBProject-1.0-SNAPSHOT/MouvementBean!com.entreprise.remote.MouvementRemote"
            );
            
            boolean success = false;
            String message = "";
            
            if ("valider".equals(action)) {
                success = mouvementBean.validerOuRefuserMouvementCourant(idMouvement,2);
                message = success ? "Mouvement #" + idMouvement + " validé avec succès !" 
                                  : "Erreur lors de la validation";
            } else if ("refuser".equals(action)) {
                success = mouvementBean.validerOuRefuserMouvementCourant(idMouvement,3);
                message = success ? "Mouvement #" + idMouvement + " refusé" 
                                  : "Erreur lors du refus";
            }
            
            if (success) {
                request.setAttribute("success", message);
            } else {
                request.setAttribute("error", message);
            }
            
            // Recharger la liste
            List<MouvementCourantDTO> mouvementsEnAttente = mouvementBean.getMouvementsEnAttente();
            request.setAttribute("mouvements", mouvementsEnAttente);
            
            request.getRequestDispatcher("validerMouvements.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("validerMouvements.jsp").forward(request, response);
        }
    }
}