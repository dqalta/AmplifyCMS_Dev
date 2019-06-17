<!DOCTYPE html>
<html class="fondo2">
    <head>        
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <%@ taglib uri="/struts-tags" prefix="st"%>
        <%@ taglib uri="/struts-bootstrap-tags" prefix="stb"%>
        <st:include value="/generals/css-js-Head.jsp" />
        <st:include value="/generals/css-js-Jquery.jsp"/>
        <st:include value="/generals/css-js-Bootstrap.jsp"/>
        <link rel="stylesheet" type="text/css" href="/MasonryCMS/intro.css">

        <title>Masonry CMS</title>
    </head>
    <body>
        <div id="contents">  
            <st:form id="frm" name="frm" action="login" method="post" cssClass="form-horizontal">
                <div style="text-align: center; color:#666666;">  
                    <div> <br></div>
                    <h3 style="color:white">Masonry Supply<br>C.M.S</h3>                
                </div>
                <br>


                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <st:textfield class="input" cssClass="form-control" name="usuario" id="usuario" placeholder="Ingrese el usuario..."/>
                </div>

                <br>

                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <st:password  class="input" cssClass="form-control" placeholder="Contraseña..." id="contrasena" name="contrasena" />
                </div>

                <br>
                <br>
                <st:submit value="Login"/>
                <div style="text-align: left; color:#e6e6e6;">
                    <st:fielderror />
                    <st:actionerror />
                    <st:actionmessage />            
                </div>
            </st:form> 



        </div>
    </body>


</html>



