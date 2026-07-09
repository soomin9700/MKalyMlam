<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sortie de stock</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
</head>
<body>

<div class="dashboard">

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <a href="${pageContext.request.contextPath}/lot/findAll" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des lots
        </a>

        <div class="form-section">

            <form action="${pageContext.request.contextPath}/mouvements/sortie/${lot.idLot}" method="post">

                <h1>
                    <i class="fas fa-minus-circle" style="color: var(--primary); margin-right:12px;"></i>
                    Sortie de stock
                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">
                    Enregistrer une sortie de stock pour le lot <strong>#${lot.idLot}</strong>
                    (${lot.ingredient.nomIngredient}).
                </p>

                <hr>
                <br>

                <div class="form-group">
                    <label>Ingrédient</label>
                    <input type="text" class="form-control" value="${lot.ingredient.nomIngredient}" readonly style="padding:10px 14px;border:1px solid #d1d5db;border-radius:10px;background:#f3f4f6;width:100%;">
                </div>

                <div class="form-group">
                    <label>Quantité restante actuelle</label>
                    <input type="text" class="form-control" value="${quantiteRestante}" readonly style="padding:10px 14px;border:1px solid #d1d5db;border-radius:10px;background:#f3f4f6;width:100%;">
                </div>

                <c:if test="${not empty param.error}">
                    <div style="background:#fee2e2;color:#b91c1c;padding:12px 16px;border-radius:10px;margin-bottom:20px;">
                        <i class="fas fa-exclamation-circle"></i>
                        ${param.error}
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="quantite">Quantité à sortir *</label>
                    <input type="number"
                           id="quantite"
                           name="quantite"
                           step="0.01"
                           min="0.01"
                           max="${quantiteRestante}"
                           required
                           placeholder="Ex: 2.5"
                           style="padding:10px 14px;border:1px solid #d1d5db;border-radius:10px;width:100%;">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-check"></i>
                        Valider la sortie
                    </button>
                    <a href="${pageContext.request.contextPath}/lot/findAll" class="btn-secondary">
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
