<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        <c:choose>
            <c:when test="${isEdit}">
                Modifier un produit
            </c:when>
            <c:otherwise>
                Ajouter un produit
            </c:otherwise>
        </c:choose>
    </title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<body>

<div class="dashboard">

    <!-- Sidebar -->
    <div class="sidebar">
        <h2>ADMIN PANEL</h2>

        <a href="#">Dashboard</a>
        <a href="#" class="active" style="color: var(--secondary);">Produits</a>
        <a href="#">Commandes</a>
        <a href="#">Employés</a>
        <a href="#">Clients</a>
        <a href="#">Statistiques</a>
        <a href="./ingredients">Ingrédients</a>
    </div>

    <div class="main">

        <!-- Retour -->
        <a href="${pageContext.request.contextPath}/produits"
           class="back-link">

            <i class="fas fa-arrow-left"></i>
            Retour à la liste des produits

        </a>

        <div class="form-section">

            <form action="${actionUrl}" method="post">

                <!-- ID caché -->

                <c:if test="${isEdit}">
                    <input type="hidden"
                           name="id"
                           value="${produit.idProduit}">
                </c:if>

                <h1>

                    <i class="fas fa-box product-icon"></i>

                    <c:choose>

                        <c:when test="${isEdit}">
                            Modifier le produit
                        </c:when>

                        <c:otherwise>
                            Ajouter un nouveau produit
                        </c:otherwise>

                    </c:choose>

                </h1>

                <p style="color:#6b7280;margin-bottom:30px;">

                    <c:choose>

                        <c:when test="${isEdit}">
                            Modifiez les informations du produit sélectionné.
                        </c:when>

                        <c:otherwise>
                            Remplissez tous les champs pour enregistrer un produit dans la base de données.
                        </c:otherwise>

                    </c:choose>

                </p>

                <hr style="border:none;border-top:1px solid var(--gray);margin:20px 0;">
                <br>

                <!-- Nom -->

                <div class="form-group">

                    <label for="nomProduit">

                        Nom du produit
                        <span class="required-star">*</span>

                    </label>

                    <input
                            type="text"
                            id="nomProduit"
                            name="nomProduit"
                            value="${produit.nomProduit}"
                            placeholder="Ex: Pizza Margherita, Burger Deluxe..."
                            required>

                </div>

                <!-- Prix -->

                <div class="form-group">

                    <label for="prixBase">

                        Prix de base (€)
                        <span class="required-star">*</span>

                    </label>

                    <input
                            type="number"
                            id="prixBase"
                            name="prixBase"
                            step="0.01"
                            min="0"
                            value="${produit.prixBase}"
                            placeholder="Ex: 12.99"
                            required>

                    <small>
                        Prix en euros, avec deux décimales maximum.
                    </small>

                </div>

                <!-- Nouveau -->

                <div class="checkbox-group">

                    <input
                            type="checkbox"
                            id="estNouveau"
                            name="estNouveau"
                            value="true"

                            <c:if test="${produit.estNouveau}">
                                checked
                            </c:if>
                    >

                    <label for="estNouveau">

                        <i class="fas fa-star"
                           style="color: var(--secondary);"></i>

                        Marquer comme nouveau produit

                        <span class="helper-text">
                            (mis en avant dans la section "Nouveautés")
                        </span>

                    </label>

                </div>

                <!-- Boutons -->

                <div class="form-actions">

                    <button
                            type="submit"
                            class="btn-success">

                        <c:choose>

                            <c:when test="${isEdit}">
                                <i class="fas fa-save"></i>
                                Mettre à jour
                            </c:when>

                            <c:otherwise>
                                <i class="fas fa-plus"></i>
                                Enregistrer le produit
                            </c:otherwise>

                        </c:choose>

                    </button>

                    <button
                            type="reset"
                            class="btn-secondary">

                        <i class="fas fa-undo"></i>
                        Réinitialiser

                    </button>

                    <a href="${pageContext.request.contextPath}/produits"
                       style="margin-left:auto;
                              align-self:center;
                              color:var(--primary);
                              text-decoration:none;">

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