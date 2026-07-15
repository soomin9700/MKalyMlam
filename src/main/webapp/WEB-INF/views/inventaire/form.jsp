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

    <script>
        function updateItemList() {
            var typeItemSelect = document.getElementById("idTypeItem");
            var idItemSelect = document.getElementById("idItem");
            var selectedType = typeItemSelect.value;
            
            // Cacher tous les groupes d'options
            document.getElementById("ingredients-group").style.display = "none";
            document.getElementById("equipements-group").style.display = "none";
            
            if (selectedType == "1") {
                // INGREDIENT
                document.getElementById("ingredients-group").style.display = "block";
                document.getElementById("idItem").name = "idItem";
            } else if (selectedType == "2") {
                // EQUIPEMENT
                document.getElementById("equipements-group").style.display = "block";
                document.getElementById("idItem").name = "idItem";
            } else {
                document.getElementById("idItem").name = "";
            }
        }
    </script>

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
                    <select name="typeItem.idTypeItem" id="idTypeItem" required onchange="updateItemList()">
                        <option value="">-- Choisir un type --</option>
                        <c:forEach items="${typeItems}" var="typeItem">
                            <option value="${typeItem.idTypeItem}"
                                ${typeItem.idTypeItem == inventaire.typeItem.idTypeItem ? 'selected' : ''}>
                                ${typeItem.libelle}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Item (Ingrédient ou Équipement) -->
                <div class="form-group">
                    <label for="idItem">
                        Item *
                    </label>

                    <select name="idItem" id="idItem" required>
                        <option value="">-- Choisir un item --</option>
                        
                        <!-- Ingrédients -->
                        <optgroup label="Ingrédients">
                            <c:forEach items="${ingredients}" var="ingredient">
                                <option value="${ingredient.idIngredient}"
                                    ${ingredient.idIngredient == inventaire.idItem && inventaire.typeItem.idTypeItem == 1 ? 'selected' : ''}>
                                    ${ingredient.nomIngredient} (${ingredient.uniteMesure})
                                </option>
                            </c:forEach>
                        </optgroup>
                        
                        <!-- Équipements -->
                        <optgroup label="Équipements">
                            <c:forEach items="${equipements}" var="equipement">
                                <option value="${equipement.idEquipement}"
                                    ${equipement.idEquipement == inventaire.idItem && inventaire.typeItem.idTypeItem == 2 ? 'selected' : ''}>
                                    ${equipement.nomEquipement}
                                </option>
                            </c:forEach>
                        </optgroup>
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
                    <small style="color:#6b7280;display:block;margin-top:5px;">
                        <i class="fas fa-calculator"></i>
                        L'écart entre la quantité physiaue et théorique sera calculé automatiquement : Quantité physique - Quantité théorique
                    </small>
                </div>

                <div class="form-group">
                    <input type="hidden" name="quantiteTheoriqueSysteme" value="${inventaire.quantiteTheoriqueSysteme}">
                    <label for="quantiteTheoriqueSysteme">
                        Quantité théorique système (calculée automatiquement)
                    </label>
                    <input
                        type="number"
                        id="quantiteTheoriqueSysteme"
                        name="quantiteTheoriqueSysteme"
                        step="0.01"
                        value="${inventaire.quantiteTheoriqueSysteme}"
                        readonly
                        style="background-color:#f3f4f6;cursor:not-allowed;">
                    <small style="color:#6b7280;display:block;margin-top:5px;">
                        <i class="fas fa-calculator"></i>
                        Cette quantité est calculée automatiquement à partir des lots d'ingrédients.
                    </small>
                </div>

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