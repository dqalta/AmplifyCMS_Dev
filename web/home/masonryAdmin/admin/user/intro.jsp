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
        <st:include value="/generals/css-js-Head.jsp" />
        <st:include value="/generals/css-js-Bootstrap.jsp"/>
        <st:include value="/generals/css-js-Datatables.jsp"/>
        <st:include value="/generals/css-js-Chosen.jsp"/>
        <st:include value="/generals/css-js-app.jsp"/>
        <st:include value="/generals/css-js-Icheck.jsp"/>
        <st:include value="/generals/css-js-LcSwitch.jsp"/>

        <script src="/MasonryCMS/home/masonryAdmin/admin/user/script.js" type="text/javascript"></script>        
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}">        
            <st:hidden id="permiso" name="permiso" value="%{permiso}" />
            <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
            <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />
            <st:include value="/generals/navBarHead.jsp" >
                <st:param name="title">USERS</st:param>
            </st:include>
            <br>
            <br>
            <br>
            <st:if test="%{permiso == true}"> 

                <st:form id="formulario" name="formulario" cssClass="form-vertical" action="user" method="post" theme="bootstrap">                                        
                    <st:hidden id="accion" name="accion" value="%{accion}"/>
                    <st:hidden id="idEdit" name="idEdit" value="%{idEdit}"/>
                    <div class="panel-group">

                        <div class="panel panel-default">
                            <div class="panel-heading clickable">
                                <h4 class="panel-title">
                                    <a><i class="glyphicon glyphicon-chevron-down"></i></a>
                                </h4>
                            </div>
                            <div id="form-panel" class="panel-collapse collapse">
                                <div class="panel-body">                                    
                                    <div id="tabsContents" class="tab-content">
                                        <div class="tab-pane fade active in" id="maintenanceTab">    
                                            <div class="container-fluid">
                                                <br>
                                                <div class="row">                                   
                                                    <div class="col-sm-2">
                                                        <st:textfield label="Code:" name="code" id="code" class="form-control disabled" readOnly="true" placeholder="Code..."/>
                                                    </div>                                   
                                                    <div class="col-sm-4">
                                                        <st:textfield label="Full Name:" name="fullName" id="fullName" class="form-control" placeholder="Full Name..."/>
                                                    </div>                                  
                                                    <div class="col-sm-4">
                                                        <st:textfield label="Email:" name="email" id="email" class="form-control" placeholder="Email..."/>
                                                    </div> 
                                                    <div class="col-sm-2">
                                                        <div class="form-group">
                                                            <label for="check_active">Active?</label>
                                                            <st:hidden id="active" name="active" value="%{active}" />
                                                            <st:checkbox class="lcc" theme="simple" name="check_active" id="check_active" fieldValue="%{active}" />
                                                        </div>
                                                    </div>   
                                                </div>

                                                <div class="row">
                                                    <div class="col-sm-2"> 
                                                        <label><strong>Modules:</strong></label>
                                                    </div>   
                                                </div>
                                                <hr>  
                                                <div class="row">
                                                    <div class="col-sm-2"> 
                                                        <label><st:checkbox theme="simple" class="check_" value="%{menuAdmin}" id="menuAdmin" name="menuAdmin"/>&nbsp;Administration</label>
                                                    </div>    
                                                    <div class="col-sm-3"> 
                                                        <label><st:checkbox theme="simple" class="check_" value="%{menuProdAdmin}" id="menuProdAdmin" name="menuProdAdmin"/>&nbsp;Product Administration</label>
                                                    </div>  
                                                    <div class="col-sm-3"> 
                                                        <label><st:checkbox theme="simple" class="check_" value="%{menuProdComp}" id="menuProdComp" name="menuProdComp"/>&nbsp;Product Components</label>
                                                    </div>     
                                                </div> 
                                                <br>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <label for="rol">Roles:</label>
                                                            <st:select class="form-control" id="rol" multiple="true" name="rol" value="%{rol}" list="roles" listKey="id" listValue="description"/> 
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>

                                                <div class="btn-group pull-right">
                                                    <a class="btn btn-warning" onclick="save();"><i class="glyphicon glyphicon-ok"></i>&nbsp;Save</a>
                                                    <a class="btn btn-default" onclick="cancel();"><i class="glyphicon glyphicon-remove"></i>&nbsp;Cancel</a>
                                                </div>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </st:form>  

                <div style="padding: 20px;" class="table-responsive">
                    <table id="table_users" class="table table-striped" style="width:100%; margin: 0px auto;">
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Email</th>
                                <th>NickName</th>
                                <th>Created</th>
                                <th>Created By</th>
                                <th>Modified</th>
                                <th>Modified By</th>
                                <th>&nbsp;</th>
                                <th>Edit</th>
                            </tr>
                        </thead>
                        <tbody>
                            <st:if test="%{!getUsers().isEmpty()}">
                                <st:iterator value="users" var="users" status="index">
                                    <tr>
                                        <td><st:property value="%{#users.code}" /></td>   
                                        <td><st:property value="%{#users.email}" /></td>           
                                        <td><st:property value="%{#users.nickname}" /></td>        
                                        <td><st:date name="%{#users.created}" format="dd/MM/yyyy"/></td>       
                                        <td><st:property value="%{#users.createdBy}" /></td>    
                                        <td><st:date name="%{#users.modified}" format="dd/MM/yyyy"/></td>    
                                        <td><st:property value="%{#users.modifiedBy}" /></td>
                                        <td>
                                            <st:if test="%{#users.active == true}">
                                                <i onclick="activeUser('<st:property value="%{#users.id}" />');" class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>                                                                                            
                                            </st:if>
                                            <st:else>
                                                <i onclick="activeUser('<st:property value="%{#users.id}" />');" class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                            </st:else>
                                        </td>
                                        <td onclick="edit('<st:property value="%{#users.id}" />');"><i class="glyphicon glyphicon-pencil" data-toggle="tooltip" data-placement="left" title="" data-original-title="Edit Row"></i></td>
                                    </tr>
                                </st:iterator>
                            </st:if> 
                        </tbody>
                    </table>
                </div>

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