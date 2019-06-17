<%-- 
    Document   : error
    Created on : 12-sep-2017, 13:53:10
    Author     : CR108002
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="st"%>
<%@ taglib uri="/struts-jquery-tags" prefix="jq"%>


<h3>Exception Name: </h3>
<st:property value="exception"/>

<hr> 

<h3>Exception Stack: </h3>

<st:property value="exceptionStack"/>
