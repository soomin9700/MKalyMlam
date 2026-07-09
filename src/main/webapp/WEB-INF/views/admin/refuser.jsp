<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Refuser la depense</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="validation"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <div class="table-header">
                <h1>
                    <i class="fas fa-times-circle" style="color:#ef4444;margin-right:10px;"></i>
                    Refuser la depense #${depense.id}
                </h1>
            </div>

            <div style="margin-bottom:20px;padding:16px 18px;background:#f9fafb;border:1px solid #e5e7eb;border-radius:14px;">
                <p><strong>Session :</strong> #${depense.session.id} - ${depense.session.truck.immatriculation}</p>
                <p><strong>Type :</strong> ${depense.typeDepense.libelle}</p>
                <p><strong>Montant :</strong> ${depense.montantDepense} €</p>
                <p><strong>Raison :</strong> ${depense.raisonDetaillee}</p>
                <p><strong>Date :</strong> ${depense.dateDepense}</p>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/admin/depenses/${depense.id}/refuser">
                <div class="form-group">
                    <label for="commentaire">Commentaire (optionnel)</label>
                    <textarea id="commentaire" name="commentaire" rows="4"
                              placeholder="Raison du refus..." style="width:100%;resize:vertical;"></textarea>
                </div>

                <div style="display:flex;gap:10px;margin-top:16px;">
                    <a href="${pageContext.request.contextPath}/admin/depenses" class="btn-secondary" style="padding:10px 20px;text-decoration:none;">
                        Annuler
                    </a>
                    <button type="submit" class="btn-danger" style="padding:10px 20px;">
                        <i class="fas fa-times"></i> Confirmer le refus
                    </button>
                </div>
            </form>

        </div>
    </div>
</div>

</body>
</html>
