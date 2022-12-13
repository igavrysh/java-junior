<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employees</title>
</head>
<body>
    <c:if test="${not empty result}">
        <h5><c:out value="${result}"></c:out></h5>
    </c:if>

    <%--  Displays Form --%>
    <form action="employee.do" method="post">
    <table border = "1" cellpadding="5" cellspacing="5">
    <tr>
        <td>Employee Name: </td>
        <td><input type="text" name="employeeName"></td>
    </tr>
    <tr>
        <td>Salary: </td>
        <td><input type="text" name="salary"></td>
    </tr>
    <tr>
        <td>Department Name: </td>
        <td><input type="text" name="deptName"> </td>
    </tr>
    <tr>
        <td colspan="2"><input type="submit" name="submit" value="Insert"> </td>
    </tr>
    </table>
    </form>
</body>
</html>