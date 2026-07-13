<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Publier la position du truck</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

<div class="dashboard">
    <c:set var="activeMenu" value="truck"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <!-- Retour -->
        <a href="${pageContext.request.contextPath}/truck"
           class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des trucks
        </a>

        <div class="form-section">

            <form action="${actionUrl}" method="post">

                <!-- ID caché (pour modification) -->
                <c:if test="${isEdit}">
                    <input type="hidden" name="id" value="${truck.idTruck}">
                </c:if>

                <h1>
                    <i class="fas fa-truck" style="color: var(--primary); margin-right: 12px;"></i>
                    <c:choose>
                        <c:when test="${isEdit}">
                            Modifier la position du truck
                        </c:when>
                        <c:otherwise>
                            Publier la position d'un truck
                        </c:otherwise>
                    </c:choose>
                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">
                    <c:choose>
                        <c:when test="${isEdit}">
                            Modifiez les informations de position du truck sélectionné.
                        </c:when>
                        <c:otherwise>
                            Sélectionnez un truck et définissez sa zone et ses horaires de présence.
                        </c:otherwise>
                    </c:choose>
                </p>

                <hr style="border:none;border-top:1px solid var(--gray);margin:20px 0;">
                <br>

                <!-- Sélection du Truck -->
                <div class="form-group">
                    <label for="truck">
                        Truck
                        <span class="required-star">*</span>
                    </label>
                    <select id="truck" name="truck" required>
                        <option value="">-- Sélectionner un truck --</option>
                        <option value="1">1234 TMA - Toyota</option>
                        <option value="2">5678 TMA - Mercedes</option>
                        <option value="3">9012 TMA - Ford</option>
                    </select>
                    <small>
                        Sélectionnez le véhicule à positionner.
                    </small>
                </div>

                <!-- Sélection de la Zone -->
                <div class="form-group">
                    <label for="zone">
                        Zone
                        <span class="required-star">*</span>
                    </label>
                    <select id="zone" name="zone" required>
                        <option value="">-- Sélectionner une zone --</option>
                        <option value="1">Analakely - Devant la gare</option>
                        <option value="2">Ivandry - Leader Price</option>
                        <option value="3">Antanimena - Université</option>
                        <option value="4">Ankorondrano - Orange</option>
                        <option value="5">Isotry - Marché</option>
                        <option value="6">Behoririka - Gare routière</option>
                        <option value="7">Anosy - Lac Anosy</option>
                        <option value="8">Ambohijatovo - BNI</option>
                        <option value="9">Antaninandro - Marché</option>
                        <option value="10">Ambodivona - Telma</option>
                    </select>
                    <small>
                        Sélectionnez la zone où le truck sera positionné.
                    </small>
                </div>

                <!-- Boutons d'action -->
                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-paper-plane"></i>
                        <c:choose>
                            <c:when test="${isEdit}">
                                Mettre à jour
                            </c:when>
                            <c:otherwise>
                                Publier la position
                            </c:otherwise>
                        </c:choose>
                    </button>

                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>

                    <a href="${pageContext.request.contextPath}/truck"
                       style="margin-left:auto; align-self:center; color:var(--primary); text-decoration:none;">
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