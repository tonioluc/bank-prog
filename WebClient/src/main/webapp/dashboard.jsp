<%@page import="com.entreprise.dto.UtilisateurDTO"%>
<%@page import="com.entreprise.dto.RoleDTO"%>
<%@page import="com.entreprise.dto.DirectionDTO"%>
<%@page import="com.entreprise.remote.UserSessionRemote"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Vérifier la session
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    UtilisateurDTO userDTO = (UtilisateurDTO) session.getAttribute("user");
    UserSessionRemote userSession = (UserSessionRemote) session.getAttribute("userSession");
    
    RoleDTO role = userSession.getUserRole();
    DirectionDTO direction = userSession.getUserDirection();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
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
            max-width: 900px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 28px;
            margin-bottom: 10px;
        }
        
        .header p {
            opacity: 0.9;
            font-size: 16px;
        }
        
        .user-info {
            background: #f8f9fa;
            padding: 20px 30px;
            border-bottom: 1px solid #e9ecef;
        }
        
        .user-info h2 {
            font-size: 18px;
            color: #495057;
            margin-bottom: 15px;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }
        
        .info-item {
            background: white;
            padding: 12px 15px;
            border-radius: 8px;
            border-left: 3px solid #667eea;
        }
        
        .info-item label {
            display: block;
            font-size: 12px;
            color: #6c757d;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 5px;
        }
        
        .info-item span {
            display: block;
            font-size: 16px;
            color: #212529;
            font-weight: 600;
        }
        
        .actions-section {
            padding: 30px;
        }
        
        .actions-section h2 {
            font-size: 20px;
            color: #212529;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
        }
        
        .actions-section h2::before {
            content: "⚡";
            margin-right: 10px;
            font-size: 24px;
        }
        
        .actions-list {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .action-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 10px;
            padding: 25px;
            text-decoration: none;
            color: white;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        
        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
        }
        
        .action-card .icon {
            font-size: 32px;
            margin-bottom: 5px;
        }
        
        .action-card .title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 5px;
        }
        
        .action-card .description {
            font-size: 14px;
            opacity: 0.9;
            line-height: 1.4;
        }
        
        .action-card.add {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        }
        
        .action-card.add:hover {
            box-shadow: 0 10px 30px rgba(17, 153, 142, 0.4);
        }
        
        .action-card.validate {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        
        .action-card.validate:hover {
            box-shadow: 0 10px 30px rgba(245, 87, 108, 0.4);
        }
        
        .logout-section {
            padding: 0 30px 30px 30px;
        }
        
        .logout-btn {
            width: 100%;
            background: #dc3545;
            color: white;
            padding: 15px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }
        
        .logout-btn:hover {
            background: #c82333;
        }
        
        .logout-btn::before {
            content: "🚪";
            font-size: 20px;
        }
        
        @media (max-width: 600px) {
            .header h1 {
                font-size: 22px;
            }
            
            .actions-list {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- En-tête -->
        <div class="header">
            <h1>Tableau de bord</h1>
            <p>Bienvenue, <%= userDTO.getNom() %></p>
        </div>
        
        <!-- Informations utilisateur -->
        <div class="user-info">
            <h2>📋 Vos informations</h2>
            <div class="info-grid">
                <div class="info-item">
                    <label>Utilisateur</label>
                    <span><%= userDTO.getNom() %></span>
                </div>
                <div class="info-item">
                    <label>Niveau de rôle</label>
                    <span><%= role.getNiveau() %></span>
                </div>
                <div class="info-item">
                    <label>Direction</label>
                    <span><%= direction.getType() %></span>
                </div>
            </div>
        </div>
        
        <!-- Section Actions -->
        <div class="actions-section">
            <h2>Actions disponibles</h2>
            
            <div class="actions-list">
                <!-- Action 1 : Ajouter un mouvement -->
                <a href="ajouterMouvement" class="action-card add">
                    <div class="icon">➕</div>
                    <div class="title">Ajouter un mouvement</div>
                    <div class="description">Créer un nouveau mouvement dans le système</div>
                </a>
                
                <!-- Action 2 : Valider des mouvements -->
                <a href="validerMouvements" class="action-card validate">
                    <div class="icon">✅</div>
                    <div class="title">Valider des mouvements</div>
                    <div class="description">Consulter et valider les mouvements en attente</div>
                </a>
            </div>
        </div>
        
        <!-- Section Déconnexion -->
        <div class="logout-section">
            <form action="logout" method="get">
                <button type="submit" class="logout-btn">
                    Se déconnecter
                </button>
            </form>
        </div>
    </div>
</body>
</html>