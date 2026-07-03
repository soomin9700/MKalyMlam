<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mouvements d'équipement</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="mouvements-equipement"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/dashboard"
        class="${activeMenu == 'dashboard' ? 'active' : ''}">
            Retour
        </a>
        <div class="form-section">
            <form action="${actionUrl}" method="post">
                <h1>
                    <i class="fas fa-exchange-alt" style="color: var(--primary); margin-right: 12px;"></i>
                        Mouvement des équipements
                    </h1>

                <hr>
                <br>

                <div class="form-group">
                    <label for="typeMouvement">Type de mouvement *</label>
                    <select id="typeMouvement" name="typeMouvement.idTypeMouvement" required>
                        <c:forEach items="${typeMouvements}" var="typeMouvement">
                            <option value="${typeMouvement.idTypeMouvement}">
                                ${typeMouvement.libelle}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="equipement">Équipement *</label>
                    <select id="equipement" name="equipement.idEquipement" required>
                        <c:forEach items="${equipements}" var="equipementItem">
                            <option value="${equipementItem.idEquipement}">
                                ${equipementItem.nomEquipement}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="quantite">Quantité *</label>
                    <input type="number"
                           id="quantite"
                           name="quantite"
                           step="0.01"
                           min="0"
                           value="${mouvementEquipement.quantite}"
                           placeholder="Ex: 5.00"
                           required>
                </div>

                <div class="form-group">
                    <label for="dateMouvement">Date du mouvement *</label>
                    <input type="date"
                           id="dateMouvement"
                           name="dateMouvement"
                           value="${mouvementEquipement.dateMouvement}"
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
                <h1>Liste des mouvements d'équipement</h1>
                <a href="${pageContext.request.contextPath}/mouvements-equipement/export" class="btn-secondary" style="margin-left: 16px; padding: 8px 14px; display: inline-block; text-decoration: none;">
                    <i class="fas fa-file-csv"></i>&nbsp;Exporter CSV
                </a>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Équipement</th>
                    <th>Type de mouvement</th>
                    <th>Quantité</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${mouvements}" var="mouvement">
                    <tr>
                        <td>${mouvement.equipement.nomEquipement}</td>
                        <td>${mouvement.typeMouvement.libelle}</td>
                        <td>${mouvement.quantite}</td>
                        <td>${mouvement.dateMouvement}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
