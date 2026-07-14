<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Publication de truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="truck"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/truck" class="back-link">
            <i class="fas fa-arrow-left"></i> Retour à la liste des trucks
        </a>

        <div class="form-section">
            <h1><i class="fas fa-bullhorn" style="color: var(--primary); margin-right: 12px;"></i> Publication de truck</h1>
            <p style="color:#6b7280;margin-bottom:20px;">Publiez un message lié à une session de truck, à un produit ou à un type de notification.</p>

            <form action="${actionUrl}" method="post" style="display:grid;gap:16px;max-width:900px;">
                <div class="form-group">
                    <label for="typeNotification">Type de notification <span class="required-star">*</span></label>
                    <select id="typeNotification" name="typeNotification" required>
                        <option value="">-- Sélectionner un type --</option>
                        <c:forEach var="type" items="${typesNotification}">
                            <option value="${type.idTypeNotification}">${type.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="titre">Titre <span class="required-star">*</span></label>
                    <input type="text" id="titre" name="titre" required placeholder="Ex. Arrivée au point de vente" />
                </div>

                <div class="form-group">
                    <label for="message">Message <span class="required-star">*</span></label>
                    <textarea id="message" name="message" rows="4" required placeholder="Décrivez la publication..."></textarea>
                </div>

                <div class="form-group">
                    <label for="produitLie">Produit lié (facultatif)</label>
                    <select id="produitLie" name="produitLie">
                        <option value="">-- Aucun produit --</option>
                        <c:forEach var="produit" items="${produits}">
                            <option value="${produit.idProduit}">${produit.nomProduit}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="sessionLiee">Session liée (facultatif)</label>
                    <select id="sessionLiee" name="sessionLiee">
                        <option value="">-- Aucune session --</option>
                        <c:forEach var="session" items="${sessions}">
                            <option value="${session.id}">Session #${session.id}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-success"><i class="fas fa-paper-plane"></i> Publier</button>
                    <button type="reset" class="btn-secondary"><i class="fas fa-undo"></i> Réinitialiser</button>
                </div>
            </form>
        </div>

        <div class="form-section" style="margin-top:24px;">
            <h2><i class="fas fa-list"></i> Publications du jour</h2>
            <c:if test="${empty notifications}">
                <p>Aucune publication pour le moment.</p>
            </c:if>
            <c:if test="${not empty notifications}">
                <div style="display:grid;gap:14px;">
                    <c:forEach var="notif" items="${notifications}">
                        <div style="border:1px solid #e5e7eb;border-radius:10px;padding:14px;background:#fff;box-shadow:0 2px 6px rgba(0,0,0,0.04);">
                            <div style="display:flex;justify-content:space-between;align-items:center;gap:12px;flex-wrap:wrap;">
                                <strong>${notif.titre}</strong>
                                <span style="color:#6b7280;font-size:0.9rem;">${notif.dateHeureEnvoi}</span>
                            </div>
                            <p style="margin:8px 0 6px;">${notif.message}</p>
                            <small>
                                Type: ${notif.typeNotification != null ? notif.typeNotification.libelle : '-'} |
                                Session: ${notif.sessionLiee != null ? notif.sessionLiee.id : '-'} |
                                Produit: ${notif.produitLie != null ? notif.produitLie.nomProduit : '-'}
                            </small>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
