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
		<div class="row" id="hello">
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
				<form:form id="acctForm" modelAttribute="acctFlowActions" role="form" class="acctForm">
					<div class="panel panel-default">
						<div class="panel-heading">
						<form:hidden path="uName" name="uName" value="${employee.username}" />
							
						</div>
						<div class="panel-body">
							<table class="table table-condensed"
								style="width: 500px">
								<thead>
									<tr>
										<th></th>
										<th></th>
									</tr>
								</thead>
								<tbody id="userInfo">
									<tr>
										<td>New passcode</td>
										<td><form:input type="password" path="credentials"  class="required" id="credentials" /> <form:errors
												cssClass="error" path="credentials" /></td>
									</tr>
									<tr>
										<td>Repeat</td>
										<td><form:input type="password" path="credentials2"  class="required" id="credentials2"/> <form:errors
												cssClass="error" path="credentials2" /></td>
									</tr>
									<tr>
										<td></td>
										<td><input type="submit" name="submit"
											value="Submit" /> <input type="button"
											name="submit" value="Cancel" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</form:form>
			</div>
			<br />
		</div>


	</div>
	<jsp:include page="../includes/footer.jsp" />
	<script type="text/javascript">

    $(document).ready(function() {
        $("#acctForm").validate({
          rules: {
        	 credentials: { 
                  required: true, minlength: 8
            }, 
            credentials2: { 
                  required: true, equalTo: "#credentials", minlength: 8
            } 
        
          },
          messages: {
        	  credentials: {
                  required: "Please provide a password",
                  minlength: "Your password must be at least 8 characters long"
              },
              credentials2: {
                  required: "Please provide a password confirmation",
                  minlength: "Your password must be at least 8 characters long",
                  equalTo: "Please enter the same password as above"
              }
          }
        });
      });

	</script>
</body>
</html>
