<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Impression - Produits</title>
    <style>
        @media print { body { margin:0; } .no-print { display:none; } table { width:100%; border-collapse:collapse; font-size:12px; } th, td { padding:6px; border:1px solid #ccc; } th { background:#eee; } }
        @media screen { body { font-family:Arial; margin:20px; } .no-print { margin-bottom:20px; } table { width:100%; border-collapse:collapse; } th, td { padding:8px; border:1px solid #ddd; } th { background:#f5f5f5; } }
    </style>
</head>
<body>
    <div class="no-print">
        <button onclick="window.print()">🖨️ Imprimer / PDF</button>
        <a href="${pageContext.request.contextPath}/produits" style="margin-left:10px;">← Retour</a>
    </div>
    <h2>Liste des produits</h2>
    <table>
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Prix</th><th>Nouveau</th><th>Date création</th></tr>
        </thead>
        <tbody>
            <c:forEach items="${produits}" var="p">
                <tr>
                    <td>${p.idProduit}</td>
                    <td>${p.nomProduit}</td>
                    <td>${p.prixBase} Ar</td>
                    <td>${p.estNouveau ? 'Oui' : 'Non'}</td>
                    <td>${p.dateCreation}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>