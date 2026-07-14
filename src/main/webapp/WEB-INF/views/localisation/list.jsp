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

                <style>
                    .cards-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
                        gap: 20px;
                        margin-top: 20px;
                    }

                    .location-card {
                        background: white;
                        border-radius: 12px;
                        padding: 20px;
                        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
                        border-left: 4px solid var(--primary);
                        transition: transform 0.2s, box-shadow 0.2s;
                    }

                    .location-card:hover {
                        transform: translateY(-4px);
                        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                    }

                    .card-header {
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        margin-bottom: 15px;
                        padding-bottom: 12px;
                        border-bottom: 1px solid #e9ecef;
                    }

                    .card-header .truck-info {
                        display: flex;
                        align-items: center;
                        gap: 12px;
                    }

                    .card-header .truck-icon {
                        width: 40px;
                        height: 40px;
                        border-radius: 50%;
                        background: #e3f2fd;
                        color: #1976d2;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 18px;
                    }

                    .card-header .truck-name {
                        font-weight: 700;
                        font-size: 16px;
                        color: #111827;
                    }

                    .card-header .truck-plate {
                        font-size: 13px;
                        color: #6b7280;
                    }

                    .card-body {
                        display: flex;
                        flex-direction: column;
                        gap: 12px;
                    }

                    .info-row {
                        display: flex;
                        align-items: center;
                        gap: 12px;
                        padding: 8px 12px;
                        background: #f8f9fa;
                        border-radius: 8px;
                    }

                    .info-row .icon {
                        width: 28px;
                        color: #6b7280;
                        font-size: 14px;
                        text-align: center;
                    }

                    .info-row .label {
                        font-size: 13px;
                        color: #6b7280;
                        min-width: 60px;
                    }

                    .info-row .value {
                        font-weight: 600;
                        color: #111827;
                    }

                    .badge-status {
                        display: inline-flex;
                        align-items: center;
                        gap: 6px;
                        padding: 4px 14px;
                        border-radius: 20px;
                        font-size: 12px;
                        font-weight: 600;
                    }

                    .badge-status.en-cours {
                        background: #e8f5e9;
                        color: #388e3c;
                    }

                    .badge-status.termine {
                        background: #fce4ec;
                        color: #c62828;
                    }

                    .badge-status.a-venir {
                        background: #fff3e0;
                        color: #e65100;
                    }

                    .badge-status.publie {
                        background: #e3f2fd;
                        color: #1565c0;
                    }

                    .card-footer {
                        margin-top: 15px;
                        padding-top: 12px;
                        border-top: 1px solid #e9ecef;
                        display: flex;
                        justify-content: flex-end;
                        gap: 10px;
                    }

                    .btn-sm {
                        padding: 6px 14px;
                        font-size: 13px;
                        border-radius: 6px;
                        text-decoration: none;
                        display: inline-flex;
                        align-items: center;
                        gap: 6px;
                    }

                    .btn-sm.btn-primary {
                        background: var(--primary);
                        color: white;
                    }

                    .btn-sm.btn-primary:hover {
                        background: #0056b3;
                    }

                    .btn-sm.btn-outline {
                        background: transparent;
                        color: #6b7280;
                        border: 1px solid #d1d5db;
                    }

                    .btn-sm.btn-outline:hover {
                        background: #f3f4f6;
                    }

                    .empty-state-cards {
                        grid-column: 1 / -1;
                        text-align: center;
                        padding: 60px 20px;
                        background: #f8f9fa;
                        border-radius: 12px;
                    }

                    .empty-state-cards i {
                        font-size: 48px;
                        color: #d1d5db;
                        margin-bottom: 15px;
                        display: block;
                    }

                    .empty-state-cards p {
                        color: #6b7280;
                        font-size: 16px;
                    }

                    /* Fonction pour formater l'heure */
                    .time-format {
                        font-weight: 600;
                        color: #111827;
                    }
                </style>
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
                                        <i class="fas fa-plus"></i>
                                        Publier une position
                                    </a>
                                </div>
                            </div>

                            <!-- Filtres rapides -->
                            <div class="filters-container" style="margin-top: 15px;">
                                <div style="display: flex; gap: 15px; flex-wrap: wrap; align-items: center;">
                                    <span style="font-size: 14px; font-weight: 600; color: #495057;">
                                        <i class="fas fa-filter"></i> Filtres :
                                    </span>
                                    <a href="#" class="badge-status en-cours" style="text-decoration: none;">
                                        En cours
                                    </a>
                                    <a href="#" class="badge-status a-venir" style="text-decoration: none;">
                                        À venir
                                    </a>
                                    <a href="#" class="badge-status termine" style="text-decoration: none;">
                                        Terminé
                                    </a>
                                    <a href="#" style="color: #6b7280; font-size: 13px; text-decoration: none;">
                                        <i class="fas fa-undo"></i> Réinitialiser
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
                                                    <a href="#" class="btn-sm btn-outline">
                                                        <i class="fas fa-edit"></i>
                                                        Modifier
                                                    </a>
                                                    <a href="#" class="btn-sm btn-primary">
                                                        <i class="fas fa-eye"></i>
                                                        Voir
                                                    </a>
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
                                                <i class="fas fa-plus"></i>
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
                                <div style="display: flex; gap: 5px;">
                                    <span class="btn-pagination"
                                        style="padding: 8px 14px; background: var(--primary); color: white; border-radius: 6px; font-weight: 600;">1</span>
                                    <a href="#" class="btn-pagination"
                                        style="padding: 8px 14px; background: #f3f4f6; border-radius: 6px; text-decoration: none; color: #1f2937; border: 1px solid #d1d5db;">2</a>
                                    <a href="#" class="btn-pagination"
                                        style="padding: 8px 14px; background: #f3f4f6; border-radius: 6px; text-decoration: none; color: #1f2937; border: 1px solid #d1d5db;">3</a>
                                    <a href="#" class="btn-pagination"
                                        style="padding: 8px 12px; background: #f3f4f6; border-radius: 6px; text-decoration: none; color: #1f2937; border: 1px solid #d1d5db;">
                                        <i class="fas fa-angle-right"></i>
                                    </a>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

            </body>

            </html>