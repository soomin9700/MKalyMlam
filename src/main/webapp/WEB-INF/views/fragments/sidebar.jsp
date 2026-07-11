<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<div class="sidebar">

    <h2>ADMIN PANEL</h2>

    <%-- <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a> --%>

    <%-- <a href="${pageContext.request.contextPath}/produits">
        Produits
    </a>

    <a href="${pageContext.request.contextPath}/commandes">
        Commandes
    </a> --%>
<%-- 
    <a href="${pageContext.request.contextPath}/employes">
        Employés
    </a> --%>

    <%-- <a href="${pageContext.request.contextPath}/clients">
        Clients
    </a> --%>

    <%-- <a href="${pageContext.request.contextPath}/statistiques">
        Statistiques
    </a> --%>

    <%-- <a href="${pageContext.request.contextPath}/ingredients">
        Ingrédients
    </a> --%>

    <a href="${pageContext.request.contextPath}/dashboard"
    class="${activeMenu == 'dashboard' ? 'active' : ''}">
        Dashboard
    </a>

    <a href="${pageContext.request.contextPath}/inventaire/findAll"
    class="${activeMenu == 'dashboard' ? 'active' : ''}">
        Inventaires
    </a>

    <a href="${pageContext.request.contextPath}/produits"
    class="${activeMenu == 'produits' ? 'active' : ''}">
        Produits
    </a>

    <!-- Hasina -->
    <a href="${pageContext.request.contextPath}/ingredients"
    class="${activeMenu == 'ingredients' ? 'active' : ''}">
        Ingrédients
    </a>

    <a href="${pageContext.request.contextPath}/lot/findAll"
    class="${activeMenu == 'lots' ? 'active' : ''}">
        Lots
    <a href="${pageContext.request.contextPath}/lot/ingredients/new"
    class="${activeMenu == 'lot-ingredients' ? 'active' : ''}">
        Créer un lot ingrédient
    </a>

    <a href="${pageContext.request.contextPath}/lot/ingredients/alertes"
    class="${activeMenu == 'lot-ingredients-alertes' ? 'active' : ''}">
        Alertes ingrédients
    </a>

    <a href="${pageContext.request.contextPath}/lot/ingredients/view/bientot-perimes"
    class="${activeMenu == 'ingredients-bientot-perimes' ? 'active' : ''}">
        Ingrédients bientôt périmés
    </a>

    <a href="${pageContext.request.contextPath}/lot/ingredients/view/perimes"
    class="${activeMenu == 'ingredients-perimes' ? 'active' : ''}">
        Ingrédients périmés
    </a>

    <a href="${pageContext.request.contextPath}/equipements"
    class="${activeMenu == 'equipements' ? 'active' : ''}">
        Équipements
    </a>

    <a href="${pageContext.request.contextPath}/equipements/alertes"
    class="${activeMenu == 'equipements-alertes' ? 'active' : ''}">
        Alertes d'équipements
    </a>

    <!-- -->
    <a href="${pageContext.request.contextPath}/mouvements-equipement"
    class="${activeMenu == 'mouvements-equipement' ? 'active' : ''}">
        Mouvement des équipements
    </a>

    <a href="${pageContext.request.contextPath}/mouvements-equipement/etat-stock"
    class="${activeMenu == 'etat-stock' ? 'active' : ''}">
        État de stock
    </a>

    <a href="${pageContext.request.contextPath}/recetteBase"
    class="${activeMenu == 'recetteBase' ? 'active' : ''}">
        Recette de Base
    </a>

    <a href="${pageContext.request.contextPath}/commande/liste"
    class="${activeMenu == 'commande' ? 'active' : ''}">
        Commandes
    </a>

    <a href="${pageContext.request.contextPath}/vente/vendeuse"
    class="${activeMenu == 'vente' ? 'active' : ''}">
        Nouvelle vente
    </a>

    <a href="${pageContext.request.contextPath}/ventes"
    class="${activeMenu == 'ventes' ? 'active' : ''}">
        Ventes (historique)
    </a>

    <a href="${pageContext.request.contextPath}/consommation/historique"
    class="${activeMenu == 'consommation' ? 'active' : ''}">
        Consommations
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
    <a href="${pageContext.request.contextPath}/conges/demande"
        class="${activeMenu == 'conges' ? 'active' : ''}">
        <i class="fas fa-plus-circle"></i> Demande de congé
    </a>

    <a href="${pageContext.request.contextPath}/fiches-paie"
    class="${activeMenu == 'fiches-paie' ? 'active' : ''}">
        Fiches de paie
    <a href="${pageContext.request.contextPath}/retour/avis"
    class="${activeMenu == 'retourAvis' ? 'active' : ''}">
        Avis
    </a>

    <a href="${pageContext.request.contextPath}/retour/demandes"
    class="${activeMenu == 'retourDemandes' ? 'active' : ''}">
        Demandes
    </a>

    <a href="${pageContext.request.contextPath}/retour"
    class="${activeMenu == 'retours' ? 'active' : ''}">
        Écrire un avis
    </a>

    <a href="${pageContext.request.contextPath}/equipe/list_equipe"
    class="${activeMenu == 'equipe' ? 'active' : ''}">
        Équipe
    </a>

    <a href="${pageContext.request.contextPath}/changement-itineraire/nouveau"
    class="${activeMenu == 'changementItineraire' ? 'active' : ''}">
        Demandes de changement d'itinéraire
    </a>

</div>
