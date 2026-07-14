<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="sidebar">

    <%-- ================= MENU ADMIN ================= --%>
    <sec:authorize access="hasRole('ADMIN')">
        <h2>ADMIN PANEL</h2>

        <a href="${pageContext.request.contextPath}/dashboard"
           class="${activeMenu == 'dashboard' ? 'active' : ''}">
            <i class="fas fa-chart-pie"></i> Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/new"
           class="${activeMenu == 'lot-ingredients' ? 'active' : ''}">
            <i class="fas fa-box-open"></i> Créer un lot ingrédient
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/view/bientot-perimes"
           class="${activeMenu == 'ingredients-bientot-perimes' ? 'active' : ''}">
            <i class="fas fa-hourglass-half"></i> Ingrédients bientôt périmés
        </a>

        <a href="${pageContext.request.contextPath}/equipements/alertes"
           class="${activeMenu == 'equipements-alertes' ? 'active' : ''}">
            <i class="fas fa-tools"></i> Alertes d'équipements
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/alertes"
           class="${activeMenu == 'lot-ingredients-alertes' ? 'active' : ''}">
            <i class="fas fa-bell"></i> Alertes ingrédients
        </a>

        <a href="${pageContext.request.contextPath}/mouvements-equipement/etat-stock"
           class="${activeMenu == 'etat-stock' ? 'active' : ''}">
            <i class="fas fa-warehouse"></i> État de stock
        </a>

        <a href="${pageContext.request.contextPath}/statistique"
           class="${activeMenu == 'statistique' ? 'active' : ''}">
            <i class="fas fa-chart-line"></i> Statistiques
        </a>

        <a href="${pageContext.request.contextPath}/clients"
           class="${activeMenu == 'clients' ? 'active' : ''}">
            <i class="fas fa-users"></i> Clients
        </a>

        <a href="${pageContext.request.contextPath}/employes"
           class="${activeMenu == 'employes' ? 'active' : ''}">
            <i class="fas fa-id-badge"></i> Employés
        </a>

        <a href="${pageContext.request.contextPath}/conges"
           class="${activeMenu == 'conges' ? 'active' : ''}">
            <i class="fas fa-calendar-minus"></i> Congés & Absences
        </a>

        <a href="${pageContext.request.contextPath}/fiches-paie"
           class="${activeMenu == 'fiches-paie' ? 'active' : ''}">
            <i class="fas fa-file-invoice-dollar"></i> Fiches de paie
        </a>

        <a href="${pageContext.request.contextPath}/retour/avis"
           class="${activeMenu == 'retourAvis' ? 'active' : ''}">
            <i class="fas fa-comment-dots"></i> Avis
        </a>

        <a href="${pageContext.request.contextPath}/equipe/list_equipe"
           class="${activeMenu == 'equipe' ? 'active' : ''}">
            <i class="fas fa-people-group"></i> Équipe
        </a>

        <a href="${pageContext.request.contextPath}/changement-itineraire/nouveau"
           class="${activeMenu == 'changementItineraire' ? 'active' : ''}">
            <i class="fas fa-route"></i> Demande de changement d'itinéraire
        </a>
    </sec:authorize>

    <%-- ================= MENU EMPLOYÉ ================= --%>
    <sec:authorize access="!hasRole('ADMIN')">
        <h2>ESPACE EMPLOYÉ</h2>

        <a href="${pageContext.request.contextPath}/inventaire/findAll"
           class="${activeMenu == 'inventaire' ? 'active' : ''}">
            <i class="fas fa-clipboard-list"></i> Inventaire
        </a>

        <a href="${pageContext.request.contextPath}/produits"
           class="${activeMenu == 'produits' ? 'active' : ''}">
            <i class="fas fa-cookie-bite"></i> Produits
        </a>

        <a href="${pageContext.request.contextPath}/ingredients"
           class="${activeMenu == 'ingredients' ? 'active' : ''}">
            <i class="fas fa-carrot"></i> Ingrédients
        </a>

        <a href="${pageContext.request.contextPath}/lot/findAll"
           class="${activeMenu == 'lots' ? 'active' : ''}">
            <i class="fas fa-boxes-stacked"></i> Lots
        </a>

        <a href="${pageContext.request.contextPath}/lot/ingredients/view/perimes"
           class="${activeMenu == 'ingredients-perimes' ? 'active' : ''}">
            <i class="fas fa-skull-crossbones"></i> Ingrédients périmés
        </a>

        <a href="${pageContext.request.contextPath}/mouvements-equipement/etat-stock"
           class="${activeMenu == 'etat-stock' ? 'active' : ''}">
            <i class="fas fa-warehouse"></i> État de stock
        </a>

        <a href="${pageContext.request.contextPath}/recetteBase"
           class="${activeMenu == 'recetteBase' ? 'active' : ''}">
            <i class="fas fa-book-open"></i> Recette de base
        </a>

        <a href="${pageContext.request.contextPath}/vente/vendeuse"
           class="${activeMenu == 'commandes' ? 'active' : ''}">
            <i class="fas fa-cash-register"></i> Commandes
        </a>

        <a href="${pageContext.request.contextPath}/changement-itineraire/nouveau"
           class="${activeMenu == 'changementItineraire' ? 'active' : ''}">
            <i class="fas fa-route"></i> Demande de changement d'itinéraire
        </a>
    </sec:authorize>

    <%-- ================= Déconnexion ================= --%>
    <div class="logout-section">
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">
            <i class="fas fa-sign-out-alt"></i> Déconnexion
        </a>
    </div>
</div>
