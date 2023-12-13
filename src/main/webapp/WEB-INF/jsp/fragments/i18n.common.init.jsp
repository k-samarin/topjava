<c:param name="addTitle" value="${addTitle}"/>
<c:param name="editTitle" value="${editTitle}"/>

<script type="text/javascript">
    const i18n = {};
    i18n["addTitle"] = '${param.addTitle}';
    i18n["editTitle"] = '${param.editTitle}';

    <c:forEach var="key" items='${["common.deleted","common.saved","common.errorStatus","common.confirm"]}'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>