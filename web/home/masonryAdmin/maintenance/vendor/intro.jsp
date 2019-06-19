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
        <st:include value="/generals/css-js-Chosen.jsp"/>
        <script src="/MasonryCMS/home/masonryAdmin/maintenance/vendor/script.js" type="text/javascript"></script>        
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}"> 

            <st:hidden id="permiso" name="permiso" value="%{permiso}" />
            <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
            <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />

            <st:include value="/generals/navBarHead.jsp" >
                <st:param name="title">Vendors</st:param>
            </st:include>
            <st:if test="%{permiso == true}">              


                <div class="container-fluid">
                    <div class="row">
                        <div class="titulo">Vendors<hr/></div>
                        <div class="panel-group">
                            <div class="panel panel-default">
                                <div class="panel-heading clickable">
                                    <h4 class="panel-title">
                                        <a><i class="glyphicon glyphicon-chevron-down"></i></a>
                                    </h4>
                                </div>
                                <div id="form-panel" class="panel-collapse collapse">
                                    <div class="panel-body">   
                                        <st:form id="formulario" name="formulario" cssClass="form-vertical" action="vendor" method="post" theme="bootstrap">
                                            <st:hidden id="accion" name="accion" value="%{accion}"/>
                                            <st:hidden id="idEdit" name="idEdit" value="%{idEdit}"/>
                                            <st:hidden id="idContact" name="idContact" value="%{idContact}"/>
                                            <ul class="nav nav-tabs"> <%--block tabs without previous data entry--%>
                                                <li class="active"><a data-toggle="tab" href="#maintenanceTab"  aria-expanded="true">General information</a></li>    
                                                    <st:if test="%{existVendor == true}"> 
                                                    <li><a data-toggle="tab" href="#maintenanceTab1" aria-expanded="true">Contact Details</a></li>  
                                                    <li><a data-toggle="tab" href="#maintenanceTab2"  aria-expanded="true">Address</a></li>                                          
                                                    </st:if>
                                                    <st:else>
                                                    <li class="disabled"><a>Contact Details</a></li> 
                                                    <li class="disabled"><a>Address</a></li>
                                                    </st:else> 
                                            </ul>
                                            <div id="tabsContents" class="tab-content">
                                                <div class="tab-pane fade active in" id="maintenanceTab">    
                                                    <div class="container-fluid">
                                                        <br>
                                                        <div class="row">

                                                            <st:if test="%{existVendor == true}"> 
                                                                <div class="col-sm-3">
                                                                    <st:textfield label="Vendor ID" name="id" id="id" class="form-control disabled" value="%{id}" placeholder="Vendors id.." readonly="true"/>
                                                                </div>
                                                            </st:if>                                                                        


                                                            <div class="col-sm-3">
                                                                <st:textfield label="Vendor Name" name="vname" id="vname" class="form-control" value="%{vname}" placeholder="Vendors Name..."/>
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

                                                        <div class="btn-group pull-right">
                                                            <a class="btn btn-danger pull-right" onclick="save();"><i class="glyphicon glyphicon-ok"></i>&nbsp;Save</a>
                                                            <a class="btn btn-default pull-right" onclick="cancel();"><i class="glyphicon glyphicon-remove"></i>&nbsp;Cancel</a>
                                                        </div>  
                                                    </div>
                                                </div>
                                                                    <%--Tab for information about vendors --%>
                                                <div class="tab-pane fade" id="maintenanceTab1">    
                                                    <div class="container-fluid">                        
                                                        <br>
                                                        <div class="row">
                                                            <div class="col-sm-3">
                                                                <st:textfield label="Description" name="description" id="description" class="form-control" value="%{description}" placeholder="Contact detail.."/>
                                                            </div>                                                 
                                                                <div class="col-sm-3">                                                      
                                                                <div class="form-group">
                                                                    <label for=type">Type:</label>
                                                                    <st:select class="form-control"  id="type" name="type" value="%{type}" list="types" listKey="description" listValue="description"/>                                          
                                                                </div>
                                                            </div>
                                                          
                                                            <div class="col-sm-1">
                                                                <div class="form-group" data-toggle="tooltip" data-placement="rigth" title="" data-original-title="Add Contact" > 
                                                                    <label class="control-label" for="addDiv">Add:</label> 
                                                                    <div id="addDiv"> 
                                                                        <a class="btn btn-danger" onclick="saveContact();">
                                                                            <i class="glyphicon glyphicon-plus"></i></a>
                                                                    </div> 
                                                                </div> 
                                                            </div> 
                                                        </div>
                                                        <br>
                                                        <div class="row">
                                                            <div class="col-sm-7">
                                                                <table id="tableContact" class="table table-striped" style="width:100%; margin: 0px auto;">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>Description</th>
                                                                            <th>Type</th>
                                                                            <th>&nbsp;</th>
                                                                            <th>&nbsp;</th>                              
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <st:if test="%{!getVendorsContacts().isEmpty()}">
                                                                            <st:iterator value="vendorsContacts" var="vendorsContacts" status="index">
                                                                                <tr>
                                                                                    <td><st:property value="%{#vendorsContacts.description}" /></td>   
                                                                                    <td><st:property value="%{#vendorsContacts.type}" /></td>                                        
                                                                                    <td>
                                                                                        <st:if test="%{#vendorsContacts.active == true}">
                                                                                            <i onclick="activeContact('<st:property value="%{#vendorsContacts.id}" />');" class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>                                                                                            
                                                                                        </st:if>
                                                                                        <st:else>
                                                                                            <i onclick="activeContact('<st:property value="%{#vendorsContacts.id}" />');" class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                                                                        </st:else>
                                                                                    </td>
                                                                                     <td>
                                                                                        <i data-toggle="tooltip" data-placement="top" title="" data-original-title="Delete Contact" onclick="deleteContact('<st:property value="%{#vendorsContacts.id}" />');" class="pull-right glyphicon glyphicon-remove"></i></td>                                                                                                           
                                                                                </tr>
                                                                            </st:iterator>
                                                                        </st:if> 
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>

<!--                                                Tab for vendor address details-->

                                                <div class="tab-pane fade" id="maintenanceTab2">    
                                                    <div class="container-fluid">

                                                        <br>
                                                        <div class="row">
                                                            <div class="col-sm-3">
                                                                <div class="form-group">
                                                                    <label for="province">Province:</label>
                                                                    <st:select class="form-control"  id="province" name="province" value="%{province}" list="provincePostalCodes" listKey="description" listValue="description" onchange="chargeCities();"/>
                                                                </div> 
                                                            </div> 
                                                                <div class="col-sm-3">
                                                                <div class="form-group">
                                                                    <label for="city">City:</label>
                                                                       <st:select class="form-control"  id="city" name="city" value="%{city}" list="cities" listKey="description" listValue="description" onchange="chargePostalCodes();"/>
                                                                 </div> 
                                                                </div> 
                                                                 <div class="col-sm-3">                                                           
                                                                 <div class="form-group">                                                                
                                                                    <label for="postalCode">Postal Code</label>
                                                                     <st:select class="form-control"  id="postalCode" name="postalCode" value="%{postalCode}" list="postalCodes" listKey="description" listValue="description"/>
                                                                 </div>
                                                                 </div>
                                                                  <div class="col-sm-3">
                                                                  <st:textfield label="Address" name="description" id="description" class="form-control" value="%{description}" placeholder="Complete the vendor address here.."/>
                                                                  </div>                                                 
                                                           
                                                            </div>
                                                            <br>

                                                            <div class="btn-group pull-right">
                                                                <a class="btn btn-danger pull-right" onclick="saveAddress();"><i class="glyphicon glyphicon-ok"></i>&nbsp;Save</a>
                                                                <a class="btn btn-defaqult pull-right" onclick="cancel();"><i class="glyphicon glyphicon-remove"></i>&nbsp;Cancel</a>
                                                            </div> 
                                                                  <div class="row">
                                                            <div class="col-sm-7">
                                                                <table id="tableAddress" class="table table-striped" style="width:100%; margin: 0px auto;">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>Postal Code</th>
                                                                            <th>Address</th>
                                                                            <th>&nbsp;</th>
                                                                            <th>&nbsp;</th>                              
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <st:if test="%{!getVendorsAddress().isEmpty()}">
                                                                            <st:iterator value="vendorsContacts" var="vendorsContacts" status="index">
                                                                                <tr>
                                                                                    <td><st:property value="%{#vendorsContacts.description}" /></td>   
                                                                                    <td><st:property value="%{#vendorsContacts.type}" /></td>                                        
                                                                                    <td>
                                                                                        <st:if test="%{#vendorsContacts.active == true}">
                                                                                            <i onclick="activeContact('<st:property value="%{#vendorsContacts.id}" />');" class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>                                                                                            
                                                                                        </st:if>
                                                                                        <st:else>
                                                                                            <i onclick="activeContact('<st:property value="%{#vendorsContacts.id}" />');" class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                                                                        </st:else>
                                                                                    </td>
                                                                                     <td>
                                                                                        <i data-toggle="tooltip" data-placement="top" title="" data-original-title="Delete Contact" onclick="deleteContact('<st:property value="%{#vendorsContacts.id}" />');" class="pull-right glyphicon glyphicon-remove"></i></td>                                                                                                           
                                                                                </tr>
                                                                            </st:iterator>
                                                                        </st:if> 
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                         </div>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                        </st:form>
                                    </div>
                                </div>

                            </div>


                            <div style="padding: 20px;" class="table-responsive">


                                <table id="table_vendor" class="table table-striped" style="width:100%; margin: 0px auto;">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Vendor Name</th>
                                            <th>Created</th>
                                            <th>Created By</th>
                                            <th>Modified</th>
                                            <th>Modified By</th>
                                            <th>Active</th>
                                            <th>Edit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <st:if test="%{!getVendors().isEmpty()}">
                                            <st:iterator value="Vendors" var="Vendors" status="index">
                                                <tr>
                                                    <td><st:property value="%{#Vendors.id}" /></td>           
                                                    <td><st:property value="%{#Vendors.vname}" /></td>        
                                                    <td><st:date name="%{#Vendors.created}" format="dd/MM/yyyy"/></td>       
                                                    <td><st:property value="%{#Vendors.createdBy}" /></td>    
                                                    <td><st:date name="%{#Vendors.modified}" format="dd/MM/yyyy"/></td>    
                                                    <td><st:property value="%{#Vendors.modifiedBy}" /></td>       
                                                    <td>
                                                        <st:if test="%{#Vendors.active == true}">
                                                            <i class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>
                                                        </st:if>
                                                        <st:else>
                                                            <i class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                                        </st:else>
                                                    </td>       
                                                    <td onclick="edit('<st:property value="%{#Vendors.id}" />');"><i class="glyphicon glyphicon-pencil" data-toggle="tooltip" data-placement="left" title="" data-original-title="Edit Row"></i></td>
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
