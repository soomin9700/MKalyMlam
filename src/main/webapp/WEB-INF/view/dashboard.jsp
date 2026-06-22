<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${titre}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
<main class="page">
    <section class="header">
        <div>
            <h1>${titre}</h1>
            <p>Chiffre d'affaires et bénéfice du food truck.</p>
        </div>
    </section>

    <section class="cards" aria-label="Indicateurs principaux">
        <article class="card">
            <span>Chiffre d'affaires global</span>
            <strong id="chiffreAffaire">...</strong>
        </article>
        <article class="card">
            <span>Bénéfice total</span>
            <strong id="beneficeTotal">...</strong>
        </article>
    </section>

    <section class="dashboard-grid">
        <article class="chart-panel">
            <div class="panel-head">
                <h2>Evolution par période</h2>
                <small id="lastUpdate"></small>
            </div>
            <div class="chart-wrap">
                <canvas id="statsChart" width="800" height="300"></canvas>
            </div>
        </article>

        <article class="table-panel">
            <div class="panel-head">
                <h2>Données</h2>
            </div>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>Période</th>
                        <th>CA</th>
                        <th>Bénéfice</th>
                    </tr>
                    </thead>
                    <tbody id="statsRows">
                    <tr>
                        <td colspan="3">Chargement...</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>
    </section>

    <div class="error" id="errorBox"></div>
</main>

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
        const padding = { top: 20, right: 20, bottom: 40, left: 60 };
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

        ctx.strokeStyle = '#e0e0e0';
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
            ctx.fillText(numberFormat.format(maxValue * i / 4), padding.left - 8, y + 4);
        }

        function plotLine(key, color) {
            ctx.strokeStyle = color;
            ctx.lineWidth = 2.5;
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
                ctx.arc(x, y, 4, 0, Math.PI * 2);
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

        ctx.fillStyle = '#da291c';
        ctx.fillRect(width - 160, 8, 10, 10);
        ctx.fillStyle = '#1a1a1a';
        ctx.textAlign = 'left';
        ctx.fillText("Chiffre d'affaires", width - 145, 17);

        ctx.fillStyle = '#ffc72c';
        ctx.fillRect(width - 85, 8, 10, 10);
        ctx.fillStyle = '#1a1a1a';
        ctx.fillText('Bénéfice', width - 70, 17);
    }

    function renderRows(rows) {
        const tbody = document.getElementById('statsRows');
        if (rows.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3">Aucune donnée.</td></tr>';
            return;
        }
        tbody.innerHTML = rows.map(row => (
            '<tr>' +
            '<td>' + row.periode + '</td>' +
            '<td>' + money(row.chiffreAffaire) + '</td>' +
            '<td>' + money(row.benefice) + '</td>' +
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
            document.getElementById('lastUpdate').textContent = 'Mis à jour maintenant';
            renderRows(rows);
            drawChart(rows);
        } catch (error) {
            errorBox.textContent = 'Impossible de charger les statistiques: ' + error.message;
            errorBox.style.display = 'block';
        }
    }

    window.addEventListener('resize', loadDashboard);
    loadDashboard();
</script>
</body>
</html>
