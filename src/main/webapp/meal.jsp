<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
    <head>
        <title>Meals</title>
    </head>
    <body>
        <h2>Meal</h2>
        <c:set var="id" value="${meal.id}"/>
        <form method="POST" action='' name="frmEditMeal">
            DateTime : <input type="datetime-local" name="datetime" value = "${meal.dateTime}"/>
            <br />
            Description : <input type="text" name="description" value = "${meal.description}"/>
            <br />
            Calories : <input type="number" name="calories" value = "${meal.calories}"/>
            <br />
            <input type="submit" value="${meal.id != null ? 'Update' : 'Add'}" />
            <button onclick="window.history.back()" type="button">Cancel</button>
        </form>
    </body>
</html>
