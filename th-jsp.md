| Thymeleaf                      | JSP/JSTL                                                |
| ------------------------------ | ------------------------------------------------------- |
| `th:href="@{/ingredients}"`    | `href="${pageContext.request.contextPath}/ingredients"` |
| `th:text="${nom}"`             | `${nom}`                                                |
| `th:each="i : ${ingredients}"` | `<c:forEach var="i" items="${ingredients}">`            |
| `th:if`                        | `<c:if>`                                                |
| `th:unless`                    | `<c:if test="${empty ...}">` ou `<c:choose>`            |
| `th:action`                    | `action="..."`                                          |
| `th:value`                     | `value="${...}"`                                        |
