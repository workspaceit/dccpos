<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute fullName="header" fragment="true" %>
<%@attribute fullName="footer" fragment="true" %>

























<html>
<body>
<div id="pageheader">
    <jsp:include page="/WEB-INF/views/home2.jsp"/>
</div>
<div id="body">
    <jsp:doBody/>
</div>
<div id="pagefooter">
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>