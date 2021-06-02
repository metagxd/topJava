<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border="1">
    <th>Дата</th>
    <th>Описание</th>
    <th>Калории</th>
    <c:forEach items="${requestScope.mealList}" var="meals">
        <tr>
            <td>${meals.dateTime}</td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
