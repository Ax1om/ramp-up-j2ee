<%@ taglib prefix="tmpl" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="userNotification" type="java.lang.String"--%>
<%--@elvariable id="employees" type="java.util.List"--%>
<%--@elvariable id="messages" type="java.util.Map"--%>
<tmpl:main>
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
    <table class="table">
        <caption class="table__caption">
            <span>Employees</span>
            <a href="${pageContext.request.contextPath}/upsert" class="table__new-employee-btn link-as-button">New Employee</a>
        </caption>
        <thead>
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Country</th>
                <th>Birth Date</th>
                <th>Marital Status</th>
                <th>Skills</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.name}</td>
                    <td>${employee.surname}</td>
                    <td>${employee.country}</td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value = "${employee.birthDate}" /></td>
                    <td>${employee.maritalStatus.status}</td>
                    <td>
                        <c:forEach var="skill" items="${employee.skills}" varStatus="loopStatus">
                            <span>${skill.name}${ not loopStatus.last ? ', ':''}</span>
                        </c:forEach>
                    </td>
                    <td>
                        <span><a href="${pageContext.request.contextPath}/upsert?employeeId=${employee.employeeId}" class="link-as-button">Edit</a></span>
                        <span><button onclick="deleteEmployee(${employee.employeeId}, '${employee.name}')" class="link-as-button">Delete</button></span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script type="text/javascript">
      function deleteEmployee(employeeId, employeeName) {
        if(confirm('Are you sure you want to delete ' + employeeName + '?')){
          location.href = '${pageContext.request.contextPath}/delete?employeeId=' + employeeId;
        }
      }
    </script>
</tmpl:main>
