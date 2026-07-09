<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des ingrédients - Administration</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="ingredients"/>

    <!-- Sidebar -->
    <%-- <div class="sidebar">
        <h2>ADMIN PANEL</h2>

        <a href="#">Dashboard</a>
        <a href="./produits">Produits</a>
        <a href="#">Commandes</a>
        <a href="#">Employés</a>
        <a href="#">Clients</a>
        <a href="#">Statistiques</a>
        <a href="#" class="active" style="color: var(--secondary);">Ingrédients</a>
    </div> --%>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <!-- En-tête -->
            <div class="table-header">

                <h1>
                    <i class="fas fa-utensils"
                       style="color: var(--primary); margin-right:10px;"></i>
                    Liste des ingrédients
                </h1>

                <a href="${pageContext.request.contextPath}/ingredients/new"
                   class="btn-add">
                    Ajouter un ingrédient
                </a>

            </div>

            <form method="get"
                  action="${pageContext.request.contextPath}/ingredients"
                  style="display:grid;grid-template-columns:1fr auto auto auto;gap:14px;align-items:end;margin-bottom:20px;padding:16px 18px;background:#f9fafb;border:1px solid #e5e7eb;border-radius:14px;">

                <div class="form-group" style="margin-bottom:0;">
                    <label for="recherche">Recherche ingrédient</label>
                    <input type="text"
                           id="recherche"
                           name="recherche"
                           value="${selectedRecherche}"
                           placeholder="Nom de l'ingrédient">
                </div>

                <div class="form-group" style="margin-bottom:0;">
                    <label for="statut">Statut</label>
                    <select id="statut" name="statut" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;">
                        <option value="">Tous</option>
                        <option value="actif" ${selectedStatut == 'actif' ? 'selected' : ''}>Actif</option>
                        <option value="inactif" ${selectedStatut == 'inactif' ? 'selected' : ''}>Inactif</option>
                    </select>
                </div>

                <button type="submit" class="btn-success" style="height:44px;">
                    <i class="fas fa-search"></i>
                    Rechercher
                </button>

                <a href="${pageContext.request.contextPath}/ingredients"
                   class="btn-secondary"
                   style="height:44px;display:inline-flex;align-items:center;justify-content:center;text-decoration:none;">
                    Réinitialiser
                </a>
            </form>

            <!-- Tableau -->
            <table>

                <thead>

                <tr>
                    <th><i class="fas fa-hashtag"></i> ID</th>
                    <th><i class="fas fa-tag"></i> Nom</th>
                    <th><i class="fas fa-exclamation-triangle"></i> Seuil d'alerte</th>
                    <th><i class="fas fa-ruler"></i> Unité</th>
                    <th><i class="fas fa-toggle-on"></i> Statut</th>
                    <th><i class="fas fa-cog"></i> Actions</th>
                </tr>

                </thead>

                <tbody>

                <!-- Aucun ingrédient -->

                <c:if test="${empty ingredients}">
                    <tr>

                        <td colspan="6">

                            <div class="empty-state">

                                <i class="fas fa-utensils"
                                   style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>

                                <p>Aucun ingrédient enregistré pour le moment.</p>

                                <a href="${pageContext.request.contextPath}/ingredients/new"
                                   class="btn-add">

                                    <i class="fas fa-plus"></i>
                                    Ajouter le premier ingrédient

                                </a>

                            </div>

                        </td>

                    </tr>
                </c:if>

                <!-- Liste des ingrédients -->

                <c:forEach var="i" items="${ingredients}">

                    <c:set var="estActif" value="${i.actif != null ? i.actif : true}"/>

                    <tr>

                        <td>
                            <span class="badge badge-id">
                                ${i.idIngredient}
                            </span>
                        </td>

                        <td>
                            <strong>${i.nomIngredient}</strong>
                        </td>

                        <td>

                            <c:choose>

                                <c:when test="${not empty i.seuilAlerteQuantite}">
                                    ${i.seuilAlerteQuantite}
                                </c:when>

                                <c:otherwise>
                                    <span class="text-muted">-</span>
                                </c:otherwise>

                            </c:choose>

                        </td>

                        <td>

                            <c:choose>

                                <c:when test="${not empty i.uniteMesure}">
                                    ${i.uniteMesure}
                                </c:when>

                                <c:otherwise>
                                    <span class="text-muted">-</span>
                                </c:otherwise>

                            </c:choose>

                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${estActif}">
                                    <span class="badge bg-success">
                                        <i class="fas fa-check-circle"></i>
                                        Actif
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">
                                        <i class="fas fa-times-circle"></i>
                                        Inactif
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>

                            <div class="actions">

                                <!-- Modifier -->

                                <a href="${pageContext.request.contextPath}/ingredients/${i.idIngredient}/edit"
                                   class="btn-edit">

                                    <i class="fas fa-edit"></i>
                                    Modifier

                                </a>

                                <!-- Activer / Désactiver -->

                                <form action="${pageContext.request.contextPath}/ingredients/${i.idIngredient}/toggle"
                                      method="post"
                                      style="display:inline;">

                                    <c:choose>
                                        <c:when test="${estActif}">
                                            <button type="submit" class="btn-delete">
                                                <i class="fas fa-ban"></i>
                                                Désactiver
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="btn-success">
                                                <i class="fas fa-check"></i>
                                                Activer
                                            </button>
                                        </c:otherwise>
                                    </c:choose>

                                </form>

                            </div>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </div>

    </div>

</div>

</body>
</html>
