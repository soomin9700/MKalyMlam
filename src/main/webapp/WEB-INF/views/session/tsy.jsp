<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ouvrir une session</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    
    <style>
        /* Styles spécifiques au formulaire d'ouverture de session */
        .session-icon {
            color: var(--primary);
            margin-right: 12px;
        }
        
        .form-group select {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid var(--gray);
            border-radius: 10px;
            font-size: 16px;
            transition: all 0.3s;
            background: white;
            font-family: 'Poppins', sans-serif;
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%236b7280' d='M6 8L1 3h10z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 15px center;
            padding-right: 40px;
            cursor: pointer;
        }
        
        .form-group select:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(198, 40, 40, 0.1);
        }
        
        .form-group select:hover {
            border-color: #9ca3af;
        }
        
        .form-group select option {
            padding: 8px;
        }
        
        .form-group select:invalid {
            color: #6b7280;
        }
        
        .form-group select option:first-child {
            color: #6b7280;
        }
        
        .form-group select:valid {
            color: var(--dark);
        }
        
        .fond-caisse-info {
            background: #f8fafc;
            border-radius: 10px;
            padding: 12px 15px;
            margin-top: 5px;
            display: flex;
            align-items: center;
            gap: 10px;
            border: 1px solid var(--gray);
        }
        
        .fond-caisse-info i {
            color: var(--secondary);
            font-size: 18px;
        }
        
        .fond-caisse-info span {
            color: #6b7280;
            font-size: 13px;
        }
        
        .btn-start {
            background: #16A34A;
            color: white;
            padding: 12px 35px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            font-family: 'Poppins', sans-serif;
        }
        
        .btn-start:hover {
            background: #15803d;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(22, 163, 74, 0.3);
        }
        
        .btn-start i {
            font-size: 16px;
        }
        
        .required-star {
            color: var(--primary);
            margin-left: 2px;
        }
        
        .form-section {
            max-width: 800px;
            margin: 0 auto;
        }
        
        @media (max-width: 768px) {
            .form-actions {
                flex-direction: column;
                align-items: stretch;
            }
            
            .form-actions a {
                margin-left: 0 !important;
                text-align: center;
                padding: 10px;
            }
            
            .btn-start,
            .btn-secondary {
                justify-content: center;
            }
        }
    </style>
</head>
<body>

<div class="dashboard">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />
    
    <div class="main">
        
        <!-- Lien retour -->
        <a href="${pageContext.request.contextPath}/session/liste" class="back-link">
            <i class="fas fa-arrow-left"></i>
            Retour à la liste
        </a>
        
        <div class="form-section">
            
            <form action="${pageContext.request.contextPath}/session/ouvrir" method="post">
                
                <h1>
                    <i class="fas fa-play-circle session-icon"></i>
                    Ouvrir une session
                </h1>
                
                <p style="color: #6b7280; margin-bottom: 30px;">
                    <i class="fas fa-info-circle" style="margin-right: 5px;"></i>
                    Sélectionnez les informations nécessaires pour démarrer une nouvelle journée de travail.
                </p>
                
                <hr style="border: none; border-top: 1px solid var(--gray); margin: 20px 0;">
                <br>
                
                <!-- Sélection du camion -->
                <div class="form-group">
                    <label for="idTruck">
                        <i class="fas fa-truck" style="color: var(--primary); margin-right: 5px;"></i>
                        Camion <span class="required-star">*</span>
                    </label>
                    <select id="idTruck" name="idTruck" required>
                        <option value="">-- Sélectionner un camion --</option>
                        <c:forEach items="${trucks}" var="truck">
                            <option value="${truck.id}">${truck.immatriculation}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <!-- Sélection de l'itinéraire -->
                <div class="form-group">
                    <label for="idItineraire">
                        <i class="fas fa-route" style="color: var(--primary); margin-right: 5px;"></i>
                        Itinéraire <span class="required-star">*</span>
                    </label>
                    <select id="idItineraire" name="idItineraire" required>
                        <option value="">-- Sélectionner un itinéraire --</option>
                        <c:forEach items="${itineraires}" var="itineraire">
                            <option value="${itineraire.id}">${itineraire.nomZone} - ${itineraire.lieuExact}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <!-- Sélection du chauffeur -->
                <div class="form-group">
                    <label for="idChauffeur">
                        <i class="fas fa-user" style="color: var(--primary); margin-right: 5px;"></i>
                        Chauffeur <span class="required-star">*</span>
                    </label>
                    <select id="idChauffeur" name="idChauffeur" required>
                        <option value="">-- Sélectionner un chauffeur --</option>
                        <c:forEach items="${chauffeurs}" var="chauffeur">
                            <option value="${chauffeur.id}">${chauffeur.prenom} ${chauffeur.nom}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <!-- Fond de caisse -->
                <div class="form-group">
                    <label for="fondDeCaisseOuverture">
                        <i class="fas fa-coins" style="color: var(--primary); margin-right: 5px;"></i>
                        Fond de caisse (€) <span class="required-star">*</span>
                    </label>
                    <input 
                        type="number" 
                        id="fondDeCaisseOuverture" 
                        name="fondDeCaisseOuverture"
                        step="0.01" 
                        min="0"
                        placeholder="Ex: 150.00"
                        required>
                    
                    <div class="fond-caisse-info">
                        <i class="fas fa-info-circle"></i>
                        <span>Montant initial disponible dans la caisse pour démarrer la journée.</span>
                    </div>
                </div>
                
                <!-- Boutons d'action -->
                <div class="form-actions">
                    <button type="submit" class="btn-start">
                        <i class="fas fa-play"></i>
                        Démarrer la journée
                    </button>
                    
                    <button type="reset" class="btn-secondary">
                        <i class="fas fa-undo"></i>
                        Réinitialiser
                    </button>
                    
                    <a href="${pageContext.request.contextPath}/session/liste"
                       style="margin-left: auto; align-self: center; color: var(--primary); text-decoration: none;">
                        <i class="fas fa-times"></i>
                        Annuler
                    </a>
                </div>
                
            </form>
            
        </div>
        
    </div>
</div>

</body>
</html>