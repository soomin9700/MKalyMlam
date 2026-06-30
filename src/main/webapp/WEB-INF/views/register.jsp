<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link rel="stylesheet" href="<c:url value='/css/auth.css' />">
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-panel">
            <h2>Créer un compte</h2>

            <c:if test="${not empty error}">
                <div class="error-msg">${error}</div>
            </c:if>

            <form action="<c:url value='/register' />" method="post">
                <label for="nom">Nom</label>
                <input type="text" id="nom" name="nom" required>

                <label for="prenom">Prénom</label>
                <input type="text" id="prenom" name="prenom" required>

                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>

                <label for="motDePasse">Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" required>

                <button type="submit">S'inscrire</button>
            </form>

            <div class="auth-link">
                <a href="<c:url value='/login' />">Déjà un compte ? Se connecter</a>
            </div>
        </div>
    </div>
</body>
</html>