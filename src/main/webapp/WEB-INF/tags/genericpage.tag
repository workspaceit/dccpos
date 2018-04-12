<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%--<%@attribute fullName="header" fragment="true" %>--%>
<%@attribute fullName="footer" fragment="true" %>
<%@attribute fullName="developerScript" fragment="true" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="d" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta fullName="viewport" content="width=device-width, initial-scale=1">
        <meta fullName="description" content="">
        <meta fullName="author" content="">
        <title>Picture Me Clubbing</title>
        <!-- Bootstrap Core CSS -->
        <link href="<s:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="<s:url value="/resources/css/sb-resetendpoint.css"/>" rel="stylesheet">
        <!-- Morris Charts CSS -->
        <link href="<s:url value="/resources/css/plugins/morris.css"/>" rel="stylesheet">

        <!-- Select2 css -->
        <link href="<s:url value="/resources/css/select2.css"/>" rel="stylesheet"/>
        <link href="<s:url value="/resources/css/daterangepicker.css"/>" rel="stylesheet"/>
        <link href="<s:url value="/resources/css/jquery.dataTables.min.css"/>" rel="stylesheet"/>
        <link href="<s:url value="/resources/css/jquery.growl.css"/>" rel="stylesheet"/>
        <!-- Custom Fonts -->
        <link href="<s:url value="/resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
        <link href="<s:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Exo:300,400,500" rel="stylesheet">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script src="<s:url value="/resources/js/jquery.js"/>"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="<s:url value="/resources/js/bootstrap.min.js"/>"></script>
        <script src="<s:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
        <script src="<s:url value="/resources/js/jquery.growl.js"/>"></script>

        <%--Developer's custom js--%>
        <script>
            var BASEURL = "<c:url value="/" />";
            try{
                BASEURL = BASEURL.split(";")[0];
            }catch(ex){
                console.log(ex);
                BASEURL = "<c:url value="/" />";
            }
        </script>
        <script src="<s:url value="/resources/developer/js/ErrorMessaging.js"/>"></script>
        <script src="<s:url value="/resources/developer/js/helper/navigation.js"/>"></script>
        <script src="<s:url value="/resources/developer/js/helper/others.js"/>"></script>
        <script src="<s:url value="/resources/developer/js/util/notification-util.js"/>"></script>
        <script src="<s:url value="/resources/developer/js/helper/file.helper.js"/>"></script>

        <jsp:invoke fragment="developerScript"/>

    </head>
    <body>
        <jsp:include page="/WEB-INF/views/admin/layout/navbar.jsp"/>
        <div id="body">
            <jsp:doBody/>
        </div>
        <jsp:include page="/WEB-INF/views/admin/layout/footer.jsp"/>
        <div id="pagefooter">
            <jsp:invoke fragment="footer"/>
        </div>
    </body>
</html>