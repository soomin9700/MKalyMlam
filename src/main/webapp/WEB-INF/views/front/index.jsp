<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Mkaly Mlam • Street Food Mobile</title>

            <!-- Liens CDN Bootstrap 5 & Font Awesome 6 -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/front.css">
        </head>

        <body>

            <!-- ===== HEADER / NAVBAR ===== -->
            <header>
                <nav class="navbar navbar-expand-lg">
                    <div class="container">
                        <a class="navbar-brand logo-container" href="#">
                            <img src="${pageContext.request.contextPath}/images/logo1.jpg" alt="Logo Mkaly Mlam"
                                class="logo-img" onerror="this.src='images/logo.jpg'">
                            <span class="logo-text">MKALY MLAM</span>
                        </a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav"
                            aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="mainNav">
                            <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-lg-center gap-2">
                                <li class="nav-item"><a class="nav-link active" href="#">Accueil</a></li>
                                <li class="nav-item"><a class="nav-link" href="#live-location">Où nous trouver ?</a>
                                </li>
                                <li class="nav-item"><a class="nav-link" href="#featured-section">Populaires</a></li>
                                <li class="nav-item"><a class="nav-link" href="#menu-complet">Le Menu</a></li>
                                <li class="nav-item"><a class="nav-link" href="#avis-clients">Avis</a></li>
                                <li class="nav-item ms-lg-2">
                                    <a class="btn btn-primary btn-nav px-4" href="#">
                                        <i class="fas fa-user me-2"></i>Connexion
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>

            <main>

                <!-- ===== HERO SECTION ===== -->
                <section class="hero-section">
                    <div class="container">
                        <div class="row align-items-center gx-5">
                            <div class="col-lg-6 hero-text text-center text-lg-start mb-5 mb-lg-0">
                                <span class="badge-hero mb-4 d-inline-block">
                                    <i class="fas fa-truck-fast me-2"></i> Street Food Mobile & Chaleureuse
                                </span>
                                <h1 class="display-4 fw-bold mb-4">
                                    Le meilleur de la <br><span class="text-primary">street food</span> là où vous êtes
                                    !
                                </h1>
                                <p class="lead mb-4 text-muted">
                                    Des burgers juteux, des tacos dorés, des hot dogs gourmands et nos spécialités
                                    inédites cuisinés sous vos yeux avec amour et produits frais.
                                </p>
                                <div
                                    class="hero-buttons d-flex flex-wrap justify-content-center justify-content-lg-start gap-3">
                                    <a href="#menu-complet" class="btn btn-primary btn-hero">
                                        <i class="fas fa-utensils me-2"></i>Explorer le Menu
                                    </a>
                                    <a href="#live-location" class="btn btn-outline-secondary btn-hero">
                                        <i class="fas fa-map-marker-alt me-2"></i>Localiser le Truck
                                    </a>
                                </div>
                                <div class="mt-5 d-flex justify-content-center justify-content-lg-start gap-4">
                                    <div><span class="fw-bold text-primary">★ 4.9</span> <span class="text-muted">(250+
                                            avis gourmands)</span></div>
                                    <div><span class="fw-bold text-primary">100%</span> <span class="text-muted">Fait
                                            Maison / Vita Malagasy</span></div>
                                </div>
                            </div>
                            <div class="col-lg-6 hero-image-container">
                                <img src="${pageContext.request.contextPath}/images/home-hero.jpg" alt="Mkaly Mlam Hero"
                                    class="img-fluid" onerror="this.src='images/burger.jpg'">
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Section Cartes Récapitulatives -->
                <!-- Section Cartes Récapitulatives -->
                <section class="truck-localization-section py-5">
                    <div class="container">
                        <div class="text-center mb-5">
                            <h2 class="section-title">Localisation des Trucks</h2>
                            <p class="section-subtitle">Retrouvez nos emplacements stratégiques et suivez nos
                                déplacements de la journée.</p>
                        </div>

                        <div class="cards-container d-flex flex-wrap justify-content-center gap-4">
                            <c:forEach var="position" items="${positions}">
                                <div class="recap-card">
                                    <div class="card-content">
                                        <h3 class="truck-name">Truck ${position.sessionTruck.truck.id}
                                            (${position.sessionTruck.truck.immatriculation})</h3>
                                        <p class="localization-text">
                                            <span class="icon"><i class="fas fa-location-dot"></i></span>
                                            ${position.itineraire.nomZone} - ${position.itineraire.lieuExact}
                                        </p>
                                        <p class="schedule-text">
                                            <span class="icon"><i class="fas fa-clock"></i></span>
                                            <c:choose>
                                                <c:when test="${not empty position.heureArrivee}">
                                                    Arrivé à ${position.heureArrivee.toString().substring(0, 5)}
                                                </c:when>
                                                <c:otherwise>
                                                    --
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                        <p class="schedule-text">
                                            <span class="icon"><i class="fas fa-calendar-day"></i></span>
                                            <c:choose>
                                                <c:when
                                                    test="${not empty position.itineraire.heureDebutPrevue and not empty position.itineraire.heureFinPrevue}">
                                                    ${position.itineraire.heureDebutPrevue.toString().substring(0, 5)}
                                                    -
                                                    ${position.itineraire.heureFinPrevue.toString().substring(0, 5)}
                                                </c:when>
                                                <c:otherwise>
                                                    --
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                        
                                    </div>
                                    <button class="btn-itinerary" onclick="showItinerary('${position.id}')">
                                        <i class="fas fa-route me-2"></i>Voir l'itinéraire
                                    </button>
                                </div>
                            </c:forEach>
                        </div>

                        <div id="itinerary-section" class="itinerary-section" style="display: none;">
                            <div class="itinerary-header">
                                <h4 class="fw-bold mb-0"><i class="fas fa-truck me-2 text-primary"></i>Détails de
                                    l'itinéraire : <span id="selected-truck" class="text-primary">Nom du Truck</span>
                                </h4>
                                <button class="btn-close-itinerary" onclick="closeItinerary()">
                                    <i class="fas fa-times"></i> Fermer
                                </button>
                            </div>

                            <ul class="itinerary-list" id="itinerary-list"></ul>
                        </div>
                    </div>
                </section>

                <!-- ===== PRODUITS MIS EN AVANT (POPULAIRES & NOUVEAUX) ===== -->
                <section class="featured-products" id="featured-section">
                    <div class="container">
                        <div class="text-center mb-5">
                            <h2 class="section-title">Les Vedettes de la Semaine</h2>
                            <p class="section-subtitle">Nos meilleures ventes et nos dernières créations à ne surtout
                                pas manquer !</p>
                        </div>

                        <div class="row g-4">
                            <c:forEach var="produit" items="${featuredProducts}">
                                <div class="col-md-6 col-lg-4">
                                    <div class="card product-card h-100">
                                        <div class="card-img-wrapper">
                                            <img src="${pageContext.request.contextPath}/${produit.img}"
                                                alt="${produit.name}"
                                                onerror="this.src='${pageContext.request.contextPath}/images/logo.jpg'">
                                            <c:choose>
                                                <c:when test="${produit.tag == 'new'}">
                                                    <span class="product-badge badge-new"><i
                                                            class="fas fa-sparkles me-1"></i>Nouveau</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="product-badge badge-popular"><i
                                                            class="fas fa-star me-1"></i>Populaire</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="card-body d-flex flex-column">
                                            <div class="d-flex justify-content-between align-items-start mb-2">
                                                <h4 class="card-title fw-bold mb-0">${produit.name}</h4>
                                                <span class="price">${produit.priceLabel} Ar</span>
                                            </div>
                                            <p class="card-text text-muted small flex-grow-1">${produit.desc}</p>
                                            <div class="d-flex justify-content-between align-items-center mt-3">
                                                <span class="stars"><i class="fas fa-star"></i> 4.8 •
                                                    ${produit.category}</span>
                                                <button class="btn-add btn-sm btn"><i
                                                        class="fas fa-plus me-1"></i>Ajouter</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </section>

                <!-- ===== SECTION MENU COMPLET AVEC FILTRAGE ET PAGINATION ===== -->
                <section class="menu-section" id="menu-complet">
                    <div class="container">
                        <div class="text-center mb-5">
                            <h2 class="section-title">Notre Menu Complet</h2>
                            <p class="section-subtitle">Faites votre choix parmi nos délicieuses recettes préparées
                                minute.</p>

                            <!-- Boutons de filtrage de catégorie -->
                            <div class="d-flex flex-wrap justify-content-center gap-2 mb-4" id="category-filters">
                                <button class="filter-btn active" onclick="filterCategory('all')">Tous</button>
                                <button class="filter-btn" onclick="filterCategory('burgers')">Burgers</button>
                                <button class="filter-btn" onclick="filterCategory('tacos')">Tacos</button>
                                <button class="filter-btn" onclick="filterCategory('classics')">Classiques</button>
                                <button class="filter-btn"
                                    onclick="filterCategory('accompagnements')">Accompagnements</button>
                            </div>
                        </div>

                        <!-- Grille de produits dynamique -->
                        <div class="row g-4" id="menu-grid">
                            <!-- Généré dynamiquement en JS -->
                        </div>

                        <!-- Pagination dynamique -->
                        <div class="pagination-container" id="menu-pagination">
                            <!-- Généré dynamiquement en JS -->
                        </div>
                    </div>
                </section>

                <!-- ===== SECTION DES AVIS GOURMANDS (TÉMOIGNAGES & PUBLICATION) ===== -->
                

            </main>

            <!-- ===== FOOTER ===== -->
            <footer>
                <div class="container">
                    <div class="row g-4">
                        <div class="col-md-4">
                            <h4 class="footer-logo mb-3">MKALY MLAM</h4>
                            <p class="text-white-50 small">La street food qui réveille vos papilles à Antananarivo. Des
                                recettes authentiques et gourmandes cuisinées avec passion.</p>
                            <div class="social-links mt-4">
                                <a href="#"><i class="fab fa-facebook-f"></i></a>
                                <a href="#"><i class="fab fa-instagram"></i></a>
                                <a href="#"><i class="fab fa-tiktok"></i></a>
                                <a href="#"><i class="fab fa-whatsapp"></i></a>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <h5>Navigation</h5>
                            <ul class="list-unstyled">
                                <li><a href="#">Accueil</a></li>
                                <li><a href="#live-location">Trouver le truck</a></li>
                                <li><a href="#menu-complet">Menu Complet</a></li>
                                <!-- <li><a href="#avis-clients">Avis Clients</a></li> -->
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <h5>Horaires d'Ouverture</h5>
                            <ul class="list-unstyled text-white-50 small">
                                <li><i class="far fa-clock me-2"></i>Lun - Ven : 11h00 - 22h00</li>
                                <li><i class="far fa-clock me-2"></i>Samedi : 12h00 - 23h00</li>
                                <li><i class="far fa-clock me-2"></i>Dimanche : Fermé</li>
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <h5>Contact Rapide</h5>
                            <ul class="list-unstyled text-white-50 small">
                                <li><i class="fas fa-phone me-2"></i> 034 11 222 33</li>
                                <li><i class="fas fa-envelope me-2"></i> contact@mkalymlam.mg</li>
                                <li><i class="fas fa-truck-moving me-2"></i> Antananarivo, Madagascar</li>
                            </ul>
                        </div>
                    </div>
                    <hr class="my-4 border-secondary">
                    <div class="text-center text-white-50 small">
                        © 2026 Mkaly Mlam. Tous droits réservés. <span class="text-primary fw-bold">Vita Malagasy</span>
                    </div>
                </div>
            </footer>

            <!-- Scripts Bootstrap -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

            <!-- JS Personnalisé pour l'interactivité dynamique -->
            <script>
                const contextPath = '${pageContext.request.contextPath}';
                const menuItems = ${ menuItemsJson };
                const itineraries = ${ itinerairesJson };

                // --- GESTION DE LA PAGINATION ET DU FILTRAGE DU MENU ---
                let currentCategory = 'all';
                let currentPage = 1;
                const itemsPerPage = 3;

                function displayMenu() {
                    const grid = document.getElementById('menu-grid');
                    const pagination = document.getElementById('menu-pagination');

                    // Filtrer
                    let filtered = menuItems;
                    if (currentCategory !== 'all') {
                        filtered = menuItems.filter(item => item.category === currentCategory);
                    }

                    // Calcul pagination
                    const totalPages = Math.ceil(filtered.length / itemsPerPage);
                    if (currentPage > totalPages) currentPage = Math.max(1, totalPages);

                    const start = (currentPage - 1) * itemsPerPage;
                    const end = start + itemsPerPage;
                    const paginatedItems = filtered.slice(start, end);

                    // Générer les cartes
                    grid.innerHTML = '';
                    if (paginatedItems.length === 0) {
                        grid.innerHTML = '<div class="col-12 text-center my-4"><p class="text-muted">Aucun plat dans cette catégorie pour le moment.</p></div>';
                    } else {
                        paginatedItems.forEach(item => {
                            let badgeHtml = '';
                            if (item.tag === 'popular') {
                                badgeHtml = `<span class="product-badge badge-popular"><i class="fas fa-star me-1"></i>Populaire</span>`;
                            } else if (item.tag === 'new') {
                                badgeHtml = `<span class="product-badge badge-new"><i class="fas fa-sparkles me-1"></i>Nouveau</span>`;
                            }

                            grid.innerHTML += `
                        <div class="col-md-6 col-lg-4">
                            <div class="card product-card h-100">
                                <div class="card-img-wrapper">
                                    <img src="\${contextPath}/\${item.img}" alt="\${item.name}" onerror="this.src='\${contextPath}/images/burger.jpg'">
                                    \${badgeHtml}
                                </div>
                                <div class="card-body d-flex flex-column">
                                    <div class="d-flex justify-content-between align-items-start mb-2">
                                        <h5 class="fw-bold mb-0">\${item.name}</h5>
                                        <span class="price">\${item.price.toLocaleString()} Ar</span>
                                    </div>
                                    <p class="card-text text-muted small flex-grow-1">\${item.desc}</p>
                                    <div class="d-flex justify-content-between align-items-center mt-3">
                                        <span class="text-muted small"><i class="fas fa-heart text-danger me-1"></i> Street Food authentique</span>
                                        <button class="btn-add btn-sm btn" onclick="alert('Ajouté au panier virtuel !')">
                                            <i class="fas fa-plus me-1"></i>Ajouter
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                        });
                    }

                    // Générer pagination
                    pagination.innerHTML = '';
                    if (totalPages > 1) {
                        for (let i = 1; i <= totalPages; i++) {
                            pagination.innerHTML += `
                        <button class="page-btn \${i === currentPage ? 'active' : ''}" onclick="changePage(\${i})">
                            \${i}
                        </button>
                    `;
                        }
                    }
                }

                function filterCategory(cat) {
                    currentCategory = cat;
                    currentPage = 1;

                    // Activer le bouton de filtre
                    const buttons = document.querySelectorAll('#category-filters .filter-btn');
                    buttons.forEach(btn => btn.classList.remove('active'));
                    event.target.classList.add('active');

                    displayMenu();
                }

                function changePage(page) {
                    currentPage = page;
                    displayMenu();
                    // Scroll doux vers le haut de la section menu
                    document.getElementById('menu-complet').scrollIntoView({ behavior: 'smooth' });
                }

                // --- GESTION DES REVIEWS (AVIS) ---
                // Sélection de la note par étoiles
                const starSelector = document.getElementById('star-selector');
                const stars = starSelector.querySelectorAll('.fa-star');
                const ratingInput = document.getElementById('selected-rating');

                stars.forEach(star => {
                    // Surligner par défaut 5 étoiles au chargement
                    star.classList.add('active');

                    star.addEventListener('click', function () {
                        const rating = this.getAttribute('data-rating');
                        ratingInput.value = rating;

                        stars.forEach(s => {
                            if (s.getAttribute('data-rating') <= rating) {
                                s.classList.add('active');
                            } else {
                                s.classList.remove('active');
                            }
                        });
                    });
                });

                function showItinerary(truckId) {
                    const itinerarySection = document.getElementById('itinerary-section');
                    const truckTitle = document.getElementById('selected-truck');
                    const itineraryList = document.getElementById('itinerary-list');

                    const selected = itineraries.find(it => String(it.id) === String(truckId)) || itineraries[0];
                    if (!selected) {
                        return;
                    }

                    truckTitle.innerText = selected.truckName;
                    itineraryList.innerHTML = '';
                    (selected.stops || []).forEach(stop => {
                        itineraryList.innerHTML += `
                    <li>
                        <span class="time">${stop.time}</span>
                        <span class="point-dot"></span>
                        <span class="location-detail"><i class="fas fa-${stop.icon} me-2"></i>${stop.label}</span>
                    </li>
                `;
                    });

                    itinerarySection.style.display = 'block';
                    itinerarySection.scrollIntoView({ behavior: 'smooth' });
                }

                function closeItinerary() {
                    document.getElementById('itinerary-section').style.display = 'none';
                }

                function submitReview(event) {
                    event.preventDefault();
                    const name = document.getElementById('reviewer-name').value;
                    const comment = document.getElementById('reviewer-comment').value;
                    const rating = parseInt(ratingInput.value);

                    // Générer le code HTML des étoiles
                    let starsHtml = '';
                    for (let i = 1; i <= 5; i++) {
                        if (i <= rating) {
                            starsHtml += '<i class="fas fa-star"></i>';
                        } else {
                            starsHtml += '<i class="far fa-star"></i>';
                        }
                    }

                    // Créer une nouvelle carte d'avis
                    const reviewsList = document.getElementById('reviews-list');
                    const newReview = document.createElement('div');
                    newReview.className = 'review-card animate__animated animate__fadeIn';
                    newReview.innerHTML = `
                <div class="d-flex align-items-center justify-content-between mb-3">
                    <div class="d-flex align-items-center gap-3">
                        <div class="review-avatar d-flex align-items-center justify-content-center bg-primary text-white fw-bold">
                            \${name.charAt(0).toUpperCase()}
                        </div>
                        <div>
                            <h6 class="fw-bold mb-0">\${name}</h6>
                            <small class="text-muted">À l'instant</small>
                        </div>
                    </div>
                    <div class="stars">
                        \${starsHtml}
                    </div>
                </div>
                <p class="mb-0 text-muted">"\${comment}"</p>
            `;

                    // Ajouter au début de la liste
                    reviewsList.insertBefore(newReview, reviewsList.firstChild);

                    // Réinitialiser le formulaire
                    document.getElementById('add-review-form').reset();
                    stars.forEach(s => s.classList.add('active'));
                    ratingInput.value = 5;

                    alert("Merci beaucoup ! Votre avis a été publié avec succès.");
                }

                // --- CHARGEMENT INITIAL ---
                // window.onload = function () {
                //     displayMenu();
                // };
            </script>
        </body>

        </html>