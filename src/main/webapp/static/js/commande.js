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
    const sessionSelect = document.getElementById("selectSession");
    const idTruck = sessionSelect ? sessionSelect.value : null;
    if(!idTruck){
        alert("Veuillez choisir un truck avec une session ouverte.");
        return;
    }

    fetch('${pageContext.request.contextPath}/commande/ajouter?idTruck='+idTruck,{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({})
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
        nouvelleCommande();
    });
}