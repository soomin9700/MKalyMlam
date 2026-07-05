<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Approvisionnement</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="approvisionnements"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <c:if test="${not empty successMessage}">
            <div style="margin-bottom:20px;padding:14px 18px;border-radius:12px;background:#ecfdf5;color:#047857;font-weight:600;">
                ${successMessage}
            </div>
        </c:if>

        <div class="table-container">
            <div class="table-header">
                <h1>
                    <i class="fas fa-cart-plus" style="color:var(--primary);margin-right:10px;"></i>
                    Approvisionnement
                </h1>

                <div style="font-weight:600;color:#374151;">
                    Date : <span id="dateApprovisionnement"></span>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/approvisionnements/enregistrer" method="post">
                <div style="display:flex;justify-content:space-between;align-items:center;gap:16px;margin-bottom:18px;flex-wrap:wrap;">
                    <div style="color:#6b7280;">
                        Ingrédients dont le stock est inférieur au seuil d'alerte.
                    </div>

                    <div style="display:flex;align-items:center;gap:14px;flex-wrap:wrap;">
                        <strong>Total estimé : <span id="totalEstime">0.00</span></strong>
                        <button type="submit" class="btn-success" <c:if test="${empty besoins}">disabled</c:if>>
                            <i class="fas fa-save"></i>
                            Enregistrer
                        </button>
                    </div>
                </div>

                <table>
                    <thead>
                    <tr>
                        <th>Ingrédient</th>
                        <th>Stock actuel</th>
                        <th>Seuil stock</th>
                        <th>Prix estimé unitaire</th>
                        <th>Quantité à acheter</th>
                        <th>Coût estimé</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:if test="${empty besoins}">
                        <tr>
                            <td colspan="6">
                                <div class="empty-state">
                                    <i class="fas fa-check-circle" style="font-size:48px;color:#10b981;margin-bottom:15px;display:block;"></i>
                                    <p>Aucun ingrédient sous le seuil pour le moment.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>

                    <c:forEach items="${besoins}" var="besoin" varStatus="status">
                        <tr class="ligne-approvisionnement">
                            <td>
                                <strong>${besoin.nomIngredient}</strong>
                                <input type="hidden" name="details[${status.index}].idIngredient" value="${besoin.idIngredient}">
                                <input type="hidden" name="details[${status.index}].stockActuel" value="${besoin.stockActuel}">
                                <input type="hidden" name="details[${status.index}].prixEstimeUnitaire" value="${besoin.prixEstimeUnitaire}">
                                <input class="cout-hidden" type="hidden" name="details[${status.index}].coutEstime" value="${besoin.coutEstime}">
                            </td>
                            <td>
                                <span class="price-tag">${besoin.stockActuel}</span>
                            </td>
                            <td>${besoin.seuilStock}</td>
                            <td>
                                <span class="prix-estime" data-prix="${besoin.prixEstimeUnitaire}">
                                    ${besoin.prixEstimeUnitaire}
                                </span>
                            </td>
                            <td>
                                <input type="number"
                                       class="quantite-acheter"
                                       name="details[${status.index}].quantiteAAcheter"
                                       step="0.01"
                                       min="0"
                                       value="${besoin.quantiteAAcheter}"
                                       style="width:120px;">
                                ${besoin.uniteMesure}
                            </td>
                            <td>
                                <strong><span class="cout-estime">${besoin.coutEstime}</span></strong>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>

        <div class="table-container" style="margin-top:24px;">
            <div class="table-header">
                <h1>
                    <i class="fas fa-clock-rotate-left" style="color:var(--primary);margin-right:10px;"></i>
                    Historique des approvisionnements
                </h1>
            </div>

            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Date</th>
                    <th>Coût total estimé</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${empty historiques}">
                    <tr>
                        <td colspan="4">
                            <div class="empty-state">
                                <p>Aucun approvisionnement enregistré.</p>
                            </div>
                        </td>
                    </tr>
                </c:if>

                <c:forEach items="${historiques}" var="approvisionnement">
                    <tr>
                        <td><span class="product-id">${approvisionnement.idApprovisionnement}</span></td>
                        <td>${approvisionnement.dateApprovisionnement}</td>
                        <td><span class="price-tag">${approvisionnement.coutTotalEstime}</span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/approvisionnements/${approvisionnement.idApprovisionnement}"
                               class="btn-edit">
                                <i class="fas fa-eye"></i>
                                Détail
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

<script>
    (function () {
        const dateBox = document.getElementById('dateApprovisionnement');
        const totalBox = document.getElementById('totalEstime');

        dateBox.textContent = new Date().toISOString().slice(0, 10);

        function formatNumber(value) {
            return value.toFixed(2);
        }

        function recalculer() {
            let total = 0;

            document.querySelectorAll('.ligne-approvisionnement').forEach(function (row) {
                const prix = parseFloat(row.querySelector('.prix-estime').dataset.prix) || 0;
                const quantiteInput = row.querySelector('.quantite-acheter');
                const quantite = parseFloat(quantiteInput.value) || 0;
                const cout = prix * quantite;

                row.querySelector('.cout-estime').textContent = formatNumber(cout);
                row.querySelector('.cout-hidden').value = formatNumber(cout);
                total += cout;
            });

            totalBox.textContent = formatNumber(total);
        }

        document.querySelectorAll('.quantite-acheter').forEach(function (input) {
            input.addEventListener('input', recalculer);
        });

        recalculer();
    })();
</script>

</body>
</html>
