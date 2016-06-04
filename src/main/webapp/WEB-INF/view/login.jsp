<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<html>
<head>
<meta charset="utf-8">
<title>Doc Bookmarks Mapping tool</title>
<jsp:include page="../includes/headTag.jsp" />
</head>
<body>
	<div class="container" id="pageBox" style="width: 500px">
		<div class="row">
			<h1>DocBookmarks</h1>
			<h2>Mapping Tool</h2>
		</div>
		<br />
		<%
			pageContext.setAttribute("now", new org.joda.time.DateTime());
		%>
		<div class="row">
			Today is ${now.toString("MM:dd:yy:HH:mm:ss")}

			<c:if test="${not empty error}">
				<div class="error" style="color: red">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg" style="color: blue">${msg}</div>
			</c:if>
		</div>

		<div class="row">
			<div class="col-md-6">
				<form:form name="login" modelAttribute="loginFlowActions"
					action="login" method='POST'>
            
		User: <br />
					<form:input type="text" path="username" />
					<form:errors cssClass="error" path="username" />
					<br />
		Passcode: <br />
					<form:input type="password" path="passcode" />
					<form:errors cssClass="error" path="passcode" />
					<br />
					<input name="_eventId_doLogin" type="submit" value="Login" /> | 
		 <input type="button" name="_eventId_cancel" value="Cancel" />

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form:form>
			</div>
		</div>
	</div>
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>
