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
            <!-- <i class="fas fa-plus"></i> -->
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

            <div style="display:flex;gap:20px;margin-bottom:20px;flex-wrap:wrap;">
                <div style="flex:1;min-width:200px;">
                    <label style="font-weight:600;font-size:13px;color:#374151;display:block;margin-bottom:5px;">
                        <i class="fas fa-clock"></i> Heure de recuperation prevue
                    </label>
                    <input type="datetime-local" id="inputHeureRecup" style="width:100%;padding:8px 12px;border:1px solid #d1d5db;border-radius:8px;font-size:14px;">
                </div>
                <div style="flex:1;min-width:200px;">
                    <label style="font-weight:600;font-size:13px;color:#374151;display:block;margin-bottom:5px;">
                        <i class="fas fa-map-marker-alt"></i> Lieu de recuperation prevu
                    </label>
                    <input type="text" id="inputLieuRecup" placeholder="Ex: Place 12, Marche Central" style="width:100%;padding:8px 12px;border:1px solid #d1d5db;border-radius:8px;font-size:14px;">
                </div>
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
                <c:forEach var="f" items="${factures}">
                <tr>
                    <td>${f.idFacture}</td>
                    <td>${f.commande.idCommande}</td>
                    <td>${f.referenceFacture}</td>
                    <td>${f.dateFacturation}</td>
                    <td>${f.modePaiement.libelle}</td>
                    <td>${f.detailsTaxesBrut} Ar</td>
                    <td>${f.commande.montantTotal} Ar</td>
                </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>

    </div>

</div>

<script>

    let cmdId = null;
let lignesLocales = [];
let produits = [];

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

        });A

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

function nouvelleCommande(){
    const heureRecup = document.getElementById("inputHeureRecup").value || null;
    const lieuRecup = document.getElementById("inputLieuRecup").value || null;

    const body = {};
    if (heureRecup) body.heureRecuperationPrevue = heureRecup;
    if (lieuRecup) body.lieuRecuperationPrevu = lieuRecup;

    fetch('${pageContext.request.contextPath}/commande/ajouter',{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(body)
    })
    .then(r=>r.json())
    .then(c=>{
        cmdId=c.idCommande;
        document.getElementById("cmdId").textContent=cmdId;
        document.getElementById("total").textContent=c.montantTotal+" Ar";
    });
}

function ajouterLigne(){

    if(!cmdId) return;

    const pId=document.getElementById("selectProduit").value;
    const qte=parseInt(document.getElementById("inputQuantite").value);
    if(!pId||!qte) return;

    const produit=produits.find(x=>x.idProduit==pId);
    if(!produit) return;

    const montant=produit.prixBase*qte;

    lignesLocales.push({
        idProduit:parseInt(pId),
        quantite:qte,
        nomProduit:produit.nomProduit,
        prixBase:produit.prixBase,
        montant:montant
    });

    afficherLignes();
    actualiserTotalLocal();
}

function supprimerLigne(index){
    lignesLocales.splice(index,1);
    afficherLignes();
    actualiserTotalLocal();
}

function afficherLignes(){
    const tbody=document.getElementById("lignes");
    tbody.innerHTML="";
    lignesLocales.forEach((l,i)=>{
        const tr=document.createElement("tr");
        tr.innerHTML=
            "<td>"+l.nomProduit+"</td>"+
            "<td>"+l.prixBase+" Ar</td>"+
            "<td>"+l.quantite+"</td>"+
            "<td>"+l.montant+" Ar</td>"+
            "<td><button class='btn-delete' onclick='supprimerLigne("+i+")'><i class='fas fa-trash-alt'></i></button></td>";
        tbody.appendChild(tr);
    });
}

function actualiserTotalLocal(){
    const total=lignesLocales.reduce((s,l)=>s+l.montant,0);
    document.getElementById("total").textContent=total+" Ar";
}

function validerCommande(){
    if(!cmdId||lignesLocales.length===0) return;

    const lignes=lignesLocales.map(l=>({
        idProduit:l.idProduit,
        quantite:l.quantite
    }));

    fetch('${pageContext.request.contextPath}/commande/valider?idCommande='+cmdId,{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(lignes)
    })
    .then(r=>r.json())
    .then(c=>{
        cmdId=null;
        document.getElementById("cmdId").textContent="-";
        document.getElementById("total").textContent="0 Ar";
        lignesLocales=[];
        afficherLignes();
    });
}
</script>
</body>
</html>
