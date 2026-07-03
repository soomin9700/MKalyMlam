<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${isEdit}">
                Modifier un inventaire
            </c:when>
            <c:otherwise>
                Ajouter un inventaire
            </c:otherwise>
        </c:choose>
    </title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

</head>
<body>
    
<div class="dashboard">

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <a href="${pageContext.request.contextPath}/inventaire/findAll" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des inventaires
        </a>

        <div class="form-section">

            <form action="${actionUrl}" method="post">

                <h1>
                    <i class="fas fa-clipboard-list" style="color: var(--primary); margin-right:12px;"></i>

                    <c:choose>
                        <c:when test="${isEdit}">
                            Mettre à jour un inventaire
                        </c:when>
                        <c:otherwise>
                            Ajouter un nouvel inventaire
                        </c:otherwise>
                    </c:choose>

                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">

                    <c:choose>
                        <c:when test="${isEdit}">
                            Modifiez les informations de l'inventaire sélectionné.
                        </c:when>
                        <c:otherwise>
                            Remplissez tous les champs pour enregistrer un inventaire dans la base de données.
                        </c:otherwise>
                    </c:choose>

                </p>

                <hr>
                <br>

                <!-- ID caché -->
                <c:if test="${isEdit}">
                    <input type="hidden" name="idInventaire" value="${inventaire.idInventaire}">
                </c:if>

                <!-- Session Truck -->
                <div class="form-group">

                    <label for="idSession">
                        Session Truck *
                    </label>

                    <select name="sessionTruck.id" id="idSession" required>
                        <option value="">-- Choisir une session --</option>
                        <c:forEach items="${sessions}" var="session">
                            <option value="${session.id}"
                                ${session.id == inventaire.sessionTruck.id ? 'selected' : ''}>
                                Session #${session.id} - ${session.dateSession}
                            </option>
                        </c:forEach>
                    </select>

                </div>

                <!-- Date Inventaire -->
                <div class="form-group">

                    <label for="dateInventaire">
                        Date de l'inventaire *
                    </label>

                    <input
                        type="date"
                        id="dateInventaire"
                        name="dateInventaire"
                        value="${inventaire.dateInventaire}"
                        required>

                </div>

                <!-- Type Item -->
                <div class="form-group">

                    <label for="idTypeItem">
                        Type d'item *
                    </label>

                    <select name="typeItem.idTypeItem" id="idTypeItem" required>
                        <option value="">-- Choisir un type --</option>
                        <c:forEach items="${typeItems}" var="typeItem">
                            <option value="${typeItem.idTypeItem}"
                                ${typeItem.idTypeItem == inventaire.typeItem.idTypeItem ? 'selected' : ''}>
                                ${typeItem.libelle}
                            </option>
                        </c:forEach>
                    </select>

                </div>

                <!-- Quantité Physique Constatée -->
                <div class="form-group">

                    <label for="quantitePhysiqueConstatee">
                        Quantité physique constatée *
                    </label>

                    <input
                        type="number"
                        id="quantitePhysiqueConstatee"
                        name="quantitePhysiqueConstatee"
                        step="0.01"
                        min="0"
                        value="${inventaire.quantitePhysiqueConstatee}"
                        placeholder="Ex: 25.50"
                        required>

                </div>

                <!-- Quantité Théorique Système -->
                <div class="form-group">

                    <label for="quantiteTheoriqueSysteme">
                        Quantité théorique système *
                    </label>

                    <input
                        type="number"
                        id="quantiteTheoriqueSysteme"
                        name="quantiteTheoriqueSysteme"
                        step="0.01"
                        min="0"
                        value="${inventaire.quantiteTheoriqueSysteme}"
                        placeholder="Ex: 25.00"
                        required>

                    <small style="color:#6b7280;display:block;margin-top:5px;">
                        <i class="fas fa-calculator"></i>
                        L'écart sera calculé automatiquement : Quantité physique - Quantité théorique
                    </small>

                </div>

                <!-- Écart (Lecture seule) -->
                <c:if test="${isEdit}">
                    <div class="form-group">

                        <label for="ecartInventaire">
                            Écart (calculé automatiquement)
                        </label>

                        <input
                            type="number"
                            id="ecartInventaire"
                            name="ecartInventaire"
                            step="0.01"
                            value="${inventaire.ecartInventaire}"
                            readonly
                            style="background-color:#f3f4f6;cursor:not-allowed;">

                    </div>
                </c:if>

                <!-- Boutons -->
                <div class="form-actions">

                    <button type="submit" class="btn-success">

                        <c:choose>
                            <c:when test="${isEdit}">
                                <i class="fas fa-pen"></i>
                                Modifier
                            </c:when>
                            <c:otherwise>
                                <i class="fas fa-save"></i>
                                Enregistrer l'inventaire
                            </c:otherwise>
                        </c:choose>

                    </button>

                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>

                    <a href="${pageContext.request.contextPath}/inventaire/findAll"
                       style="margin-left:auto;align-self:center;color:var(--primary);">

                        <i class="fas fa-times"></i>
                        Annuler

                    </a>

                </div>

            </form>

        </div>

    </div>

</div>

</body>
</html>