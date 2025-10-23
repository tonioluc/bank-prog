<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - EJB Application</title>
    <style>
        body { font-family: Arial; background: #f0f0f0; }
        .login-container {
            width: 300px; margin: 100px auto;
            background: white; padding: 30px;
            border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 10px; margin: 10px 0;
            border: 1px solid #ddd; border-radius: 4px;
        }
        button {
            width: 100%; padding: 12px;
            background: #4CAF50; color: white;
            border: none; border-radius: 4px;
            cursor: pointer; font-size: 16px;
        }
        button:hover { background: #45a049; }
        .error { color: red; margin: 10px 0; }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Connexion</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <form method="post" action="login">
            <input type="text" name="nom" value="user" placeholder="Nom d'utilisateur" required>
            <input type="password" name="password" value="user123" placeholder="Mot de passe" required>
            <button type="submit">Se connecter</button>
        </form>
        
        <p style="font-size: 12px; color: #666; margin-top: 20px;">
            Test: admin / admin123 ou user / user123
        </p>
    </div>
</body>
</html>