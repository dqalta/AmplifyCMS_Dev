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
        <st:include value="/generals/css-js-app.jsp"/>
        <st:include value="/generals/css-js-LcSwitch.jsp"/>
        <st:include value="/generals/css-js-Chosen.jsp"/>
        <link rel="stylesheet" type="text/css" href="/MasonryCMS/home/masonryAdmin/maintenance/product/style.css" media="screen" />       
        <script src="/MasonryCMS/home/masonryAdmin/maintenance/product/script.js" type="text/javascript"></script>        
    </head>
    <body style="margin: 0px;">
        <st:if test="%{sesionActiva == true}">     
                <st:hidden id="permiso" name="permiso" value="%{permiso}" />
                <st:hidden id="mensaje" name="mensaje" value="%{mensaje}" />
                <st:hidden id="mensajes" name="mensajes" value="%{mensajes}" />
                <st:include value="/generals/navBarHead.jsp" >
                    <st:param name="title">PRODUCTS</st:param>
                </st:include>
                <st:if test="%{permiso == true}">  

                <div class="container">
                    <div class="row">
                        <section>
                            <div class="wizard">
                                <div class="wizard-inner">
                                    <div class="connecting-line"></div>
                                    <ul class="nav nav-tabs" role="tablist">

                                        <li role="presentation" class="active">
                                            <a href="#step1" data-toggle="tab" data-value="Asiento" aria-controls="step1" role="tab" title="General Information.">
                                                <span class="round-tab">
                                                    <i class="fa fa-pencil"></i>
                                                </span>
                                            </a>
                                        </li>

                                        <li role="presentation" class="disabled">
                                            <a href="#step2" data-toggle="tab" data-value="Mes" aria-controls="step2" role="tab" title="Features.">
                                                <span class="round-tab">
                                                    <i class="fa fa-cogs"></i>
                                                </span>
                                            </a>
                                        </li>
                                        <li role="presentation" class="disabled">
                                            <a href="#step3" data-toggle="tab" data-value="Fecha" aria-controls="step3" role="tab" title="Categories.">
                                                <span class="round-tab">
                                                    <i class="fa fa-sitemap"></i>
                                                </span>
                                            </a>
                                        </li>
                                        <li role="presentation" class="disabled">
                                            <a href="#step4" data-toggle="tab" data-value="Dimensions" aria-controls="step4" role="tab" title="Dimensions.">
                                                <span class="round-tab">
                                                    <i class="fa fa-arrows"></i>
                                                </span>
                                            </a>
                                        </li>
                                        <li role="presentation" class="disabled">
                                            <a href="#step5" data-toggle="tab" data-value="Photos" aria-controls="step5" role="tab" title="Photos.">
                                                <span class="round-tab">
                                                    <i class="fa fa-photo"></i>
                                                </span>
                                            </a>
                                        </li>

                                        <li role="presentation" class="disabled">
                                            <a href="#complete" data-toggle="tab" data-value="Final" aria-controls="complete" role="tab" title="Proceso completo.">
                                                <span class="round-tab">
                                                    <i class="glyphicon glyphicon-ok"></i>
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>


                                <div class="tab-content">
                                    <div class="tab-pane active" role="tabpanel" id="step1">
                                        <h3></h3>
                                        <div class="row">
                                                                                               
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button onclick="cancelar();" type="button" class="btn btn-default"><i class="glyphicon glyphicon-remove"></i> Cancel</button></li>
                                            <li><button type="button" class="btn btn-warning next-step"><i class="glyphicon glyphicon-arrow-right"></i> Next</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step2">
                                        <h3>Seleccione un mes contable</h3>
                                        <div class="row">
                                           
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button type="button" class="btn btn-default prev-step"><i class="glyphicon glyphicon-arrow-left"></i> Anterior</button></li>
                                            <li><button type="button" class="btn btn-primary next-step"><i class="glyphicon glyphicon-arrow-right"></i> Siguiente</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step3">
                                        <h3>Seleccione la fecha que desea asignar</h3>
                                        <div class="row">
                                            
                                        </div>

                                        <ul class="list-inline pull-right">
                                            <li><button type="button" class="btn btn-default prev-step"><i class="glyphicon glyphicon-arrow-left"></i> Anterior</button></li>
                                            <li><button onclick="preguntaCambiarFecha();" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-transfer"></i> Cambiar Fecha</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step4">
                                        <h3></h3>
                                        <div class="row">
                                                                                               
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button onclick="cancelar();" type="button" class="btn btn-default"><i class="glyphicon glyphicon-remove"></i> Cancel</button></li>
                                            <li><button type="button" class="btn btn-warning next-step"><i class="glyphicon glyphicon-arrow-right"></i> Next</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="step5">
                                        <h3></h3>
                                        <div class="row">
                                                                                               
                                        </div>
                                        <ul class="list-inline pull-right">
                                            <li><button onclick="cancelar();" type="button" class="btn btn-default"><i class="glyphicon glyphicon-remove"></i> Cancel</button></li>
                                            <li><button type="button" class="btn btn-warning next-step"><i class="glyphicon glyphicon-arrow-right"></i> Next</button></li>
                                        </ul>
                                    </div>
                                    <div class="tab-pane" role="tabpanel" id="complete">
                                        <h3>Proceso exitoso</h3>
                                        <p>Se ha cambiado la fecha con &eacute;xito</p>
                                        <button onclick="cancelar();" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i> Listo</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>

<%--

                <div style="padding: 20px;" class="table-responsive">
                    <table id="table_product" class="table table-striped" style="width:100%; margin: 0px auto;">
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
                               <st:if test="%{!getProducts().isEmpty()}">
                                   <st:iterator value="products" var="products" status="index">
                                       <tr>
                                           <td><st:property value="%{#products.id}" /></td>           
                                           <td><st:property value="%{#products.pname}" /></td>        
                                           <td><st:date name="%{#products.created}" format="dd/MM/yyyy"/></td>       
                                           <td><st:property value="%{#products.createdBy}" /></td>    
                                           <td><st:date name="%{#products.modified}" format="dd/MM/yyyy"/></td>    
                                           <td><st:property value="%{#products.modifiedBy}" /></td>       
                                           <td>
                                               <st:if test="%{#products.active == true}">
                                                   <i class="glyphicon glyphicon-off text-success" data-toggle="tooltip" data-placement="top" title="" data-original-title="Active"</i>
                                               </st:if>
                                               <st:else>
                                                   <i class="glyphicon glyphicon-off text-danger"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Inactive"></i>
                                               </st:else>
                                           </td>       
                                           <td onclick="edit('<st:property value="%{#products.id}" />');"><i class="glyphicon glyphicon-pencil" data-toggle="tooltip" data-placement="left" title="" data-original-title="Edit Row"></i></td>
                                       </tr>
                                   </st:iterator>
                               </st:if>
                        </tbody>
                    </table>
                </div>
--%>

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