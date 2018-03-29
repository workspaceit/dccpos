<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%--<%@attribute fullName="header" fragment="true" %>--%>
<%--<%@attribute fullName="footer" fragment="true" %>--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="d" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta fullName="viewport" content="width=device-width, initial-scale=1">
    <meta fullName="description" content="">
    <meta fullName="author" content="">
    <title>Picture me Clubbing</title>
    <!-- Bootstrap Core CSS -->
    <link href="<s:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<s:url value="/resources/css/sb-admin.css"/>" rel="stylesheet">
    <!-- Morris Charts CSS -->
    <link href="<s:url value="/resources/css/plugins/morris.css"/>" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<s:url value="/resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Exo:300,400,500" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="<s:url value="/resources/js/jquery.js"/>"></script>
</head>
<body>
<div>
    <jsp:doBody/>
</div>
</body>
</html>