<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>

<html>
<style>
    input {
        margin-bottom: 6px;
    }

    b {
        float: left;
        width: 15%;
        margin-top: 6px;
    }
</style>
<head>
    <title>Edit</title>
</head>
<body>
<h1>${action} meal</h1>
<section>
    <form method="post">
        <input type="hidden" name="id" value="<c:out value="${meal.id}"/>">
        <b>First Name</b>
        <input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>">
        <br>
        <b>Description</b>
        <input type="text" name="description" value="<c:out value="${meal.description}"/>">
        <br>
        <b>Calories</b>
        <input type="text" name="calories" value="<c:out value="${meal.calories}"/>">
        <br>
        <input type="submit" value="Submit">
        <button onclick="window.history.back()" value="cancel" name="cancelButton">Cancel</button>
    </form>
</section>
</body>
</html>