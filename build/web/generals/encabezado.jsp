<%-- 
   Document   : encabezado
   Created on : 10-oct-2017, 15:41:33
   Author     : CR108002
--%>  

<%@ taglib uri="/struts-tags" prefix="st"%>  
<title>Admin Area:Amplify Masonry Store</title>
<link rel="shortcut icon" type="image/png" href="/MasonryCMS/home/img/icon.png"/>
<st:set var="titulo_modulo">${param.titulo_modulo}</st:set>
    <div>
        <div style="height: 70px; background-image: url('/MasonryCMS/home/img/logo.png'); background-repeat: no-repeat;">
            <div class="derecha titulo_app" style="margin-left: 270px;">MDK - <st:property value="%{titulo_modulo}" /></div>
        <div class="derecha logo_app">
            <a href="http://www.masonrydesk.com" target="_blank"><img src="/MasonryCMS/home/img/logo.png" alt="Logo" title="Logo" border="0" width="180px" height="120px" /></a><br/>
            <span style="color: #535353;" class="tituloBienvenido"><i class="glyphicon glyphicon-user"></i>&nbsp;<st:property value="#attr.user" />&nbsp;-&nbsp;<st:property value="#attr.userName" /></span>
        </div>
    </div>
</div>