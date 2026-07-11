<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>Nouvelle vente</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

</head>

<body>

<div class="dashboard">

    <c:set var="activeMenu" value="vente"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp"/>

    <div class="main">

        <div class="table-container">

            <div class="table-header">

    <h1>
        <i class="fas fa-cash-register"
           style="color:var(--primary);margin-right:10px;"></i>
        Nouvelle vente
    </h1>

    <div style="display:flex; gap:10px; align-items:center;">
        <button class="btn-add" onclick="nouvelleCommande()">
            <i class="fas fa-plus"></i>
            Nouvelle commande
        </button>

            </div>
        <!-- Boutons d'export (ne cassent rien) -->
        <a href="${pageContext.request.contextPath}/commandes/export/csv"
           class="btn-add" style="background:#f0f0f0; color:#1a1a1a; border:1px solid #ddd;">
            <i class="fas fa-file-csv"></i> CSV
        </a>
        <a href="${pageContext.request.contextPath}/commandes/export/pdf" target="_blank"
           class="btn-add" style="background:#f0f0f0; color:#1a1a1a; border:1px solid #ddd;">
            <i class="fas fa-file-pdf"></i> PDF
        </a>
    </div>

</div>
            <h2 style="margin:25px 0;">
                Commande N°
                <span id="cmdId">-</span>
            </h2>

            <table>

                <thead>

                <tr>

                    <th>Produit</th>

                    <th>Prix unitaire</th>

                    <th>Quantité</th>

                    <th>Montant</th>

                    <th>Action</th>

                </tr>

                </thead>

                <tbody id="lignes">

                </tbody>

                <tfoot>

                <tr>

                    <td>

                        <select id="selectProduit">

                            <option value="">

                                Choisir un produit

                            </option>

                        </select>

                    </td>

                    <td id="prixUnitaire">-</td>

                    <td>

                        <input
                                id="inputQuantite"
                                type="number"
                                min="1"
                                value="1">

                    </td>

                    <td id="previewMontant">-</td>

                    <td>

                        <button
                                class="btn-success"
                                onclick="ajouterLigne()">

                            <i class="fas fa-plus"></i>

                            Ajouter

                        </button>

                    </td>

                </tr>

                </tfoot>

            </table>

            <div class="total-container">

                <div class="total-card">

                    <div class="total-label">
                        Montant total
                    </div>

                    <div id="total" class="total-value">
                        0 Ar
                    </div>

                </div>

                <button
                        class="btn-success"
                        onclick="validerCommande()"
                        style="margin-top:15px;">

                    <i class="fas fa-check"></i>
                    Valider la commande

                </button>

            </div>

            <div class="table-header" style="margin-top:35px;">

                <h1>
                    <i class="fas fa-file-invoice"
                       style="color:var(--primary);margin-right:10px;"></i>
                    Liste des factures
                </h1>

                <div style="display:flex;gap:10px;align-items:center;flex-wrap:wrap;">
                    <a href="${pageContext.request.contextPath}/vente/factures/export/csv"
                       class="btn-secondary"
                       style="height:44px;display:inline-flex;align-items:center;justify-content:center;text-decoration:none;">
                        <i class="fas fa-file-csv"></i>
                        CSV
                    </a>

                    <a href="${pageContext.request.contextPath}/vente/factures/export/pdf"
                       class="btn-secondary"
                       style="height:44px;display:inline-flex;align-items:center;justify-content:center;text-decoration:none;">
                        <i class="fas fa-file-pdf"></i>
                        PDF
                    </a>
                </div>

            </div>

            <table>

                <thead>

                <tr>
                    <th>ID facture</th>
                    <th>ID commande</th>
                    <th>Référence</th>
                    <th>Date facturation</th>
                    <th>Mode paiement</th>
                    <th>Taxes brut</th>
                    <th>Montant total</th>
                </tr>

                </thead>

                <tbody>

                <tr>
                    <td>1</td>
                    <td>101</td>
                    <td>FAC-2026-001</td>
                    <td>2026-07-03 09:15</td>
                    <td>Espèces</td>
                    <td>1 200 Ar</td>
                    <td>24 000 Ar</td>
                </tr>

                <tr>
                    <td>2</td>
                    <td>102</td>
                    <td>FAC-2026-002</td>
                    <td>2026-07-03 10:40</td>
                    <td>Mobile Money</td>
                    <td>2 500 Ar</td>
                    <td>50 000 Ar</td>
                </tr>

                <tr>
                    <td>3</td>
                    <td>103</td>
                    <td>FAC-2026-003</td>
                    <td>2026-07-03 11:25</td>
                    <td>Carte</td>
                    <td>1 800 Ar</td>
                    <td>36 000 Ar</td>
                </tr>

                <tr>
                    <td>4</td>
                    <td>104</td>
                    <td>FAC-2026-004</td>
                    <td>2026-07-03 13:10</td>
                    <td>Espèces</td>
                    <td>900 Ar</td>
                    <td>18 000 Ar</td>
                </tr>

                </tbody>

            </table>
        </div>

    </div>

</div>

<script src="${pageContext.request.contextPath}/static/commande.js"></script>
</body>
</html>
