<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<head>
<title>Doc Bookmarks</title>
<jsp:include page="../includes/headTag.jsp" />
</head>

<body>
	<div class="container">
	<jsp:include page="../includes/navbar.jsp" />
		<div class="row" id="topper">
		<div class="col-md-8">
			<h3>
				Hello
			
			</h3>
			<br />
			
			</div>
		</div>
		<div class="row">
		<div class="col-md-8">
			<h3>
				Hello
				<c:out value="${employee.username}" />
			</h3>
			<br />
			<c:if test="${not empty error}">
				<div class="error" style="color: red">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg" style="color: blue">${msg}</div>
			</c:if>
			</div>
		</div>
		<div class="row" id="row1">
			<div class="col-md-8">
				<form:form id="home" modelAttribute="homeFlowActions" role="form">
					<div class="panel panel-default">
						<div class="panel-heading">
							<strong class="panel-title"> Select a Doc Shell to
								begin: </strong>
						</div>
						<div class="panel-body">
							Listed as <br /> DocShellId DisplayName ShellType
							<c:choose>
							<c:when test="${not empty homeFlowActions.notDoneDocShells}">
							<form:select path="selectedDocShell">
								<form:options items="${homeFlowActions.notDoneDocShells}" />
							</form:select>
							<form:errors cssClass="error" path="selectedDocShell" />							
							</c:when>
							<c:otherwise>
							No more doc shells to report.
							</c:otherwise>
							</c:choose>
							<br /> <input type="submit" name="_eventId_mapDocShell"
								value="Start" /> <input type="button" name="_eventId_cancel"
								value="Cancel" />
						</div>
					</div>
				</form:form>
			</div>
			<br />
		</div>
		<div class="row" id="row2">
			<div class="col-md-8">
			<%--
			 	<hr />
				Review DocShell Partial Completed: <br /> <a
					href="./docshell/list">Doc Shell With Records List</a> <br />
				<hr />
				--%>
			</div>
		</div>
		<div class="row" id="row3">
			<div id="doneDocShellsListed" class="panel panel-default"
				style="position: relative">
				<div class="panel-heading">
					<div class="panel-title">List of Done Doc Shell</div>
				</div>

				<div class="panel-body">
					<table id="doneDocShellsListedTable" class="table table striped">
						<thead>
							<tr>
								<th>DocShell ID</th>
								<th>DocShell Name</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${homeFlowActions.doneDocShells}"
								var="docShell">
								<c:if test="${ docShell.id>1}">
									<tr>
										<td><c:out value="${docShell.id} " /></td>

										<td><c:out value="${docShell.docShellName }" /></td>
										<td><spring:url value="/docshell/${docShell.id }"
												var="docShellUrl">
											</spring:url> <a href="${(docShellUrl)}">Review</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../includes/footer.jsp" />
</body>
</html>
