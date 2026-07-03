<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des équipements</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form_equip.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="equipements"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

    <a href="${pageContext.request.contextPath}/dashboard"
    class="${activeMenu == 'dashboard' ? 'active' : ''}">
        Retour
    </a>

        <div class="form-section">
            <form action="${actionUrl}" method="post">
                <h1>
                    <i class="fas fa-cogs" style="color: var(--primary); margin-right: 12px;"></i>
                        <b>Nouvel équipement</b>
                </h1>

                <hr>
                <br>

                <div class="form-group">
                    <label for="nomEquipement">Nom *</label>
                    <input type="text"
                           id="nomEquipement"
                           name="nomEquipement"
                           value="${equipement.nomEquipement}"
                           placeholder="Ex: Chaîne, Four, Balance..."
                           required>
                </div>

                <div class="form-group">
                    <label for="typeEquipement">Catégorie *</label>
                    <select id="typeEquipement" name="typeEquipement.idTypeEquipement" required>
                        <c:forEach items="${typeEquipements}" var="typeEquipement">
                            <option value="${typeEquipement.idTypeEquipement}">
                                ${typeEquipement.libelle}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="methodeComptable">Méthode comptable *</label>
                    <select id="methodeComptable" name="methodeComptable.idMethodeComptable" required>
                        <c:forEach items="${methodesComptables}" var="methode">
                            <option value="${methode.idMethodeComptable}">
                                ${methode.libelle}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="prixUnitaire">Prix unitaire *</label>
                    <input type="number"
                           id="prixUnitaire"
                           name="prixUnitaire"
                           step="0.01"
                           min="0"
                           value="${equipement.prixUnitaire}"
                           placeholder="Ex: 1500.00"
                           required>
                </div>

                <div class="form-group">
                    <label for="quantiteMin">Quantité minimale *</label>
                    <input type="number"
                           id="quantiteMin"
                           name="quantiteMin"
                           step="0.01"
                           min="0"
                           value="${equipement.quantiteMin}"
                           placeholder="Ex: 1.00"
                           required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-save"></i>
                        Enregistrer
                    </button>
                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>
                </div>
            </form>
        </div>

        <div class="table-container" style="margin-top: 30px;">
            <div class="table-header">
                <h1>Liste des équipements</h1>
                <a href="${pageContext.request.contextPath}/equipements/export" class="btn-secondary" style="margin-left: 16px; padding: 8px 14px; display: inline-block; text-decoration: none;">
                    <i class="fas fa-file-csv"></i>&nbsp;Exporter CSV
                </a>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Catégorie</th>
                    <th>Méthode comptable</th>
                    <th>Prix unitaire</th>
                    <th>Quantité minimale</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${equipements}" var="equipementItem">
                    <tr>
                        <td>${equipementItem.nomEquipement}</td>
                        <td>${equipementItem.typeEquipement.libelle}</td>
                        <td>${equipementItem.methodeComptable.libelle}</td>
                        <td>${equipementItem.prixUnitaire}</td>
                        <td>${equipementItem.quantiteMin}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>
</html>
