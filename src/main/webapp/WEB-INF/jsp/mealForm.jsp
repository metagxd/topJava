<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sping" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<link rel="stylesheet" href="/topjava/resources/css/style.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <hr>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <h2>
        <spring:message code="${action=='create'?'meal.create':'meal.update'}"/>
    </h2>

    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>
                <spring:message code="meal.date"/>:
            </dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>
                <spring:message code="meal.description"/>:
            </dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>
                <spring:message code="meal.calories"/>:
            </dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>