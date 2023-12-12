<c:forEach var="key" items='${["common.deleted","common.saved","common.errorStatus","common.confirm"]}'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
</c:forEach>