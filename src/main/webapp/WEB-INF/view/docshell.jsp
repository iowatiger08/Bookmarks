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
	<form:form id="mapBookmarkToDocShell" modelAttribute="docShellFlowActions">
		<form:hidden path="docShellId" name="docShellId" />
		<form:hidden path="employee" name="employee" value="${employee.username}" />
		<div class="row" id="topper">
		<div class="col-md-8">
			<p>
				Hello
			
			</p>
			<br />
			
			</div>
		</div>
		<div class="row" >
		<div class="col-md-8">
			<h4>Hello <c:out value="${employee.username}" /></h4><br />
			<p> Select Doc Bookmark found for this Doc Shell and the Submit. <br />
			The bookmark should then show in the list of Mapped Doc Bookmark. <br /> 
			When done, click the box &quot;Is Done&quot;</p>
			<strong><spring:url value="/home" var="homeUrl" /> <a href="${(homeUrl)}"> &lt; Go Back to Home</a></strong>
		</div>
		</div>

		<div class="row">
			Messages ::
			<c:if test="${not empty error}">
				<div class="error" style="color: red">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg" style="color: blue">${msg}</div>
			</c:if>
		</div>		
		<div class="row" id="docShellInfo">
			<div id="formHeaderBanner">
				<table class="table tabled-bordered  table-condensed"
					style="width: 500px">
					<thead>
						<tr>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody id="shellInfo">
						<tr>
							<td><strong>Selected DocShell:</strong></td>
							<td><c:out value="${docShell.id } ${docShell.docShellName }" /><br /></td>
						</tr>
						<tr>
							<td><strong>Doc Shell Name:</strong></td>
							<td><c:out value="${docShell.docShellName }" /><br /></td>
						</tr>

						<tr>
							<td style="vertical-align: top"><strong>Display Name:</strong></td>
							<td><c:out value="${docShell.docShellDisplayName }" /></td>
						</tr>
						<tr>
							<td style="vertical-align: top"><strong>Shell Type:</strong></td>
							<td><c:out value="${docShell.shellType }" /></td>
						</tr>
						<c:choose>
							<c:when test="${docShell.datawindowObjectName!=null }">
								<tr>
									<td style="vertical-align: top"><strong>Data Window Object Name:</strong></td>
									<td><c:out value="${docShell.datawindowObjectName }" /></td>
								</tr>
							</c:when>
							<c:otherwise>

							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>

		</div>
		<div class="row" id="rowIsDone">
			<div id="isDone" class="md-col-8">
			<strong>Is Done ::</strong><br />
					Yes <form:radiobutton path="isDone" name ="isDone" value="1"/> <br />
					No <form:radiobutton path="isDone" name ="isDone" value="0"/> 
					<br /><input type="submit" name="_eventId_shellIsDone" value="Update Is Done" /> <br />
					<form:errors cssClass="error" path="isDone" /><br />   		 
				</div>
		</div>		
		<div class="row" id="rowSelectMarks">
			<div id="mappedDocBookmarks" class="md-col-8">
			<strong>Available DocBookmarks for this DocShell</strong><br/>
			<%--Start typing internal name for DocBookmarks <br/>  --%>
			Pick one <br/>
									
					<form:select path="selectedMark" size="8">
						<form:option value="0" >None</form:option>
						<form:options items="${docShellFlowActions.notSelectedBookmarks}" />
					</form:select>
					<form:errors cssClass="error" path="selectedMark" />	
					<%--
					<form:input path="selectedMark" id="selectedMark" />
					 --%>
					<form:errors cssClass="error" path="selectedMark" />
					 <br />	        
					 <input type="submit" name="_eventId_addMarktoShell" value="Add Mark to This Shell" /> 
 					 <br />	
 					 <br />	
			</div>
		</div>
		
		<div class="row" id="row3">
			<div id="doneDocBookmarksListed" class="panel panel-default"
				style="position: relative">
				<div class="panel-heading">
					<div class="panel-title">List of Mapped Doc Bookmarks</div>
				</div>

				<div class="panel-body">
					<table id="doneDocShellsListedTable" class="table table striped">

								<thead>
									<tr>
										<th>Mark ID</th>
										<th>Mark Name</th>
										<th>Employee</th>
										<th>Remove?</th>
									</tr>
								</thead>
								<tbody>
						<c:choose>
							<c:when test="${!docShell.mappedDocBookmarks.isEmpty() }">						
							<c:forEach items="${docShell.mappedDocBookmarks}"
								var="mappedDocBookmark">
								<c:if test="${ mappedDocBookmark.docBookmark.id>1}">
									<tr>
										<td><c:out value="${mappedDocBookmark.docBookmark.id} " /></td>

										<td><c:out value="${mappedDocBookmark.docBookmark.markName }" /></td>
										<td><c:out value="${mappedDocBookmark.user.userName}" /></td>
										<td><spring:url value="/docshell/${docShell.id }/r/${mappedDocBookmark.docBookmark.id} "
												var="rMappedDocMarkUrl">
											</spring:url> <a href="${(rMappedDocMarkUrl)}">Remove</a></td>
									</tr>
								</c:if>
							</c:forEach>
							</c:when>
							<c:otherwise> 
							</c:otherwise>
						</c:choose>
						</tbody>
						
					</table>
				</div>
			</div>
		</div>


		</form:form>
	</div>
	<spring:url value="/docshell/${docShell.id }/getTags" var="tagsUrl"></spring:url>

 <script>
  $(document).ready(function() {
 
	$('#selectedMark').autocomplete({
		serviceUrl: '/docshell/${docShell.id}/getTags',
		paramName: "tagName",
		delimiter: ",",
	   transformResult: function(response) {
 
		return {      	
		  //must convert json to javascript object before process
		  suggestions: $.map($.parseJSON(response), function(item) {
 
		      return { value: item.tagName, data: item.id };
		   })
 
		 };
 
            }
 
	 });
 
  });
  </script>
    
</body>
</html>