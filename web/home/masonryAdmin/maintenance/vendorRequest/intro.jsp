<%-- 
    Document   : inicio
    Created on : 10-oct-2017, 15:13:01
    Author     : CR108002
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="st"%>
<%@ taglib uri="/struts-jquery-tags" prefix="stj"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="stb"%>
<!DOCTYPE html>
<html>
    <head>    
        <st:include value="/generals/css-js-Jquery.jsp"/>
        <st:include value="/generals/css-js-Head.jsp" />
         <st:include value="/generals/css-js-app.jsp"/>
        <st:include value="/generals/css-js-Bootstrap.jsp"/>
        <st:include value="/generals/css-js-Datatables.jsp"/>
       
        <st:include value="/generals/css-js-LcSwitch.jsp"/>

        <script src="/MasonryCMS/home/masonryAdmin/maintenance/vendorRequest/script.js" type="text/javascript"></script>        
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}">        
            <div class="titulo">VENDOR ACTIVATIONS<hr/></div>
                <st:hidden id="permiso" name="permiso" value="%{permiso}" />
                <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
                <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />
                <st:include value="/generals/navBarHead.jsp" >
                    <st:param name="title">VENDOR ACTIVATIONS</st:param>
            </st:include>
            <st:if test="%{permiso == true}"> 

                <st:form id="formulario" name="formulario" cssClass="form-vertical" action="vendorRequest" method="post" theme="bootstrap">                                        
                    <st:hidden id="accion" name="accion" value="%{accion}"/>
                    <st:hidden id="idEdit" name="idEdit" value="%{idEdit}"/>
                </st:form>   

                <div class="container">
                    <div class="page-header">
                        <h1>Vendor accounts<small> Pending review</small></h1>
                    </div>
                    <div style="padding: 20px;" class="table-responsive">
                        <table id="table_vendorsPending" class="table table-striped" style="width:100%; margin: 0px auto;">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Company Name</th>
                                    <th>Name</th>
                                    <th>Phone Number</th>
                                    <th>Website</th>
                                    <th>City</th>
                                    <th>Email</th>                              
                                </tr>
                            </thead>
                            <tbody>
                                <st:if test="%{!getVendorsPendings().isEmpty()}">
                                    <st:iterator value="VendorsPendings" var="VendorsPendings" status="index">
                                        <tr>
                                            <td><st:property value="%{#VendorsPendings.idVendorRegister}" /></td>           
                                            <td><st:property value="%{#VendorsPendings.companyName}" /></td>        
                                            <td><st:property value="%{#VendorsPendings.vname}" /></td>      
                                            <td><st:property value="%{#VendorsPendings.phoneNumber}" /></td>      
                                            <td><st:property value="%{#VendorsPendings.webSite}" /></td>      
                                            <td><st:property value="%{#VendorsPendings.city}" /></td>      
                                            <td><st:property value="%{#VendorsPendings.email}" /></td>      
                                            <td>                                                           
                                                <button type="button" class="btn btn-info btn-sm"  onclick="activateVendor('<st:property value="%{#VendorsPendings.idVendorRegister}" />');">
                                                    Accept
                                                </button>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-warning btn-sm"  onclick="deleteVendor('<st:property value="%{#VendorsPendings.idVendorRegister}" />');">
                                                    Reject
                                                </button>
                                            </td>       

                                        </tr>
                                    </st:iterator>
                                </st:if>
                                <st:else>
                                    <%response.sendRedirect("/MasonryCMS/");%>
                                </st:else>
                            </tbody>
                        </table>
                    </div>
                      <div style="text-align: left; color:#e6e6e6;">
                        <st:fielderror />
                        <st:actionerror />
                        <st:actionmessage />            
                    </div>
                   
                    <!--Original modal-->
                    <div class="modal" id="ModalProcesando">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title"><i class="glyphicon glyphicon-folder-open"></i>&nbsp;&nbsp;Masonry CMS</h4>
                                </div>
                                <div class="modal-body">
                                    <h4>Processing, Please wait...<i class="glyphicon glyphicon-repeat fast-right-spinner"></i></h4>
                                </div>
                            </div>
                        </div>
                    </div>
                  
                </div>
            </st:if> 
            <st:else>
                <st:include value="/generals/permiso.jsp" />
            </st:else>

            <st:include value="/generals/navBarFooter.jsp" /> 
        </st:if>
        <st:else>
            <%response.sendRedirect("/MasonryCMS/");%>
        </st:else> 

    </body>
</html>