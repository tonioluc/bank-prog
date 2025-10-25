<%@page import="com.entreprise.dto.*"%>
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
    List<MouvementCourantDTO> mouvements = (List<MouvementCourantDTO>) request.getAttribute("mouvements");
    
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Valider les Mouvements</title>
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
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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
        
        .content {
            padding: 30px;
        }
        
        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
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
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .empty-state .icon {
            font-size: 64px;
            margin-bottom: 20px;
        }
        
        .empty-state h3 {
            font-size: 24px;
            margin-bottom: 10px;
            color: #333;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        
        thead {
            background: #f8f9fa;
        }
        
        thead th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #495057;
            border-bottom: 2px solid #dee2e6;
        }
        
        tbody tr {
            border-bottom: 1px solid #dee2e6;
            transition: background 0.2s;
        }
        
        tbody tr:hover {
            background: #f8f9fa;
        }
        
        tbody td {
            padding: 15px;
        }
        
        .badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .badge.credit {
            background: #d4edda;
            color: #155724;
        }
        
        .badge.debit {
            background: #f8d7da;
            color: #721c24;
        }
        
        .badge.attente {
            background: #fff3cd;
            color: #856404;
        }
        
        .montant {
            font-size: 18px;
            font-weight: 600;
        }
        
        .montant.credit {
            color: #28a745;
        }
        
        .montant.debit {
            color: #dc3545;
        }
        
        .actions {
            display: flex;
            gap: 10px;
        }
        
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-valider {
            background: #28a745;
            color: white;
        }
        
        .btn-valider:hover {
            background: #218838;
            transform: translateY(-2px);
        }
        
        .btn-refuser {
            background: #dc3545;
            color: white;
        }
        
        .btn-refuser:hover {
            background: #c82333;
            transform: translateY(-2px);
        }
        
        .btn-retour {
            background: #6c757d;
            color: white;
            padding: 12px 24px;
        }
        
        .btn-retour:hover {
            background: #5a6268;
        }
        
        .footer-actions {
            padding: 0 30px 30px 30px;
        }
        
        @media (max-width: 768px) {
            table {
                font-size: 14px;
            }
            
            thead th, tbody td {
                padding: 10px;
            }
            
            .actions {
                flex-direction: column;
            }
        }
    </style>
    <script>
        function confirmerAction(action, idMouvement, montant) {
            var message = action === 'valider' 
                ? 'Voulez-vous vraiment VALIDER ce mouvement de ' + montant + ' Ar ?' 
                : 'Voulez-vous vraiment REFUSER ce mouvement de ' + montant + ' Ar ?';
            
            if (confirm(message)) {
                document.getElementById('action').value = action;
                document.getElementById('idMouvement').value = idMouvement;
                document.getElementById('actionForm').submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <!-- En-tête -->
        <div class="header">
            <h1>✅ Validation des Mouvements</h1>
            <p>Liste des mouvements en attente de validation</p>
            <div class="user-badge">
                👤 <%= userDTO.getNom() %>
            </div>
        </div>
        
        <!-- Contenu -->
        <div class="content">
            
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
            
            <!-- Informations -->
            <div class="info-box">
                <p><strong>ℹ️ Instructions :</strong></p>
                <p>• Vérifiez les détails de chaque mouvement avant de valider</p>
                <p>• Un mouvement validé ne peut plus être modifié</p>
                <p>• Un mouvement refusé sera archivé</p>
            </div>
            
            <!-- Liste des mouvements -->
            <% if (mouvements == null || mouvements.isEmpty()) { %>
                <div class="empty-state">
                    <div class="icon">📋</div>
                    <h3>Aucun mouvement en attente</h3>
                    <p>Tous les mouvements ont été traités</p>
                </div>
            <% } else { %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Type</th>
                            <th>Client</th>
                            <th>Compte</th>
                            <th>Montant</th>
                            <th>Statut</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (MouvementCourantDTO mvt : mouvements) { 
                            String typeClass = mvt.getTypeMouvement().getDescription().equalsIgnoreCase("Crédit") ? "credit" : "debit";
                            String montantFormate = String.format("%,.2f", mvt.getMontant());
                        %>
                            <tr>
                                <td><strong>#<%= mvt.getIdMouvement() %></strong></td>
                                <td>
                                    <span class="badge <%= typeClass %>">
                                        <%= mvt.getTypeMouvement().getDescription() %>
                                    </span>
                                </td>
                                <td><%= mvt.getCompte().getClient().getNom() %></td>
                                <td>Compte #<%= mvt.getCompte().getIdCompte() %></td>
                                <td>
                                    <span class="montant <%= typeClass %>">
                                        <%= montantFormate %> Ar
                                    </span>
                                </td>
                                <td>
                                    <span class="badge attente">
                                        En attente
                                    </span>
                                </td>
                                <td>
                                    <div class="actions">
                                        <button 
                                            class="btn btn-valider" 
                                            onclick="confirmerAction('valider', <%= mvt.getIdMouvement() %>, '<%= montantFormate %>')">
                                            ✓ Valider
                                        </button>
                                        <button 
                                            class="btn btn-refuser" 
                                            onclick="confirmerAction('refuser', <%= mvt.getIdMouvement() %>, '<%= montantFormate %>')">
                                            ✗ Refuser
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
                
                <p style="color: #666; font-size: 14px;">
                    <strong>Total :</strong> <%= mouvements.size() %> mouvement(s) en attente
                </p>
            <% } %>
            
            <!-- Formulaire caché pour les actions -->
            <form id="actionForm" method="post" action="validerMouvements" style="display: none;">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="idMouvement" name="idMouvement">
            </form>
            
        </div>
        
        <!-- Pied de page -->
        <div class="footer-actions">
            <a href="dashboard.jsp" class="btn btn-retour">
                🏠 Retour au tableau de bord
            </a>
        </div>
    </div>
</body>
</html>