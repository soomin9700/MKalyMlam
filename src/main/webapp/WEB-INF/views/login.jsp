<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" href="<c:url value='/css/auth.css' />">
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-panel">
            <h2>Connexion</h2>

            <c:if test="${param.error != null}">
                <div class="error-msg">Email ou mot de passe incorrect.</div>
            </c:if>
            <c:if test="${param.logout != null}">
                <div class="success-msg">Vous avez été déconnecté.</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success-msg">${success}</div>
            </c:if>

            <form action="<c:url value='/login' />" method="post">
                <label for="username">Email</label>
                <input type="email" id="username" name="username" required>

                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" required>

                <button type="submit">Se connecter</button>
            </form>

            <div class="auth-link">
                <a href="<c:url value='/register' />">Créer un compte</a>
            </div>
        </div>
    </div>
</body>
</html>