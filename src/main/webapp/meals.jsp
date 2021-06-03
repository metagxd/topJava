<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<table>
    <th>Дата</th>
    <th>Описание</th>
    <th>Калории</th>
    <th></th>
    <th></th>
    <c:forEach items="${requestScope.mealList}" var="meals">
        <tr class="${meals.excess == true ? 'red' : 'green'}">
            <td>${meals.formattedDateTime}</td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
            <td><a href="meals?action=edit&mealId=${meals.id}">Edit</a></td>
            <td><a href="meals?action=delete&mealId=${meals.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<a href="meals?action=create">Create</a>
</body>
</html>
