<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Inventaires</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_badge.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <!-- En-tête -->
            <div class="table-header">

                <h1>
                    <i class="fas fa-utensils"
                       style="color: var(--primary); margin-right:10px;"></i>
                    Liste des inventaires
                </h1>

                <a href="${pageContext.request.contextPath}/inventaire/new"
                   class="btn-add">
                    Nouvel inventaire
                </a>

            </div>

            <!-- Tableau -->
            <table>

                <thead>
                    <tr>
                        <th><i class="fas fa-tag"></i> Session</th>
                        <th><i class="fas fa-calendar"></i> Date inventaire</th>
                        <th><i class="fas fa-tag"></i> Type</th>
                        <th><i class="fas fa-box"></i> Item</th>  <!-- Nouvelle colonne -->
                        <th><i class="fas fa-weight"></i> Quantité physique</th>
                        <th><i class="fas fa-weight-hanging"></i> Quantité théorique</th>
                        <th><i class="fas fa-balance-scale"></i> Écart</th>
                        <th><i class="fas fa-cog"></i> Actions</th>
                    </tr>
                </thead>

                <tbody>

                    <!-- Aucun inventaire -->
                    <c:if test="${empty inventaires}">
                        <tr>
                            <td colspan="8">
                                <div class="empty-state">
                                    <i class="fas fa-boxes"
                                       style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucun inventaire enregistré pour le moment.</p>
                                    <a href="${pageContext.request.contextPath}/inventaire/save"
                                       class="btn-add">
                                        <i class="fas fa-plus"></i>
                                        Ajouter le premier inventaire
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:if>

                    <!-- Liste des inventaires -->
                    <c:forEach items="${inventaires}" var="inventaire">

                        <tr>
                            <td>
                                <strong>Session #${inventaire.sessionTruck.id}</strong>
                                <br>
                                <small>${inventaire.sessionTruck.dateSession}</small>
                            </td>

                            <td>
                                ${inventaire.dateInventaire}
                            </td>

                            <td>
                                <span class="">
                                    ${inventaire.typeItem.libelle}
                                </span>
                            </td>

                            <!-- ✅ Affichage du nom de l'item -->
                            <td>
                                <span class="badge bg-info">
                                    <i class="fas fa-box"></i>
                                    ${inventaire.nomItem}
                                </span>
                            </td>

                            <td>
                                <span class="">
                                    ${inventaire.quantitePhysiqueConstatee}
                                </span>
                            </td>

                            <td>
                                <span class="">
                                    ${inventaire.quantiteTheoriqueSysteme}
                                </span>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${inventaire.ecartInventaire != 0}">
                                        <span class="badge bg-danger">
                                            <i class="fas fa-times-circle"></i>
                                            ${inventaire.ecartInventaire}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-success">
                                            <i class="fas fa-check-circle"></i>
                                            OK
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <div class="actions">
                                    <a href="${pageContext.request.contextPath}/inventaire/edit/${inventaire.idInventaire}"
                                       class="btn-edit">
                                        <i class="fas fa-edit"></i>
                                        Modifier
                                    </a>

                                    <form action="${pageContext.request.contextPath}/inventaire/delete/${inventaire.idInventaire}"
                                          method="post"
                                          style="display:inline;"
                                          onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet inventaire ?');">
                                        <button type="submit" class="btn-delete">
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