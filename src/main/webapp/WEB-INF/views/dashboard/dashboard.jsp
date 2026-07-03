<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${titre}</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">

</head>

<body>

<div class="dashboard">

    <c:set var="activeMenu" value="dashboard"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <div class="table-header" style="flex-direction: column; align-items: flex-start; gap: 5px;">
                <h1>
                    <i class="fas fa-chart-line" style="color:var(--primary); margin-right:10px;"></i>
                    ${titre}
                </h1>
                <p style="color: #666; margin: 0 0 15px 40px;">Chiffre d'affaires et bénéfice du food truck.</p>
            </div>

            <section class="cards" aria-label="Indicateurs principaux" style="display: flex; gap: 20px; margin-bottom: 30px;">
                <article class="card" style="flex: 1; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); border-left: 4px solid #da291c;">
                    <span style="display: block; color: #666; font-size: 14px; margin-bottom: 5px;"><i class="fas fa-wallet"></i> Chiffre d'affaires global</span>
                    <strong id="chiffreAffaire" style="font-size: 24px; color: #1a1a1a;">...</strong>
                </article>
                <article class="card" style="flex: 1; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); border-left: 4px solid #ffc72c;">
                    <span style="display: block; color: #666; font-size: 14px; margin-bottom: 5px;"><i class="fas fa-coins"></i> Bénéfice total</span>
                    <strong id="beneficeTotal" style="font-size: 24px; color: #1a1a1a;">...</strong>
                </article>
            </section>

            <section class="dashboard-grid">
                
                <article class="chart-panel" style="background: #fff; padding: 20px; border-radius: 8px; margin-bottom: 30px; box-shadow: 0 2px 4px rgba(0,0,0,0.05);">
                    <div class="panel-head" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                        <h2><i class="fas fa-chart-area"></i> Évolution par période</h2>
                        <small id="lastUpdate" style="color: #999;"></small>
                    </div>
                    <div class="chart-wrap" style="overflow-x: auto;">
                        <canvas id="statsChart" width="800" height="300" style="max-width: 100%; height: auto;"></canvas>
                    </div>
                </article>

                <article class="table-panel">
                    <div class="panel-head" style="margin-bottom: 15px;">
                        <h2><i class="fas fa-list"></i> Données détaillées</h2>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                            <tr>
                                <th><i class="fas fa-calendar-alt"></i> Période</th>
                                <th><i class="fas fa-dollar-sign"></i> CA</th>
                                <th><i class="fas fa-hand-holding-usd"></i> Bénéfice</th>
                            </tr>
                            </thead>
                            <tbody id="statsRows">
                            <tr>
                                <td colspan="3" style="text-align: center; padding: 20px; color: #666;">
                                    <i class="fas fa-spinner fa-spin"></i> Chargement...
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </article>
            </section>

            <div class="error" id="errorBox" style="display: none; margin-top: 20px; padding: 15px; background-color: #f8d7da; color: #721c24; border-radius: 4px; border: 1px solid #f5c6cb;"></div>
        </div>
    </div>
</div>

<script>
    const contextPath = '${pageContext.request.contextPath}';
    const numberFormat = new Intl.NumberFormat('fr-FR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });

    const money = value => numberFormat.format(Number(value || 0)) + ' Ar';
    const endpoint = path => contextPath + '/statistiques' + path;

    async function getJson(path) {
        const response = await fetch(endpoint(path), { headers: { 'Accept': 'application/json' } });
        if (!response.ok) throw new Error('Erreur HTTP ' + response.status);
        return response.json();
    }

    function normalizeRows(rows) {
        return rows.map(row => ({
            periode: String(row.periode || ''),
            chiffreAffaire: Number(row.chiffreAffaire || 0),
            benefice: Number(row.benefice || 0)
        }));
    }

    function drawChart(rows) {
        const canvas = document.getElementById('statsChart');
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const padding = { top: 30, right: 20, bottom: 40, left: 70 };
        const chartWidth = width - padding.left - padding.right;
        const chartHeight = height - padding.top - padding.bottom;

        if (rows.length === 0) {
            ctx.clearRect(0, 0, width, height);
            ctx.fillStyle = '#666';
            ctx.font = '14px Arial';
            ctx.textAlign = 'center';
            ctx.fillText('Aucune donnée', width / 2, height / 2);
            return;
        }

        const maxValue = Math.max(1, ...rows.flatMap(row => [row.chiffreAffaire, row.benefice]));
        const step = rows.length > 1 ? chartWidth / (rows.length - 1) : chartWidth;

        ctx.clearRect(0, 0, width, height);
        ctx.fillStyle = '#ffffff';
        ctx.fillRect(0, 0, width, height);

        ctx.strokeStyle = '#eef2f5';
        ctx.lineWidth = 1;
        ctx.fillStyle = '#666';
        ctx.font = '11px Arial';

        for (let i = 0; i <= 4; i++) {
            const y = padding.top + chartHeight - (chartHeight * i / 4);
            ctx.beginPath();
            ctx.moveTo(padding.left, y);
            ctx.lineTo(width - padding.right, y);
            ctx.stroke();
            ctx.textAlign = 'right';
            ctx.fillText(numberFormat.format(maxValue * i / 4) + ' Ar', padding.left - 8, y + 4);
        }

        function plotLine(key, color) {
            ctx.strokeStyle = color;
            ctx.lineWidth = 3;
            ctx.beginPath();
            rows.forEach((row, index) => {
                const x = padding.left + (rows.length > 1 ? index * step : chartWidth / 2);
                const y = padding.top + chartHeight - (row[key] / maxValue) * chartHeight;
                if (index === 0) ctx.moveTo(x, y);
                else ctx.lineTo(x, y);
            });
            ctx.stroke();
        }

        function plotDots(key, color) {
            ctx.fillStyle = color;
            rows.forEach((row, index) => {
                const x = padding.left + (rows.length > 1 ? index * step : chartWidth / 2);
                const y = padding.top + chartHeight - (row[key] / maxValue) * chartHeight;
                ctx.beginPath();
                ctx.arc(x, y, 5, 0, Math.PI * 2);
                ctx.fill();
            });
        }

        plotLine('chiffreAffaire', '#da291c');
        plotLine('benefice', '#ffc72c');
        plotDots('chiffreAffaire', '#da291c');
        plotDots('benefice', '#ffc72c');

        ctx.fillStyle = '#1a1a1a';
        ctx.textAlign = 'center';
        rows.forEach((row, index) => {
            const x = padding.left + (rows.length > 1 ? index * step : chartWidth / 2);
            ctx.fillText(row.periode.substring(0, 10), x, height - 10);
        });

        // Légendes positionnées plus proprement en haut à droite
        ctx.fillStyle = '#da291c';
        ctx.fillRect(width - 240, 8, 12, 12);
        ctx.fillStyle = '#1a1a1a';
        ctx.textAlign = 'left';
        ctx.font = '12px Arial';
        ctx.fillText("Chiffre d'affaires", width - 222, 18);

        ctx.fillStyle = '#ffc72c';
        ctx.fillRect(width - 110, 8, 12, 12);
        ctx.fillStyle = '#1a1a1a';
        ctx.fillText('Bénéfice', width - 92, 18);
    }

    function renderRows(rows) {
        const tbody = document.getElementById('statsRows');
        if (rows.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3" style="text-align:center;">' +
                '<div class="empty-state" style="padding:20px; color:#666;">' +
                '<i class="fas fa-chart-bar" style="font-size:32px;color:#d1d5db;margin-bottom:10px;display:block;"></i>' +
                'Aucune donnée statistique enregistrée.' +
                '</div></td></tr>';
            return;
        }
        tbody.innerHTML = rows.map(row => (
            '<tr>' +
            '<td><strong>' + row.periode + '</strong></td>' +
            '<td><span class="price-tag" style="background:#fff5f5; color:#da291c; padding:3px 8px; border-radius:4px; font-weight:bold;">' + money(row.chiffreAffaire) + '</span></td>' +
            '<td><span class="price-tag" style="background:#fffdf0; color:#b08600; padding:3px 8px; border-radius:4px; font-weight:bold;">' + money(row.benefice) + '</span></td>' +
            '</tr>'
        )).join('');
    }

    async function loadDashboard() {
        const errorBox = document.getElementById('errorBox');
        errorBox.style.display = 'none';
        try {
            const [chiffreAffaire, benefice, graphique] = await Promise.all([
                getJson('/chiffreAffaire'),
                getJson('/benefice'),
                getJson('/graphique')
            ]);
            const rows = normalizeRows(graphique);
            document.getElementById('chiffreAffaire').textContent = money(chiffreAffaire);
            document.getElementById('beneficeTotal').textContent = money(benefice);
            document.getElementById('lastUpdate').textContent = ' Mis à jour à l\'instant';
            renderRows(rows);
            drawChart(rows);
        } catch (error) {
            errorBox.innerHTML = '<i class="fas fa-exclamation-triangle"></i> Impossible de charger les statistiques: ' + error.message;
            errorBox.style.display = 'block';
        }
    }

    window.addEventListener('resize', loadDashboard);
    loadDashboard();
</script>
</body>
</html>