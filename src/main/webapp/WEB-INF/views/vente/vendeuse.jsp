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

let cmdId = null;
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

        });

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

    fetch('${pageContext.request.contextPath}/commande/ajouter',{

        method:'POST',

        headers:{
            'Content-Type':'application/json'
        },

        // body:JSON.stringify({
        //     date:new Date().toISOString().slice(0,10)
        // })

        body:JSON.stringify({})

    })

    .then(r=>r.json())

    .then(c=>{
        console.log(c);
        

        cmdId=c.idCommande;

        document.getElementById("cmdId").textContent=cmdId;

        document.getElementById("lignes").innerHTML="";

        document.getElementById("total").textContent="0 Ar";

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

            // "<td>"+ligne.getSousTotal()+" Ar</td>"+

            "<td></td>";

        document.getElementById("lignes").appendChild(tr);

        actualiserTotal();

    });

}

function actualiserTotal(){

    fetch('${pageContext.request.contextPath}/commande/montant?id='+cmdId)

    .then(r=>r.text())

    .then(t=>{

        document.getElementById("total").textContent=t+" Ar";

    });

}

</script>

</body>
</html>