<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Impression - Ingrédients</title>
    <style>
        @media print { body { margin:0; } .no-print { display:none; } table { width:100%; border-collapse:collapse; font-size:12px; } th, td { padding:6px; border:1px solid #ccc; } th { background:#eee; } }
        @media screen { body { font-family:Arial; margin:20px; } .no-print { margin-bottom:20px; } table { width:100%; border-collapse:collapse; } th, td { padding:8px; border:1px solid #ddd; } th { background:#f5f5f5; } }
    </style>
</head>
<body>
    <div class="no-print">
        <button onclick="window.print()">🖨️ Imprimer / PDF</button>
        <a href="${pageContext.request.contextPath}/ingredients" style="margin-left:10px;">← Retour</a>
    </div>
    <h2>Liste des ingrédients</h2>
    <table>
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Seuil alerte</th><th>Unité</th></tr>
        </thead>
        <tbody>
            <c:forEach items="${ingredients}" var="i">
                <tr>
                    <td>${i.idIngredient}</td>
                    <td>${i.nomIngredient}</td>
                    <td>${i.seuilAlerteQuantite}</td>
                    <td>${i.uniteMesure}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>