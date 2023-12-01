<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script src="resources/js/topjava.common.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>

        <form class="form-row" method="get" action="meals/filter">
            <div class=" form-group col-sm">
                <label><spring:message code="meal.startDate"/>:</label>
                <input type="date" name="startDate" value="${param.startDate}">
            </div>
            <div class="form-group col-sm">
                <label><spring:message code="meal.endDate"/>:</label>
                <input type="date" name="endDate" value="${param.endDate}">
            </div>
            <div class="form-group col-sm">
                <label><spring:message code="meal.startTime"/>:</label>
                <input type="time" name="startTime" value="${param.startTime}">
            </div>
            <div class="form-group col-sm">
                <label><spring:message code="meal.endTime"/>:</label>
                <input type="time" name="endTime" value="${param.endTime}">
            </div>
            <div class="float-right">
                <button class="btn btn-primary"">
                    <spring:message code="meal.filter"/>
                </button>
            </div>
        </form>


        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.meals}" var="meal">
                <tr data-meal-excess="${meal.excess}">
                    <td>${fn:formatDateTime(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                    <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>