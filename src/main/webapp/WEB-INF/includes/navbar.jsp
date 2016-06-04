<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:url value="/home" var="homeUrl"></spring:url> 
<spring:url value="/acct" var="acctUrl"></spring:url> 
<spring:url value="/list" var="reportUrl"></spring:url> 
<spring:url value="/login?logout=true" var="logoutUrl"></spring:url> 
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">IPERS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="${(homeUrl)}">Home</a></li>

            <li><a href="${(reportUrl)}">Report</a></li>
            <li><a href="${(logoutUrl)}">Logout</a></li>
 
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>