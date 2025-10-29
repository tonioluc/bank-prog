package com.entreprise.webclient.servlet;

import com.entreprise.devises.dto.ConversionResultDTO;
import com.entreprise.devises.dto.DeviseDTO;
import com.entreprise.devises.remote.DeviseConversionRemote;
import com.entreprise.dto.CompteCourantDTO;
import com.entreprise.dto.MouvementCourantDTO;
import com.entreprise.dto.TypeMouvementDTO;
import com.entreprise.remote.MouvementRemote;
import com.entreprise.webclient.util.DeviseRestClient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties; // ← Pour utiliser la classe Properties
import javax.naming.Context; // ← Pour la constante Context.*
import javax.naming.InitialContext;

@WebServlet(name = "AjouterMouvementServlet", urlPatterns = { "/ajouterMouvement" })
public class AjouterMouvementServlet extends HttpServlet {

    private void loadData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InitialContext ic = new InitialContext();
        MouvementRemote mouvementBean = (MouvementRemote) ic.lookup(
                "java:global/EJBProject-1.0-SNAPSHOT/MouvementBean!com.entreprise.remote.MouvementRemote");

        // Récupérer les types de mouvement
        List<TypeMouvementDTO> types = mouvementBean.getAllTypesMouvement();
        request.setAttribute("typesMouvement", types);

        // Récupérer les comptes
        List<CompteCourantDTO> comptes = mouvementBean.getAllComptes();
        request.setAttribute("comptes", comptes);

        // ⭐ Charger les devises
        // Properties props = new Properties();
        // props.put(Context.INITIAL_CONTEXT_FACTORY,
        // "com.sun.enterprise.naming.SerialInitContextFactory");
        // props.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
        // props.put(Context.STATE_FACTORIES,
        // "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        // // ⚠️ C’est ici que tu indiques le GlassFish Docker
        // props.put("org.omg.CORBA.ORBInitialHost", "localhost"); // si ton Docker
        // tourne localement
        // props.put("org.omg.CORBA.ORBInitialPort", "3701"); // port exposé par ton
        // docker (-p 3701:3700)

        // // Création du contexte JNDI distant
        // InitialContext ic2 = new InitialContext(props);

        // DeviseConversionRemote deviseBean = (DeviseConversionRemote) ic2.lookup(
        // "java:global/DevisesEJB-1.0-SNAPSHOT/DeviseConversionBean!com.entreprise.devises.remote.DeviseConversionRemote");
        // List<DeviseDTO> devises = deviseBean.getAllDevises();

        // ⭐ Appel REST au lieu de JNDI
        System.out.println("Appel Web service");
        List<DeviseDTO> devises = DeviseRestClient.getAllDevises();
        request.setAttribute("devises", devises);

    }

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
            loadData(request, response);
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
            String devise = request.getParameter("devise");

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
            montant = conversionEnAriary(montant, devise);
            System.out.println("Valeur montant après conversion = " + montant);
            MouvementRemote mouvementBean = (MouvementRemote) ic.lookup(
                    "java:global/EJBProject-1.0-SNAPSHOT/MouvementBean!com.entreprise.remote.MouvementRemote");

            MouvementCourantDTO mouvement = mouvementBean.ajouterMouvement(montant, idTypeMouvement, idCompte);

            if (mouvement != null) {
                request.setAttribute("success", "Mouvement ajouté avec succès !");
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
            try {
                loadData(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            request.getRequestDispatcher("ajouterMouvement.jsp").forward(request, response);
        }
    }

    private BigDecimal conversionEnAriary(BigDecimal montant, String devise) throws Exception {
        // ⚙️ Configuration du contexte JNDI distant (vers le Docker)
        // Properties props = new Properties();
        // props.put(Context.INITIAL_CONTEXT_FACTORY,
        // "com.sun.enterprise.naming.SerialInitContextFactory");
        // props.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
        // props.put(Context.STATE_FACTORIES,
        // "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        // // ⚠️ C’est ici que tu indiques le GlassFish Docker
        // props.put("org.omg.CORBA.ORBInitialHost", "localhost"); // si ton Docker
        // tourne localement
        // props.put("org.omg.CORBA.ORBInitialPort", "3701"); // port exposé par ton
        // docker (-p 3701:3700)

        // // Création du contexte JNDI distant
        // InitialContext ic = new InitialContext(props);

        // if (!"AR".equalsIgnoreCase(devise)) {
        // String jndiName =
        // "java:global/DevisesEJB-1.0-SNAPSHOT/DeviseConversionBean!com.entreprise.devises.remote.DeviseConversionRemote";
        // System.out.println("🔍 Lookup distant sur : " + jndiName);

        // DeviseConversionRemote deviseBean = (DeviseConversionRemote)
        // ic.lookup(jndiName);

        // ConversionResultDTO conversion = deviseBean.convertirEnAriary(devise,
        // montant);

        // if (!conversion.isSuccess()) {
        // throw new Exception("Erreur conversion: " + conversion.getMessage());
        // }

        // System.out.println("✅ Conversion réussie = " +
        // conversion.getMontantAriary());
        // return conversion.getMontantAriary();
        // }

        // return montant;
        if ("AR".equalsIgnoreCase(devise)) {
            return montant;
        }

        // ⭐ Appel REST au lieu de JNDI
        System.out.println("🔍 Appel REST pour conversion: " + devise + " " + montant);

        ConversionResultDTO conversion = DeviseRestClient.convertirEnAriary(devise, montant);

        if (!conversion.isSuccess()) {
            throw new Exception("Erreur conversion: " + conversion.getMessage());
        }

        System.out.println("✅ Conversion réussie = " + conversion.getMontantAriary());
        return conversion.getMontantAriary();
    }
}