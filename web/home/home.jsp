
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="st"%>
<%@ taglib uri="/struts-jquery-tags" prefix="stj"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="stb"%>
<!DOCTYPE html>
<html>
    <head> 
        <st:include value="/generals/css-js-Head.jsp" />
        <st:include value="/generals/css-js-Bootstrap.jsp"/>
        <st:include value="/generals/css-js-app.jsp"/>
    </head>
    <body style="margin: 0px;">

        <st:if test="%{sesionActiva == true}">     
            <st:include value="/generals/navBarHead.jsp" /> 
            <st:include value="/generals/navBarFooter.jsp" />  
        </st:if>
        <st:else>
            <%response.sendRedirect("/MasonryCMS/");%>
        </st:else> 
        
    </body>
</html>





