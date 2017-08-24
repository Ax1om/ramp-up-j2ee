<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mauricio
  Date: 7/25/17
  Time: 4:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Employees CRUD</title>
      <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/main.css' />" >
  </head>
  <body>
    <div class="container">
      <jsp:include page="/search" />
    </div>
  </body>
</html>
