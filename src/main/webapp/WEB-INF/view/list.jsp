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

		<div class="row">
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
			
			<strong><spring:url value="/home" var="homeUrl" /> <a href="${(homeUrl)}"> &lt; Go Back to Home</a></strong>
			
		</div>

		<div class="row" id="row3">
			<div id="doneDocShellsListed" class="panel panel-default"
				style="position: relative">
				<div class="panel-heading">
					<div class="panel-title">List of Partial Done Doc Shell</div>
				</div>

				<div class="panel-body">
					<table id="NotDoneDocShellsListedTable" class="table table striped">
						<thead>
							<tr>
								<th>DocShell ID</th>
								<th>DocShell Name</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${NotDoneDocShells}"
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
		
				<div class="row" id="row3">
			<div id="orphanedDocBookmarksListed" class="panel panel-default"
				style="position: relative">
				<div class="panel-heading">
					<div class="panel-title">List of Orphaned Doc Bookmarks</div>
				</div>

				<div class="panel-body">
					<table id="orphanedDocBookmarksListedTable" class="table table striped">
						<thead>
							<tr>
								<th>Bookmark ID</th>
								<th>Bookmark Name</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orphanedDocBookmarks}"
								var="docBookmark">
								<c:if test="${ docBookmark.id>1}">
									<tr>
										<td><c:out value="${docBookmark.id} " /></td>

										<td><c:out value="${docBookmark.internalName }" /></td>
										<td><spring:url value="/mark/${docBookmark.id }"
												var="docBookmarkUrl">
											</spring:url> <a href="${(docBookmarkUrl)}">Review</a></td>
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
