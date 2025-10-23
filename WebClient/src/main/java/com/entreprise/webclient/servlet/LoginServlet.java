package com.entreprise.webclient.servlet;

import com.entreprise.dto.UtilisateurDTO;
import com.entreprise.remote.AuthenticationRemote;
import com.entreprise.remote.UserSessionRemote;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import javax.naming.InitialContext;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String password = request.getParameter("password");
        
        System.out.println("📝 Login tentative: " + nom);
        
        try {
            InitialContext ic = new InitialContext();
            AuthenticationRemote authBean = (AuthenticationRemote) ic.lookup(
                "java:global/EJBProject-1.0-SNAPSHOT/AuthenticationBean!com.entreprise.remote.AuthenticationRemote"
            );
            
            // ⭐ Récupérer le DTO
            UtilisateurDTO userDTO = authBean.authenticate(nom, password);
            
            if (userDTO != null) {
                System.out.println("=== DEBUG LOGIN ===");
                System.out.println("✅ User ID: " + userDTO.getIdUtilisateur());
                System.out.println("✅ User nom: " + userDTO.getNom());
                System.out.println("✅ Role niveau: " + userDTO.getRole().getNiveau());
                System.out.println("✅ Direction: " + userDTO.getDirection().getType());
                System.out.println("==================");
                
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("user", userDTO);
                
                // Créer le Stateful Bean
                UserSessionRemote userSession = (UserSessionRemote) ic.lookup(
                    "java:global/EJBProject-1.0-SNAPSHOT/UserSessionBean!com.entreprise.remote.UserSessionRemote"
                );
                
                // Stocker les DTOs dans le Stateful Bean
                userSession.setUserData(userDTO.getRole(), userDTO.getDirection() , userDTO);
                httpSession.setAttribute("userSession", userSession);
                
                response.sendRedirect("dashboard.jsp");
                
            } else {
                request.setAttribute("error", "Nom ou mot de passe incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}