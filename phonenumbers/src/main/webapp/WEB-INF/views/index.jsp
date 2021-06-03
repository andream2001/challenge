<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ciao</title>
</head>
<body>
	<form:form method="post" action="/check" modelAttribute="phonenumber">
		<form:input path="smsPhone" />
		<input type="submit" value="Submit" />
	</form:form>

	${message}

</body>
</html>