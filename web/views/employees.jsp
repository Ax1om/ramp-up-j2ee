<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="employees" type="java.util.List"--%>

<script type="text/javascript">
    function deleteEmployee(employeeId, employeeName) {
      if(confirm("Are you sure you want to delete " + employeeName + "?")){
        location.href = "/delete?employeeId=" + employeeId;
      }
    }
</script>
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
                <td>
                    <span><a href="<c:url value='/upsert?employeeId=${employee.employeeId}' />" class="link-as-button">Edit</a></span>
                    <span><button onclick="deleteEmployee(${employee.employeeId}, '${employee.name}')" class="link-as-button">Delete</button></span>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>