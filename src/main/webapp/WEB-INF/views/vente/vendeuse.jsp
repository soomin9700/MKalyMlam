<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Nouvelle vente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        .custom-badge-container {
            font-size: 0.85em;
            color: #555;
            margin-top: 8px;
            padding-left: 5px;
            border-left: 2px solid var(--primary, #ff5722);
        }

        .custom-badge {
            display: inline-block;
            background-color: #f1f1f1;
            padding: 2px 6px;
            border-radius: 4px;
            margin: 2px 4px 2px 0;
            font-weight: 500;
        }

        .custom-badge.ajouter {
            color: #2ecc71;
            background-color: #e8f8f0;
        }

        .custom-badge.retirer {
            color: #e74c3c;
            background-color: #fdedec;
        }

        .flex-actions {
            display: flex;
            gap: 6px;
            align-items: center;
            flex-wrap: wrap;
        }

        .select-small,
        .input-small {
            padding: 4px;
            font-size: 0.85em;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .btn-small {
            padding: 4px 8px;
            font-size: 0.8em;
            cursor: pointer;
        }

        .total-container {
            display: flex;
            flex-direction: column;
            align-items: flex-end;
            gap: 15px;
            margin-top: 20px;
        }

        .btn-validate {
            background-color: #2ecc71;
            color: white;
            border: none;
            padding: 12px 30px;
            font-size: 1.1em;
            font-weight: bold;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s;
            box-shadow: 0 4px 6px rgba(46, 204, 113, 0.2);
        }

        .btn-validate:hover {
            background-color: #27ae60;
        }
    </style>
</head>

<body>

    <div class="dashboard">

        <c:set var="activeMenu" value="vente" />
        <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

        <div class="main">
            <div class="table-container">
                <div class="table-header">
                    <h1>
                        <i class="fas fa-cash-register" style="color:var(--primary);margin-right:10px;"></i>
                        Nouvelle vente
                    </h1>
                    <button class="btn-add" onclick="nouvelleCommande()">
                        Nouvelle commande
                    </button>
                </div>

                <h2 style="margin:25px 0;">
                    Commande N° <span id="cmdId">-</span>
                </h2>

                <table>
                    <thead>
                        <tr>
                            <th>Produit</th>
                            <th>Prix unitaire</th>
                            <th>Quantité</th>
                            <th>Montant</th>
                            <th>Actions & Personnalisation</th>
                        </tr>
                    </thead>

                    <tbody id="lignes">
                    </tbody>

                    <tfoot>
                        <tr>
                            <td>
                                <select id="selectProduit">
                                    <option value="">Choisir un produit</option>
                                </select>
                            </td>
                            <td id="prixUnitaire">-</td>
                            <td>
                                <input id="inputQuantite" type="number" min="1" value="1">
                            </td>
                            <td id="previewMontant">-</td>
                            <td>
                                <button class="btn-success" onclick="ajouterLigne()">
                                    <i class="fas fa-plus"></i> Ajouter au panier
                                </button>
                            </td>
                        </tr>
                    </tfoot>
                </table>

                <div class="total-container">
                    <div class="total-card">
                        <div class="total-label">Montant total</div>
                        <div id="total" class="total-value">0 Ar</div>
                    </div>
                    
                    <button class="btn-validate" id="btnValiderCommande" onclick="validerEtEncaisser()">
                        <i class="fas fa-check-circle"></i> Valider et Encaisser
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script>
        let cmdId = null;
        let produits = [];
        let ingredients = []; 
        let cachePersonnalisations = {}; 
        let cacheLignesCourantes = [];

        window.onload = function () {
            fetch('${pageContext.request.contextPath}/produits/liste')
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
                })
                .catch(err => console.error("Erreur chargement produits:", err));

            fetch('${pageContext.request.contextPath}/ingredients/liste')
                .then(r => r.json())
                .then(data => {
                    ingredients = data;
                })
                .catch(err => console.error("Erreur chargement ingrédients:", err));
        };

        document.getElementById("selectProduit").onchange = afficherPrix;
        document.getElementById("inputQuantite").oninput = afficherPrix;

        function afficherPrix() {
            const sel = document.getElementById("selectProduit");
            const qte = parseInt(document.getElementById("inputQuantite").value) || 0;
            const p = produits.find(x => x.idProduit == sel.value);

            document.getElementById("prixUnitaire").textContent = p ? p.prixBase + " Ar" : "-";
            document.getElementById("previewMontant").textContent = (p && qte > 0) ? (p.prixBase * qte) + " Ar" : "-";
        }

        function nouvelleCommande() {
            fetch('${pageContext.request.contextPath}/commande/ajouter', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    idSession: 1,
                    idTypeCommande: 1,
                    idStatutCommande: 1,
                    idTypeTarification: 1
                })
            })
                .then(r => r.json())
                .then(c => {
                    cmdId = c.idCommande;
                    document.getElementById("cmdId").textContent = cmdId;
                    document.getElementById("lignes").innerHTML = "";
                    document.getElementById("total").textContent = "0 Ar";
                    cachePersonnalisations = {};
                    cacheLignesCourantes = [];
                })
                .catch(err => console.error("Erreur initialisation commande:", err));
        }

        function ajouterLigne() {
            if (!cmdId) {
                alert("Veuillez d'abord créer une nouvelle commande !");
                return;
            }

            const pId = document.getElementById("selectProduit").value;
            const qte = parseInt(document.getElementById("inputQuantite").value);

            if (!pId || !qte || qte <= 0) {
                alert("Veuillez choisir un produit et une quantité valide.");
                return;
            }

            fetch('${pageContext.request.contextPath}/ligneCommande/ajouter', {
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
                    recupererEtAfficherLignes();
                    document.getElementById("selectProduit").value = "";
                    document.getElementById("inputQuantite").value = "1";
                    afficherPrix();
                })
                .catch(err => console.error("Erreur ajout ligne:", err));
        }

        function recupererEtAfficherLignes() {
            fetch('${pageContext.request.contextPath}/ligneCommande/liste?idCommande=' + cmdId)
                .then(r => r.json())
                .then(lignes => {
                    cacheLignesCourantes = lignes;
                    const tbody = document.getElementById("lignes");
                    tbody.innerHTML = "";

                    if(lignes.length === 0) {
                        actualiserTotalDynamique();
                        return;
                    }

                    lignes.forEach(ligne => {
                        const p = produits.find(x => x.idProduit == ligne.idProduit);
                        const nom = p ? p.nomProduit : "Produit inconnu";
                        const prix = p ? p.prixBase : 0;
                        const currentLineId = ligne.idLine;

                        const tr = document.createElement("tr");
                        tr.id = "tr-ligne-" + currentLineId;

                        tr.innerHTML =
                            "<td>" +
                            "<strong>" + nom + "</strong>" +
                            "<div id='custom-list-" + currentLineId + "' class='custom-badge-container'></div>" +
                            "</td>" +
                            "<td>" + prix + " Ar</td>" +
                            "<td>" + ligne.quantite + "</td>" +
                            "<td id='montant-ligne-" + currentLineId + "'>" + (prix * ligne.quantite) + " Ar</td>" +
                            "<td>" +
                            "<div class='flex-actions'>" +
                            "<select id='custom-ing-" + currentLineId + "' class='select-small'>" +
                            "<option value=''>Ingrédient...</option>" +
                            "</select>" +
                            "<select id='custom-act-" + currentLineId + "' class='select-small'>" +
                            "<option value='1'>AJOUTER (+)</option>" +
                            "<option value='2'>RETIRER (-)</option>" +
                            "</select>" +
                            "<input type='number' id='custom-qte-" + currentLineId + "' value='1' min='1' class='input-small' style='width:50px;'>" +
                            "<button class='btn-success btn-small' onclick='appliquerPersonnalisation(" + currentLineId + ")'>" +
                            "<i class='fas fa-sliders-h'></i> Perso" +
                            "</button>" +
                            "</div>" +
                            "</td>";

                        tbody.appendChild(tr);

                        const selectIng = document.getElementById("custom-ing-" + currentLineId);
                        ingredients.forEach(ing => {
                            const opt = document.createElement("option");
                            opt.value = ing.idIngredient;
                            const prixAffichage = ing.prix ? " (+" + ing.prix + " Ar)" : " (+0 Ar)";
                            opt.textContent = ing.nomIngredient + prixAffichage;
                            selectIng.appendChild(opt);
                        });

                        chargerHistoriquePerso(currentLineId);
                    });
                })
                .catch(err => console.error("Erreur récupération des lignes:", err));
        }

        function appliquerPersonnalisation(idLine) {
            const idIngredient = document.getElementById("custom-ing-" + idLine).value;
            const idActionCommande = document.getElementById("custom-act-" + idLine).value;
            const quantite = document.getElementById("custom-qte-" + idLine).value;

            if (!idIngredient || !quantite || quantite <= 0) {
                alert("Veuillez choisir un ingrédient et une quantité valide.");
                return;
            }

            const payload = {
                idLine: idLine,
                idIngredient: parseInt(idIngredient),
                idActionCommande: parseInt(idActionCommande),
                quantiteAjustee: parseFloat(quantite)
            };

            fetch('${pageContext.request.contextPath}/personnalisation/save', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
                .then(r => r.json())
                .then(data => {
                    chargerHistoriquePerso(idLine);
                    document.getElementById("custom-ing-" + idLine).value = "";
                    document.getElementById("custom-qte-" + idLine).value = "1";
                })
                .catch(err => console.error("Erreur enregistrement personnalisation:", err));
        }

        function chargerHistoriquePerso(idLine) {
            fetch('${pageContext.request.contextPath}/personnalisation/findAllByLigne?id_ligne=' + idLine)
                .then(r => r.json())
                .then(listPerso => {
                    const container = document.getElementById("custom-list-" + idLine);
                    container.innerHTML = ""; 

                    if (listPerso && !Array.isArray(listPerso)) {
                        listPerso = [listPerso];
                    }

                    cachePersonnalisations[idLine] = listPerso || [];

                    if (!listPerso || listPerso.length === 0) {
                        container.style.display = "none";
                        actualiserTotalDynamique();
                        return;
                    }
                    container.style.display = "block";

                    listPerso.forEach(p => {
                        const ingObj = ingredients.find(i => i.idIngredient == p.idIngredient);
                        const nomIngredient = ingObj ? ingObj.nomIngredient : "Ingrédient";

                        const estAjout = p.idActionCommande === 1;
                        const symbole = estAjout ? "+" : "-";
                        const classeCSS = estAjout ? "ajouter" : "retirer";

                        const span = document.createElement("span");
                        span.className = "custom-badge " + classeCSS;
                        span.textContent = symbole + " " + nomIngredient + " x" + p.quantiteAjustee;

                        container.appendChild(span);
                    });

                    actualiserTotalDynamique();
                })
                .catch(err => console.error("Erreur historique perso:", err));
        }

        // CALCULATEUR DYNAMIQUE ET SÛR A 100%
        function actualiserTotalDynamique() {
            let grandTotal = 0;

            cacheLignesCourantes.forEach(ligne => {
                const p = produits.find(x => x.idProduit == ligne.idProduit);
                const prixBaseProduit = p ? p.prixBase : 0;
                
                // 1. Calcul du prix de base
                let sousTotalLigne = prixBaseProduit * ligne.quantite;
                
                // 2. Ajout dynamique des suppléments d'ingrédients s'ils ont un prix défini
                const persosDeLaLigne = cachePersonnalisations[ligne.idLine] || [];
                persosDeLaLigne.forEach(perso => {
                    const ing = ingredients.find(i => i.idIngredient == perso.idIngredient);
                    // Si ton entité Ingrédient utilise 'prix' ou 'prixIngredient', ajuste ici si besoin
                    const prixIngredient = (ing && ing.prix) ? ing.prix : 0; 
                    
                    if (perso.idActionCommande === 1) { 
                        sousTotalLigne += (prixIngredient * perso.quantiteAjustee);
                    }
                });

                // Met à jour la colonne MONTANT de la ligne correspondante
                const tdMontant = document.getElementById("montant-ligne-" + ligne.idLine);
                if(tdMontant) {
                    tdMontant.textContent = sousTotalLigne + " Ar";
                }

                grandTotal += sousTotalLigne;
            });

            // Met à jour le gros bloc de prix final
            document.getElementById("total").textContent = grandTotal + " Ar";
        }

        function validerEtEncaisser() {
    if (!cmdId) {
        alert("Aucune commande active.");
        return;
    }

    // Nettoie le montant (enlève " Ar" pour garder le nombre)
    const montantTexte = document.getElementById("total").textContent;
    const montantNumerique = parseFloat(montantTexte.replace(" Ar", ""));

    if (confirm("Valider la commande N° " + cmdId + " ?")) {
        // Envoi simple avec paramètres d'URL (ne nécessite pas de bibliothèque de JSON)
        fetch('${pageContext.request.contextPath}/commande/valider?id=' + cmdId + '&montant=' + montantNumerique, {
            method: 'POST'
        })
        .then(r => r.text()) // On attend juste du texte en retour
        .then(data => {
            if (data === "OK") {
                alert("Commande validée avec succès !");
                location.reload();
            } else {
                alert("Erreur lors de la validation.");
            }
        })
        .catch(err => console.error("Erreur:", err));
    }
}
    </script>
</body>
</html>