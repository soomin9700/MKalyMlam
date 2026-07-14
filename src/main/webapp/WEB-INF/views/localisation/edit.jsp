<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier la position du truck</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="localisation"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <!-- Retour -->
        <a href="${pageContext.request.contextPath}/localisation/list"
           class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des positions
        </a>

        <div class="form-section">

            <!-- Affichage des messages -->
            <c:if test="${not empty success}">
                <div class="alert alert-success" style="padding: 15px; background: #d4edda; border-radius: 8px; color: #155724; margin-bottom: 20px;">
                    <i class="fas fa-check-circle"></i> ${success}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" style="padding: 15px; background: #f8d7da; border-radius: 8px; color: #721c24; margin-bottom: 20px;">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/localisation/update/${position.id}" method="post">

                <h1>
                    <i class="fas fa-edit" style="color: var(--primary); margin-right: 12px;"></i>
                    Modifier la position
                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">
                    Modifiez la zone ou l'heure d'arrivée de la publication.
                </p>

                <hr style="border:none;border-top:1px solid var(--gray);margin:20px 0;">
                <br>

                <!-- Informations de la session (lecture seule) -->
                <div class="form-group" style="background: #f8f9fa; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                    <label style="font-weight: 600;">Truck en session</label>
                    <p style="margin: 5px 0 0 0; color: #111827;">
                        <strong>${position.sessionTruck.truck.immatriculation}</strong> - 
                        ${position.sessionTruck.itineraire.nomZone}
                        (${position.sessionTruck.dateSession})
                    </p>
                    <small>
                        La session ne peut pas être modifiée. Pour changer de truck, supprimez et créez une nouvelle publication.
                    </small>
                </div>

                <!-- Sélection de l'Itinéraire (Zone) -->
                <div class="form-group">
                    <label for="idItineraire">
                        Zone / Itinéraire
                        <span class="required-star">*</span>
                    </label>
                    <select id="idItineraire" name="idItineraire" required>
                        <option value="">-- Sélectionner une zone --</option>
                        <c:forEach items="${itineraires}" var="itineraire">
                            <option value="${itineraire.id}" ${itineraire.id == position.itineraire.id ? 'selected' : ''}>
                                ${itineraire.nomZone} - ${itineraire.lieuExact}
                                (${itineraire.heureDebutPrevue.toString().substring(0, 5)} - ${itineraire.heureFinPrevue.toString().substring(0, 5)})
                            </option>
                        </c:forEach>
                    </select>
                    <small>
                        Sélectionnez la nouvelle zone où le truck sera positionné.
                    </small>
                </div>

                <!-- Heure d'arrivée -->
                <div class="form-group">
                    <label for="heureArrivee">
                        Heure d'arrivée
                    </label>
                    <input type="time" id="heureArrivee" name="heureArrivee" 
                           value="${position.heureArrivee.toString().substring(0, 5)}">
                    <small>
                        Modifiez l'heure d'arrivée si nécessaire.
                    </small>
                </div>

                <!-- Date de publication (lecture seule) -->
                <div class="form-group" style="background: #f8f9fa; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                    <label style="font-weight: 600;">Date de publication</label>
                    <p style="margin: 5px 0 0 0; color: #111827;">
                        ${position.datePublication.toString()}
                    </p>
                    <small>
                        La date de publication ne peut pas être modifiée.
                    </small>
                </div>

                <!-- Boutons d'action -->
                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-save"></i>
                        Mettre à jour
                    </button>

                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>

                    <a href="${pageContext.request.contextPath}/localisation/list"
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