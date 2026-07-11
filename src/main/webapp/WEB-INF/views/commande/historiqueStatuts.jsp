<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Historique des statuts - Commande #${commande.idCommande}</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="dashboard">
    <c:set var="activeMenu" value="commande"/>

    <jsp:include page="/WEB-INF/views/fragments/sidebar.jsp"/>

    <div class="main">

        <div class="table-container" style="margin-top:30px;">

            <div class="table-header">
                <h1>
                    <i class="fas fa-history" style="color: var(--primary); margin-right:10px;"></i>
                    Historique des statuts — Commande #${commande.idCommande}
                </h1>
                <a href="${pageContext.request.contextPath}/commande/liste" class="btn-reset">
                    <i class="fas fa-arrow-left"></i> Retour
                </a>
            </div>

            <div style="background:#f9fafb;padding:20px;border-radius:12px;margin-bottom:25px;display:flex;gap:30px;flex-wrap:wrap;">
                <div>
                    <small style="color:#6b7280;">Statut actuel</small><br>
                    <strong style="font-size:16px;">
                        <c:choose>
                            <c:when test="${commande.statutCommande.libelle == 'EN_ATTENTE'}">
                                <span style="color:#D97706;">EN ATTENTE</span>
                            </c:when>
                            <c:when test="${commande.statutCommande.libelle == 'PREPARATION'}">
                                <span style="color:#2563EB;">PREPARATION</span>
                            </c:when>
                            <c:when test="${commande.statutCommande.libelle == 'PRETE_POUR_RECUPERATION'}">
                                <span style="color:#7C3AED;">PRETE</span>
                            </c:when>
                            <c:when test="${commande.statutCommande.libelle == 'LIVREE'}">
                                <span style="color:#059669;">LIVREE</span>
                            </c:when>
                            <c:when test="${commande.statutCommande.libelle == 'ANNULEE'}">
                                <span style="color:#DC2626;">ANNULEE</span>
                            </c:when>
                            <c:otherwise>
                                ${commande.statutCommande.libelle}
                            </c:otherwise>
                        </c:choose>
                    </strong>
                </div>
                <div>
                    <small style="color:#6b7280;">Montant total</small><br>
                    <strong style="font-size:16px;">
                        <fmt:formatNumber value="${commande.montantTotal}" type="number" minFractionDigits="2"/> Ar
                    </strong>
                </div>
                <div>
                    <small style="color:#6b7280;">Date de création</small><br>
                    <strong style="font-size:16px;">
                        <fmt:parseDate value="${commande.dateHeureCreation}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </strong>
                </div>
            </div>

            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th><i class="fas fa-clock"></i> Date du changement</th>
                    <th><i class="fas fa-arrow-left"></i> Ancien statut</th>
                    <th><i class="fas fa-arrow-right"></i> Nouveau statut</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty historiques}">
                    <tr>
                        <td colspan="4">
                            <div class="empty-state">
                                <i class="fas fa-history" style="font-size:48px;color:#d1d5db;margin-bottom:15px;display:block;"></i>
                                <p>Aucun historique de changement de statut.</p>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <c:forEach items="${historiques}" var="h" varStatus="status">
                    <tr>
                        <td>
                            <span class="badge badge-id">#${h.idHistorique}</span>
                        </td>
                        <td>
                            <fmt:parseDate value="${h.dateChangement}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate2" type="both"/>
                            <fmt:formatDate value="${parsedDate2}" pattern="dd/MM/yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <c:if test="${h.ancienStatut != null}">
                                <c:choose>
                                    <c:when test="${h.ancienStatut == 'EN_ATTENTE'}">
                                        <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#FEF3C7;color:#92400E;">EN_ATTENTE</span>
                                    </c:when>
                                    <c:when test="${h.ancienStatut == 'PREPARATION'}">
                                        <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#DBEAFE;color:#1E40AF;">PREPARATION</span>
                                    </c:when>
                                    <c:when test="${h.ancienStatut == 'PRETE_POUR_RECUPERATION'}">
                                        <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#EDE9FE;color:#5B21B6;">PRETE</span>
                                    </c:when>
                                    <c:when test="${h.ancienStatut == 'LIVREE'}">
                                        <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#D1FAE5;color:#065F46;">LIVREE</span>
                                    </c:when>
                                    <c:when test="${h.ancienStatut == 'ANNULEE'}">
                                        <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#FEE2E2;color:#991B1B;">ANNULEE</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#F3F4F6;color:#374151;">${h.ancienStatut}</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${h.ancienStatut == null}">
                                <span style="color:#9ca3af;font-style:italic;">Initial</span>
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${h.nouveauStatut == 'EN_ATTENTE'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#FEF3C7;color:#92400E;">EN_ATTENTE</span>
                                </c:when>
                                <c:when test="${h.nouveauStatut == 'PREPARATION'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#DBEAFE;color:#1E40AF;">PREPARATION</span>
                                </c:when>
                                <c:when test="${h.nouveauStatut == 'PRETE_POUR_RECUPERATION'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#EDE9FE;color:#5B21B6;">PRETE</span>
                                </c:when>
                                <c:when test="${h.nouveauStatut == 'LIVREE'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#D1FAE5;color:#065F46;">LIVREE</span>
                                </c:when>
                                <c:when test="${h.nouveauStatut == 'ANNULEE'}">
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#FEE2E2;color:#991B1B;">ANNULEE</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="padding:4px 12px;border-radius:20px;font-size:12px;font-weight:600;background:#F3F4F6;color:#374151;">${h.nouveauStatut}</span>
                                </c:otherwise>
                            </c:choose>
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
