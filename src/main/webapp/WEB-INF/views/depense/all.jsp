<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Toutes les depenses</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">

    <style>
        .cartes {
            display:grid;grid-template-columns:repeat(2,1fr);gap:16px;margin-bottom:24px;
        }
        .carte {
            padding:20px;border-radius:14px;border:1px solid #e5e7eb;background:#fff;
        }
        .carte .label { font-size:13px;color:#6b7280;margin-bottom:4px; }
        .carte .valeur { font-size:26px;font-weight:700;color:var(--primary);margin:0; }
    </style>
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="depenses"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp" />

    <div class="main">

        <div class="table-container">

            <div class="table-header">
                <h1>
                    <i class="fas fa-money-bill-wave" style="color:var(--primary);margin-right:10px;"></i>
                    Toutes les depenses
                </h1>

                <div style="display:flex;gap:10px;align-items:center;flex-wrap:wrap;">
                    <c:url var="csvExportUrl" value="/depenses/export/csv">
                        <c:if test="${not empty selectedType}">
                            <c:param name="idTypeDepense" value="${selectedType}" />
                        </c:if>
                        <c:if test="${not empty selectedStatut}">
                            <c:param name="idStatut" value="${selectedStatut}" />
                        </c:if>
                        <c:if test="${not empty selectedSession}">
                            <c:param name="idSession" value="${selectedSession}" />
                        </c:if>
                        <c:if test="${not empty dateDebut}">
                            <c:param name="dateDebut" value="${dateDebut}" />
                        </c:if>
                        <c:if test="${not empty dateFin}">
                            <c:param name="dateFin" value="${dateFin}" />
                        </c:if>
                    </c:url>

                    <c:url var="pdfExportUrl" value="/depenses/export/pdf">
                        <c:if test="${not empty selectedType}">
                            <c:param name="idTypeDepense" value="${selectedType}" />
                        </c:if>
                        <c:if test="${not empty selectedStatut}">
                            <c:param name="idStatut" value="${selectedStatut}" />
                        </c:if>
                        <c:if test="${not empty selectedSession}">
                            <c:param name="idSession" value="${selectedSession}" />
                        </c:if>
                        <c:if test="${not empty dateDebut}">
                            <c:param name="dateDebut" value="${dateDebut}" />
                        </c:if>
                        <c:if test="${not empty dateFin}">
                            <c:param name="dateFin" value="${dateFin}" />
                        </c:if>
                    </c:url>

                    <a href="${csvExportUrl}" class="btn-secondary"
                       style="height:44px;display:inline-flex;align-items:center;justify-content:center;text-decoration:none;">
                        <i class="fas fa-file-csv"></i>
                        CSV
                    </a>

                    <a href="${pdfExportUrl}" class="btn-secondary"
                       style="height:44px;display:inline-flex;align-items:center;justify-content:center;text-decoration:none;">
                        <i class="fas fa-file-pdf"></i>
                        PDF
                    </a>
                </div>
            </div>

            <div class="cartes">
                <div class="carte">
                    <div class="label"><i class="fas fa-euro-sign"></i> Total filtre</div>
                    <p class="valeur">${totalFiltre} €</p>
                </div>
                <div class="carte">
                    <div class="label"><i class="fas fa-receipt"></i> Depenses</div>
                    <p class="valeur">${depenses.size()}</p>
                </div>
            </div>

            <form method="get" action="${pageContext.request.contextPath}/depenses"
                  style="display:grid;grid-template-columns:1fr 1fr 1fr 1fr 1fr auto;gap:12px;align-items:end;margin-bottom:20px;padding:16px 18px;background:#f9fafb;border:1px solid #e5e7eb;border-radius:14px;">

                <div class="form-group" style="margin-bottom:0;">
                    <label for="dateDebut">Du</label>
                    <input type="date" id="dateDebut" name="dateDebut" value="${dateDebut}"
                           style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                </div>

                <div class="form-group" style="margin-bottom:0;">
                    <label for="dateFin">Au</label>
                    <input type="date" id="dateFin" name="dateFin" value="${dateFin}"
                           style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                </div>

                <div class="form-group" style="margin-bottom:0;">
                    <label for="idSession">Session</label>
                    <select id="idSession" name="idSession" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                        <option value="">Toutes</option>
                        <c:forEach items="${sessions}" var="s">
                            <option value="${s.id}" ${selectedSession == s.id ? 'selected' : ''}>
                                #${s.id} - ${s.truck.immatriculation} - ${s.dateSession}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group" style="margin-bottom:0;">
                    <label for="idTypeDepense">Type</label>
                    <select id="idTypeDepense" name="idTypeDepense" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                        <option value="">Tous</option>
                        <c:forEach items="${types}" var="t">
                            <option value="${t.id}" ${selectedType == t.id ? 'selected' : ''}>${t.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group" style="margin-bottom:0;">
                    <label for="idStatut">Statut</label>
                    <select id="idStatut" name="idStatut" style="height:44px;padding:0 12px;border:1px solid #d1d5db;border-radius:10px;background:white;width:100%;">
                        <option value="">Tous</option>
                        <c:forEach items="${statuts}" var="s">
                            <option value="${s.id}" ${selectedStatut == s.id ? 'selected' : ''}>${s.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn-success" style="height:44px;">
                    <i class="fas fa-filter"></i>
                    Filtrer
                </button>
            </form>

            <table>
                <thead>
                    <tr>
                        <th><i class="fas fa-hashtag"></i> ID</th>
                        <th><i class="fas fa-truck"></i> Session</th>
                        <th><i class="fas fa-tag"></i> Type</th>
                        <th><i class="fas fa-euro-sign"></i> Montant</th>
                        <th><i class="fas fa-align-left"></i> Raison</th>
                        <th><i class="fas fa-calendar"></i> Date</th>
                        <th><i class="fas fa-check-circle"></i> Statut</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty depenses}">
                        <tr>
                            <td colspan="7">
                                <div class="empty-state">
                                    <i class="fas fa-money-bill-wave" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                    <p>Aucune depense enregistree.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach items="${depenses}" var="d">
                        <tr>
                            <td><span class="badge badge-id">${d.id}</span></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/session/${d.session.id}/depenses"
                                   style="color:var(--primary);text-decoration:none;font-weight:600;">
                                    #${d.session.id} - ${d.session.truck.immatriculation}
                                </a>
                            </td>
                            <td><strong>${d.typeDepense.libelle}</strong></td>
                            <td><span style="font-weight:600;color:var(--primary);">${d.montantDepense} €</span></td>
                            <td>${d.raisonDetaillee}</td>
                            <td>${d.dateDepense}</td>
                            <td>
                                <span class="badge bg-warning text-dark">
                                    ${d.statutValidationAdmin.libelle}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>

    </div>
</div>

</body>
</html>
