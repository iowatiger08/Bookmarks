<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

  <spring:url value="/resources/js/jquery-2.1.1.min.js" var="jQuery"/>
   <script src="${jQuery}"></script>
     <spring:url value="/resources/js/jquery.validate.min.js" var="jQueryValidate"/>
   <script src="${jQueryValidate}"></script>
     <spring:url value="/resources/js/jquery-ui.js" var="jQueryUI"/>
   <script src="${jQueryUI}"></script>   
     <spring:url value="/resources/js/bootstrap.min.js" var="bootstrap"/>
   <script src="${bootstrap}"></script>
        <spring:url value="/resources/css/bootstramp-theme.min.css" var="bootstrapThemeCss"/>
    <link href="${bootstrapThemeCss}" rel="stylesheet"/>  
     <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>  
     <spring:url value="/resources/css/base.css" var="baseCss"/>
    <link href="${baseCss}" rel="stylesheet"/>  
    
    
 