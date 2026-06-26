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

            <!-- Tableau -->
            <table>

                <thead>

                <tr>
                    <th><i class="fas fa-hashtag"></i> ID</th>
                    <th><i class="fas fa-tag"></i> Nom</th>
                    <th><i class="fas fa-exclamation-triangle"></i> Seuil d'alerte</th>
                    <th><i class="fas fa-ruler"></i> Unité</th>
                    <th><i class="fas fa-cog"></i> Actions</th>
                </tr>

                </thead>

                <tbody>

                <!-- Aucun ingrédient -->

                <c:if test="${empty ingredients}">
                    <tr>

                        <td colspan="5">

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

                            <div class="actions">

                                <!-- Modifier -->

                                <a href="${pageContext.request.contextPath}/ingredients/${i.idIngredient}/edit"
                                   class="btn-edit">

                                    <i class="fas fa-edit"></i>
                                    Modifier

                                </a>

                                <!-- Supprimer -->

                                <form action="${pageContext.request.contextPath}/ingredients/${i.idIngredient}/delete"
                                      method="post"
                                      style="display:inline;"
                                      onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet ingrédient ?');">

                                    <button type="submit"
                                            class="btn-delete">

                                        <i class="fas fa-trash-alt"></i>
                                        Supprimer

                                    </button>

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