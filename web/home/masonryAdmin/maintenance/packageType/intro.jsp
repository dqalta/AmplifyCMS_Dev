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
        <st:include value="/generals/css-js-LcSwitch.jsp"/>
        <st:include value="/generals/css-js-app.jsp"/>
        <script src="/MasonryCMS/home/masonryAdmin/maintenance/packageType/script.js" type="text/javascript"></script>        
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}"> 

            <st:hidden id="permiso" name="permiso" value="%{permiso}" />
            <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
            <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />

            <st:include value="/generals/navBarHead.jsp" >
                <st:param name="title">PACKAGE TYPES</st:param>
            </st:include>
            <st:if test="%{permiso == true}">              


                <div class="container-fluid">
                    <div class="row">
                        <div class="titulo">Package Type<hr/></div>
                        <div class="panel-group">
                            <div class="panel panel-default">
                                <div class="panel-heading clickable">
                                    <h4 class="panel-title">
                                        <a><i class="glyphicon glyphicon-chevron-down"></i></a>
                                    </h4>
                                </div>
                                <div id="form-panel" class="panel-collapse collapse">
                                    <div class="panel-body">    
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#maintenanceTab" data-toggle="tab" aria-expanded="true">Maintenance-Package Types</a></li>                  
                                        </ul>
                                        <div id="tabsContents" class="tab-content">
                                            <div class="tab-pane fade active in" id="maintenanceTab">    
                                                <div class="container-fluid">
                                                    <st:form id="formulario" name="formulario" cssClass="form-vertical" action="packageType" method="post" theme="bootstrap">                                        
                                                        <st:hidden id="accion" name="accion" value="%{accion}"/>
                                                        <st:hidden id="idEdit" name="idEdit" value="%{idEdit}"/>
                                                        <br>
                                                        <div class="row">
                                                            <div class="col-sm-10">
                                                                <st:textfield label="Description:" name="description" id="description" class="form-control" value="%{description}" placeholder="Description..."/>
                                                            </div>  
                                                            <div class="col-sm-2">
                                                                <div class="form-group">
                                                                    <label for="check_active">Active?</label>
                                                                    <st:hidden id="active" name="active" value="%{active}" />
                                                                    <st:checkbox class="lcc" theme="simple" name="check_active" id="check_active" fieldValue="%{active}" />
                                                                </div>
                                                            </div> 
                                                        </div>
                                                        <br>
                                                    </st:form>   
                                                    <div class="btn-group pull-right">
                                                        <a class="btn btn-danger pull-right" onclick="save();"><i class="glyphicon glyphicon-ok"></i>&nbsp;Save</a>
                                                        <a class="btn btn-default pull-right" onclick="cancel();"><i class="glyphicon glyphicon-remove"></i>&nbsp;Cancel</a>
                                                    </div>  
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div style="padding: 20px;" class="table-responsive">
                            <style>
                                table.dataTable tbody th,
                                table.dataTable tbody td {
                                    white-space: nowrap;
                                }
                                .dataTables_filter,
                                .dataTables_info { 
                                    display: none;
                                }
                            </style>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                                        <input id="fieldSearch" type="text" class="form-control" placeholder="Enter the data to search...">
                                    </div>
                                </div>
                            </div>
                            <table id="table_packageType" class="table table-striped" style="width:100%; margin: 0px auto;">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Description</th>
                                        <th>Created</th>
                                        <th>Created By</th>
                                        <th>Modified</th>
                                        <th>Modified By</th>
                                        <th>Active</th>
                                        <th>Edit</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <st:if test="%{!getPackageTypes().isEmpty()}">
                                        <st:iterator value="PackageTypes" var="PackageTypes" status="index">
                                            <tr>
                                                <td><st:property value="%{#PackageTypes.id}" /></td>           
                                                <td><st:property value="%{#PackageTypes.description}" /></td>        
                                                <td><st:date name="%{#PackageTypes.created}" format="dd/MM/yyyy"/></td>       
                                                <td><st:property value="%{#PackageTypes.createdBy}" /></td>    
                                                <td><st:date name="%{#PackageTypes.modified}" format="dd/MM/yyyy"/></td>    
                                                <td><st:property value="%{#PackageTypes.modifiedBy}" /></td>       
                                                <td>
                                                    <st:if test="%{#PackageTypes.active == true}">
                                                        <i class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>
                                                    </st:if>
                                                    <st:else>
                                                        <i class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                                    </st:else>
                                                </td>       
                                                <td onclick="edit('<st:property value="%{#PackageTypes.id}" />');"><i class="glyphicon glyphicon-pencil" data-toggle="tooltip" data-placement="left" title="" data-original-title="Edit Row"></i></td>
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
            </div>
        </div>




    </body>
</html>
