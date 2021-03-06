<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/resources/css/styles.css'/>" rel="stylesheet" type="text/css">
        <title>Admin edit exercise</title>
    </head>

    <body>
        <%@ include file="/WEB-INF/fragment/navbar.jsp" %>

        <div class="main-container-add">
            <div class="container-add">

                <h2 class="text-breaker">EDIT EXERCISE <br> <c:out value="${exercise.title}"/></h2>

                <div class="content-add">
                    <c:choose>

                        <c:when test="${exerciseNotExists}">
                            <p class="feedback-text-submitted">Such exercise is not registered.</p>
                        </c:when>

                        <c:otherwise>
                            <form action="${contextPath}/admin/edit/exercise" method="post">
                                <label>
                                    <input type="text" name="title" placeholder="Exercise title" value="<c:out value='${titleVal}'/>">
                                    <c:if test="${not empty blankTitle}">
                                        <label class="hasError"><c:out value="${blankTitle}"/></label>
                                    </c:if>
                                    <c:if test="${not empty capacityExceededTitle}">
                                        <label class="hasError"><c:out value="${capacityExceededTitle}"/></label>
                                    </c:if>
                                </label>

                                <br>

                                <label>
                                    <textarea name="description" placeholder="Exercise description" rows="10"><c:out value="${descriptionVal}"/></textarea>
                                    <c:if test="${not empty blankDescription}">
                                        <label class="hasError"><c:out value="${blankDescription}"/></label>
                                    </c:if>
                                    <c:if test="${not empty capacityExceededDescription}">
                                        <label class="hasError"><c:out value="${capacityExceededDescription}"/></label>
                                    </c:if>
                                </label>

                                <label>
                                    <input class="addButton" type="submit" value="Save">
                                </label>

                            </form>
                        </c:otherwise>

                    </c:choose>
                </div>
            </div>
            <div class="backButton">
                <button><a href="${contextPath}/admin/exercises">Back</a></button>
            </div>

            <c:if test="${isUpdated}">
                <p class="feedback-text-submitted">Exercise updated.</p>
            </c:if>
        </div>

    </body>
</html>