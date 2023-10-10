<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
    <head>
        <title>Meals</title>
    </head>
    <body>
        <h2>Meal</h2>
        <c:set var="id" value="${id}"/>
        <form method="POST" action='' name="frmEditMeal">
            DateTime : <input type="datetime-local" name="datetime" value = "${datetime}"/>
            <br />
            Description : <input type="text" name="description" value = "${description}"/>
            <br />
            Calories : <input type="text" name="calories" value = "${calories}"/>
            <br />
            <c:set var="addOrUpdate" value="Add"/>
            <c:choose>
                <c:when test="${id >= 0}">
                    <c:set var="addOrUpdate" value="Update"/>
                </c:when>
            </c:choose>
            <input type="submit" value="${addOrUpdate}" />
            <button onclick="window.history.back()" type="button">Cancel</button>
        </form>
    </body>
</html>
