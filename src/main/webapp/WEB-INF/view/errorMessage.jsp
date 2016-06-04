<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/taglibInclude.jsp" %>

<div>
	<h1>An Error Has Occurred</h1>

		<h2>State Exception</h2>
		<c:out value="${exception}" />
		
		<h2>Root Cause</h2>
		<c:out value="${error}" />
</div>