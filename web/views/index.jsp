<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Employees CRUD</title>
      <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/main.css' />" >
  </head>
  <body>
    <div class="container">
      <div class="search__container">
        <form>
          <fieldset class="search__fieldset">
            <legend>Search</legend>
            <label>
              <input class="search__input" type="text" name="query">
            </label>
            <input class="link-as-button" type="submit" value="Search">
            <c:if test="${not empty param.query}">
              <div class="search__result">
                <span>Filtering by: <b>${param.query}</b> </span>
                <a href="${pageContext.request.contextPath}/">clear</a>
              </div>
            </c:if>
          </fieldset>
        </form>
      </div>
      <jsp:include page="/search" />
    </div>
  </body>
</html>
