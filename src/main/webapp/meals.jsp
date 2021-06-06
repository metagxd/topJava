<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        <%--https://stackoverflow.com/questions/35606551/jstl-localdatetime-format--%>
        <tr class="${meals.excess == true ? 'red' : 'green'}">
            <fmt:parseDate value="${meals.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <td><fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
