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
        .radio-group {
            display: flex;
            gap: 20px;
            margin: 15px 0;
            padding: 10px;
            background: #f8f9fa;
            border-radius: 8px;
        }
        .radio-group label {
            display: flex;
            align-items: center;
            gap: 8px;
            cursor: pointer;
            font-weight: 500;
        }
        .radio-group input[type="radio"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
        }
        .filter-bar {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            align-items: center;
            flex-wrap: wrap;
        }
        .filter-bar label {
            font-weight: 600;
            color: var(--text, #333);
        }
        .filter-bar select {
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
        }
        .statut-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }
        .statut-EN_ATTENTE { background: #fff3cd; color: #856404; }
        .statut-PREPARATION { background: #cce5ff; color: #004085; }
        .statut-PRETE_POUR_RECUPERATION { background: #d4edda; color: #155724; }
        .statut-LIVREE { background: #d1e7dd; color: #0f5132; }
        .statut-ANNULEE { background: #f8d7da; color: #721c24; }
        .statut-select {
            padding: 4px 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 12px;
            cursor: pointer;
        }
        .btn-statut {
            padding: 4px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
            font-weight: 600;
            background: var(--primary, #4361ee);
            color: #fff;
        }
        .btn-statut:hover {
            opacity: 0.85;
        }
        .type-badge {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 11px;
            font-weight: 600;
        }
        .type-SUR_PLACE { background: #e2e3f9; color: #3a3f9e; }
        .type-A_DISTANCE { background: #f0e6f6; color: #7b2d8e; }

        .liste-commandes {
            margin-top: 40px;
            border-top: 2px solid #e0e0e0;
            padding-top: 20px;
        }

        .commande-row {
            cursor: pointer;
            transition: background 0.15s;
        }
        .commande-row:hover {
            background: #f0f4ff;
        }
        .commande-row.selected {
            background: #e8f0fe;
            font-weight: 600;
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

            </div>

            <h2 style="margin:25px 0;">
                Commande N°
                <span id="cmdId">-</span>
            </h2>

            <div class="radio-group">
                <label>
                    <input type="radio" name="typeCommande" value="SUR_PLACE" checked>
                    <i class="fas fa-store"></i> Sur place
                </label>
                <label>
                    <input type="radio" name="typeCommande" value="A_DISTANCE">
                    <i class="fas fa-shopping-bag"></i> À distance
                </label>
            </div>

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

            <div style="margin-top:15px;">
                <button class="btn-add" onclick="nouvelleCommande()">
                    <i class="fas fa-plus"></i> Nouvelle commande
                </button>
            </div>

        </div>

        <div class="liste-commandes">

            <h2>
                <i class="fas fa-list" style="color:var(--primary);margin-right:8px;"></i>
                Liste des commandes
            </h2>

            <div class="filter-bar">
                <label for="filterStatut">Filtrer par statut :</label>
                <select id="filterStatut" onchange="appliquerFiltre()">
                    <option value="">Toutes</option>
                    <option value="EN_ATTENTE">EN_ATTENTE</option>
                    <option value="PREPARATION">PREPARATION</option>
                    <option value="PRETE_POUR_RECUPERATION">PRETE_POUR_RECUPERATION</option>
                    <option value="LIVREE">LIVREE</option>
                    <option value="ANNULEE">ANNULEE</option>
                </select>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>N°</th>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Montant</th>
                        <th>Statut</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="listeCommandesBody">
                </tbody>
            </table>

        </div>

    </div>

</div>

<script>

let cmdId = null;
let produits = [];
let commandes = [];

window.onload = function () {

    fetch('${pageContext.request.contextPath}/produits/liste')
        .then(r => r.json())
        .then(data => {

            produits = data;

            const sel = document.getElementById("selectProduit");

            produits.forEach(p => {

                const opt = document.createElement("option");

                opt.value = p.idProduit;

                opt.textContent =
                    p.nomProduit + " (" + p.prixBase + " Ar)";

                sel.appendChild(opt);

            });

        });

    chargerCommandes();

};

document.getElementById("selectProduit").onchange = afficherPrix;

document.getElementById("inputQuantite").oninput = afficherPrix;

function afficherPrix(){

    const sel=document.getElementById("selectProduit");

    const qte=parseInt(document.getElementById("inputQuantite").value)||0;

    const p=produits.find(x=>x.idProduit==sel.value);

    document.getElementById("prixUnitaire").textContent=
        p?p.prixBase+" Ar":"-";

    document.getElementById("previewMontant").textContent=
        (p&&qte>0)?
        (p.prixBase*qte)+" Ar":"-";

}

function getTypeCommandeSelectionne() {
    const radios = document.getElementsByName("typeCommande");
    for (let r of radios) {
        if (r.checked) return r.value;
    }
    return "SUR_PLACE";
}

function nouvelleCommande(){

    const typeCmd = getTypeCommandeSelectionne();

    fetch('${pageContext.request.contextPath}/commande/ajouter',{

        method:'POST',

        headers:{
            'Content-Type':'application/json'
        },

        body:JSON.stringify({
            typeCommande: typeCmd
        })

    })

    .then(r=>r.json())

    .then(c=>{
        console.log(c);

        cmdId=c.idCommande;

        document.getElementById("cmdId").textContent=cmdId;

        document.getElementById("lignes").innerHTML="";

        document.getElementById("total").textContent="0 Ar";

        chargerCommandes();
    });

}

function ajouterLigne(){

    console.log(cmdId);
    if(!cmdId) return;

    const pId=document.getElementById("selectProduit").value;

    const qte=parseInt(document.getElementById("inputQuantite").value);

    if(!pId||!qte) return;

    fetch('${pageContext.request.contextPath}/ligneCommande/ajouter',{

        method:'POST',

        headers:{
            'Content-Type':'application/json'
        },

        body:JSON.stringify({

            idCommande:cmdId,

            idProduit:parseInt(pId),

            quantite:qte

        })

    })

    .then(r=>r.json())

    .then(ligne=>{

        const p=produits.find(x=>x.idProduit==ligne.idProduit);

        const tr=document.createElement("tr");

        tr.innerHTML=

            "<td>"+p.nomProduit+"</td>"+

            "<td>"+p.prixBase+" Ar</td>"+

            "<td>"+ligne.quantite+"</td>"+

            "<td>" + (ligne.sousTotal || p.prixBase * ligne.quantite) + " Ar</td>";

        document.getElementById("lignes").appendChild(tr);

        document.getElementById("selectProduit").value = "";

        document.getElementById("inputQuantite").value = 1;

        afficherPrix();

        actualiserTotal();
    })
    .catch(err => {
        console.error("Erreur ajout ligne :", err);
        alert("Erreur lors de l'ajout de la ligne.");
    });
}

function actualiserTotal(){

    fetch('${pageContext.request.contextPath}/commande/montant?id='+cmdId)

    .then(r=>r.text())

    .then(t=>{

        document.getElementById("total").textContent=t+" Ar";

    });

}

function chargerCommandes() {
    fetch('${pageContext.request.contextPath}/commande/liste')
        .then(r => r.json())
        .then(data => {
            commandes = data;
            appliquerFiltre();
        });
}

function appliquerFiltre() {
    const filtre = document.getElementById("filterStatut").value;
    const tbody = document.getElementById("listeCommandesBody");
    tbody.innerHTML = "";

    const filtered = filtre
        ? commandes.filter(c => c.statutCommande === filtre)
        : commandes;

    filtered.forEach(c => {
        const tr = document.createElement("tr");
        tr.className = "commande-row" + (cmdId === c.idCommande ? " selected" : "");
        tr.onclick = function() { chargerCommande(c.idCommande); };

        const dateStr = c.dateHeureCreation
            ? new Date(c.dateHeureCreation).toLocaleString("fr-FR")
            : "-";

        const montant = c.montantTotal ? c.montantTotal.toFixed(2) : "0.00";

        const typeHtml = c.typeCommande
            ? '<span class="type-badge type-' + c.typeCommande + '">' + c.typeCommande.replace("_", " ") + '</span>'
            : '<span style="color:#999;">-</span>';

        const statutHtml = c.statutCommande
            ? '<span class="statut-badge statut-' + c.statutCommande + '">' + c.statutCommande.replace(/_/g, " ") + '</span>'
            : '<span style="color:#999;">-</span>';

        const statutSelectId = "statutSelect_" + c.idCommande;

        const actionHtml =
            '<select id="' + statutSelectId + '" class="statut-select">' +
                '<option value="EN_ATTENTE"' + (c.statutCommande === "EN_ATTENTE" ? " selected" : "") + '>EN_ATTENTE</option>' +
                '<option value="PREPARATION"' + (c.statutCommande === "PREPARATION" ? " selected" : "") + '>PREPARATION</option>' +
                '<option value="PRETE_POUR_RECUPERATION"' + (c.statutCommande === "PRETE_POUR_RECUPERATION" ? " selected" : "") + '>PRETE</option>' +
                '<option value="LIVREE"' + (c.statutCommande === "LIVREE" ? " selected" : "") + '>LIVREE</option>' +
                '<option value="ANNULEE"' + (c.statutCommande === "ANNULEE" ? " selected" : "") + '>ANNULEE</option>' +
            '</select>' +
            ' <button class="btn-statut" onclick="event.stopPropagation();changerStatut(' + c.idCommande + ',\'' + statutSelectId + '\')">OK</button>';

        tr.innerHTML =
            "<td><strong>#" + c.idCommande + "</strong></td>" +
            "<td>" + dateStr + "</td>" +
            "<td>" + typeHtml + "</td>" +
            "<td><strong>" + montant + " Ar</strong></td>" +
            "<td>" + statutHtml + "</td>" +
            "<td>" + actionHtml + "</td>";

        tbody.appendChild(tr);
    });
}

function chargerCommande(idCommande) {
    cmdId = idCommande;
    document.getElementById("cmdId").textContent = cmdId;
    document.getElementById("lignes").innerHTML = "";

    fetch('${pageContext.request.contextPath}/ligneCommande/liste?idCommande=' + cmdId)
        .then(r => r.json())
        .then(lignes => {
            lignes.forEach(ligne => {
                const p = produits.find(x => x.idProduit == ligne.idProduit);
                if (!p) return;
                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + p.nomProduit + "</td>" +
                    "<td>" + p.prixBase + " Ar</td>" +
                    "<td>" + ligne.quantite + "</td>" +
                    "<td>" + (ligne.sousTotal || p.prixBase * ligne.quantite) + " Ar</td>";
                document.getElementById("lignes").appendChild(tr);
            });
            actualiserTotal();
        });

    const c = commandes.find(x => x.idCommande === idCommande);
    if (c && c.typeCommande) {
        const radios = document.getElementsByName("typeCommande");
        for (let r of radios) {
            r.checked = (r.value === c.typeCommande);
        }
    }

    appliquerFiltre();
}

function changerStatut(idCommande, selectId) {
    const nouveauStatut = document.getElementById(selectId).value;

    fetch('${pageContext.request.contextPath}/commande/changerStatut?id_commande=' + idCommande + '&nouveau_statut=' + nouveauStatut, {
        method: 'POST'
    })
    .then(r => r.json())
    .then(c => {
        console.log("Statut mis à jour :", c);
        chargerCommandes();
    })
    .catch(err => {
        console.error("Erreur lors du changement de statut :", err);
        alert("Erreur : impossible de changer le statut.");
    });
}

</script>

</body>
</html>
