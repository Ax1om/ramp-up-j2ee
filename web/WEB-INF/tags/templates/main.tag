<%--@elvariable id="userNotification" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Employees CRUD</title>
        <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/main.css' />" >
        <c:if test="${not empty userNotification}">
            <script type="text/javascript">
              window.notification = ${userNotification};
            </script>
        </c:if>
    </head>
    <body>
        <div class="container">
         <jsp:doBody />
        </div>
        <div id="notification-area" class="notification-area"></div>
        <script type="text/javascript" src="<c:url value='/static/js/notifications.js' />"></script>
    </body>
</html>
