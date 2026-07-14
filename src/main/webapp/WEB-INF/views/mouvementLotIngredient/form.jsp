<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mouvements des lots d'ingrédients</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        .form-section {
            background-color: #f8f9fa;
            padding: 30px;
            border-radius: 8px;
            margin-bottom: 30px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }
        
        .form-group select,
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        
        .form-group select:focus,
        .form-group input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(13, 110, 253, 0.15);
        }
        
        .form-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        
        .btn-success,
        .btn-secondary {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            text-decoration: none;
        }
        
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #218838;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        
        .list-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        
        .list-header h1 {
            margin: 0;
            color: #333;
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
    </style>
</head>
<body>
<div class="dashboard">
    <c:set var="activeMenu" value="mouvements-lots"/>
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">
        <a href="${pageContext.request.contextPath}/lot/findAll" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste des lots
        </a>

        <c:if test="${not empty param.error}">
            <div class="error">
                <i class="fas fa-exclamation-circle"></i>
                ${param.error}
            </div>
        </c:if>

        <div class="form-section">
            <form action="${pageContext.request.contextPath}/mouvement/save" method="post">
                <h1>
                    <i class="fas fa-exchange-alt" style="color: var(--primary); margin-right: 12px;"></i>
                    Nouveau mouvement d'ingrédient
                </h1>

                <hr>
                <br>

                <div class="form-group">
                    <label for="typeMouvement">Type de mouvement *</label>
                    <select id="typeMouvement" name="typeMouvement.idTypeMouvement" required>
                        <option value="">Sélectionnez un type</option>
                        <c:forEach items="${typeMouvements}" var="typeMouvement">
                            <option value="${typeMouvement.idTypeMouvement}">
                                ${typeMouvement.libelle}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="lot">Lot d'ingrédient *</label>
                    <select id="lot" name="lot.idLot" required>
                        <option value="">Sélectionnez un lot</option>
                        <c:forEach items="${lots}" var="lot">
                            <option value="${lot.idLot}">
                                Lot ${lot.idLot} - ${lot.ingredient.nomIngredient} (Péremption: ${lot.datePeremption})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="quantite">Quantité *</label>
                    <input type="number"
                           id="quantite"
                           name="quantite"
                           step="0.01"
                           min="0"
                           placeholder="Ex: 5.00"
                           required>
                </div>

                <div class="form-group">
                    <label for="dateMouvement">Date du mouvement</label>
                    <input type="datetime-local"
                           id="dateMouvement"
                           name="dateMouvement">
                    <small style="color: #666; font-size: 12px;">Laissez vide pour la date/heure actuelle</small>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-success">
                        <i class="fas fa-save"></i>
                        Enregistrer le mouvement
                    </button>
                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>
                </div>
            </form>
        </div>

        <div class="table-container" style="margin-top: 30px;">
            <div class="list-header">
                <h1>Liste des mouvements</h1>
            </div>

            <c:choose>
                <c:when test="${empty mouvements}">
                    <div style="text-align: center; padding: 40px; color: #999;">
                        <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 20px;"></i>
                        <p>Aucun mouvement enregistré</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID Mouvement</th>
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
                                            ${mouvement.lot.idLot}
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
