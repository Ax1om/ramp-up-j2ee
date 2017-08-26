<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="propertyName" required="true" %>
<%--@elvariable id="messages" type="java.util.Map"--%>
<c:set var="propertyName" value="error_${propertyName}" />
<c:if test="${not empty messages.get(propertyName)}">
    <span class="upsert__error-message">${messages.get(propertyName)}</span>
    <c:set var="dummy" value="${messages.remove(propertyName)}" />
</c:if>