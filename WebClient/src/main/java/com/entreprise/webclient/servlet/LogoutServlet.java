package com.entreprise.webclient.servlet;

import com.entreprise.remote.UserSessionRemote;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Récupérer et détruire le Stateful Bean
            UserSessionRemote userSession = (UserSessionRemote) session.getAttribute("userSession");
            if (userSession != null) {
                try {
                    userSession.endSession(); // Appelle @Remove
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            session.invalidate();
        }
        
        response.sendRedirect("login.jsp");
    }
}