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

    <style>
        .btn-danger {
            background: #b91c1c;
            color: #fff;
            border: none;
            padding: 6px 12px;
            border-radius: 8px;
            cursor: pointer;
        }

        .perso-row td {
            background: #fff7f7;
            padding: 0 !important;
        }

        .perso-panel {
            padding: 18px 20px;
            border-left: 4px solid var(--primary);
        }

        .perso-panel h4 {
            margin: 14px 0 8px;
            color: var(--primary);
        }

        .perso-list {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .perso-item {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
            background: #fff;
            padding: 8px 12px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, .05);
        }

        .perso-item > span {
            flex: 1;
            min-width: 160px;
        }

        .perso-item select,
        .perso-item input {
            padding: 6px 8px;
            border: 1px solid var(--gray);
            border-radius: 8px;
            font-family: inherit;
        }

        .perso-empty {
            color: #6b7280;
            font-style: italic;
            padding: 6px 0;
        }
    </style>

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

                <button
                        class="btn-add"
                        onclick="nouvelleCommande()">

                    <%-- <i class="fas fa-plus"></i> --%>
                    Nouvelle commande

                </button>

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

            </div>
        </div>

    </div>

</div>

<script>

const ctx = '${pageContext.request.contextPath}';

let cmdId = null;
let produits = [];
let actionsList = [];
let allIngredients = [];

window.onload = function () {
    chargerReference();
};

function chargerReference() {
    fetch(ctx + '/produits/liste')
        .then(r => r.json())
        .then(data => {
            produits = data;
            const sel = document.getElementById("selectProduit");
            produits.forEach(p => {
                const opt = document.createElement("option");
                opt.value = p.idProduit;
                opt.textContent = p.nomProduit + " (" + p.prixBase + " Ar)";
                sel.appendChild(opt);
            });
        });

    fetch(ctx + '/personnalisation/actions')
        .then(r => r.json())
        .then(data => { actionsList = data; });

    fetch(ctx + '/personnalisation/ingredients')
        .then(r => r.json())
        .then(data => { allIngredients = data; });
}

document.getElementById("selectProduit").onchange = afficherPrix;
document.getElementById("inputQuantite").oninput = afficherPrix;

function afficherPrix() {
    const sel = document.getElementById("selectProduit");
    const qte = parseInt(document.getElementById("inputQuantite").value) || 0;
    const p = produits.find(x => x.idProduit == sel.value);
    document.getElementById("prixUnitaire").textContent =
        p ? p.prixBase + " Ar" : "-";
    document.getElementById("previewMontant").textContent =
        (p && qte > 0) ? (p.prixBase * qte) + " Ar" : "-";
}

function actionId(libelle) {
    const a = actionsList.find(x => x.libelle === libelle);
    if (a) return a.idActionCommande;
    return libelle === 'AJOUTER' ? 1 : 2;
}

function nouvelleCommande() {
    fetch(ctx + '/commande/ajouter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({})
    })
    .then(r => r.json())
    .then(c => {
        cmdId = c.idCommande;
        document.getElementById("cmdId").textContent = cmdId;
        document.getElementById("lignes").innerHTML = "";
        document.getElementById("total").textContent = "0 Ar";
    });
}

function ajouterLigne() {
    if (!cmdId) return;
    const pId = document.getElementById("selectProduit").value;
    const qte = parseInt(document.getElementById("inputQuantite").value);
    if (!pId || !qte) return;

    fetch(ctx + '/ligneCommande/ajouter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            idCommande: cmdId,
            idProduit: parseInt(pId),
            quantite: qte
        })
    })
    .then(r => r.json())
    .then(ligne => {
        const p = produits.find(x => x.idProduit == ligne.idProduit);
        ajouterLigneUI(ligne, p, qte);
        actualiserTotal();
    });
}

function ajouterLigneUI(ligne, p, qte) {
    const tbody = document.getElementById("lignes");

    const tr = document.createElement("tr");
    tr.setAttribute("data-ligne", ligne.idLigneCommande);
    tr.setAttribute("data-produit", ligne.idProduit);
    tr.innerHTML =
        "<td>" + p.nomProduit + "</td>" +
        "<td>" + p.prixBase + " Ar</td>" +
        "<td>" + ligne.quantite + "</td>" +
        "<td class='montant-ligne'></td>" +
        "<td><button class='btn-outline' onclick='togglePerso(" +
            ligne.idLigneCommande + ")'>Personnaliser</button></td>";
    tbody.appendChild(tr);

    const trPerso = document.createElement("tr");
    trPerso.className = "perso-row";
    trPerso.id = "perso-" + ligne.idLigneCommande;
    trPerso.style.display = "none";
    const td = document.createElement("td");
    td.colSpan = 5;
    td.innerHTML =
        "<div class='perso-panel' id='perso-panel-" +
        ligne.idLigneCommande + "'></div>";
    trPerso.appendChild(td);
    tbody.appendChild(trPerso);

    chargerPersonnalisation(ligne.idLigneCommande, ligne.idProduit);
}

function togglePerso(ligneId) {
    const row = document.getElementById("perso-" + ligneId);
    row.style.display = row.style.display === "none" ? "" : "none";
}

function getProduitId(ligneId) {
    return document.querySelector('tr[data-ligne="' + ligneId + '"]')
        .getAttribute("data-produit");
}

function chargerPersonnalisation(ligneId, produitId) {
    const panel = document.getElementById("perso-panel-" + ligneId);

    Promise.all([
        fetch(ctx + '/personnalisation/recette?idProduit=' + produitId)
            .then(r => r.json()),
        fetch(ctx + '/personnalisation/liste?idLigne=' + ligneId)
            .then(r => r.json())
    ]).then(([recette, persos]) => {

        let html = "<h4>Ingrédients de la recette</h4><div class='perso-list'>";
        if (recette.length === 0) {
            html += "<div class='perso-empty'>Aucun ingrédient de base</div>";
        }
        recette.forEach(ing => {
            html +=
                "<div class='perso-item'>" +
                    "<span>" + ing.nomIngredient + " (" +
                        ing.quantiteRecette + " " + (ing.uniteMesure || "") + ")</span>" +
                    "<select id='act-" + ligneId + "-" + ing.idIngredient + "'>" +
                        "<option value='" + actionId('RETIRER') + "'>Retirer</option>" +
                        "<option value='" + actionId('AJOUTER') + "'>Ajouter</option>" +
                    "</select>" +
                    "<input type='number' step='0.01' min='0' value='" +
                        ing.quantiteRecette + "' id='qte-" + ligneId + "-" +
                        ing.idIngredient + "'>" +
                    "<button class='btn-success' onclick='appliquerPerso(" +
                        ligneId + "," + produitId + "," + ing.idIngredient + ")'>" +
                        "Appliquer</button>" +
                "</div>";
        });
        html += "</div>";

        html += "<h4>Ajouter un ingrédient</h4><div class='perso-list'>" +
                "<div class='perso-item'>" +
                    "<select id='ing-add-" + ligneId + "'>" +
                        allIngredients.map(i =>
                            "<option value='" + i.idIngredient + "'>" +
                            i.nomIngredient + "</option>").join("") +
                    "</select>" +
                    "<select id='act-add-" + ligneId + "'>" +
                        "<option value='" + actionId('AJOUTER') + "'>Ajouter</option>" +
                    "</select>" +
                    "<input type='number' step='0.01' min='0' value='1' id='qte-add-" +
                        ligneId + "'>" +
                    "<button class='btn-success' onclick='ajouterPerso(" +
                        ligneId + ")'>Ajouter</button>" +
                "</div></div>";

        html += "<h4>Personnalisations appliquées</h4><div class='perso-list'>";
        if (persos.length === 0) {
            html += "<div class='perso-empty'>Aucune personnalisation</div>";
        }
        persos.forEach(p => {
            const ing = allIngredients.find(x => x.idIngredient == p.idIngredient);
            const nomIng = ing ? ing.nomIngredient : ("#" + p.idIngredient);
            const act = actionsList.find(a => a.idActionCommande == p.idActionCommande);
            const nomAct = act ? act.libelle : ("#" + p.idActionCommande);
            html +=
                "<div class='perso-item'>" +
                    "<span>" + nomAct + " " + nomIng + " (" +
                        (p.quantiteAjustee != null ? p.quantiteAjustee : "") + ")</span>" +
                    "<button class='btn-danger' onclick='supprimerPerso(" +
                        p.idPersonnalisation + "," + ligneId + "," + produitId +
                        ")'>Supprimer</button>" +
                "</div>";
        });
        html += "</div>";

        panel.innerHTML = html;
    });
}

function appliquerPerso(ligneId, produitId, idIngredient) {
    const act = document.getElementById("act-" + ligneId + "-" + idIngredient).value;
    const qte = document.getElementById("qte-" + ligneId + "-" + idIngredient).value;
    fetch(ctx + '/personnalisation/ajouter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            idLigne: ligneId,
            idIngredient: idIngredient,
            idActionCommande: parseInt(act),
            quantiteAjustee: parseFloat(qte) || null
        })
    })
    .then(r => r.json())
    .then(() => chargerPersonnalisation(ligneId, produitId));
}

function ajouterPerso(ligneId) {
    const ing = document.getElementById("ing-add-" + ligneId).value;
    const act = document.getElementById("act-add-" + ligneId).value;
    const qte = document.getElementById("qte-add-" + ligneId).value;
    const produitId = getProduitId(ligneId);
    fetch(ctx + '/personnalisation/ajouter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            idLigne: ligneId,
            idIngredient: parseInt(ing),
            idActionCommande: parseInt(act),
            quantiteAjustee: parseFloat(qte) || null
        })
    })
    .then(r => r.json())
    .then(() => chargerPersonnalisation(ligneId, parseInt(produitId)));
}

function supprimerPerso(id, ligneId, produitId) {
    fetch(ctx + '/personnalisation/supprimer?id=' + id, { method: 'DELETE' })
        .then(() => chargerPersonnalisation(ligneId, produitId));
}

function actualiserTotal() {
    fetch(ctx + '/commande/montant?id=' + cmdId)
        .then(r => r.text())
        .then(t => {
            document.getElementById("total").textContent = t + " Ar";
        });
}

</script>

</body>
</html>
