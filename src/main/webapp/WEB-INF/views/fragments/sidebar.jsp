<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="sidebar">

    <%-- ================= MENU ADMIN ================= --%>
    <sec:authorize access="hasRole('ADMIN')">
        <h2>ADMIN PANEL</h2>

        <a href="${pageContext.request.contextPath}/dashboard"
           class="${activeMenu == 'dashboard' ? 'active' : ''}">
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/new"
           class="${activeMenu == 'lot-ingredients' ? 'active' : ''}">
            Créer un lot ingrédient
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/view/bientot-perimes"
           class="${activeMenu == 'ingredients-bientot-perimes' ? 'active' : ''}">
            Ingrédients bientôt périmés
        </a>

        <a href="${pageContext.request.contextPath}/equipements/alertes"
           class="${activeMenu == 'equipements-alertes' ? 'active' : ''}">
            Alertes d'équipements
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/alertes"
           class="${activeMenu == 'lot-ingredients-alertes' ? 'active' : ''}">
            Alertes ingrédients
        </a>

        <a href="${pageContext.request.contextPath}/mouvements-equipement/etat-stock"
           class="${activeMenu == 'etat-stock' ? 'active' : ''}">
            État de stock
        </a>

        <a href="${pageContext.request.contextPath}/statistique"
           class="${activeMenu == 'statistique' ? 'active' : ''}">
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

        <a href="${pageContext.request.contextPath}/conges"
           class="${activeMenu == 'conges' ? 'active' : ''}">
            Congés & Absences
        </a>

        <a href="${pageContext.request.contextPath}/fiches-paie"
           class="${activeMenu == 'fiches-paie' ? 'active' : ''}">
            Fiches de paie
        </a>

        <a href="${pageContext.request.contextPath}/retour/avis"
           class="${activeMenu == 'retourAvis' ? 'active' : ''}">
            Avis
        </a>

        <a href="${pageContext.request.contextPath}/equipe/list_equipe"
           class="${activeMenu == 'equipe' ? 'active' : ''}">
            Équipe
        </a>

        <a href="${pageContext.request.contextPath}/changement-itineraire/nouveau"
           class="${activeMenu == 'changementItineraire' ? 'active' : ''}">
            Demande de changement d'itinéraire
        </a>
    </sec:authorize>

    <%-- ================= MENU EMPLOYÉ ================= --%>
    <sec:authorize access="!hasRole('ADMIN')">
        <h2>ESPACE EMPLOYÉ</h2>

        <a href="${pageContext.request.contextPath}/inventaire/findAll"
           class="${activeMenu == 'inventaire' ? 'active' : ''}">
            Inventaire
        </a>

        <a href="${pageContext.request.contextPath}/produits"
           class="${activeMenu == 'produits' ? 'active' : ''}">
            Produits
        </a>

        <a href="${pageContext.request.contextPath}/ingredients"
           class="${activeMenu == 'ingredients' ? 'active' : ''}">
            Ingrédients
        </a>

        <a href="${pageContext.request.contextPath}/lot/findAll"
           class="${activeMenu == 'lots' ? 'active' : ''}">
            Lots
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/view/perimes"
           class="${activeMenu == 'ingredients-perimes' ? 'active' : ''}">
            Ingrédients périmés
        </a>

        <a href="${pageContext.request.contextPath}/mouvements-equipement/etat-stock"
           class="${activeMenu == 'etat-stock' ? 'active' : ''}">
            État de stock
        </a>

        <a href="${pageContext.request.contextPath}/recetteBase"
           class="${activeMenu == 'recetteBase' ? 'active' : ''}">
            Recette de base
        </a>

        <a href="${pageContext.request.contextPath}/vente/vendeuse"
           class="${activeMenu == 'commandes' ? 'active' : ''}">
            Commandes
        </a>

        <a href="${pageContext.request.contextPath}/changement-itineraire/nouveau"
           class="${activeMenu == 'changementItineraire' ? 'active' : ''}">
            Demande de changement d'itinéraire
        </a>
    </sec:authorize>

    <%-- ================= Déconnexion ================= --%>
    <div class="logout-section">
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">
            <i class="fas fa-sign-out-alt"></i> Déconnexion
        </a>
    </div>
</div>
