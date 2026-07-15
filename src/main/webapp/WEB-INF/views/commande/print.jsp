<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Impression - Commandes</title>
    <style>
        @media print { body { margin:0; } .no-print { display:none; } table { width:100%; border-collapse:collapse; font-size:12px; } th, td { padding:6px; border:1px solid #ccc; } th { background:#eee; } }
        @media screen { body { font-family:Arial; margin:20px; } .no-print { margin-bottom:20px; } table { width:100%; border-collapse:collapse; } th, td { padding:8px; border:1px solid #ddd; } th { background:#f5f5f5; } }
    </style>
</head>
<body>
    <div class="no-print">
        <button onclick="window.print()">🖨️ Imprimer / PDF</button>
        <a href="${pageContext.request.contextPath}/vente/vendeuse" style="margin-left:10px;">← Retour</a>
    </div>
    <h2>Historique des commandes</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th><th>Session</th><th>Vendeuse</th><th>Type</th><th>Date</th><th>Montant</th><th>Statut</th><th>Tarif</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${commandes}" var="cmd">
                <tr>
                    <td>${cmd.id}</td>
                    <td>${cmd.session}</td>
                    <td>${cmd.vendeuse}</td>
                    <td>${cmd.type}</td>
                    <td>${cmd.date}</td>
                    <td>${cmd.montant} Ar</td>
                    <td>${cmd.statut}</td>
                    <td>${cmd.tarif}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>