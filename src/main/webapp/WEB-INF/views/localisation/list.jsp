<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Localisations des trucks</title>

                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

                <!-- Font Awesome -->
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/publication_localisation.css">

            </head>

            <body>

                <div class="dashboard">
                    <c:set var="activeMenu" value="localisation" />

                    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

                    <div class="main">

                        <div class="table-container">

                            <!-- En-tête -->
                            <div class="table-header">
                                <h1>
                                    <i class="fas fa-map-marker-alt"
                                        style="color: var(--primary); margin-right: 10px;"></i>
                                    Localisations des trucks
                                </h1>
                                <div style="display: flex; gap: 10px;">
                                    <a href="${pageContext.request.contextPath}/localisation/form" class="btn-add">
                                        Publier une position
                                    </a>
                                </div>
                            </div>

                            <!-- Affichage des messages -->
                            <c:if test="${not empty success}">
                                <div class="alert alert-success"
                                    style="padding: 15px; background: #d4edda; border-radius: 8px; color: #155724; margin: 20px 0;">
                                    <i class="fas fa-check-circle"></i> ${success}
                                </div>
                            </c:if>

                            <!-- Grille des cartes -->
                            <div class="cards-grid">
                                <c:choose>
                                    <c:when test="${not empty positions}">
                                        <c:forEach items="${positions}" var="position">
                                            <div class="location-card">
                                                <div class="card-header">
                                                    <div class="truck-info">
                                                        <div class="truck-icon">
                                                            <i class="fas fa-truck"></i>
                                                        </div>
                                                        <div>
                                                            <div class="truck-name">
                                                                Truck ${position.sessionTruck.truck.immatriculation}
                                                            </div>
                                                            <div class="truck-plate">
                                                                Session #${position.sessionTruck.id}
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <span class="badge-status publie">
                                                        <i class="fas fa-circle" style="font-size: 8px;"></i>
                                                        Publié
                                                    </span>
                                                </div>
                                                <div class="card-body">
                                                    <div class="info-row">
                                                        <span class="icon"><i class="fas fa-map-pin"></i></span>
                                                        <span class="label">Zone</span>
                                                        <span class="value">${position.itineraire.nomZone}</span>
                                                    </div>
                                                    <div class="info-row">
                                                        <span class="icon"><i class="fas fa-location-dot"></i></span>
                                                        <span class="label">Lieu</span>
                                                        <span class="value">${position.itineraire.lieuExact}</span>
                                                    </div>
                                                    <div class="info-row">
                                                        <span class="icon"><i class="fas fa-clock"></i></span>
                                                        <span class="label">Arrivée</span>
                                                        <span class="value">
                                                            <c:choose>
                                                                <c:when test="${not empty position.heureArrivee}">
                                                                    ${position.heureArrivee.toString().substring(0, 5)}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    --
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </div>
                                                    <div class="info-row">
                                                        <span class="icon"><i class="fas fa-calendar-day"></i></span>
                                                        <span class="label">Horaires</span>
                                                        <span class="value">
                                                            <c:choose>
                                                                <c:when
                                                                    test="${not empty position.itineraire.heureDebutPrevue and not empty position.itineraire.heureFinPrevue}">
                                                                    ${position.itineraire.heureDebutPrevue.toString().substring(0,
                                                                    5)}
                                                                    -
                                                                    ${position.itineraire.heureFinPrevue.toString().substring(0,
                                                                    5)}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    --
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </div>
                                                    <div class="info-row">
                                                        <span class="icon"><i class="fas fa-calendar"></i></span>
                                                        <span class="label">Publié le</span>
                                                        <span class="value">
                                                            <c:choose>
                                                                <c:when test="${not empty position.datePublication}">
                                                                    ${position.datePublication.toString()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    --
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="card-footer">
                                                    <a href="${pageContext.request.contextPath}/localisation/edit/${position.id}"
                                                        class="btn-sm btn-outline">
                                                        <i class="fas fa-edit"></i>
                                                        Modifier
                                                    </a>
                                                    <form
                                                        action="${pageContext.request.contextPath}/localisation/delete/${position.id}"
                                                        method="post" style="display: inline; margin: 0;"
                                                        onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cette publication ?');">
                                                        <button type="submit" class="btn-sm btn-outline"
                                                            style="background: transparent; border: 1px solid #d1d5db; color: #c62828; cursor: pointer;">
                                                            <i class="fas fa-trash"></i>
                                                            Supprimer
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state-cards">
                                            <i class="fas fa-map-marker-alt"></i>
                                            <p>Aucune localisation publiée pour le moment.</p>
                                            <a href="${pageContext.request.contextPath}/localisation/form"
                                                class="btn-add"
                                                style="display: inline-block; margin-top: 15px; text-decoration: none;">
                                                Publier une première position
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- Pagination -->
                            <div
                                style="margin-top: 25px; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px;">
                                <span style="color: #6b7280; font-size: 14px;">
                                    <i class="fas fa-info-circle"></i>
                                    Affichage de <strong>${positions.size()}</strong> localisation(s)
                                </span>
                            </div>

                        </div>

                    </div>

                </div>

            </body>

            </html>