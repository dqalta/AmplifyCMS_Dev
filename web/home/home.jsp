
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
        <link rel="icon" type="image/png" href="/MasonryCMS/home/img/favicon.ico" />
        <style type="text/css">
            * {
                box-sizing: border-box;
            }
            img {
                width: 50%;
                height: auto;                   
                display:block;
                margin: 200px auto;
            }


        </style>
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}">     
            <st:include value="/generals/navBarHead.jsp" >
                <st:param name="title">HOME</st:param>
            </st:include>
            <st:include value="/generals/navBarFooter.jsp" />  
        </st:if>
        <st:else>
            <%response.sendRedirect("/MasonryCMS/");%>
        </st:else> 
        <img src="/MasonryCMS/home/img/logo.png">
    </body>
</html>





