<%--@elvariable id="employee" type="com.nrqapps.j2ee.models.Employee"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>Upsert Employee</title>
        <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/main.css'/>" >
    </head>
    <body>
        <div class="upsert container">
            <form method="post">
                <fieldset class="upsert__fieldset">
                    <legend class="upsert__legend">${not empty employee.employeeId ? 'Edit ' : 'Insert '} Employee</legend>
                    <label class="upsert__label">
                        <span class="upsert__label-text">Name:</span>
                        <input class="upsert__input" name="name" type="text" placeholder="Name" value="${employee.name}">
                    </label>
                    <label class="upsert__label">
                        <span class="upsert__label-text">Surname:</span>
                        <input class="upsert__input" name="surname" type="text" placeholder="Surname" value="${employee.surname}">
                    </label>
                    <label class="upsert__label">
                        <span class="upsert__label-text">Country:</span>
                        <input class="upsert__input" name="country" type="text" placeholder="Country" value="${employee.country}">
                    </label>
                    <label class="upsert__label">
                        <span class="upsert__label-text">Birth Date:</span>
                        <input class="upsert__input" name="birthDate" type="date" placeholder="Birth Date" value="<fmt:formatDate pattern="yyyy-MM-dd" value = "${employee.birthDate}" />">
                    </label>
                    <label class="upsert__label">
                        <span class="upsert__label-text">Marital Status:</span>
                        <select class="upsert__input" name="maritalStatusId">
                            <c:forEach var="maritalStatus" items="${maritalStatusList}">
                                <option
                                        value="${maritalStatus.maritalStatusId}"
                                    ${employee.maritalStatus.status eq maritalStatus.status ? 'selected':''}
                                >
                                        ${maritalStatus.status}
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                    <div>
                        <input class="link-as-button" type="submit" value="Save">
                        <a href="<c:url value="/"/>" class="link-as-button">Cancel</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>
