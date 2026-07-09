<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Modifier la depense</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="sessions"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <a href="${pageContext.request.contextPath}/session/${session.id}/depenses" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour aux depenses
        </a>

        <div class="form-section">

            <form action="${pageContext.request.contextPath}/session/${session.id}/depenses/${depense.id}/modifier" method="post">

                <h1>
                    <i class="fas fa-edit" style="color:var(--primary);margin-right:12px;"></i>
                    Modifier la depense #${depense.id}
                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">
                    Session #${session.id} - ${session.truck.immatriculation} - ${session.dateSession}
                </p>

                <hr>
                <br>

                <div class="form-group">
                    <label for="idTypeDepense">Type de depense *</label>
                    <select id="idTypeDepense" name="idTypeDepense" required>
                        <option value="">-- Choisir un type --</option>
                        <c:forEach items="${types}" var="t">
                            <option value="${t.id}" ${t.id == depense.typeDepense.id ? 'selected' : ''}>${t.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="montantDepense">Montant (€) *</label>
                    <input type="number" id="montantDepense" name="montantDepense"
                           step="0.01" min="0" required
                           value="${depense.montantDepense}"
                           placeholder="Ex: 50.00">
                </div>

                <div class="form-group">
                    <label for="raisonDetaillee">Raison detaillee *</label>
                    <textarea id="raisonDetaillee" name="raisonDetaillee" rows="4"
                              required placeholder="Decrivez la raison de cette depense..."
                              style="padding:10px 14px;border:1px solid #d1d5db;border-radius:10px;width:100%;resize:vertical;">${depense.raisonDetaillee}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-save"></i>
                        Modifier
                    </button>
                    <a href="${pageContext.request.contextPath}/session/${session.id}/depenses" class="btn-secondary">
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
