<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="sidebar">
    <h2>ADMIN PANEL</h2>

    <a href="${pageContext.request.contextPath}/dashboard"
       class="${activeMenu == 'dashboard' ? 'active' : ''}">
        Dashboard
    </a>
    <a href="${pageContext.request.contextPath}/produits"
       class="${activeMenu == 'produits' ? 'active' : ''}">
        Produits
    </a>
    <a href="${pageContext.request.contextPath}/ingredients"
       class="${activeMenu == 'ingredients' ? 'active' : ''}">
        Ingrédients
    </a>
    <a href="${pageContext.request.contextPath}/recetteBase"
       class="${activeMenu == 'recetteBase' ? 'active' : ''}">
        Recette de Base
    </a>
    <a href="${pageContext.request.contextPath}/vente/vendeuse"
       class="${activeMenu == 'commandes' ? 'active' : ''}">
        Commandes
    </a>
    <a href="${pageContext.request.contextPath}/statistiques"
       class="${activeMenu == 'statistiques' ? 'active' : ''}">
        Statistiques
    </a>
    <a href="${pageContext.request.contextPath}/clients"
       class="${activeMenu == 'clients' ? 'active' : ''}">
        Clients
    </a>
    <a href="${pageContext.request.contextPath}/employes"
       class="${activeMenu == 'employes' ? 'active' : ''}">
        Employés
    </a>

    <!-- Déconnexion -->
    <div class="logout-section">
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">
            <i class="fas fa-sign-out-alt"></i> Déconnexion
        </a>
    </div>
</div>