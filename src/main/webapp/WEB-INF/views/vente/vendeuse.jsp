<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Vente - MKaly Mlam</title>

    </head>

    <body>
        <h1>Interface Vendeuse</h1>

        <button class="btn-nouveau" onclick="nouvelleCommande()">Nouvelle Commande</button>

        <div id="panel" class="hidden">
            <h2>Commande #<span id="cmdId"></span></h2>

            <table>
                <thead>
                    <tr>
                        <th>Produit</th>
                        <th>Prix unitaire</th>
                        <th>Quantité</th>
                        <th>Montant</th>
                    </tr>
                </thead>
                <tbody id="lignes"></tbody>
                <tfoot>
                    <tr>
                        <td>
                            <select id="selectProduit">
                                <option value="">-- Choisir --</option>
                            </select>
                        </td>
                        <td id="prixUnitaire">-</td>
                        <td><input type="number" id="inputQuantite" min="1" value="1"></td>
                        <td id="previewMontant">-</td>
                        <td><button class="btn-ajouter" onclick="ajouterLigne()">+ Ajouter</button></td>
                    </tr>
                </tfoot>
            </table>

            <div class="montant-total">Montant Total : <span id="total">0</span> Ar</div>
        </div>

        <script>
            let cmdId = null;
            let produits = [];

            window.onload = function () {
                fetch('/produits')
                    .then(r => r.json())
                    .then(data => {
                        produits = data;
                        console.log(data);
                        
                        const sel = document.getElementById('selectProduit');
                        data.forEach(p => {
                            const opt = document.createElement('option');
                            opt.value = p.id;
                            opt.textContent = p.nomProduit + '  (' + p.prixBase + ' Ar)';
                            opt.dataset.prix = p.prixBase;
                            sel.appendChild(opt);
                        });
                    });
            };

            document.getElementById('selectProduit').onchange = afficherPrix;
            document.getElementById('inputQuantite').oninput = afficherPrix;

            function afficherPrix() {
                const sel = document.getElementById('selectProduit');
                const qte = parseInt(document.getElementById('inputQuantite').value) || 0;
                const p = produits.find(x => x.id == sel.value);
                document.getElementById('prixUnitaire').textContent = p ? p.prixBase + ' Ar' : '-';
                document.getElementById('previewMontant').textContent = (p && qte > 0) ? (p.prixBase * qte) + ' Ar' : '-';
            }

            function nouvelleCommande() {
                fetch('/commande/ajouter', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ date: new Date().toISOString().slice(0, 10) })
                })
                    .then(r => r.json())
                    .then(c => {
                        cmdId = c.id;
                        document.getElementById('cmdId').textContent = cmdId;
                        document.getElementById('panel').classList.remove('hidden');
                        document.getElementById('lignes').innerHTML = '';
                        document.getElementById('total').textContent = '0';
                    });
            }

            function ajouterLigne() {
                if (!cmdId) return;
                const pId = document.getElementById('selectProduit').value;
                const qte = parseInt(document.getElementById('inputQuantite').value);
                if (!pId || !qte) return;

                fetch('/ligneCommande/ajouter', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ idCommande: cmdId, idProduit: parseInt(pId), quantite: qte })
                })
                    .then(r => r.json())
                    .then(ligne => {
                        const p = produits.find(x => x.id == ligne.idProduit);
                        const tr = document.createElement('tr');
                        tr.innerHTML = '<td>' + (p ? p.nomProduit : '#' + ligne.idProduit) + '</td>'
                            + '<td>' + (p ? p.prixBase + ' Ar' : '-') + '</td>'
                            + '<td>' + ligne.quantite + '</td>'
                            + '<td>' + ligne.sousTotal + ' Ar</td>';
                        document.getElementById('lignes').appendChild(tr);
                        actualiserTotal();
                    });
            }

function actualiserTotal() {
    fetch('/commande/montant?id=' + cmdId)
        .then(r => r.text())
        .then(t => { document.getElementById('total').textContent = t; });
}
        </script>

    </body>

    </html>