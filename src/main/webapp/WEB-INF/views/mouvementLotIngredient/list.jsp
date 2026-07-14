<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des mouvements</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        
        .filter-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        
        .filter-header h2 {
            margin: 0;
            font-size: 16px;
            color: #333;
        }
        
        .filter-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 15px;
        }
        
        .filter-group {
            display: flex;
            flex-direction: column;
        }
        
        .filter-group label {
            font-weight: 600;
            margin-bottom: 5px;
            color: #333;
            font-size: 14px;
        }
        
        .filter-group select,
        .filter-group input {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        
        .filter-group select:focus,
        .filter-group input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(13, 110, 253, 0.15);
        }
        
        .filter-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-top: 15px;
        }
        
        .btn-filter,
        .btn-reset {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
            gap: 6px;
            text-decoration: none;
        }
        
        .btn-filter {
            background-color: var(--primary);
            color: white;
        }
        
        .btn-filter:hover {
            background-color: #0b5ed7;
        }
        
        .btn-reset {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-reset:hover {
            background-color: #5a6268;
        }
        
        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            color: var(--primary);
            text-decoration: none;
            margin-bottom: 20px;
            padding: 8px 12px;
            border-radius: 4px;
            transition: background-color 0.2s;
        }
        
        .back-link:hover {
            background-color: rgba(13, 110, 253, 0.1);
        }
        
        .table-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        
        .table-header h1 {
            margin: 0;
        }
        
        .badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .badge-entree {
            background-color: #d4edda;
            color: #155724;
        }
        
        .badge-sortie {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        
        table thead {
            background-color: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
        }
        
        table th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #333;
        }
        
        table td {
            padding: 12px 15px;
            border-bottom: 1px solid #dee2e6;
        }
        
        table tbody tr:hover {
            background-color: #f8f9fa;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
        
        .empty-state i {
            font-size: 48px;
            margin-bottom: 20px;
            opacity: 0.5;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="mouvements-lots"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/mouvement/findAll" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour au formulaire
        </a>

        <!-- Filtres -->
        <div class="filter-section">
            <div class="filter-header">
                <h2>
                    <i class="fas fa-filter"></i>
                    Filtrer les mouvements
                </h2>
            </div>

            <form action="${pageContext.request.contextPath}/mouvement/view" method="get" id="filterForm">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="filterType">Type de mouvement</label>
                        <select id="filterType" name="typeId">
                            <option value="">Tous</option>
                            <option value="1" ${param.typeId == '1' ? 'selected' : ''}>ENTREE</option>
                            <option value="2" ${param.typeId == '2' ? 'selected' : ''}>SORTIE</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="filterLot">Lot</label>
                        <select id="filterLot" name="lotId">
                            <option value="">Tous</option>
                            <c:forEach items="${lots}" var="lot">
                                <option value="${lot.idLot}" ${param.lotId == lot.idLot ? 'selected' : ''}>
                                    Lot ${lot.idLot} - ${lot.ingredient.nomIngredient}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="filterIngredient">Ingrédient</label>
                        <select id="filterIngredient" name="ingredientId">
                            <option value="">Tous</option>
                            <c:forEach items="${ingredients}" var="ingredient">
                                <option value="${ingredient.idIngredient}" ${param.ingredientId == ingredient.idIngredient ? 'selected' : ''}>
                                    ${ingredient.nomIngredient}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="filter-row">
                    <div class="filter-group">
                        <label for="filterDateFrom">Date du mouvement (À partir de)</label>
                        <input type="date" id="filterDateFrom" name="dateFrom" value="${param.dateFrom}">
                    </div>

                    <div class="filter-group">
                        <label for="filterDateTo">Date du mouvement (Jusqu'à)</label>
                        <input type="date" id="filterDateTo" name="dateTo" value="${param.dateTo}">
                    </div>
                </div>

                <div class="filter-actions">
                    <button type="submit" class="btn-filter">
                        <i class="fas fa-search"></i>
                        Appliquer les filtres
                    </button>
                    <a href="${pageContext.request.contextPath}/mouvement/view" class="btn-reset">
                        <i class="fas fa-redo"></i>
                        Réinitialiser
                    </a>
                </div>
            </form>
        </div>

        <!-- Tableau -->
        <div class="table-container" style="margin-top: 30px;">
            <div class="table-header">
                <h1>
                    <i class="fas fa-exchange-alt" style="color: var(--primary); margin-right: 10px;"></i>
                    Liste des mouvements
                </h1>
            </div>

            <c:choose>
                <c:when test="${empty mouvements}">
                    <div class="empty-state">
                        <i class="fas fa-inbox"></i>
                        <p>Aucun mouvement trouvé</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Ingrédient</th>
                                <th>Lot</th>
                                <th>Type</th>
                                <th>Quantité</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${mouvements}" var="mouvement">
                                <tr>
                                    <td>${mouvement.idMouvementLot}</td>
                                    <td>
                                        <c:if test="${not empty mouvement.lot && not empty mouvement.lot.ingredient}">
                                            ${mouvement.lot.ingredient.nomIngredient}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty mouvement.lot}">
                                            Lot ${mouvement.lot.idLot}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty mouvement.typeMouvement}">
                                            <c:choose>
                                                <c:when test="${mouvement.typeMouvement.libelle == 'ENTREE'}">
                                                    <span class="badge badge-entree">
                                                        <i class="fas fa-arrow-down"></i>
                                                        ENTREE
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-sortie">
                                                        <i class="fas fa-arrow-up"></i>
                                                        SORTIE
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </td>
                                    <td>${mouvement.quantite}</td>
                                    <td>
                                        <c:if test="${not empty mouvement.dateMouvement}">
                                            ${mouvement.dateMouvement}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
