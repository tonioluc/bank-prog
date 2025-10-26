<%@page import="com.entreprise.dto.*"%>
<%@page import="com.entreprise.devises.dto.*"%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Vérifier la session
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    UtilisateurDTO userDTO = (UtilisateurDTO) session.getAttribute("user");
    
    @SuppressWarnings("unchecked")
    List<TypeMouvementDTO> typesMouvement = (List<TypeMouvementDTO>) request.getAttribute("typesMouvement");
    
    @SuppressWarnings("unchecked")
    List<CompteCourantDTO> comptes = (List<CompteCourantDTO>) request.getAttribute("comptes");

    @SuppressWarnings("unchecked")
    List<DeviseDTO> devises = (List<DeviseDTO>) request.getAttribute("devises");
    
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Mouvement</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 700px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 26px;
            margin-bottom: 5px;
        }
        
        .header p {
            opacity: 0.9;
            font-size: 14px;
        }
        
        .user-badge {
            display: inline-block;
            background: rgba(255, 255, 255, 0.2);
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 13px;
            margin-top: 10px;
        }
        
        .form-section {
            padding: 40px;
        }
        
        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .alert.success {
            background: #d4edda;
            color: #155724;
            border-left: 4px solid #28a745;
        }
        
        .alert.error {
            background: #f8d7da;
            color: #721c24;
            border-left: 4px solid #dc3545;
        }
        
        .alert::before {
            font-size: 20px;
        }
        
        .alert.success::before {
            content: "✅";
        }
        
        .alert.error::before {
            content: "❌";
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-group label {
            display: block;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
            font-size: 14px;
        }
        
        .form-group label span {
            color: #dc3545;
        }
        
        .form-group input,
        .form-group select {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 15px;
            transition: border-color 0.3s ease;
        }
        
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #11998e;
        }
        
        .form-group input[type="number"] {
            -moz-appearance: textfield;
        }
        
        .form-group input[type="number"]::-webkit-outer-spin-button,
        .form-group input[type="number"]::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
        
        .form-group select {
            cursor: pointer;
            background: white;
        }
        
        .form-group small {
            display: block;
            margin-top: 5px;
            color: #6c757d;
            font-size: 12px;
        }
        
        .button-group {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }
        
        .btn {
            flex: 1;
            padding: 14px 20px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(17, 153, 142, 0.4);
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background: #5a6268;
        }
        
        .btn::before {
            font-size: 18px;
        }
        
        .btn-primary::before {
            content: "➕";
        }
        
        .btn-secondary::before {
            content: "🏠";
        }
        
        .info-box {
            background: #e3f2fd;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 25px;
            border-left: 4px solid #2196F3;
        }
        
        .info-box p {
            margin: 5px 0;
            color: #1976d2;
            font-size: 14px;
        }
        
        @media (max-width: 600px) {
            .form-section {
                padding: 25px;
            }
            
            .button-group {
                flex-direction: column;
            }
            
            .header h1 {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- En-tête -->
        <div class="header">
            <h1>Ajouter un Mouvement</h1>
            <p>Créer un nouveau mouvement bancaire</p>
            <div class="user-badge">
                👤 <%= userDTO.getNom() %>
            </div>
        </div>
        
        <!-- Formulaire -->
        <div class="form-section">
            
            <!-- Messages d'alerte -->
            <% if (success != null) { %>
                <div class="alert success">
                    <%= success %>
                </div>
            <% } %>
            
            <% if (error != null) { %>
                <div class="alert error">
                    <%= error %>
                </div>
            <% } %>
            
            <!-- Formulaire -->
            <form method="post" action="ajouterMouvement">
                
                <!-- Type de mouvement -->
                <div class="form-group">
                    <label for="idTypeMouvement">
                        Type de mouvement <span>*</span>
                    </label>
                    <select id="idTypeMouvement" name="idTypeMouvement" required>
                        <option value="">-- Sélectionnez un type --</option>
                        <% 
                        if (typesMouvement != null && !typesMouvement.isEmpty()) {
                            for (TypeMouvementDTO type : typesMouvement) { 
                        %>
                            <option value="<%= type.getIdTypeMouvement() %>">
                                <%= type.getDescription() %>
                            </option>
                        <% 
                            }
                        } else {
                        %>
                            <option value="" disabled>Aucun type disponible</option>
                        <% } %>
                    </select>
                </div>
                
                <!-- Compte -->
                <div class="form-group">
                    <label for="idCompte">
                        Compte client <span>*</span>
                    </label>
                    <select id="idCompte" name="idCompte" required>
                        <option value="">-- Sélectionnez un compte --</option>
                        <% 
                        if (comptes != null && !comptes.isEmpty()) {
                            for (CompteCourantDTO compte : comptes) { 
                        %>
                            <option value="<%= compte.getIdCompte() %>">
                                <%= compte.getClient().getNom() %> 
                                (Compte #<%= compte.getIdCompte() %> - Solde: <%= String.format("%,.2f", compte.getSolde()) %> Ar)
                            </option>
                        <% 
                            }
                        } else {
                        %>
                            <option value="" disabled>Aucun compte disponible</option>
                        <% } %>
                    </select>
                    <small>Le compte affiche le client et le solde actuel</small>
                </div>
                
                <!-- Montant -->
                <div class="form-group">
                    <label for="montant">
                        Montant<span>*</span>
                    </label>
                    <input 
                        type="number" 
                        id="montant" 
                        name="montant" 
                        step="0.01" 
                        min="0.01"
                        placeholder="Ex: 50000.00"
                        required
                    />
                    <small>Entrez le montant en Ariary (format: 50000.00)</small>
                </div>

                <!-- Devises -->
                <div class="form-group">
                    <label for="devise">
                        Devises <span>*</span>
                    </label>
                    <select id="devise" name="devise" required>
                        <option value="">-- Sélectionnez un compte --</option>
                        <%  
                        if (devises != null && !devises.isEmpty()) {
                            for (DeviseDTO devise : devises) { 
                        %>
                            <option value="<%= devise.getCode() %>">
                                <%= devise.getCode()%> 
                                <%-- (devise #<%= devise.getIdCompte() %> - Solde: <%= String.format("%,.2f", devise.getSolde()) %> Ar) --%>
                            </option>
                        <% 
                            }
                        } else {
                        %>
                            <option value="" disabled>Aucun devise disponible</option>
                        <% } %>
                    </select>
                </div>
                
                <!-- Boutons -->
                <div class="button-group">
                    <button type="submit" class="btn btn-primary">
                        Enregistrer le mouvement
                    </button>
                    <a href="dashboard.jsp" class="btn btn-secondary">
                        Retour au tableau de bord
                    </a>
                </div>
                
            </form>
            
        </div>
    </div>
</body>
</html>